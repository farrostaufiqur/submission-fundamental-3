package com.belajar.github_user_app.ui.profile

import androidx.lifecycle.*
import com.belajar.github_user_app.database.ConfigDatabase
import com.belajar.github_user_app.network.GithubResponse
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class ProfileViewModel(private val db: ConfigDatabase) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun saveUser(user: GithubResponse.User) {
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                db.dao.delete(user)
                _isFavorite.value = false
            } else {
                db.dao.insert(user)
                _isFavorite.value = true
            }
        }
    }

    fun getFavorite(username: String){
        viewModelScope.launch {
            val user = db.dao.getUser(username)
            if (user != null){
                _isFavorite.value = true
            }
        }
    }

    class Factory(private val db: ConfigDatabase) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ProfileViewModel(db) as T
    }
}