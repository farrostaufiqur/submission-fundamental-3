package com.belajar.github_user_app.ui.settings

import com.belajar.github_user_app.helper.PreferencesModelFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.belajar.github_user_app.databinding.ActivitySettingsBinding
import com.belajar.github_user_app.datastore.UserPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = binding.switchTheme

        val pref = UserPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, PreferencesModelFactory(pref))[SettingsViewModel::class.java]
        viewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
        binding.language.setOnClickListener{
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
    }
}