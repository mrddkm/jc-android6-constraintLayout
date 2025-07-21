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
        startDestination = Screen.Activation.route
    ) {
        composable(Screen.Activation.route) {
            ActivationScreen(
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.Activation.route) { inclusive = true }
                    }
                },
                onThemeToggle = onThemeToggle,
                isDarkTheme = isDarkTheme,
                footerPercent = footerPercent,
                isTablet = isTablet
            )
        }

        composable(Screen.SignIn.route) {
            SignInScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                },
                onThemeToggle = onThemeToggle,
                isDarkTheme = isDarkTheme,
                footerPercent = footerPercent,
                isTablet = isTablet
            )
        }

        // Add other screens here with responsive parameters
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToPayment = {
                    navController.navigate(Screen.Payment.route)
                },
                onThemeToggle = onThemeToggle,
                isDarkTheme = isDarkTheme,
                headerPercent = headerPercent,
                footerPercent = footerPercent,
                isTablet = isTablet,
            )
        }

        composable(Screen.Payment.route) {
            // TODO:: PaymentScreen implementation
        }

        composable(Screen.Print.route) {
            // TODO:: PrintScreen implementation
        }
    }
}