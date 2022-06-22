@file:Suppress("DEPRECATION")

package com.belajar.github_user_app.ui.splash

import com.belajar.github_user_app.helper.PreferencesModelFactory
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.belajar.github_user_app.databinding.ActivitySplashBinding
import com.belajar.github_user_app.datastore.UserPreferences
import com.belajar.github_user_app.ui.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val handler = Handler(mainLooper)
        val pref = UserPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, PreferencesModelFactory(pref))[SplashViewModel::class.java]
        handler.postDelayed({
            viewModel.getThemeSettings().observe(this@SplashActivity) { isDarkModeActive->
                if (isDarkModeActive) {
                    moveToMainActivity()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    moveToMainActivity()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }, 1000)
    }
    private fun moveToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}