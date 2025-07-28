package com.jc.presentation.ui.screens.shared.ext

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jc.constraintlayout.R
import com.jc.core.util.Constants
import com.jc.presentation.ui.theme.AppSize
import com.jc.presentation.viewmodel.LanguageOption
import com.jc.presentation.viewmodel.LanguageViewModel
import org.koin.androidx.compose.koinViewModel

data class UserProfile(
    val username: String,
    val fullName: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsProfileBottomSheet(
    onDismissRequest: () -> Unit,
    isTablet: Boolean,
    currentUserProfile: UserProfile? = null,
    languageViewModel: LanguageViewModel = koinViewModel()
) {
    val appSize = AppSize(isTablet = isTablet)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val activity = LocalContext.current as? Activity

    val currentLanguageCode by languageViewModel.currentLanguageCode.collectAsState()
    Log.d("SettingsSheet", "Current language code from VM: $currentLanguageCode")

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = appSize.verticalPadding / 2),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(4.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            shape = CircleShape
                        )
                )
            }
        }
    ) {
        SettingsSheetContent(
            isTablet = isTablet,
            userProfile = currentUserProfile,
            selectedLanguageCode = currentLanguageCode,
            supportedLanguages = languageViewModel.supportedLanguages,
            onLanguageSelected = { newLanguageCode ->
                Log.d(
                    "SettingsSheet",
                    "Language selected in BottomSheet: $newLanguageCode. Calling VM."
                )
                languageViewModel.setLanguage(newLanguageCode) {
                    Log.d("SettingsSheet", "Attempting to recreate activity.")
                    Log.d(
                        "SettingsSheet",
                        "Activity instance: $activity, isFinishing: ${activity?.isFinishing}"
                    )
                    activity?.recreate()
                }
                // onDismissRequest() // Optional, uncomment if you want to close the sheet after selecting a language
            }
        )
    }
}

@Composable
private fun SettingsSheetContent(
    isTablet: Boolean,
    userProfile: UserProfile?,
    selectedLanguageCode: String,
    supportedLanguages: List<LanguageOption>,
    onLanguageSelected: (String) -> Unit,
) {
    val appSize = AppSize(isTablet = isTablet)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = appSize.horizontalPadding * 3f)
            .padding(bottom = appSize.verticalPadding)
            .navigationBarsPadding()
    ) {
        val title = if (userProfile == null) stringResource(R.string.settings_title)
        else stringResource(R.string.settings_profile_title)

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = appSize.verticalPadding)
        )

        if (userProfile != null) {
            SectionTitle(
                title = stringResource(R.string.settings_section_profile),
                isTablet = isTablet
            )
            UserProfileInfo(
                userProfile = userProfile,
                isTablet = isTablet,
            )
            Spacer(modifier = Modifier.height(appSize.verticalPadding / 2))
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 50.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(appSize.verticalPadding))
        }

        SectionTitle(
            title = stringResource(R.string.settings_section_language),
            isTablet = isTablet
        )

        supportedLanguages.forEach { langOption ->
            LanguageRow(
                languageName = langOption.displayName,
                languageCode = langOption.code,
                isSelected = selectedLanguageCode == langOption.code,
                isTablet = isTablet,
                onLanguageSelected = {
                    Log.d("SettingsSheet", "LanguageRow clicked for code: ${langOption.code}")
                    onLanguageSelected(langOption.code)
                }
            )
        }

        Spacer(modifier = Modifier.height(appSize.verticalPadding))
    }
}

@Composable
private fun SectionTitle(title: String, isTablet: Boolean) {
    val appSize = AppSize(isTablet = isTablet)
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = appSize.fieldSpacing / 8)
    )
}

@Composable
private fun UserProfileInfo(
    userProfile: UserProfile,
    isTablet: Boolean
) {
    val appSize = AppSize(isTablet = isTablet)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = appSize.verticalPadding / 4)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = appSize.verticalPadding / 2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(appSize.fieldSpacing / 2)
        ) {
            Icon(
                imageVector = Icons.Filled.Key,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(appSize.iconSize / 1.2f)
            )
            Text(
                text = userProfile.username,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = appSize.bodyTextSize
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = appSize.verticalPadding / 2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(appSize.horizontalPadding / 2)
        ) {
            Icon(
                imageVector = Icons.Filled.AssignmentInd,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(appSize.iconSize / 1.2f)
            )
            Text(
                text = userProfile.fullName,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = appSize.bodyTextSize
            )
        }
    }
}

@Composable
private fun LanguageRow(
    languageName: String,
    languageCode: String,
    isSelected: Boolean,
    isTablet: Boolean,
    onLanguageSelected: () -> Unit
) {
    val appSize = AppSize(isTablet = isTablet)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLanguageSelected() }
            .padding(vertical = appSize.verticalPadding / 1.5f),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val flagIconResId: Int
        var imageTint: Color? = null

        when (languageCode) {
            Constants.LANGUAGE_CODE_INDONESIAN -> flagIconResId = R.drawable.id_flag_ic
            Constants.LANGUAGE_CODE_ENGLISH -> {
                flagIconResId = R.drawable.world_flag_ic
                imageTint = Color.Gray
            }

            else -> {
                flagIconResId = R.drawable.world_flag_ic
                imageTint = Color.DarkGray
            }
        }

        Image(
            painter = painterResource(id = flagIconResId),
            contentDescription = languageName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(appSize.iconSize / 1.2f)
                .clip(CircleShape),
            colorFilter = if (imageTint != null) ColorFilter.tint(imageTint) else null
        )
        Spacer(modifier = Modifier.width(appSize.horizontalPadding / 2))
        Text(
            text = "($languageCode) $languageName",
            fontSize = appSize.bodyTextSize,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Selected Language",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(appSize.iconSize / 1.2f)
            )
        } else {
            Spacer(modifier = Modifier.size(appSize.iconSize / 1.2f))
        }
    }
}