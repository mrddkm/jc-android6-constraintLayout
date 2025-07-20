package com.jc.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.jc.presentation.ui.screens.shared.LayoutTemplate
import com.jc.presentation.ui.theme.ConstraintLayoutTheme

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupWindowForAndroid6()

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            ConstraintLayoutTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
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
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                // Android 11+ (API 30+)
                enableEdgeToEdge()
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                // Android 6+ (API 23+)
                setupFullscreenForAndroid6()
            }

            else -> {
                // Android 5 and below
                setupLegacyFullscreen()
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setupFullscreenForAndroid6() {
        // Option 1: Hide navigation bar completely (user can swipe to show it)
        hideNavigationBar()

        // Option 2: Navigation bar with padding
        // setupWindowInsets()
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun hideNavigationBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }

    private fun setupWindowInsets() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setupLegacyFullscreen() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hideNavigationBar()
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

    LayoutTemplate(
        title = title,
        onBackClick = onBackClick,
        onThemeToggle = onThemeToggle,
        isDarkTheme = isDarkTheme,
        headerPercent = headerPercent,
        footerPercent = footerPercent
    )
}