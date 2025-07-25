@file:OptIn(DelicateCoroutinesApi::class)

package com.jc.presentation.ui.screens.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun SignInScreen(
    onNavigateToMain: () -> Unit = {},
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
                SignInContent(
                    onSignIn = onNavigateToMain,
                    isTablet = isTablet
                )
            },
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
fun SignInContent(
    onSignIn: () -> Unit = {},
    isTablet: Boolean = false
) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var shouldSignIn by remember { mutableStateOf(false) }

    if (shouldSignIn) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500)
            isLoading = false
            onSignIn()
            shouldSignIn = false
        }
    }

    val titleSize = if (isTablet) 32.sp else 28.sp
    val subtitleSize = if (isTablet) 18.sp else 16.sp
    val buttonTextSize = if (isTablet) 18.sp else 16.sp
    val horizontalPadding = if (isTablet) 32.dp else 16.dp
    val topMargin = if (isTablet) 48.dp else 16.dp
    val fieldSpacing = if (isTablet) 40.dp else 16.dp
    val buttonHeight = if (isTablet) 56.dp else 48.dp
    val iconSize = if (isTablet) 24.dp else 20.dp

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (headerLogo, title, subtitle, userIdField, passwordField, signInButton) = createRefs()

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
            text = "Sign In",
            fontSize = titleSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(headerLogo.bottom, margin = topMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "Please sign in to your account",
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

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            enabled = !isLoading,
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding)
                .constrainAs(passwordField) {
                    top.linkTo(userIdField.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Button(
            onClick = {
                if (userId.isNotBlank() && password.isNotBlank()) {
                    isLoading = true
                    shouldSignIn = true
                }
            },
            enabled = userId.isNotBlank() && password.isNotBlank() && !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight)
                .padding(horizontal = horizontalPadding)
                .constrainAs(signInButton) {
                    top.linkTo(passwordField.bottom, margin = fieldSpacing)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(iconSize),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = "Sign In",
                    fontSize = buttonTextSize,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun SignInScreenPreview() {
    AppTheme {
        SignInScreen()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun SignInScreenDarkPreview() {
    AppTheme(darkTheme = true) {
        SignInScreen(isDarkTheme = true)
    }
}