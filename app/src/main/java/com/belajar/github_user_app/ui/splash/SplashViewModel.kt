package com.belajar.github_user_app.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.belajar.github_user_app.datastore.UserPreferences

class SplashViewModel(private val pref: UserPreferences): ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}