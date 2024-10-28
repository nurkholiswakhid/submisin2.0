package com.dicodingeventstracker.data.local.room

import androidx.room.*
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicodingeventstracker.data.local.room.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteEvent(eventEntity: EventEntity)

    @Query("SELECT * FROM events_table ORDER BY idNo ASC")
    fun readFavoriteEvents(): Flow<List<EventEntity>>

    @Delete
    suspend fun deleteFavoriteEvent(eventEntity: EventEntity)

    /*@Query("DELETE FROM events_table")
    suspend fun deleteAllFavoriteEvents()*/
}