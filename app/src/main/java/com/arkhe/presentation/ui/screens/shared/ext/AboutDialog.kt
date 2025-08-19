@file:OptIn(ExperimentalMaterial3Api::class)

package com.arkhe.presentation.ui.screens.shared.ext

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.arkhe.base.R
import com.arkhe.core.utils.ConsLang
import com.arkhe.presentation.ui.components.SourceCodePro
import com.arkhe.presentation.ui.theme.AppSize
import com.arkhe.presentation.ui.theme.AppTheme
import com.arkhe.presentation.viewmodel.LanguageViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun AboutDialog(
    onDismissRequest: () -> Unit,
    isTablet: Boolean = false,
) {
    val appSize = AppSize(isTablet = isTablet)
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = appSize.horizontalPadding,
                    vertical = appSize.verticalPadding
                ),
            shape = RoundedCornerShape(appSize.roundedCornerShapeSize),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = appSize.cardElevation)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                HeaderSection(isTablet = isTablet)
                ContentSection(isTablet = isTablet)
                FooterSection(onDismiss = onDismissRequest)
            }
        }
    }
}

@Composable
private fun HeaderSection(isTablet: Boolean = false) {
    val appSize = AppSize(isTablet = isTablet)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = appSize.horizontalPadding,
                vertical = appSize.verticalPadding / 2
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_ic),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(appSize.logoSize / 1.2f)
                .padding(10.dp)
        )
    }
}

@Composable
private fun ContentSection(isTablet: Boolean = false) {
    val appSize = AppSize(isTablet = isTablet)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = appSize.horizontalPadding,
                vertical = appSize.verticalPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(appSize.fieldSpacing),
    ) {
        AppDescriptionCard(isTablet = isTablet)
        DevelopedFor(isTablet = isTablet)
        TextWithInlineIcon(isTablet = isTablet)
        VersionInfoCard()
    }
}

@Composable
private fun AppDescriptionCard(isTablet: Boolean = false) {
    val appSize = AppSize(isTablet = isTablet)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = appSize.horizontalPadding,
                vertical = appSize.verticalPadding / 6
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(appSize.fieldSpacing / 4),
    ) {
        Text(
            text = "Tripkeun Indonesia",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "An exclusive travel for a limited audience, providing premium travel experiences with personalized services and selected destinations.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DevelopedFor(isTablet: Boolean = false) {
    val appSize = AppSize(isTablet = isTablet)
    HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = appSize.horizontalPadding,
                vertical = appSize.verticalPadding / 6
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(appSize.fieldSpacing / 4),
    ) {
        Text(
            text = "\"This application was developed for Tripkeun Indonesia to support its business activities within a limited audience.\"",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal,
            fontFamily = SourceCodePro
        )
    }
}

@Composable
fun TextWithInlineIcon(isTablet: Boolean = false) {
    val appSize = AppSize(isTablet = isTablet)
    val textVerticalAlign = PlaceholderVerticalAlign.TextCenter
    val iconSize = appSize.iconSizeSp / 1.5f
    val gaentaIconId = "gaentaIcon"
    val arkheIconId = "arkheIcon"
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = appSize.bodyTextSize,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
            )
        ) {
            append(" Built using ")
        }
        appendInlineContent(arkheIconId, "arkhe")
        withStyle(
            style = SpanStyle(
                fontSize = appSize.bodyTextSize,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
            )
        ) {
            append(" Arkhe {base} Framework.")
        }
    }
    val inlineContent = mapOf(
        Pair(
            gaentaIconId,
            InlineTextContent(
                Placeholder(iconSize, iconSize, textVerticalAlign)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ae_ic),
                    contentDescription = null,
                    modifier = Modifier.size(iconSize.value.dp),
                    contentScale = ContentScale.Fit
                )
            }
        ),
        Pair(
            arkheIconId,
            InlineTextContent(
                Placeholder(iconSize, iconSize, textVerticalAlign)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_ic),
                    contentDescription = null,
                    modifier = Modifier.size(iconSize.value.dp),
                    contentScale = ContentScale.Fit
                )
            }
        )
    )
    Text(
        text = annotatedString,
        inlineContent = inlineContent,
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.Normal,
        fontFamily = SourceCodePro,
        modifier = Modifier.padding(
            horizontal = appSize.horizontalPadding,
            vertical = appSize.verticalPadding / 6
        )
    )
}

@Composable
fun VersionInfoCard(
    isTablet: Boolean = false,
    viewModelLanguage: LanguageViewModel = koinViewModel(),
) {
    val appSize = AppSize(isTablet = isTablet)
    val copyright = " " + viewModelLanguage.getLocalizedString(ConsLang.COPYRIGHT)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = appSize.horizontalPadding,
                vertical = appSize.verticalPadding / 6
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(appSize.fieldSpacing / 4),
    ) {
        Text(
            text = "Version 1.0.0",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal,
            fontFamily = SourceCodePro,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ae_ic),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)),
                modifier = Modifier.size(appSize.iconSizeDp / 1.3f),
                contentScale = ContentScale.Fit
            )
            Text(
                text = copyright,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun FooterSection(onDismiss: () -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun TripkeunAboutDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    AppTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
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