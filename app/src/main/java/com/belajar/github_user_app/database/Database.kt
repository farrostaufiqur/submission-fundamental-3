package com.belajar.github_user_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.belajar.github_user_app.network.GithubResponse

@Database(entities = [GithubResponse.User::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase(){
    abstract fun UserDao(): UserDao
}