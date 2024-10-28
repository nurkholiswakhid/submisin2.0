package com.dicodingeventstracker.domain.splashscreen

import androidx.lifecycle.ViewModel
import com.dicodingeventstracker.data.local.datastore.SettingThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val settingThemePreferences: SettingThemePreferences
) : ViewModel() {

    fun getTheme(): Flow<Boolean> = settingThemePreferences.getTheme()
}