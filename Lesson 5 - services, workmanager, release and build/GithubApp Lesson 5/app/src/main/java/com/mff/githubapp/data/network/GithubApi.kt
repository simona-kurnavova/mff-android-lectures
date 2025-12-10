package com.mff.githubapp.data.network

import com.mff.githubapp.data.model.GithubRepository
import com.mff.githubapp.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): User

    @GET("users/{username}/repos")
    suspend fun getRepositories(
        @Path("username") username: String
    ): List<GithubRepository>
}