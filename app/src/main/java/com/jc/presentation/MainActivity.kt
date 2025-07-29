package com.jc.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import org.koin.compose.KoinContext

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupWindowForAndroid6()

        setContent {
            KoinContext {
                AppTheme {
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
                            isTablet = isTablet,
                        )
                    }
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
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hideNavigationBar()
        }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ResponsiveApp(
    navController: androidx.navigation.NavHostController,
    isTablet: Boolean = false,
) {
    val (headerPercent, footerPercent) = if (isTablet) {
        0.09f to 0.07f
    } else {
        0.10f to 0.08f
    }

    AppNavigation(
        navController = navController,
        headerPercent = headerPercent,
        footerPercent = footerPercent,
        isTablet = isTablet,
    )
}