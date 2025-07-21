package com.jc.presentation.navigation

sealed class Screen(val route: String) {
    /* Auth Flow */
    data object Activation : Screen("activation")
    data object SignIn : Screen("signIn")

    /* Main Flow */
    data object Main : Screen("main")
    data object Payment : Screen("payment")
    data object Print : Screen("print")

    companion object {
        fun fromRoute(route: String?): Screen? {
            return when (route) {
                Activation.route -> Activation
                SignIn.route -> SignIn
                Main.route -> Main
                Payment.route -> Payment
                Print.route -> Print
                else -> null
            }
        }
    }
}