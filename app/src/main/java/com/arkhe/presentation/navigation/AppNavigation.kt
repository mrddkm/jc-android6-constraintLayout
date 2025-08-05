package com.arkhe.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arkhe.domain.model.ThemeMode
import com.arkhe.presentation.state.ThemeUiState
import com.arkhe.presentation.ui.screens.auth.activation.ActivationScreen
import com.arkhe.presentation.ui.screens.auth.signin.SignInScreen
import com.arkhe.presentation.ui.screens.main.MainScreen
import com.arkhe.presentation.ui.screens.payment.PaymentCashScreen
import com.arkhe.presentation.ui.screens.payment.PaymentQRISScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    headerPercent: Float = 0.10f,
    footerPercent: Float = 0.08f,
    isTablet: Boolean = false,
    uiStateTheme: ThemeUiState,
    onThemeSelected: (ThemeMode) -> Unit,
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
                footerPercent = footerPercent,
                isTablet = isTablet,
                uiStateTheme = uiStateTheme,
                onThemeSelected = onThemeSelected
            )
        }

        composable(Screen.SignIn.route) {
            SignInScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                },
                footerPercent = footerPercent,
                isTablet = isTablet,
                uiStateTheme = uiStateTheme,
                onThemeSelected = onThemeSelected,
            )
        }

        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToPaymentQris = {
                    navController.navigate(Screen.PaymentQris.route)
                },
                onNavigateToPaymentCash = {
                    navController.navigate(Screen.PaymentCash.route)
                },
                onSignOut = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                },
                headerPercent = headerPercent,
                footerPercent = footerPercent,
                isTablet = isTablet,
                uiStateTheme = uiStateTheme,
                onThemeSelected = onThemeSelected,
            )
        }

        composable(Screen.PaymentQris.route) {
            PaymentQRISScreen(
                onPaymentConfirm = {
                    navController.navigate(Screen.Print.route)
                },
                isTablet = isTablet,
            )
        }

        composable(Screen.PaymentCash.route) {
            PaymentCashScreen(
                onPaymentConfirm = {
                    navController.navigate(Screen.Print.route)
                },
                isTablet = isTablet,
            )
        }

        composable(Screen.Print.route) {

        }
    }
}