package com.dicodingeventstracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.dicodingeventstracker.R
import com.dicodingeventstracker.adapters.EventsAdapter.EventsHolder
import com.dicodingeventstracker.adapters.EventsAdapter.OnEventClickListener
import com.dicodingeventstracker.data.local.room.EventEntity
import com.dicodingeventstracker.databinding.ItemEventBinding
import com.dicodingeventstracker.domain.entity.Events

class FavoriteEventsAdapter: RecyclerView.Adapter<FavoriteEventsAdapter.FavoriteEventsHolder>() {

    private var favList = ArrayList<EventEntity>()
    private var onFavClickListener: OnEventFavoriteClickListener?= null

    class FavoriteEventsHolder (view: ItemEventBinding): RecyclerView.ViewHolder(view.root){
        val txtTitleEvent = view.txtTitleNameEvent
        val imgPromoteEvent = view.imgEvent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventsHolder {
        val bindingView = ItemEventBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteEventsHolder(bindingView)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavoriteEventsHolder, position: Int) {
        val eventFavData = favList[position].events
        holder.txtTitleEvent.text = eventFavData.name
        Picasso.get()
            .load(eventFavData.imageLogo)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.error_placeholder)
            .into(holder.imgPromoteEvent)

        holder.itemView.setOnClickListener {
            onFavClickListener?.onClick(position,eventFavData)
        }
    }

    fun setOnClickListener(onClickListener: OnEventFavoriteClickListener) {
        this.onFavClickListener = onClickListener
    }

    fun setDataFavorite(favoriteList:List<EventEntity>?){
        if (favoriteList == null)return
        favList.clear()
        favList.addAll(favoriteList)
        notifyDataSetChanged()
    }


    interface OnEventFavoriteClickListener {
        fun onClick(position: Int, event: Events)
    }
}