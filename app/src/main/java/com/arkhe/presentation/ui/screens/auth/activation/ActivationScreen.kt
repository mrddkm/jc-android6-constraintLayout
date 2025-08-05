package com.arkhe.presentation.ui.screens.auth.activation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.arkhe.base.R
import com.arkhe.core.utils.ConsLang
import com.arkhe.domain.model.ThemeMode
import com.arkhe.presentation.state.ThemeUiState
import com.arkhe.presentation.ui.screens.shared.FooterSection
import com.arkhe.presentation.ui.screens.shared.LoadingScreen
import com.arkhe.presentation.ui.screens.shared.MainSection
import com.arkhe.presentation.ui.theme.AppSize
import com.arkhe.presentation.viewmodel.LanguageViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActivationScreen(
    onNavigateToSignIn: () -> Unit = {},
    footerPercent: Float = 0.08f,
    isTablet: Boolean = false,
    viewModelLanguage: LanguageViewModel = koinViewModel(),
    uiStateTheme: ThemeUiState,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    val languageState by viewModelLanguage.languageState.collectAsState()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (main, footer) = createRefs()
        val bottomGuideline = createGuidelineFromBottom(footerPercent)

        AnimatedContent(
            targetState = languageState.isChangingLanguage,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "language_change_animation"
        ) { isLoading ->
            if (isLoading) {
                LoadingScreen()
            } else {
                MainSection(
                    contentMain = {
                        ActivationContent(
                            onActivate = onNavigateToSignIn,
                            isTablet = isTablet,
                            viewModelLanguage = viewModelLanguage
                        )
                    },
                    isTablet = isTablet,
                    modifier = Modifier.constrainAs(main) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(bottomGuideline)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )
            }
        }

        FooterSection(
            isTablet = isTablet,
            currentUserProfile = null,
            uiStateTheme = uiStateTheme,
            onThemeSelected = onThemeSelected,
            modifier = Modifier.constrainAs(footer) {
                top.linkTo(bottomGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
fun ActivationContent(
    onActivate: () -> Unit = {},
    isTablet: Boolean = false,
    viewModelLanguage: LanguageViewModel
) {
    val appSize = AppSize(isTablet = isTablet)

    var userId by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var shouldActivate by remember { mutableStateOf(false) }
    val userIdFocusRequester = remember { FocusRequester() }

    if (shouldActivate) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500)
            isLoading = false
            onActivate()
            shouldActivate = false
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (headerLogo, title, subtitle, userIdField, activateButton) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.app_ic),
            contentDescription = "App Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .constrainAs(headerLogo) {
                    top.linkTo(parent.top, margin = appSize.screenTopMargin)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(appSize.logoSize)
        )

        Text(
            text = viewModelLanguage.getLocalizedString(ConsLang.ACTIVATION_TITLE),
            fontSize = appSize.titleSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(headerLogo.bottom, margin = appSize.fieldSpacing)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = viewModelLanguage.getLocalizedString(ConsLang.ACTIVATION_SUBTITLE),
            fontSize = appSize.subtitleSize,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(horizontal = appSize.horizontalPadding)
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text(stringResource(R.string.user_id)) },
            placeholder = { Text(viewModelLanguage.getLocalizedString(ConsLang.USERID_PLACEHOLDER)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = appSize.horizontalPadding)
                .focusRequester(userIdFocusRequester)
                .constrainAs(userIdField) {
                    top.linkTo(subtitle.bottom, margin = appSize.fieldSpacing)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = appSize.cardElevation),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = appSize.horizontalPadding)
                .constrainAs(activateButton) {
                    top.linkTo(userIdField.bottom, margin = appSize.fieldSpacing)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = {
                        userId = ""
                        userIdFocusRequester.requestFocus()
                    },
                    modifier = Modifier
                        .weight(0.2f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(appSize.iconSizeDp)
                    )
                }
                Button(
                    onClick = {
                        if (userId.isNotBlank()) {
                            isLoading = true
                            shouldActivate = true
                        }
                    },
                    enabled = userId.isNotBlank() && !isLoading,
                    modifier = Modifier
                        .weight(0.8f)
                        .height(appSize.buttonHeight),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = appSize.roundedCornerShapeSize,
                        bottomStart = 0.dp,
                        bottomEnd = appSize.roundedCornerShapeSize
                    ),
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(appSize.circularProgressIndicatorSize),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = viewModelLanguage.getLocalizedString(ConsLang.SUBMIT_BUTTON),
                            fontSize = appSize.buttonTextSize,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

/*@Preview(
    name = "Smartphone",
    widthDp = 360,
    heightDp = 640,
    showBackground = true,
)
@Composable
fun ActivationScreenPreview() {
    AppTheme {
        ActivationScreen()
    }
}*/
