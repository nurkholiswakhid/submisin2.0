package com.dicodingeventstracker.data.local

import com.dicodingeventstracker.data.local.datastore.SettingThemePreferences
import com.dicodingeventstracker.data.local.room.EventDao
import com.dicodingeventstracker.data.local.room.EventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val settingThemePreferences: SettingThemePreferences,
    private val eventDao: EventDao
) {
    /**
     * Mengambil pengaturan tema dari preferences.
     */
    fun getTheme(): Flow<Boolean> = settingThemePreferences.getTheme()

    /**
     * Mengatur tema berdasarkan status yang diberikan.
     * @param isDarkModeActive Status tema gelap aktif.
     */
    suspend fun setTheme(isDarkModeActive: Boolean) = settingThemePreferences.setTheme(isDarkModeActive)

    /**
     * Menyisipkan event favorit ke dalam database.
     * @param eventEntity Entitas acara yang akan disimpan.
     */
    suspend fun insertFavoriteEvent(eventEntity: EventEntity) = eventDao.insertFavoriteEvent(eventEntity)

    /**
     * Mengambil daftar event favorit.
     * @return Flow dari daftar event favorit.
     */
    fun showFavoriteEvent(): Flow<List<EventEntity>> = eventDao.readFavoriteEvents()

    /**
     * Menghapus event favorit dari database.
     * @param eventEntity Entitas acara yang akan dihapus.
     */
    suspend fun deleteFavoriteEvent(eventEntity: EventEntity) = eventDao.deleteFavoriteEvent(eventEntity)

    // Uncomment jika diperlukan
    // suspend fun deleteAllFavoriteEvent() = eventDao.deleteAllFavoriteEvents()
}