package com.jc.constraintlayout

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.jc.constraintlayout.ui.theme.ConstraintLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupWindowForAndroid6()

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            ConstraintLayoutTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ResponsiveConstraintLayoutTemplate(
                        title = "Dashboard",
                        onBackClick = {
                            onBackPressed()
                        },
                        onThemeToggle = {
                            isDarkTheme = !isDarkTheme
                        },
                        isDarkTheme = isDarkTheme
                    )
                }
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setupWindowForAndroid6() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            enableEdgeToEdge()
        } else {
            WindowCompat.setDecorFitsSystemWindows(window, false)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val windowInsetsController =
                    WindowCompat.getInsetsController(window, window.decorView)
                windowInsetsController.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    @Deprecated(
        "This method has been deprecated in favor of using the" +
                "{@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}." +
                "The OnBackPressedDispatcher controls how back button events are dispatched" +
                "to one or more {@link OnBackPressedCallback} objects."
    )
    @SuppressLint("GestureBackNavigation")
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            super.onBackPressed()
        } else {
            finish()
        }
    }
}

@Composable
fun ResponsiveConstraintLayoutTemplate(
    title: String,
    onBackClick: () -> Unit,
    onThemeToggle: () -> Unit,
    isDarkTheme: Boolean
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val isTablet = with(density) {
        configuration.screenWidthDp.dp >= 600.dp
    }

    val (headerPercent, footerPercent) = if (isTablet) {
        0.12f to 0.08f // Tablet: header 12%, footer 8%
    } else {
        0.15f to 0.10f // Phone: header 15%, footer 10%
    }

    ConstraintLayoutTemplate(
        title = title,
        onBackClick = onBackClick,
        onThemeToggle = onThemeToggle,
        isDarkTheme = isDarkTheme,
        headerPercent = headerPercent,
        footerPercent = footerPercent
    )
}

@SuppressLint("ObsoleteSdkInt")
fun isAndroid6Plus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

fun isAndroid10Plus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}