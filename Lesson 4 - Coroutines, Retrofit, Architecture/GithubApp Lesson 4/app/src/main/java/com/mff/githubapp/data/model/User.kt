package com.mff.githubapp.data.model

/**
 * Github user details.
 */
data class User(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String
)