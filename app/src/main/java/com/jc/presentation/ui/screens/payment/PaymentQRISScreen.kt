package com.jc.presentation.ui.screens.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jc.presentation.ui.screens.payment.ext.QRISSimple
import com.jc.presentation.ui.screens.shared.MainSection
import com.jc.presentation.ui.theme.AppSize
import com.jc.presentation.ui.theme.AppTheme

@Composable
fun PaymentQRISScreen(
    onPaymentConfirm: () -> Unit = {},
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
    }
}

@Composable
fun QRISMainSection(
    onPaymentConfirm: () -> Unit,
    isTablet: Boolean = false
) {
    val appSize = AppSize(isTablet = isTablet)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = appSize.horizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QRISSimple(
            isTablet = isTablet,
            merchantName = "Gaenta Sinergi Sukses, PT",
            nmid = "IDXXXXXXXXX"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(appSize.horizontalPadding / 2),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Nominal Bayar",
                fontSize = appSize.captionTextSize,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Rp100.000",
                fontSize = appSize.titleSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = appSize.cardElevation),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = appSize.horizontalPadding,
                    vertical = appSize.verticalPadding
                ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    appSize.horizontalPadding / 2,
                    Alignment.CenterHorizontally
                ),
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.weight(0.2f)
                ) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(appSize.iconSize)
                    )
                }
                Button(
                    onClick = onPaymentConfirm,
                    modifier = Modifier
                        .weight(0.8f)
                        .height(appSize.buttonHeight),
                    shape = RoundedCornerShape(appSize.roundedCornerShapeSize),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Confirmation",
                        fontSize = appSize.buttonTextSize,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Preview(
    name = "SUNMI V1s",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
//    device = "spec:width=360dp,height=640dp,dpi=320"
)
@Composable
fun QRISPaymentPreview() {
    AppTheme {
        PaymentQRISScreen()
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
fun PaymentQrisScreenTabletPreview() {
    AppTheme(darkTheme = true) {
        PaymentQRISScreen()
    }
}
*/
