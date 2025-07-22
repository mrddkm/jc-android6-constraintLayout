@file:OptIn(DelicateCoroutinesApi::class)

package com.jc.presentation.ui.screens.auth.activation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jc.constraintlayout.R
import com.jc.presentation.ui.screens.shared.FooterSection
import com.jc.presentation.ui.screens.shared.MainSection
import com.jc.presentation.ui.theme.AppTheme
import kotlinx.coroutines.DelicateCoroutinesApi

@Composable
fun ActivationScreen(
    onNavigateToSignIn: () -> Unit = {},
    onThemeToggle: () -> Unit = {},
    isDarkTheme: Boolean = false,
    footerPercent: Float = 0.08f,
    isTablet: Boolean = false
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (main, footer) = createRefs()
        val bottomGuideline = createGuidelineFromBottom(footerPercent)

        MainSection(
            contentMain = {
                ActivationContent(
                    onActivate = onNavigateToSignIn,
                    isTablet = isTablet
                )
            },
            isDarkTheme = isDarkTheme,
            modifier = Modifier.constrainAs(main) {
                top.linkTo(parent.top)
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
fun ActivationContent(
    onActivate: () -> Unit = {},
    isTablet: Boolean = false
) {
    var userId by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var shouldActivate by remember { mutableStateOf(false) }

    if (shouldActivate) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500)
            isLoading = false
            onActivate()
            shouldActivate = false
        }
    }

    val titleSize = if (isTablet) 32.sp else 28.sp
    val subtitleSize = if (isTablet) 18.sp else 16.sp
    val buttonTextSize = if (isTablet) 18.sp else 16.sp
    val horizontalPadding = if (isTablet) 32.dp else 16.dp
    val topMargin = if (isTablet) 48.dp else 16.dp
    val fieldSpacing = if (isTablet) 40.dp else 16.dp

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (headerLogo, title, subtitle, userIdField, activateButton) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.app_ic),
            contentDescription = "App Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.constrainAs(headerLogo){
                top.linkTo(parent.top, margin = topMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
                .size(if (isTablet) 120.dp else 80.dp)
        )

        Text(
            text = "Activation",
            fontSize = titleSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(headerLogo.bottom, margin = fieldSpacing)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "Please enter User ID to activate",
            fontSize = subtitleSize,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(horizontal = horizontalPadding)
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("User ID") },
            placeholder = { Text("Enter your User ID") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding)
                .constrainAs(userIdField) {
                    top.linkTo(subtitle.bottom, margin = fieldSpacing)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Button(
            onClick = {
                if (userId.isNotBlank()) {
                    isLoading = true
                    shouldActivate = true
                }
            },
            enabled = userId.isNotBlank() && !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isTablet) 56.dp else 48.dp)
                .padding(horizontal = horizontalPadding)
                .constrainAs(activateButton) {
                    top.linkTo(userIdField.bottom, margin = fieldSpacing)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(if (isTablet) 24.dp else 20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = "Activate Account",
                    fontSize = buttonTextSize,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun ActivationScreenPreview() {
    AppTheme {
        ActivationScreen()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun ActivationScreenDarkPreview() {
    AppTheme(darkTheme = true) {
        ActivationScreen(isDarkTheme = true)
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 1200)
@Composable
fun ActivationScreenTabletPreview() {
    AppTheme {
        ActivationScreen(
            isTablet = true,
            footerPercent = 0.07f
        )
    }
}