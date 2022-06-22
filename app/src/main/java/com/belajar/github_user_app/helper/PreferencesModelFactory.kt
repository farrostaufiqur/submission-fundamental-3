package com.belajar.github_user_app.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.belajar.github_user_app.datastore.UserPreferences
import com.belajar.github_user_app.ui.settings.SettingsViewModel
import com.belajar.github_user_app.ui.splash.SplashViewModel

class PreferencesModelFactory(private val pref: UserPreferences) : NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}