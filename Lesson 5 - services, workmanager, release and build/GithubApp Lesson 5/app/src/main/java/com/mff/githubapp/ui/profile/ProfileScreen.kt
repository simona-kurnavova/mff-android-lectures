package com.mff.githubapp.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mff.githubapp.R
import com.mff.githubapp.data.model.GithubRepository
import com.mff.githubapp.data.model.User
import com.mff.githubapp.ui.components.UserCard
import com.mff.githubapp.ui.theme.GithubAppTheme

@Composable
fun ProfileScreen(
    name: String,
    viewModel: ProfileViewModel = viewModel<ProfileViewModel>()
) {
    LaunchedEffect(name) {
        viewModel.loadUser(name)
    }

    val state = viewModel.state.collectAsStateWithLifecycle()
    val profileState = state.value

    when(profileState) {
        ProfileState.Idle -> Unit
        ProfileState.Loading -> ProfileLoading()
        is ProfileState.Loaded -> {
            ProfileUser(
                user = profileState.user,
                repositories = profileState.repositories
            )
        }
        ProfileState.Error -> ProfileError()
    }
}

@Composable
private fun ProfileLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ProfileUser(
    user: User,
    repositories: List<GithubRepository>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        UserCard(
            title = user.login,
            subtitle = user.id.toString(),
            url = user.html_url
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.profile_repositories_list_title),
            modifier = Modifier.padding(horizontal = 12.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(repositories, key = { it.id }) { repository ->
                RepositoryRow(
                    repository = repository,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun RepositoryRow(
    repository: GithubRepository,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(8.dp)
    ) {
        Text(
            text = repository.name,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
        )

        repository.description?.let {
            Text(
                text = repository.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun ProfileError(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.profile_error_loading),
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center
    )
}

@Preview(
    showBackground = true,
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ProfileScreenPreview() {
    val user = User(
        login = "avast",
        id = 3996079,
        avatar_url = "https://avatars1.githubusercontent.com/u/3996079?v=4",
        url = "",
        html_url = "https://github.com/avast",
        followers_url = "",
        following_url = "",
    )
    GithubAppTheme {
        ProfileUser(
            user = user,
            repositories = listOf(
                GithubRepository(
                    id = 15397085,
                    name = "android-butterknife-zelezny",
                    description = "Android Studio plug-in for generating ButterKnife injections from selected layout XML.",
                    full_name = "avast/android-butterknife-zelezny",
                    private = false
                ),
                GithubRepository(
                    id = 45212866,
                    name = "android-lectures",
                    description = "Class material for lectures about Android development",
                    full_name = "avast/android-lectures",
                    private = false
                )
            )
        )
    }
}


@Preview(
    showBackground = true,
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ProfileScreenErrorPreview() {
    GithubAppTheme {
        ProfileError()
    }
}