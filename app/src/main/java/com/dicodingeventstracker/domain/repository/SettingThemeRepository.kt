// SettingThemeRepository.kt
package com.dicodingeventstracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingThemeRepository {
    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}