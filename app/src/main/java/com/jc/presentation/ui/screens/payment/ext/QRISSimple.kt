package com.jc.presentation.ui.screens.payment.ext

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.jc.constraintlayout.R
import com.jc.presentation.ui.theme.AppTheme

@Composable
fun QRISSimple(
    isTablet: Boolean = false,
    merchantName: String = "NAMA MERCHANT",
    nmid: String = "IDXXXXXXXXX"
) {
    val qrisCode =
        "00020101021126610017ID.CO.BANKBJB.WWW0118936001103001278321020713436290303UMI51440014ID.CO.QRIS.WWW0215ID10221796982980303UMI5204546253033605802ID5919PARKIR BERLANGGANAN6007CIANJUR61054321162070703A016304F00C"

    val horizontalPadding = if (isTablet) 32.dp else 16.dp

    // Ukuran Card berdasarkan rasio background 400x550 (sekitar 0.73:1)
    val cardWidth = if (isTablet) 400.dp else 280.dp
    val cardHeight = if (isTablet) 550.dp else 385.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .width(cardWidth)
                .height(cardHeight)
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Background image sebagai overlay
                Image(
                    painter = painterResource(id = R.drawable.qris_mpm_ringkas_bg),
                    contentDescription = "QRIS MPM Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                // QR Code - berdasarkan referensi, QR berada di tengah-bawah
                // Dari background 400x550, QR di posisi x=25, y=171 dengan ukuran 350x330
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            // Proporsional padding berdasarkan ukuran card
                            start = cardWidth * 0.0625f, // 25/400 = 0.0625
                            top = cardHeight * 0.311f,   // 171/550 = 0.311
                            end = cardWidth * 0.0625f,   // 25/400 = 0.0625
                            bottom = cardHeight * 0.091f  // 50/550 = 0.091 (sisa ruang bawah)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    QRCodeSimpleImage(
                        qrContent = qrisCode,
                        // QR mengisi area yang tersedia
                        width = (cardWidth.value * 0.875).toInt(), // 350/400 = 0.875
                        height = (cardHeight.value * 0.6).toInt()   // 330/550 = 0.6
                    )
                }
            }
        }
    }
}

@Composable
fun QRCodeSimpleImage(
    qrContent: String,
    width: Int,
    height: Int = width
) {
    AndroidView(
        factory = { ctx ->
            val imageView = ImageView(ctx)

            try {
                val writer = QRCodeWriter()
                val hints = hashMapOf<EncodeHintType, Any>()
                hints[EncodeHintType.MARGIN] = 1 // Margin minimal untuk QR yang bersih

                // Menggunakan ukuran persegi untuk QR code yang optimal
                val qrSize = minOf(width, height)
                val bitMatrix =
                    writer.encode(qrContent, BarcodeFormat.QR_CODE, qrSize, qrSize, hints)

                val bitmapWidth = bitMatrix.width
                val bitmapHeight = bitMatrix.height
                val bitmap = createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.RGB_565)

                for (x in 0 until bitmapWidth) {
                    for (y in 0 until bitmapHeight) {
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
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
    )
}

@Preview(showBackground = true, name = "QRIS with Background")
@Composable
fun QRISSimplePreview() {
    AppTheme {
        QRISSimple(
            isTablet = false,
            merchantName = "PARKIR BERLANGGANAN",
            nmid = "ID10221796982980"
        )
    }
}