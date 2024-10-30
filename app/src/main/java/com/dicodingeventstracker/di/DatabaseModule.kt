package com.dicodingeventstracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.dicodingeventstracker.data.local.LocalDataSource
import com.dicodingeventstracker.data.local.room.EventDao
import com.dicodingeventstracker.data.local.room.EventDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideEventDao(@ApplicationContext context: Context): EventDao {
        val eventDatabase = EventDatabase.getInstance(context)
        return eventDatabase.getEventDao()
    }

    @Provides
    fun provideLocalDataSource(@ApplicationContext context: Context): LocalDataSource {
        val eventDao = provideEventDao(context)
        val settingThemPref = DataStoreModule.provideSettingThemePreferences(context)
        return LocalDataSource(settingThemPref, eventDao)
    }

}