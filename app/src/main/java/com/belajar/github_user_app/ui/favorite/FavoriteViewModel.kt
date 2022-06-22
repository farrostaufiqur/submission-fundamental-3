package com.belajar.github_user_app.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.belajar.github_user_app.database.ConfigDatabase

@Suppress("UNCHECKED_CAST")
class FavoriteViewModel(private val ConfigDatabase: ConfigDatabase) : ViewModel() {

    fun getAllFavorite() = ConfigDatabase.dao.getAllUser()

    class Factory(private val db: ConfigDatabase) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}