package com.jc.presentation.ui.screens.payment

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.jc.presentation.ui.screens.shared.FooterSection
import com.jc.presentation.ui.screens.shared.MainSection
import com.jc.presentation.ui.theme.AppTheme

@Composable
fun PaymentQrisScreen(
    onPaymentConfirm: () -> Unit = {},
    onThemeToggle: () -> Unit = {},
    isDarkTheme: Boolean = false,
    headerPercent: Float = 0.01f,
    footerPercent: Float = 0.08f,
    isTablet: Boolean = false
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (main, footer) = createRefs()

        val topGuideline = createGuidelineFromTop(headerPercent)
        val bottomGuideline = createGuidelineFromBottom(footerPercent)

        MainSection(
            contentMain = {
                QRISPaymentContent(
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
fun QRISPaymentContent(
    onPaymentConfirm: () -> Unit,
    isTablet: Boolean = false
) {
    val qrisCode =
        "00020101021126610017ID.CO.BANKBJB.WWW0118936001103001278321020713436290303UMI51440014ID.CO.QRIS.WWW0215ID10221796982980303UMI5204546253033605802ID5919PARKIR BERLANGGANAN6007CIANJUR61054321162070703A016304F00C"

    val buttonTextSize = if (isTablet) 18.sp else 16.sp
    val horizontalPadding = if (isTablet) 32.dp else 8.dp
    val allPadding = if (isTablet) 8.dp else 4.dp
    val topMarginSub = if (isTablet) 8.dp else 4.dp
    val qrSize = if (isTablet) 280.dp else 240.dp
    val amountSize = if (isTablet) 32.sp else 28.sp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = horizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(horizontalPadding),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(qrSize)
                    .padding(allPadding),
                contentAlignment = Alignment.Center
            ) {
                QRCodeImage(
                    qrContent = qrisCode,
                    size = qrSize.value.toInt() - 32
                )
            }
        }
        Spacer(modifier = Modifier.height(horizontalPadding))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nominal Bayar",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rp100.000",
                fontSize = amountSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(modifier = Modifier.height(horizontalPadding))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .weight(0.2f)
            ) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
            }
            Button(
                onClick = onPaymentConfirm,
                modifier = Modifier
                    .weight(0.8f),
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

@Composable
fun QRCodeImage(
    qrContent: String,
    size: Int
) {
    AndroidView(
        factory = { ctx ->
            val imageView = ImageView(ctx)

            try {
                val writer = QRCodeWriter()
                val hints = hashMapOf<EncodeHintType, Any>()
                hints[EncodeHintType.MARGIN] = 0

                val bitMatrix = writer.encode(qrContent, BarcodeFormat.QR_CODE, size, size, hints)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bitmap = createBitmap(width, height, Bitmap.Config.RGB_565)

                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bitmap[x, y] =
                            if (bitMatrix[x, y]) Color.Black.toArgb() else Color.White.toArgb()
                    }
                }

                imageView.setImageBitmap(bitmap)
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            } catch (e: Exception) {
                println("Error generating QR code: ${e.message}")
            }

            imageView
        },
        modifier = Modifier.size(size.dp)
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun QRISPaymentPreview() {
    AppTheme {
        PaymentQrisScreen()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaymentQrisScreenTabletPreview() {
    AppTheme(darkTheme = true) {
        PaymentQrisScreen(
            isDarkTheme = true
        )
    }
}