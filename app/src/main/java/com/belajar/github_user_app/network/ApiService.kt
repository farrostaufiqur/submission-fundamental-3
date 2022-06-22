package com.belajar.github_user_app.network

import com.belajar.github_user_app.BuildConfig.TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
//
interface ApiService {
    @GET("search/users")
    @Headers(TOKEN)
    fun searchUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers(TOKEN)
    fun getUser(
        @Path("username") username: String
    ): Call<GithubResponse.User>

    @GET("users/{username}/followers")
    @Headers(TOKEN)
    fun getFollowers(
        @Path("username") username: String,
    ): Call<List<GithubResponse.User>>

    @GET("users/{username}/following")
    @Headers(TOKEN)
    fun getFollowing(
        @Path("username") username: String,
    ): Call<List<GithubResponse.User>>
}