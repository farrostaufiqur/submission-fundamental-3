package com.belajar.github_user_app.network

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GithubResponse(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: MutableList<User>
) {
    @Parcelize
    @Entity(tableName = "user_table")
    data class User(

        @PrimaryKey
        @ColumnInfo(name = "username")
        @field:SerializedName("login")
        val login: String,

        @ColumnInfo(name = "avatar")
        @field:SerializedName("avatar_url")
        val avatarUrl: String,

        @ColumnInfo(name = "followers")
        @field:SerializedName("followers")
        val followers: Int? = null,

        @ColumnInfo(name = "following")
        @field:SerializedName("following")
        val following: Int? = null,

        @ColumnInfo(name = "name")
        @field:SerializedName("name")
        val name: String? = null,

        @ColumnInfo(name = "company")
        @field:SerializedName("company")
        val company: String? = null,

        @ColumnInfo(name = "location")
        @field:SerializedName("location")
        val location: String? = null,

        @ColumnInfo(name = "bio")
        @field:SerializedName("bio")
        val bio: String? = null,

        @ColumnInfo(name = "blog")
        @field:SerializedName("blog")
        val blog: String? = null,

        @ColumnInfo(name = "repos")
        @field:SerializedName("public_repos")
        val publicRepos: Int? = null

    ) : Parcelable
}