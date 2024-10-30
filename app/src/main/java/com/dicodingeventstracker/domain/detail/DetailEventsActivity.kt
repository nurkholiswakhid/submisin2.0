package com.dicodingeventstracker.domain.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup
import com.dicodingeventstracker.R
import com.dicodingeventstracker.data.local.room.EventEntity
import com.dicodingeventstracker.data.remoteUtils.RemoteResponse
import com.dicodingeventstracker.databinding.ActivityDetailEventsBinding
import com.dicodingeventstracker.domain.entity.Events
import com.dicodingeventstracker.domain.viewmodels.DetailEventViewModel
import com.dicodingeventstracker.utils.animateLoadingProcessData
import com.dicodingeventstracker.utils.getStringDate

@AndroidEntryPoint
class DetailEventsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventsBinding
    private val navArgs:DetailEventsActivityArgs by navArgs()
    private val viewmodel: DetailEventViewModel by viewModels ()
    private var idEvent:Int=0
    private var detailEvent: Events?=null
    private var linkEvent=""
    private var isStatusFavorited= false
    private var savedEventId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventsBinding.inflate(layoutInflater)
        idEvent = navArgs.eventDetails.id!!
        
        setContentView(binding.root)
        if (savedInstanceState === null) {
            viewmodel.setValueId(idEvent)
        }

        setViewDetailOfEvents()
        checkSavedEvents()
    }

    private fun checkSavedEvents() {
        viewmodel.readToCheckEvents.observe(this){ favoriteEventsEntity ->
            try {
                for (savedEvent in favoriteEventsEntity){
                    if (savedEvent.events.id == navArgs.eventDetails.id){
                        binding.fabFavoriteEvent.imageTintList = getColorStateList(R.color.yellow)
                        savedEventId = savedEvent.idNo
                        isStatusFavorited = true
                    }
                }
            }catch (e: Exception) {
                Log.d("DetailEventsActivity", e.message.toString())
            }
        }
    }

    private fun setViewDetailOfEvents() {
        viewmodel.eventDetail.observe(this, detailObserver)
    }

    private val detailObserver = Observer<RemoteResponse<Events?>?> { response ->
        when(response){
            is RemoteResponse.Loading -> {
                applyLoadProgressStateDetail(true)
            }
            is RemoteResponse.Success -> {
                applyLoadProgressStateDetail(false)
                detailEvent = response.data
                Picasso
                    .get()
                    .load(detailEvent?.imageLogo)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.error_placeholder)
                    .into(binding.imgBackDrop)
                linkEvent = detailEvent?.link.toString()
                setUpContentDetail(detailEvent)
                binding.detailEventMain.btnRegisterEvent.isEnabled = true
                setupFunctionalLinkDetailEvent(linkEvent)

            }
            is RemoteResponse.Error -> {
                applyLoadProgressStateDetail(false)
                binding.detailEventMain.btnRegisterEvent.isEnabled = false
                Toast.makeText(this,response.errorMessage, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun setupFunctionalLinkDetailEvent(linkEvent: String) {
        binding.detailEventMain.btnRegisterEvent.setOnClickListener {
            val toLinkPage =Intent(this,RegisterEventActivity::class.java)
            toLinkPage.putExtra(REGISTER_EVENT,linkEvent)
            startActivity(toLinkPage)
            finish()
        }
    }


    private fun setUpContentDetail(detailEvent: Events?) {
        Picasso
            .get()
            .load(detailEvent?.imageLogo)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.error_placeholder)
            .into(binding.detailEventMain.imgLogoEvent)
        binding.detailEventMain.txtKindOfEvent.text = detailEvent?.category
        binding.detailEventMain.txtTitleEventDetail.text=detailEvent?.name
        binding.detailEventMain.txtHostedBy.text=getString(R.string.hosted_by,detailEvent?.ownerName)
        binding.detailEventMain.tvStartTime.text = getStringDate(detailEvent?.beginTime)
        binding.detailEventMain.tvEndTime.text = getStringDate(detailEvent?.endTime)

        if (detailEvent?.cityName.equals("Online")){
            binding.detailEventMain.tvLocation.text=
                getString(R.string.live_at_youtube_dicoding_indonesia, detailEvent?.cityName)
        }else{
            binding.detailEventMain.tvLocation.text= detailEvent?.cityName
        }

        val quota = detailEvent?.quota
        val registrants = detailEvent?.registrants

        val remainingParticipants = registrants?.let { quota?.minus(it) }
        val convertStringRemainParticipantNumber = remainingParticipants.toString()
        binding.detailEventMain.tvRemainingQuotaParticipants.text = "$convertStringRemainParticipantNumber " +getString(
            R.string.participants
        )
        binding.detailEventMain.tvSummaryEvent.text = detailEvent?.summary

        binding.detailEventMain.tvDescription.text = Jsoup.parse(detailEvent?.description).text()

        binding.fabFavoriteEvent.isEnabled=true
        binding.fabFavoriteEvent.setOnClickListener {
            setFavoriteEventStatus(!isStatusFavorited)
        }
    }

    private fun setFavoriteEventStatus(statusFavourite: Boolean) {

        if (statusFavourite){
            val eventEntity = EventEntity(0,navArgs.eventDetails)
            binding.fabFavoriteEvent.imageTintList = getColorStateList(R.color.yellow)
            viewmodel.actionInsertFavEvent(eventEntity)
            showSnackBar(getString(R.string.event_saved))
            isStatusFavorited = true
        }else{
            val eventEntity = EventEntity(savedEventId,navArgs.eventDetails)
            binding.fabFavoriteEvent.imageTintList = getColorStateList(R.color.white)
            viewmodel.actionDeleteFavEvent(eventEntity)
            showSnackBar(getString(R.string.removed_from_favorites))
            isStatusFavorited = false
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.mainDetail,message, Snackbar.LENGTH_SHORT)
            .setAction("Ok"){}
            .show()
    }


    private fun applyLoadProgressStateDetail(onProcess:Boolean){

        binding.detailEventMain.btnRegisterEvent.isEnabled = !onProcess

        if (onProcess){
            binding.maskedViewPgDetail.animateLoadingProcessData(true)
        }else{
            binding.maskedViewPgDetail.animateLoadingProcessData(false)
        }
    }

    override fun onResume() {
        super.onResume()
        setViewDetailOfEvents()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object{
        const val REGISTER_EVENT="register_event"
    }
}