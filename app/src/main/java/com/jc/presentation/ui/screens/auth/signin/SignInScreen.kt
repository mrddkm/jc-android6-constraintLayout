@file:OptIn(DelicateCoroutinesApi::class)

package com.jc.presentation.ui.screens.auth.signin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.jc.presentation.ui.screens.shared.FooterSection
import com.jc.presentation.ui.screens.shared.MainSection
import com.jc.presentation.ui.theme.ConstraintLayoutTheme
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

        // FOOTER SECTION (10%)
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

    // Responsive sizing based on device type
    val titleSize = if (isTablet) 32.sp else 28.sp
    val subtitleSize = if (isTablet) 18.sp else 16.sp
    val buttonTextSize = if (isTablet) 18.sp else 16.sp
    val helpTextSize = if (isTablet) 16.sp else 14.sp
    val smallTextSize = if (isTablet) 14.sp else 12.sp
    val horizontalPadding = if (isTablet) 32.dp else 16.dp
    val topMargin = if (isTablet) 48.dp else 32.dp
    val fieldSpacing = if (isTablet) 40.dp else 32.dp
    val buttonHeight = if (isTablet) 56.dp else 48.dp
    val iconSize = if (isTablet) 24.dp else 20.dp

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (title, subtitle, userIdField, passwordField, signInButton, forgotPassword, helpCard) = createRefs()

        // Title
        Text(
            text = "Sign In",
            fontSize = titleSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = topMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Subtitle
        Text(
            text = "Welcome back! Please sign in to your account",
            fontSize = subtitleSize,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = horizontalPadding)
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // User ID Input Field
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

        // Password Input Field
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
                    top.linkTo(userIdField.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // Sign In Button
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
                    top.linkTo(passwordField.bottom, margin = 24.dp)
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

        // Forgot Password
        TextButton(
            onClick = { /* Handle forgot password */ },
            enabled = !isLoading,
            modifier = Modifier.constrainAs(forgotPassword) {
                top.linkTo(signInButton.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = "Forgot Password?",
                fontSize = helpTextSize
            )
        }

        // Help Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontalPadding)
                .constrainAs(helpCard) {
                    top.linkTo(forgotPassword.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(if (isTablet) 20.dp else 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Having trouble signing in?",
                    fontSize = helpTextSize,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Make sure your User ID is activated and your password is correct",
                    fontSize = smallTextSize,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    ConstraintLayoutTheme {
        SignInScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenDarkPreview() {
    ConstraintLayoutTheme(darkTheme = true) {
        SignInScreen(isDarkTheme = true)
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 1200)
@Composable
fun SignInScreenTabletPreview() {
    ConstraintLayoutTheme {
        SignInScreen(
            isTablet = true,
            footerPercent = 0.07f
        )
    }
}