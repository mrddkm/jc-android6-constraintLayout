package com.jc.presentation.navigation

sealed class Screen(val route: String) {
    object Activation : Screen("activation")
    object SignIn : Screen("signin")
    object Main : Screen("main")
    object Payment : Screen("payment")
    object Print : Screen("print")
}