package com.jc.presentation.ui.screens.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jc.presentation.ui.screens.payment.ext.QRISPaymentContent
import com.jc.presentation.ui.screens.shared.MainSection
import com.jc.presentation.ui.theme.AppTheme

@Composable
fun PaymentQRISScreen(
    onPaymentConfirm: () -> Unit = {},
    isDarkTheme: Boolean = false,
    headerPercent: Float = 0.00f,
    footerPercent: Float = 0.00f,
    isTablet: Boolean = false
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (main) = createRefs()

        val topGuideline = createGuidelineFromTop(headerPercent)
        val bottomGuideline = createGuidelineFromBottom(footerPercent)

        MainSection(
            contentMain = {
                QRISMainSection(
                    onPaymentConfirm = onPaymentConfirm,
                    isTablet = isTablet
                )
            },
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
    }
}

@Composable
fun QRISMainSection(
    onPaymentConfirm: () -> Unit,
    isTablet: Boolean = false
) {
    val buttonTextSize = if (isTablet) 18.sp else 16.sp
    val horizontalPadding = if (isTablet) 32.dp else 8.dp
    val verticalPadding = if (isTablet) 32.dp else 8.dp
    val allPadding = if (isTablet) 8.dp else 4.dp
    val amountSize = if (isTablet) 32.sp else 28.sp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = horizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QRISPaymentContent(
            isTablet = isTablet
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(allPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Nominal Bayar",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Rp100.000",
                fontSize = amountSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.weight(0.2f)
                ) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Button(
                    onClick = onPaymentConfirm,
                    modifier = Modifier.weight(0.8f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Confirmation",
                        fontSize = buttonTextSize,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun QRISPaymentPreview() {
    AppTheme {
        PaymentQRISScreen()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaymentQrisScreenTabletPreview() {
    AppTheme(darkTheme = true) {
        PaymentQRISScreen(
            isDarkTheme = true
        )
    }
}