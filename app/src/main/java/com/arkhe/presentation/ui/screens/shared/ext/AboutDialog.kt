@file:OptIn(ExperimentalMaterial3Api::class)

package com.arkhe.presentation.ui.screens.shared.ext

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.arkhe.base.R
import com.arkhe.presentation.ui.theme.AppSize
import com.arkhe.presentation.ui.theme.AppTheme

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun AboutDialog(
    onDismissRequest: () -> Unit,
    isTablet: Boolean = false,
) {
    val appSize = AppSize(isTablet = isTablet)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val dialogWidth = screenWidth * 0.9f
    val dialogHeight = screenHeight * 0.7f

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .size(width = dialogWidth, height = dialogHeight),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                AboutDialogContent(
                    onDismiss = onDismissRequest,
                )
            }
        }
    }
}

@Composable
private fun AboutDialogContent(
    onDismiss: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (header, scrollableContent, footer) = createRefs()
        HeaderSection(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        ScrollableContentSection(
            modifier = Modifier.constrainAs(scrollableContent) {
                top.linkTo(header.bottom)
                bottom.linkTo(footer.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )
        FooterSection(
            onDismiss = onDismiss,
            modifier = Modifier.constrainAs(footer) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier.padding(10.dp)
    ) {
        val (logo, title) = createRefs()
        Card(
            modifier = Modifier
                .size(50.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.splash_ic),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
        Text(
            text = "Arkhe {base}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(logo.bottom, margin = 2.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
private fun ScrollableContentSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        AppDescriptionCard()
        DeveloperInfoCard()
        FrameworkInfoCard()
        VersionInfoCard()
    }
}

@Composable
private fun AppDescriptionCard() {
    Column {
        Text(
            text = "Tripkeun",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "An exclusive travel for a limited audience, providing premium travel experiences with personalized services and selected destinations.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
        )
    }
}

@Composable
private fun DeveloperInfoCard() {
    Column {
        Text(
            text = "Developed by Gaenta",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
        )
        Card(
            modifier = Modifier
                .size(16.dp),
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF9C27B0), Color(0xFFE91E63))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ae_ic),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Text(
            text = "Gaenta - Experienced development team that prioritizes quality and innovation in every digital product.",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 10.sp,
            lineHeight = 12.sp,
        )
    }
}

@Composable
private fun FrameworkInfoCard() {
    Column {
        Text(
            text = "Built with arkhe {base} Framework",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
        )

        Card(
            modifier = Modifier
                .size(16.dp),
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF3F51B5), Color(0xFF2196F3))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Replace with actual logo: painterResource(R.drawable.ic_arkhe_logo)
                Icon(
                    imageVector = Icons.Default.Layers,
                    contentDescription = "Arkhe Logo",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Text(
            text = "Built using Arkhe Framework - A robust and scalable base framework for modern mobile app development.",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 10.sp,
            lineHeight = 12.sp,
        )
    }
}

@Composable
private fun VersionInfoCard() {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (divider, version, copyright) = createRefs()

        HorizontalDivider(
            modifier = Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )

        Text(
            text = "Version 1.0.0",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(version) {
                top.linkTo(divider.bottom, margin = 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = "© 2024 Tripkeun Indonesia",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(copyright) {
                top.linkTo(version.bottom, margin = 4.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
private fun FooterSection(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
    ) {
        Button(
            onClick = onDismiss,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Close",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripkeunAboutDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Simulate some background content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Background Content",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "This will be blurred when dialog is shown",
                    style = MaterialTheme.typography.bodyLarge
                )

                if (!showDialog) {
                    Button(
                        onClick = { showDialog = true }
                    ) {
                        Text("Show About Dialog")
                    }
                }
            }

            if (showDialog) {
                AboutDialog(
                    onDismissRequest = { showDialog = false },
                )
            }
        }
    }
}