package com.dicodingeventstracker.domain.repository

import androidx.lifecycle.LiveData
import com.dicodingeventstracker.data.remoteUtils.RemoteResponse
import com.dicodingeventstracker.domain.entity.Events


interface EventsRepository {

    fun getFutureEvents(active:Int): LiveData<RemoteResponse<List<Events>?>?>

    fun getPastEvents(active:Int): LiveData<RemoteResponse<List<Events>?>?>

    fun getDetailEvents(id:Int): LiveData<RemoteResponse<Events?>?>

    fun searchEvents(active:Int,query:String):LiveData<RemoteResponse<List<Events>?>?>
}