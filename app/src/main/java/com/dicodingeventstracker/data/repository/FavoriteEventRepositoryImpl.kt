package com.dicodingeventstracker.data.repository

import com.dicodingeventstracker.domain.repository.FavoriteEventRepository
import com.dicodingeventstracker.data.local.LocalDataSource
import com.dicodingeventstracker.data.local.room.EventEntity

class FavoriteEventRepositoryImpl(private val localDataSource: LocalDataSource
): FavoriteEventRepository {
    /*override suspend fun insertFavEvent(events: Events) {
        val entity = DataMapper.mapEventDomainToEntity(events)
        localDataSource.insertFavoriteEvent(entity)
    }

    override suspend fun deleteFavEvent(events: Events) {
        val entity = DataMapper.mapEventDomainToEntity(events)
        localDataSource.deleteFavoriteEvent(entity)
    }

    override fun showFavEvent()= liveData {
        val localEvent = localDataSource.showFavoriteEvent().map { localEvent ->
            localEvent.map { eventEntity ->
                DataMapper.mapEventEntityToDomain(eventEntity)
            }
        }
        emitSource(localEvent)
    }*/
    override suspend fun insertFavEvent(eventEntity: EventEntity) {
        localDataSource.insertFavoriteEvent(eventEntity)
    }

    override suspend fun deleteFavEvent(eventEntity: EventEntity) {
        localDataSource.deleteFavoriteEvent(eventEntity)
    }

    override fun showFavEvent()= localDataSource.showFavoriteEvent()

    companion object {
        @Volatile
        private var instance: FavoriteEventRepositoryImpl? = null

        fun getInstanceForFavorite(localDataSource: LocalDataSource) =
            instance ?: synchronized(this) {
                instance ?: FavoriteEventRepositoryImpl(localDataSource)
            }.also {
                instance = it
            }
    }
}