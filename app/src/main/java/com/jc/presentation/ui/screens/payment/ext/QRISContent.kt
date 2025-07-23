package com.jc.presentation.ui.screens.payment.ext

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.jc.constraintlayout.R
import com.jc.presentation.ui.theme.AppTheme

@Composable
fun QRISPaymentContent(
    isTablet: Boolean = false
) {
    val qrisCode =
        "00020101021126610017ID.CO.BANKBJB.WWW0118936001103001278321020713436290303UMI51440014ID.CO.QRIS.WWW0215ID10221796982980303UMI5204546253033605802ID5919PARKIR BERLANGGANAN6007CIANJUR61054321162070703A016304F00C"

    val horizontalPadding = if (isTablet) 32.dp else 16.dp
    val allPadding = if (isTablet) 16.dp else 12.dp
    val qrSize = if (isTablet) 300.dp else 260.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(allPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.qris_logo_full_ic),
                        contentDescription = "QRIS Logo",
                        modifier = Modifier
                            .height(45.dp)
                            .padding(end = 8.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.gpn_logo_ic),
                        contentDescription = "GPN Logo",
                        modifier = Modifier.height(40.dp)
                    )
                }

                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "NAMA MERCHANT",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "NMID: IDXXXXXXXXX",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(qrSize)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.TopStart)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.dp)
                                    .align(Alignment.TopStart)
                            )
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .fillMaxHeight()
                                    .align(Alignment.TopStart)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.TopEnd)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.dp)
                                    .align(Alignment.TopEnd)
                            )
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .fillMaxHeight()
                                    .align(Alignment.TopEnd)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.BottomStart)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.dp)
                                    .align(Alignment.BottomStart)
                            )
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .fillMaxHeight()
                                    .align(Alignment.BottomStart)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.BottomEnd)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.dp)
                                    .align(Alignment.BottomEnd)
                            )
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .fillMaxHeight()
                                    .align(Alignment.BottomEnd)
                            )
                        }
                    }

                    QRCodeImage(
                        qrContent = qrisCode,
                        size = (qrSize.value.toInt() - 60)
                    )
                }

                Text(
                    text = "Dicetak oleh: (Kode NNS)",
                    fontSize = 10.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
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

@Preview(showBackground = true)
@Composable
fun QRISContentPreview() {
    AppTheme {
        QRISPaymentContent(
            isTablet = false
        )
    }
}