package com.mff.githubapp.data.network

import com.mff.githubapp.data.GithubRepositoryApi
import com.mff.githubapp.data.model.GithubRepository
import com.mff.githubapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkGithubRepository : GithubRepositoryApi {
    private val dispatchers = Dispatchers.IO

    override suspend fun getUser(username: String): Result<User> =
        withContext(dispatchers) {
            runCatching {
                Provider.api.getUser(username)
            }
        }

    override suspend fun getUserRepository(username: String): Result<List<GithubRepository>> =
        withContext(dispatchers) {
            runCatching {
                Provider.api.getRepositories(username)
            }
        }
}