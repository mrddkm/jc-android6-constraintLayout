@file:OptIn(DelicateCoroutinesApi::class)

package com.jc.presentation.ui.screens.auth.signin

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
import androidx.compose.material.icons.outlined.Abc
import androidx.compose.material.icons.outlined.Password
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jc.constraintlayout.R
import com.jc.presentation.ui.screens.shared.ext.AboutDialog
import com.jc.presentation.ui.screens.shared.FooterSection
import com.jc.presentation.ui.screens.shared.MainSection
import com.jc.presentation.ui.theme.AppSize
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
    var showAboutDialog by remember { mutableStateOf(false) }
    
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

        FooterSection(
            onThemeToggle = onThemeToggle,
            isDarkTheme = isDarkTheme,
            isTablet = isTablet,
            onAboutClick = { showAboutDialog = true },
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

    if (showAboutDialog) {
        AboutDialog(
            onDismissRequest = { showAboutDialog = false },
            isTablet = isTablet
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
    val userIdFocusRequester = remember { FocusRequester() }

    if (shouldSignIn) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500)
            isLoading = false
            onSignIn()
            shouldSignIn = false
        }
    }

    val appSize = AppSize(isTablet = isTablet)

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (headerLogo, title, subtitle, userIdField, passwordField, signInButton) = createRefs()

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
            text = "Sign In",
            fontSize = appSize.titleSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(headerLogo.bottom, margin = appSize.screenTopMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "Please sign in to your account",
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
            label = { Text("User ID") },
            placeholder = { Text("Enter your User ID") },
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
                        imageVector = if (passwordVisible) Icons.Outlined.Abc else Icons.Outlined.Password,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        modifier = Modifier.size(appSize.iconSize)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = appSize.horizontalPadding)
                .constrainAs(passwordField) {
                    top.linkTo(userIdField.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = appSize.cardElevation),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = appSize.horizontalPadding)
                .constrainAs(signInButton) {
                    top.linkTo(passwordField.bottom, margin = appSize.fieldSpacing)
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
                        password = ""
                        userIdFocusRequester.requestFocus()
                    },
                    modifier = Modifier
                        .weight(0.2f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(appSize.iconSize)
                    )
                }

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
                            text = "Sign In",
                            fontSize = appSize.buttonTextSize,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "SUNMI V1s",
    widthDp = 360,
    heightDp = 640,
    showBackground = true,
)
@Composable
fun SignInScreenPreview() {
    AppTheme {
        SignInScreen()
    }
}

/*
@Preview(
    name = "SUNMI V1s Dark",
    widthDp = 360,
    heightDp = 640,
    showBackground = true,
)
@Composable
fun SignInScreenDarkPreview() {
    AppTheme(darkTheme = true) {
        SignInScreen(isDarkTheme = true)
    }
}
*/