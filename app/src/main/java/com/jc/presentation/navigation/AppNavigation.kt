package com.jc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jc.presentation.ui.screens.auth.activation.ActivationScreen
import com.jc.presentation.ui.screens.auth.signin.SignInScreen
import com.jc.presentation.ui.screens.main.MainScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Activation.route
) {
    var isDarkTheme by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Activation.route) {
            ActivationScreen(
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.Activation.route) { inclusive = true }
                    }
                },
                onThemeToggle = { isDarkTheme = !isDarkTheme },
                isDarkTheme = isDarkTheme
            )
        }

        composable(Screen.SignIn.route) {
            SignInScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.navigateUp()
                },
                onThemeToggle = { isDarkTheme = !isDarkTheme },
                isDarkTheme = isDarkTheme
            )
        }

        composable(Screen.Main.route) {
            MainScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onThemeToggle = { isDarkTheme = !isDarkTheme },
                isDarkTheme = isDarkTheme
            )
        }
    }
}





