package com.belajar.github_user_app.database

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class ConfigDatabase(context: Context) : ViewModelProvider.NewInstanceFactory(){
    private val db = getDatabase(context)

    val dao = db.UserDao()

    companion object {
        @Volatile
        private var INSTANCE: Database? = null

        @JvmStatic
        fun getDatabase(context: Context): Database {
            if (INSTANCE == null) {
                synchronized(Database::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        Database::class.java, "usergithub.db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as Database
        }
    }
}