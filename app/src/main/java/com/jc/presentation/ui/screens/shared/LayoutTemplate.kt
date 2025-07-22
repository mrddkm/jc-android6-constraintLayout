@file:OptIn(ExperimentalMaterial3Api::class)

package com.jc.presentation.ui.screens.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jc.presentation.ui.theme.AppTheme

@Composable
fun LayoutTemplate(
    onThemeToggle: () -> Unit = {},
    isDarkTheme: Boolean = false,
    headerPercent: Float = 0.10f,
    footerPercent: Float = 0.07f,
    contentHeader: @Composable () -> Unit = { DefaultHeaderContent() },
    contentMain: @Composable () -> Unit = { DefaultMainContent() }
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isDarkTheme) Color(0xFF121212)
                else Color(0xFFF5F5F5)
            )
    ) {
        val (header, main, footer) = createRefs()

        val topGuideline = createGuidelineFromTop(headerPercent)
        val bottomGuideline = createGuidelineFromBottom(footerPercent)

        HeaderSection(
            contentHeader = contentHeader,
            isDarkTheme = isDarkTheme,
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
            isDarkTheme = isDarkTheme,
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
            onThemeToggle = onThemeToggle,
            isDarkTheme = isDarkTheme,
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
fun HeaderSection(
    contentHeader: @Composable () -> Unit,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF1E1E1E)
            else Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            contentHeader()
        }
    }
}

@Composable
fun MainSection(
    contentMain: @Composable () -> Unit,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF1E1E1E)
            else Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            contentMain()
        }
    }
}

@Composable
fun FooterSection(
    onThemeToggle: () -> Unit,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF1E1E1E)
            else Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            val (appInfo, themeButton, versionText) = createRefs()

            Text(
                text = "Gaenta © 2025",
                fontSize = 10.sp,
                color = if (isDarkTheme) Color.Gray else Color.DarkGray,
                modifier = Modifier.constrainAs(appInfo) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            IconButton(
                onClick = onThemeToggle,
                modifier = Modifier.constrainAs(themeButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Icon(
                    if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "Toggle Theme",
                    tint = if (isDarkTheme) Color.White else Color.Black
                )
            }

            Text(
                text = "v1.0.0",
                fontSize = 10.sp,
                color = if (isDarkTheme) Color.Gray else Color.DarkGray,
                modifier = Modifier.constrainAs(versionText) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}

@Composable
fun DefaultHeaderContent() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
    ) {
        val (appName, signOutButton) = createRefs()

        Text(
            text = "Parkir App",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(appName) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        IconButton(
            onClick = {},
            modifier = Modifier.constrainAs(signOutButton) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "Sign Out"
            )
        }
    }
}

@Composable
fun DefaultMainContent() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (welcomeText, descriptionText, sampleCard) = createRefs()

        Text(
            text = "Hello World!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(welcomeText) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "Template layout ConstraintLayout",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(descriptionText) {
                    top.linkTo(welcomeText.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .constrainAs(sampleCard) {
                    top.linkTo(descriptionText.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Content",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Section of business logic or information.",
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun LayoutLightPreview() {
    AppTheme {
        LayoutTemplate()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun LayoutDarkPreview() {
    AppTheme(darkTheme = true) {
        LayoutTemplate(isDarkTheme = true)
    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 800)
@Composable
fun ConstraintLayoutTemplateTabletPreview() {
    AppTheme {
        LayoutTemplate(
            isDarkTheme = true
        )
    }
}