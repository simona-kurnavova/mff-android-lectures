package com.mff.githubapp.data.model

/**
 * Github repository details.
 */
data class GithubRepository(
    val id: Int,
    val name: String,
    val full_name: String,
    val private: Boolean,
    val description: String? = "",
)