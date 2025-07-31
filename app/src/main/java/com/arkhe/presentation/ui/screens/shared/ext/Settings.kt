package com.arkhe.presentation.ui.screens.shared.ext

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkhe.constraintlayout.R
import com.arkhe.core.utils.LanguageManager
import com.arkhe.model.language.Language
import com.arkhe.model.language.Languages
import com.arkhe.presentation.ui.theme.AppSize
import com.arkhe.presentation.viewmodel.LanguageViewModel
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
    viewModel: LanguageViewModel = koinViewModel()
) {
    val appSize = AppSize(isTablet = isTablet)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val languageState by viewModel.languageState.collectAsState()

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
            selectedLanguage = languageState.currentLanguage,
            onLanguageSelected = viewModel::selectLanguage,
        )
    }
}

@Composable
private fun SettingsSheetContent(
    isTablet: Boolean,
    userProfile: UserProfile?,
    selectedLanguage: Language,
    onLanguageSelected: (Language) -> Unit,
) {
    val appSize = AppSize(isTablet = isTablet)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = appSize.horizontalPadding * 3f)
            .padding(bottom = appSize.verticalPadding)
            .navigationBarsPadding()
    ) {
        val title = if (userProfile == null)
            LanguageManager.getLocalizedString(
                context,
                R.string.settings_title,
                selectedLanguage.code
            )
        else LanguageManager.getLocalizedString(
            context,
            R.string.settings_profile_title,
            selectedLanguage.code
        )

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
                title = LanguageManager.getLocalizedString(
                    context,
                    R.string.settings_section_profile,
                    selectedLanguage.code
                ),
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
            title = LanguageManager.getLocalizedString(
                context,
                R.string.select_language,
                selectedLanguage.code
            ),
            isTablet = isTablet
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(appSize.verticalPadding)
        ) {
            LazyColumn {
                items(Languages.availableLanguages) { language ->
                    LanguageItem(
                        language = language,
                        isSelected = language.code == selectedLanguage.code,
                        onLanguageSelected = onLanguageSelected
                    )
                }
            }
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
private fun LanguageItem(
    language: Language,
    isSelected: Boolean,
    onLanguageSelected: (Language) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLanguageSelected(language) }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = language.nativeName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = language.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}