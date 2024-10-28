// SettingThemeRepositoryImpl.kt
package com.dicodingeventstracker.data.repository

import com.dicodingeventstracker.data.local.datastore.SettingThemePreferences
import com.dicodingeventstracker.domain.repository.SettingThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingThemeRepositoryImpl @Inject constructor(
    private val settingThemePreferences: SettingThemePreferences
) : SettingThemeRepository {

    override fun getThemeSetting(): Flow<Boolean> {
        return settingThemePreferences.getTheme()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingThemePreferences.setTheme(isDarkModeActive)  // Menggunakan setTheme() bukan saveThemeSetting()
    }
}