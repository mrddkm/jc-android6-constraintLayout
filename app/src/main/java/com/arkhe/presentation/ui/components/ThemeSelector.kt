package com.arkhe.presentation.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arkhe.domain.model.ThemeMode
import com.arkhe.presentation.ui.theme.AppSize

/*UI Option 1: Cycling Button*/
@Composable
fun CyclingThemeButton(
    currentTheme: ThemeMode,
    onCycleTheme: () -> Unit,
) {
    val context = LocalContext.current
    val appSize = AppSize(isTablet = false)

    val icon = when (currentTheme) {
        ThemeMode.LIGHT -> Icons.Filled.LightMode
        ThemeMode.DARK -> Icons.Filled.DarkMode
        ThemeMode.AUTOMATIC -> Icons.Filled.BrightnessAuto
    }

    IconButton(
        onClick = {
            onCycleTheme()
            val nextTheme = when (currentTheme) {
                ThemeMode.LIGHT -> ThemeMode.DARK
                ThemeMode.DARK -> ThemeMode.AUTOMATIC
                ThemeMode.AUTOMATIC -> ThemeMode.LIGHT
            }
            Toast.makeText(
                context,
                nextTheme.displayName,
                Toast.LENGTH_SHORT
            ).show()
        },
        modifier = Modifier
            .size(appSize.iconSize / 1.2f)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(appSize.iconSize / 1.2f)
        )
    }
}

/*UI Option 2: Three Buttons Row*/
@Composable
fun ThreeButtonsRow(
    currentTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ThemeIconButton(
                themeMode = ThemeMode.LIGHT,
                isSelected = currentTheme == ThemeMode.LIGHT,
                onClick = {
                    onThemeSelected(ThemeMode.LIGHT)
                }
            )

            ThemeIconButton(
                themeMode = ThemeMode.AUTOMATIC,
                isSelected = currentTheme == ThemeMode.AUTOMATIC,
                onClick = {
                    onThemeSelected(ThemeMode.AUTOMATIC)
                }
            )

            ThemeIconButton(
                themeMode = ThemeMode.DARK,
                isSelected = currentTheme == ThemeMode.DARK,
                onClick = {
                    onThemeSelected(ThemeMode.DARK)
                }
            )
        }
    }
}

@Composable
private fun ThemeIconButton(
    themeMode: ThemeMode,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = when (themeMode) {
        ThemeMode.LIGHT -> Icons.Filled.LightMode
        ThemeMode.DARK -> Icons.Filled.DarkMode
        ThemeMode.AUTOMATIC -> Icons.Filled.BrightnessAuto
    }

    val colors = if (isSelected) {
        ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    } else {
        ButtonDefaults.outlinedButtonColors()
    }

    val buttonModifier = if (isSelected) {
        modifier.size(72.dp)
    } else {
        modifier.size(64.dp)
    }

    if (isSelected) {
        FilledTonalButton(
            onClick = onClick,
            modifier = buttonModifier,
            colors = colors,
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = themeMode.displayName,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (themeMode) {
                        ThemeMode.LIGHT -> "Light"
                        ThemeMode.DARK -> "Dark"
                        ThemeMode.AUTOMATIC -> "Auto"
                    },
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = buttonModifier,
            colors = colors,
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = themeMode.displayName,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (themeMode) {
                        ThemeMode.LIGHT -> "Light"
                        ThemeMode.DARK -> "Dark"
                        ThemeMode.AUTOMATIC -> "Auto"
                    },
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}