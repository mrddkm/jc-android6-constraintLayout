package com.jc.presentation.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Data class to hold responsive size and spacing values for the application.
 * These values adjust based on whether the device is a tablet or a phone.
 *
 * @param isTablet A boolean indicating if the current device is a tablet.
 */
@Suppress("unused")
data class AppSize(val isTablet: Boolean) {

    // Text Sizes
    val titleSize: TextUnit
        get() = if (isTablet) 32.sp else 28.sp
    val subtitleSize: TextUnit
        get() = if (isTablet) 18.sp else 16.sp
    val buttonTextSize: TextUnit
        get() = if (isTablet) 18.sp else 16.sp
    val bodyTextSize: TextUnit
        get() = if (isTablet) 16.sp else 14.sp
    val captionTextSize: TextUnit
        get() = if (isTablet) 14.sp else 12.sp

    // Spacing and Padding
    val horizontalPadding: Dp
        get() = if (isTablet) 32.dp else 16.dp
    val verticalPadding: Dp
        get() = if (isTablet) 24.dp else 12.dp
    val screenTopMargin: Dp
        get() = if (isTablet) 48.dp else 16.dp
    val fieldSpacing: Dp
        get() = if (isTablet) 40.dp else 16.dp
    val buttonHeight: Dp
        get() = if (isTablet) 56.dp else 48.dp
    val logoSize: Dp
        get() = if (isTablet) 120.dp else 80.dp
    val circularProgressIndicatorSize: Dp
        get() = if (isTablet) 24.dp else 20.dp

    // Other common sizes (you can add more as needed)
    val iconSize: Dp
        get() = if (isTablet) 32.dp else 24.dp
    val cardElevation: Dp
        get() = if (isTablet) 4.dp else 2.dp
    val cardCornerRadius: Dp
        get() = if (isTablet) 16.dp else 8.dp
    val snackbarHeight: Dp
        get() = if (isTablet) 64.dp else 48.dp
    val imageHeight: Dp
        get() = if (isTablet) 200.dp else 150.dp
    val imageWidth: Dp
        get() = if (isTablet) 300.dp else 200.dp
    val roundedCornerShapeSize: Dp
        get() = if (isTablet) 8.dp else 4.dp
}
