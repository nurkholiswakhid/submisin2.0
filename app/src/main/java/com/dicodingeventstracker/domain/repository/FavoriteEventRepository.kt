package com.dicodingeventstracker.domain.repository

import kotlinx.coroutines.flow.Flow
import com.dicodingeventstracker.data.local.room.EventEntity

interface FavoriteEventRepository {

    suspend fun insertFavEvent(eventEntity: EventEntity)

    suspend fun deleteFavEvent(eventEntity: EventEntity)

    fun showFavEvent(): Flow<List<EventEntity>>

    //suspend fun deleteAllFavEvent()
}