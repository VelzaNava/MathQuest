package com.mathquest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mathquest.app.navigation.Routes
import com.mathquest.app.ui.screens.IntroStoryScreen
import com.mathquest.app.ui.screens.LevelEarthScreen
import com.mathquest.app.ui.screens.LoginScreen
import com.mathquest.app.ui.screens.MainMenuScreen
import com.mathquest.app.ui.theme.MathQuestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Draw under system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Immersive sticky — hide status & navigation bars; swipe edge to reveal
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())

        setContent {
            MathQuestTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    MathQuestApp()
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Re-hide bars when returning to the app
            val controller = WindowInsetsControllerCompat(window, window.decorView)
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.hide(WindowInsetsCompat.Type.systemBars())
        }
    }
}

@Composable
fun MathQuestApp(
    navController: NavHostController = rememberNavController(),
    viewModel: GameViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN_MENU
    ) {
        composable(Routes.MAIN_MENU) {
            MainMenuScreen(
                hasSavedProgress = state.hasSavedProgress,
                playerName = state.playerName,
                musicVolume = state.musicVolume,
                sfxVolume = state.sfxVolume,
                onMusicVolume = viewModel::setMusicVolume,
                onSfxVolume = viewModel::setSfxVolume,
                onStartAdventure = {
                    navController.navigate(Routes.LOGIN)
                },
                onContinue = {
                    navController.navigate(Routes.LEVEL_EARTH)
                },
                onViewProgress = {
                    if (state.hasSavedProgress) {
                        navController.navigate(Routes.LEVEL_EARTH)
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onSubmit = { name ->
                    viewModel.setPlayerName(name)
                    navController.navigate(Routes.INTRO_STORY) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.INTRO_STORY) {
            IntroStoryScreen(
                playerName = state.playerName,
                onContinue = {
                    navController.navigate(Routes.LEVEL_EARTH) {
                        popUpTo(Routes.INTRO_STORY) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LEVEL_EARTH) {
            LevelEarthScreen(
                playerName = state.playerName,
                musicVolume = state.musicVolume,
                sfxVolume = state.sfxVolume,
                onMusicVolume = viewModel::setMusicVolume,
                onSfxVolume = viewModel::setSfxVolume,
                onHome = {
                    navController.navigate(Routes.MAIN_MENU) {
                        popUpTo(Routes.MAIN_MENU) { inclusive = true }
                    }
                }
            )
        }
    }
}
