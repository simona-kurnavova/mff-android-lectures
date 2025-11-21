package com.mff.githubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mff.githubapp.ui.home.HomeScreen
import com.mff.githubapp.ui.profile.ProfileScreen
import com.mff.githubapp.ui.theme.GithubAppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            GithubAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(stringResource(R.string.app_name)) })
                    }
                ) { innerPadding ->
                    Main(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier
    ) {
        composable<Home> {
            HomeScreen(
                navigateToProfile = { username ->
                    navController.navigate(Profile(username))
                }
            )
        }

        composable<Profile> { backStack ->
            val username = backStack.toRoute<Profile>().username

            ProfileScreen(
                name = username
            )
        }
    }
}

@Preview
@Composable
private fun MainPreview() {
    GithubAppTheme {
        Main()
    }
}

@Serializable
object Home

@Serializable
data class Profile(val username: String)