package com.belajar.github_user_app.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<List<User>>
}