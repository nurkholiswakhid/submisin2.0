// DataStoreModule.kt
package com.dicodingeventstracker.di

import android.content.Context
import com.dicodingeventstracker.data.local.datastore.SettingThemePreferences
import com.dicodingeventstracker.data.local.datastore.SettingThemePreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideSettingThemePreferences(
        @ApplicationContext context: Context
    ): SettingThemePreferences {
        return SettingThemePreferencesImpl(context)
    }
}