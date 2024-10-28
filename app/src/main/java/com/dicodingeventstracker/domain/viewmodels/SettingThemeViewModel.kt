// SettingThemeViewModel.kt
package com.dicodingeventstracker.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicodingeventstracker.domain.repository.SettingThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingThemeViewModel @Inject constructor(
    private val settingThemeRepository: SettingThemeRepository
) : ViewModel() {

    val themeSetting: Flow<Boolean> = settingThemeRepository.getThemeSetting()

    fun saveThemeSetting(isDarkModeActive: Boolean ) {
        viewModelScope.launch {
            settingThemeRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}