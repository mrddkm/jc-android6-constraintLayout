package com.arkhe.presentation.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.SettingsBrightness
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkhe.domain.model.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectorBottomSheet(
    currentTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .selectableGroup()
        ) {
            Text(
                text = "Choose Theme",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ThemeMode.entries.forEach { themeMode ->
                ThemeOptionItem(
                    themeMode = themeMode,
                    isSelected = currentTheme == themeMode,
                    onSelected = {
                        onThemeSelected(themeMode)
                        onDismiss()
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ThemeOptionItem(
    themeMode: ThemeMode,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = when (themeMode) {
        ThemeMode.LIGHT -> Icons.Outlined.LightMode
        ThemeMode.DARK -> Icons.Outlined.DarkMode
        ThemeMode.AUTOMATIC -> Icons.Outlined.SettingsBrightness
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onSelected,
                role = Role.RadioButton
            )
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = themeMode.displayName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}

// UI Option 2: Cycling Button
@Composable
fun CyclingThemeButton(
    currentTheme: ThemeMode,
    onCycleTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val icon = when (currentTheme) {
        ThemeMode.LIGHT -> Icons.Outlined.LightMode
        ThemeMode.DARK -> Icons.Outlined.DarkMode
        ThemeMode.AUTOMATIC -> Icons.Outlined.SettingsBrightness
    }

    Button(
        onClick = {
            onCycleTheme()
            // Show toast with current theme
            val nextTheme = when (currentTheme) {
                ThemeMode.LIGHT -> ThemeMode.DARK
                ThemeMode.DARK -> ThemeMode.AUTOMATIC
                ThemeMode.AUTOMATIC -> ThemeMode.LIGHT
            }
            Toast.makeText(
                context,
                "Switched to ${nextTheme.displayName}",
                Toast.LENGTH_SHORT
            ).show()
        },
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.Autorenew,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Cycle Theme (${currentTheme.displayName})")
    }
}

// UI Option 3: Three Buttons Row
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
        Text(
            text = "Choose Theme",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Light Theme Button
            ThemeIconButton(
                themeMode = ThemeMode.LIGHT,
                isSelected = currentTheme == ThemeMode.LIGHT,
                onClick = {
                    onThemeSelected(ThemeMode.LIGHT)
                    Toast.makeText(
                        context,
                        "Light Theme",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

            // Automatic Theme Button
            ThemeIconButton(
                themeMode = ThemeMode.AUTOMATIC,
                isSelected = currentTheme == ThemeMode.AUTOMATIC,
                onClick = {
                    onThemeSelected(ThemeMode.AUTOMATIC)
                    Toast.makeText(
                        context,
                        "Automatic Theme",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

            // Dark Theme Button
            ThemeIconButton(
                themeMode = ThemeMode.DARK,
                isSelected = currentTheme == ThemeMode.DARK,
                onClick = {
                    onThemeSelected(ThemeMode.DARK)
                    Toast.makeText(
                        context,
                        "Dark Theme",
                        Toast.LENGTH_SHORT
                    ).show()
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
        ThemeMode.LIGHT -> Icons.Outlined.LightMode
        ThemeMode.DARK -> Icons.Outlined.DarkMode
        ThemeMode.AUTOMATIC -> Icons.Outlined.SettingsBrightness
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