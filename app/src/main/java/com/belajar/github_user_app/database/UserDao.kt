package com.belajar.github_user_app.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.belajar.github_user_app.network.GithubResponse

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GithubResponse.User)

    @Delete
    fun delete(user: GithubResponse.User)

    @Query("SELECT * FROM user_table ORDER BY username ASC")
    fun getAllUser(): LiveData<List<GithubResponse.User>>

    @Query("SELECT * FROM user_table WHERE username = :username LIMIT 1")
    fun getUser(username: String): GithubResponse.User
}