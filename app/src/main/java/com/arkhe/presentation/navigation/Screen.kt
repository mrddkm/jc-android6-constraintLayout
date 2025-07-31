package com.arkhe.presentation.navigation

sealed class Screen(val route: String) {
    /* Auth Flow */
    data object Activation : Screen("activation")
    data object SignIn : Screen("signIn")

    /* Main Flow */
    data object Main : Screen("main")
    data object PaymentQris : Screen("paymentQris")
    data object PaymentCash : Screen("paymentCash")
    data object Print : Screen("print")

    companion object {
        fun fromRoute(route: String?): Screen? {
            return when (route) {
                Activation.route -> Activation
                SignIn.route -> SignIn
                Main.route -> Main
                PaymentQris.route -> PaymentQris
                PaymentCash.route -> PaymentCash
                Print.route -> Print
                else -> null
            }
        }
    }
}