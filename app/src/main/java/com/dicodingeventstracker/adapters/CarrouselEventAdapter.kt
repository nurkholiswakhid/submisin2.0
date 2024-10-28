package com.dicodingeventstracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicodingeventstracker.R
import com.dicodingeventstracker.databinding.ItemEventCarrouselBinding
import com.squareup.picasso.Picasso
import com.dicodingeventstracker.domain.entity.Events
import com.dicodingeventstracker.utils.EventDiffUtil


class CarrouselEventsAdapter (private val mContext: Context): RecyclerView.Adapter<CarrouselEventsAdapter.CarrouselEventsHolder>() {

    private var events = emptyList<Events>()
    private var onClickListener: OnEventClickListener?= null

    class CarrouselEventsHolder (view: ItemEventCarrouselBinding): RecyclerView.ViewHolder(view.root){
        val txtTitleEvent = view.txtTitleNameEvent
        val imgPromoteEvent = view.imgEvent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrouselEventsHolder {
        val bindingView = ItemEventCarrouselBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CarrouselEventsHolder(bindingView)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setOnClickListener(onClickListener: OnEventClickListener) {
        this.onClickListener = onClickListener
    }


    interface OnEventClickListener {
        fun onClick(position: Int, event: Events)
    }

    override fun onBindViewHolder(holder: CarrouselEventsHolder, position: Int) {
        val eventData = events[position]
        holder.txtTitleEvent.text = eventData.name
        Picasso.get()
            .load(eventData.imageLogo)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.error_placeholder)
            .into(holder.imgPromoteEvent)

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position,eventData)
        }
    }



    fun setData(eventData: List<Events>){
        val dataDiffUtil = EventDiffUtil(events,eventData)
        val diffUtilResult = DiffUtil.calculateDiff(dataDiffUtil)
        events = eventData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}