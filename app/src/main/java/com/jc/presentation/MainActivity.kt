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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.WindowMetricsCalculator
import com.jc.presentation.navigation.AppNavigation
import com.jc.presentation.ui.theme.AppTheme

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupWindowForAndroid6()

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            AppTheme(
                darkTheme = isDarkTheme,
                dynamicColor = false
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val windowMetrics = remember(this) {
                        WindowMetricsCalculator.getOrCreate()
                            .computeCurrentWindowMetrics(this).bounds
                    }
                    val isTablet = with(LocalDensity.current) {
                        windowMetrics.width().toDp() >= 600.dp
                    }

                    ResponsiveApp(
                        navController = navController,
                        onThemeToggle = { isDarkTheme = !isDarkTheme },
                        isDarkTheme = isDarkTheme,
                        isTablet = isTablet
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
                hideNavigationBar()
            }

            else -> {
                // Android 5 and below
                setupLegacyFullscreen()
            }
        }
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

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ResponsiveApp(
    navController: androidx.navigation.NavHostController,
    onThemeToggle: () -> Unit,
    isDarkTheme: Boolean,
    isTablet: Boolean = false,
) {
    val (headerPercent, footerPercent) = if (isTablet) {
        0.09f to 0.07f // Tablet: header 9%, footer 7%
    } else {
        0.10f to 0.08f // Phone: header 10%, footer 8%
    }

    // Pass responsive parameters to AppNavigation
    AppNavigation(
        navController = navController,
        onThemeToggle = onThemeToggle,
        isDarkTheme = isDarkTheme,
        headerPercent = headerPercent,
        footerPercent = footerPercent,
        isTablet = isTablet
    )
}