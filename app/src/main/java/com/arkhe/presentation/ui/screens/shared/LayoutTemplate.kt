@file:OptIn(ExperimentalMaterial3Api::class)

package com.arkhe.presentation.ui.screens.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkhe.constraintlayout.R
import com.arkhe.core.utils.ConsLang
import com.arkhe.domain.model.ThemeMode
import com.arkhe.presentation.ui.components.CyclingThemeButton
import com.arkhe.presentation.ui.screens.shared.ext.UserProfile
import com.arkhe.presentation.ui.theme.AppSize
import com.arkhe.presentation.ui.theme.AppTheme
import com.arkhe.presentation.viewmodel.LanguageViewModel
import com.arkhe.presentation.viewmodel.ThemeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LayoutTemplate(
    headerPercent: Float = 0.10f,
    footerPercent: Float = 0.08f,
    isTablet: Boolean = false,
    contentHeader: @Composable () -> Unit = { DefaultHeaderContent(isTablet = isTablet) },
    contentMain: @Composable () -> Unit = { DefaultMainContent(isTablet = isTablet) },
    onAboutClick: () -> Unit = {},
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (header, main, footer) = createRefs()

        val topGuideline = createGuidelineFromTop(headerPercent)
        val bottomGuideline = createGuidelineFromBottom(footerPercent)

        HeaderSection(
            contentHeader = contentHeader,
            isTablet = isTablet,
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(topGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )

        MainSection(
            contentMain = contentMain,
            isTablet = isTablet,
            modifier = Modifier.constrainAs(main) {
                top.linkTo(topGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )

        FooterSection(
            isTablet = isTablet,
            onAboutClick = onAboutClick,
            onSettingsClick = { /* Default empty lambda */ },
            currentUserProfile = null,
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
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Changing language...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun HeaderSection(
    contentHeader: @Composable () -> Unit,
    isTablet: Boolean,
    modifier: Modifier = Modifier
) {
    val appSize = AppSize(isTablet = isTablet)
    Card(
        modifier = modifier.padding(appSize.verticalPadding / 2),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = appSize.cardElevation)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(appSize.horizontalPadding / 4)
        ) {
            contentHeader()
        }
    }
}

@Composable
fun MainSection(
    contentMain: @Composable () -> Unit,
    isTablet: Boolean,
    modifier: Modifier = Modifier,
) {
    val appSize = AppSize(isTablet = isTablet)
    Card(
        modifier = modifier.padding(appSize.horizontalPadding / 2.8f, appSize.verticalPadding / 10),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = appSize.cardElevation)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(appSize.horizontalPadding / 4)
        ) {
            contentMain()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FooterSection(
    isTablet: Boolean,
    onAboutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    currentUserProfile: UserProfile? = null,
    viewModelTheme: ThemeViewModel = koinViewModel(),
    viewModelLanguage: LanguageViewModel = koinViewModel(),
    modifier: Modifier
) {
    val appSize = AppSize(isTablet = isTablet)

    val uiStateTheme by viewModelTheme.uiState.collectAsStateWithLifecycle()

    val darkTheme = when (uiStateTheme.currentTheme) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.AUTOMATIC -> isSystemInDarkTheme()
    }

//    val userThemePreference by viewModelTheme.isDarkTheme.collectAsState()
//    val isCurrentlyDark = userThemePreference ?: isSystemInDarkTheme()

    Card(
        modifier = modifier.padding(appSize.verticalPadding / 2),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        shape = RoundedCornerShape(
            topStart = appSize.roundedCornerShapeSize,
            topEnd = appSize.roundedCornerShapeSize,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = appSize.cardElevation)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(appSize.horizontalPadding / 4)
        ) {
            val (appInfo, themeButton, settingsAndProfile) = createRefs()
            val copyright =
                " " + viewModelLanguage.getLocalizedString(ConsLang.COPYRIGHT) + " " +
                        viewModelLanguage.getLocalizedString(ConsLang.COMPANY)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(appInfo) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                IconButton(
                    onClick = { onAboutClick() },
                    modifier = Modifier
                        .size(appSize.iconSize * 1.1f)
                        .padding(appSize.horizontalPadding / 8)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ae_ic),
                        contentDescription = "ae_ic",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = copyright,
                    fontSize = appSize.captionTextSize * 0.8f,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 1.dp)
                )
            }

            IconButton(
                onClick = {
                    val newThemeState = !isCurrentlyDark
                    viewModelTheme.setTheme(newThemeState)
                },
                modifier = Modifier.constrainAs(themeButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                CyclingThemeButton(
                    currentTheme = uiStateTheme.currentTheme,
                    onCycleTheme = viewModelTheme.cycleTheme()
                )

                IconButton(onClick = { viewModelTheme.setTheme(!isCurrentlyDark) }) {
                    Icon(
                        imageVector = if (isCurrentlyDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle Theme",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(appSize.iconSize)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(settingsAndProfile) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                currentUserProfile?.username?.let {
                    Text(
                        text = it,
                        fontSize = appSize.captionTextSize * 0.8f,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
                IconButton(
                    onClick = { onSettingsClick() },
                    modifier = Modifier
                        .size(appSize.iconSize / 1.2f)
                ) {
                    Icon(
                        imageVector = if (currentUserProfile == null) Icons.Filled.Settings else Icons.Filled.ManageAccounts,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(appSize.iconSize / 1.2f)
                    )
                }
            }
        }
    }
}

@Composable
fun DefaultHeaderContent(
    isTablet: Boolean,
    viewModelLanguage: LanguageViewModel = koinViewModel(),
) {
    val appSize = AppSize(isTablet = isTablet)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(appSize.horizontalPadding / 4)
    ) {
        val (appName, signOutButton) = createRefs()

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(appName) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ae_ic),
                contentDescription = "ae_ic",
                modifier = Modifier
                    .size(appSize.iconSize * 1.3f)
                    .padding(appSize.horizontalPadding / 8),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentScale = ContentScale.Fit
            )
            Text(
                text = viewModelLanguage.getLocalizedString(ConsLang.COMPANY_NAME),
                fontSize = appSize.bodyTextSize * 1.3f,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 1.dp)
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier.constrainAs(signOutButton) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = "Sign Out",
                modifier = Modifier.size(appSize.iconSize)
            )
        }
    }
}

@Composable
fun DefaultMainContent(
    isTablet: Boolean,
    viewModelLanguage: LanguageViewModel = koinViewModel(),
) {
    val appSize = AppSize(isTablet = isTablet)
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (welcomeText, descriptionText, sampleCard) = createRefs()

        Text(
            text = viewModelLanguage.getLocalizedString(ConsLang.HELLO_WORLD),
            fontSize = appSize.titleSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(welcomeText) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = viewModelLanguage.getLocalizedString(ConsLang.TEMPLATE_DESCRIPTION),
            textAlign = TextAlign.Center,
            fontSize = appSize.bodyTextSize,
            modifier = Modifier
                .padding(horizontal = appSize.horizontalPadding)
                .constrainAs(descriptionText) {
                    top.linkTo(welcomeText.bottom, margin = appSize.verticalPadding)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(appSize.horizontalPadding)
                .constrainAs(sampleCard) {
                    top.linkTo(descriptionText.bottom, margin = appSize.verticalPadding)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(appSize.horizontalPadding),
            ) {
                Text(
                    text = viewModelLanguage.getLocalizedString(ConsLang.FRAMEWORK),
                    fontSize = appSize.subtitleSize,
                    fontWeight = FontWeight.SemiBold
                )
                Image(
                    painter = painterResource(id = R.drawable.splash_ic),
                    contentDescription = "Sample Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(appSize.imageHeight)
                        .padding(vertical = appSize.verticalPadding / 2)
                )
                Spacer(modifier = Modifier.height(appSize.verticalPadding / 2))
                Text(
                    text = viewModelLanguage.getLocalizedString(ConsLang.FRAMEWORK_NAME),
                    fontSize = appSize.bodyTextSize,
                )
                Image(
                    painter = painterResource(id = R.drawable.powered_ic),
                    contentDescription = "Sample Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(appSize.imageHeight / 4)
                        .padding(vertical = appSize.verticalPadding / 2)
                )
            }
        }
    }
}

@Preview(
    name = "Smartphone",
    widthDp = 360,
    heightDp = 640,
    showBackground = true,
)
@Composable
fun LayoutLightPreview() {
    AppTheme {
        LayoutTemplate()
    }
}