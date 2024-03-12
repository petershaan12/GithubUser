package com.petershaan.githubuser.data.retrofit

import com.petershaan.githubuser.data.response.DetailGithubResponse
import com.petershaan.githubuser.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getResponUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailGithubResponse>

}