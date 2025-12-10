package com.mff.githubapp.data.network

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Provider {
    val client: Retrofit by lazy {
        val okHttp = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Accept", "application/vnd.github.v3+json")
                    .build()
                chain.proceed(newRequest)
            }
        }.build()

        Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
    }

    val api: GithubApi by lazy {
        client.create(GithubApi::class.java)
    }
}

private const val HOST: String = "https://api.github.com/"
