package com.arkhe.presentation.ui.screens.payment.ext

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.arkhe.constraintlayout.R
import com.arkhe.presentation.ui.components.OpenSans
import com.arkhe.presentation.ui.theme.AppTheme

@Composable
fun QRISSimple(
    isTablet: Boolean = false,
    merchantName: String = "NAMA MERCHANT",
    nmid: String = "IDXXXXXXXXX",
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val qrisCode =
        "00020101021126610017ID.CO.BANKBJB.WWW0118936001103001278321020713436290303UMI51440014ID.CO.QRIS.WWW0215ID10221796982980303UMI5204546253033605802ID5919PARKIR BERLANGGANAN6007CIANJUR61054321162070703A016304F00C"

    val horizontalPadding = if (isTablet) 32.dp else 4.dp

    val cardWidth = if (isTablet) 400.dp else 280.dp
    val cardHeight = if (isTablet) 550.dp else 385.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .width(cardWidth)
                .height(cardHeight)
                .padding(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(2.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.qris_mpm_simple_bg),
                    contentDescription = "QRIS MPM Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    text = merchantName.take(25),
                    modifier = Modifier
                        .offset(
                            x = 0.dp,
                            y = cardHeight * (103f / 550f) // 103/550 = 0.187
                        )
                        .fillMaxWidth(),
                    fontFamily = OpenSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = (20 * (cardWidth.value / 400f)).sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "NMID: $nmid",
                    modifier = Modifier
                        .offset(
                            x = 0.dp,
                            y = cardHeight * (131f / 550f) // 131/550 = 0.238
                        )
                        .fillMaxWidth(),
                    fontFamily = OpenSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = (14 * (cardWidth.value / 400f)).sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Box(
                    modifier = Modifier
                        .offset(
                            x = cardWidth * (30f / 400f),
                            y = cardHeight * (164f / 550f)
                        )
                        .width(cardWidth * (340f / 400f))
                        .height(cardHeight * (340f / 550f)),
                    contentAlignment = Alignment.Center
                ) {
                    QRCodeSimpleImage(
                        qrContent = qrisCode,
                        width = (cardWidth.value * (340f / 400f)).toInt(),
                        height = (cardHeight.value * (340f / 550f)).toInt()
                    )
                }

                Text(
                    text = "Dicetak oleh: (Kode NNS)",
                    modifier = Modifier
                        .offset(
                            x = cardWidth * (30f / 400f),
                            y = cardHeight * (504f / 550f)
                        ),
                    fontFamily = OpenSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = (12 * (cardWidth.value / 400f)).sp,
                    color = Color.Black
                )
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
                hints[EncodeHintType.MARGIN] = 1

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
            merchantName = "Gaenta Sinergi Sukses, PT",
            nmid = "ID10221796982980"
        )
    }
}