package com.jc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jc.presentation.ui.screens.auth.activation.ActivationScreen
import com.jc.presentation.ui.screens.auth.signin.SignInScreen
import com.jc.presentation.ui.screens.main.MainScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    onThemeToggle: () -> Unit = {},
    isDarkTheme: Boolean = false,
    headerPercent: Float = 0.10f,
    footerPercent: Float = 0.08f,
    isTablet: Boolean = false
) {
    NavHost(
        navController = navController,
        startDestination = "activation"
    ) {
        composable("activation") {
            ActivationScreen(
                onNavigateToSignIn = {
                    navController.navigate("signIn") {
                        popUpTo("activation") { inclusive = true }
                    }
                },
                onThemeToggle = onThemeToggle,
                isDarkTheme = isDarkTheme,
                footerPercent = footerPercent,
                isTablet = isTablet
            )
        }

        composable("signIn") {
            SignInScreen(
                onNavigateToMain = {
                    navController.navigate("main") {
                        popUpTo("signIn") { inclusive = true }
                    }
                },
                onThemeToggle = onThemeToggle,
                isDarkTheme = isDarkTheme,
                footerPercent = footerPercent,
                isTablet = isTablet
            )
        }

        // Add other screens here with responsive parameters
        composable("main") {
             MainScreen(
                 onThemeToggle = onThemeToggle,
                 isDarkTheme = isDarkTheme,
                 headerPercent = headerPercent,
                 footerPercent = footerPercent,
                 isTablet = isTablet
             )
        }
    }
}