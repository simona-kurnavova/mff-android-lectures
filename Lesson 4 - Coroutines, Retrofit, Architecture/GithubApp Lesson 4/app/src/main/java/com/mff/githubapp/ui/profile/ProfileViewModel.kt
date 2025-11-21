package com.mff.githubapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mff.githubapp.data.GithubRepositoryApi
import com.mff.githubapp.data.mocked.MockedGithubRepository
import com.mff.githubapp.data.model.GithubRepository
import com.mff.githubapp.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository: GithubRepositoryApi by lazy {
        MockedGithubRepository
    }

    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Idle)
    val state: StateFlow<ProfileState> = _state

    fun loadUser(username: String) {
        viewModelScope.launch {
            _state.update { ProfileState.Loading }

            val result = repository.getUser(username)

            result.onSuccess { user ->
                val repositoriesResult = repository.getUserRepository(user.login)

                repositoriesResult.onSuccess { repositories ->
                    _state.update {
                        ProfileState.Loaded(
                            user = user,
                            repositories = repositories
                        )
                    }
                }.onFailure {
                    _state.update { ProfileState.Error }
                }
            }.onFailure {
                _state.update { ProfileState.Error }
            }
        }
    }
}

sealed interface ProfileState {
    data object Idle : ProfileState
    data object Loading : ProfileState
    data class Loaded(val user: User, val repositories: List<GithubRepository>) : ProfileState
    data object Error : ProfileState
}
