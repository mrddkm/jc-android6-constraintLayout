package com.arkhe.presentation.ui.screens.payment

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.arkhe.presentation.ui.screens.payment.ext.QRISSimple
import com.arkhe.presentation.ui.screens.shared.MainSection
import com.arkhe.presentation.ui.theme.AppSize
import com.arkhe.presentation.ui.theme.AppTheme

@Composable
fun PaymentQRISScreen(
    onPaymentConfirm: () -> Unit = {},
    isTablet: Boolean = false
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (main) = createRefs()

        MainSection(
            contentMain = {
                QRISMainSection(
                    onPaymentConfirm = onPaymentConfirm,
                    isTablet = isTablet
                )
            },
            isTablet = isTablet,
            modifier = Modifier.constrainAs(main) {
                top.linkTo(parent.top)
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
fun QRISMainSection(
    onPaymentConfirm: () -> Unit,
    isTablet: Boolean = false
) {
    var isLoading by remember { mutableStateOf(false) }

    val appSize = AppSize(isTablet = isTablet)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = appSize.horizontalPadding / 8),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        QRISSimple(
            isTablet = isTablet,
            merchantName = "Gaenta Sinergi Sukses, PT",
            nmid = "IDXXXXXXXXX",
            modifier = Modifier.fillMaxWidth()
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
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.weight(0.2f)
                ) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(appSize.iconSizeDp)
                    )
                }
                Button(
                    onClick = onPaymentConfirm,
                    modifier = Modifier
                        .weight(0.8f)
                        .height(appSize.buttonHeight),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = appSize.roundedCornerShapeSize,
                        bottomStart = 0.dp,
                        bottomEnd = appSize.roundedCornerShapeSize
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(appSize.circularProgressIndicatorSize),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
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
