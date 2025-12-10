package com.mff.githubapp.data

import com.mff.githubapp.data.model.GithubRepository
import com.mff.githubapp.data.model.User

interface GithubRepositoryApi {
    /**
     * Obrain user with specified username.
     *
     * @param username username
     */
    suspend fun getUser(username: String): Result<User>

    /**
     * Returns list of repositories for user.
     *
     * @param username Name of user that repositories belong to.
     */
    suspend fun getUserRepository(username: String): Result<List<GithubRepository>>
}
