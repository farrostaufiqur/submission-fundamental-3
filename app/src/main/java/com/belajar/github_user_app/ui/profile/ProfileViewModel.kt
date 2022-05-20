package com.belajar.github_user_app.ui.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import com.belajar.github_user_app.database.Favorite
import com.belajar.github_user_app.repository.FavoriteRepository

class ProfileViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun update(favorite: Favorite) {
        mFavoriteRepository.update(favorite)
    }

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }
}