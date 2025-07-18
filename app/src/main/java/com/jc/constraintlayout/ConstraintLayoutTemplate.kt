@file:OptIn(ExperimentalMaterial3Api::class)

package com.jc.constraintlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintLayoutTemplate(
    title: String = "Template Layout",
    onBackClick: () -> Unit = {},
    onThemeToggle: () -> Unit = {},
    isDarkTheme: Boolean = false,
    headerPercent: Float = 0.15f,
    footerPercent: Float = 0.10f,
    content: @Composable () -> Unit = { DefaultMainContent() }
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

        // HEADER SECTION (flexible %)
        HeaderSection(
            title = title,
            onBackClick = onBackClick,
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

        // MAIN SECTION (flexible %)
        MainSection(
            content = content,
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

        // FOOTER SECTION (flexible %)
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
    title: String,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF1E1E1E)
            else Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (backButton, titleText, profileImage) = createRefs()

            IconButton(
                onClick = onBackClick,
                modifier = Modifier.constrainAs(backButton) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = if (isDarkTheme) Color.White else Color.Black
                )
            }

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color.Black,
                modifier = Modifier.constrainAs(titleText) {
                    start.linkTo(backButton.end, margin = 16.dp)
                    end.linkTo(profileImage.start, margin = 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                },
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (isDarkTheme) Color.Gray else Color.LightGray,
                        RoundedCornerShape(20.dp)
                    )
                    .constrainAs(profileImage) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "U",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MainSection(
    content: @Composable () -> Unit,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF1E1E1E)
            else Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            content()
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
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF1E1E1E)
            else Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val (appInfo, themeButton, versionText) = createRefs()

            Text(
                text = "MyApp © 2024",
                fontSize = 12.sp,
                color = if (isDarkTheme) Color.Gray else Color.DarkGray,
                modifier = Modifier.constrainAs(appInfo) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            // Theme Toggle Button
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
                fontSize = 12.sp,
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

@Composable
fun ExampleUsage() {
    var isDarkTheme by remember { mutableStateOf(false) }

    ConstraintLayoutTemplate(
        title = "Dashboard",
        onBackClick = { /* Handle back navigation */ },
        onThemeToggle = { isDarkTheme = !isDarkTheme },
        isDarkTheme = isDarkTheme
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Custom Main Content",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Action */ }
            ) {
                Text("Sample Button")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutTemplatePreview() {
    MaterialTheme {
        ConstraintLayoutTemplate()
    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 800)
@Composable
fun ConstraintLayoutTemplateTabletPreview() {
    MaterialTheme {
        ConstraintLayoutTemplate(
            title = "Tablet View",
            isDarkTheme = true
        )
    }
}