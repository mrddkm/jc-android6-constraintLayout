package com.jc.presentation.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jc.constraintlayout.R
import com.jc.presentation.navigation.Screen
import com.jc.presentation.ui.components.SourceCodePro
import com.jc.presentation.ui.screens.shared.FooterSection
import com.jc.presentation.ui.screens.shared.HeaderSection
import com.jc.presentation.ui.screens.shared.MainSection
import com.jc.presentation.ui.theme.AppTheme

@Composable
fun MainScreen(
    onNavigateToPaymentQris: (String) -> Unit = {},
    onNavigateToPaymentCash: (String) -> Unit = {},
    onSignOut: () -> Unit = {},
    onThemeToggle: () -> Unit = {},
    isDarkTheme: Boolean = false,
    headerPercent: Float = 0.10f,
    footerPercent: Float = 0.08f,
    isTablet: Boolean = false
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (header, main, footer) = createRefs()

        val topGuideline = createGuidelineFromTop(headerPercent)
        val bottomGuideline = createGuidelineFromBottom(footerPercent)

        HeaderSection(
            contentHeader = {
                MainHeaderSection(
                    onSignOut = onSignOut,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            isDarkTheme = isDarkTheme,
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(topGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )

        MainSection(
            contentMain = {
                MainContent(
                    onNavigateToPaymentQris = onNavigateToPaymentQris,
                    onNavigateToPaymentCash = onNavigateToPaymentCash,
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
fun MainHeaderSection(
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        val (clientName, signOutButton) = createRefs()

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(clientName) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.client_dishub_ic),
                contentDescription = "Client",
                modifier = Modifier
                    .size(32.dp)
                    .padding(2.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                "Dinas Perhubungan",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 1.dp)
            )
        }

        IconButton(
            onClick = onSignOut,
            modifier = Modifier.constrainAs(signOutButton) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "Sign Out",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun MainContent(
    onNavigateToPaymentQris: (String) -> Unit,
    onNavigateToPaymentCash: (String) -> Unit,
    isTablet: Boolean = false
) {
    var showVehicleDetail by remember { mutableStateOf(false) }
    var vehicleData by remember { mutableStateOf<VehicleData?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    var areaCode by remember { mutableStateOf("") }
    var plateNumber by remember { mutableStateOf("") }
    var seriesCode by remember { mutableStateOf("") }
    val areaCodeFocusRequester = remember { FocusRequester() }

    val titleSize = if (isTablet) 32.sp else 20.sp
    val buttonTextSize = if (isTablet) 18.sp else 16.sp
    val horizontalPadding = if (isTablet) 32.dp else 8.dp
    val topMarginSub = if (isTablet) 8.dp else 4.dp

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (headerLogo, title, platField, detailCard) = createRefs()

        Box(
            modifier = Modifier.constrainAs(headerLogo) {
                top.linkTo(parent.top, margin = topMarginSub)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.client_kab_cianjur_ic),
                    contentDescription = "Client",
                    modifier = Modifier
                        .size(if (isTablet) 100.dp else 60.dp)
                        .padding(1.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    "Kabupaten Cianjur",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Text(
            text = "Parkir Berlangganan",
            fontSize = titleSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(horizontal = horizontalPadding)
                .constrainAs(title) {
                    top.linkTo(headerLogo.bottom, margin = topMarginSub)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding)
                .constrainAs(platField) {
                    top.linkTo(title.bottom, margin = topMarginSub)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = topMarginSub),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Plat Kendaraan",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 2.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = areaCode,
                            onValueChange = {
                                if (it.length <= 2) areaCode = it.uppercase()
                            },
                            textStyle = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                fontFamily = SourceCodePro
                            ),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Characters,
                                keyboardType = KeyboardType.Text
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .width(60.dp)
                                .focusRequester(areaCodeFocusRequester),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 1.dp, vertical = 1.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (areaCode.isEmpty()) {
                                        Text(
                                            text = "F",
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.Light,
                                            color = Color.LightGray,
                                            textAlign = TextAlign.Center,
                                            fontFamily = SourceCodePro
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        BasicTextField(
                            value = plateNumber,
                            onValueChange = {
                                if (it.length <= 4 && it.all { char -> char.isDigit() })
                                    plateNumber = it
                            },
                            textStyle = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                fontFamily = SourceCodePro
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                            modifier = Modifier.width(120.dp),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 1.dp, vertical = 1.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (plateNumber.isEmpty()) {
                                        Text(
                                            text = "8939",
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.Light,
                                            color = Color.LightGray,
                                            textAlign = TextAlign.Center,
                                            fontFamily = SourceCodePro
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        BasicTextField(
                            value = seriesCode,
                            onValueChange = {
                                if (it.length <= 3) seriesCode = it.uppercase()
                            },
                            textStyle = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                fontFamily = SourceCodePro
                            ),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Characters,
                                keyboardType = KeyboardType.Text
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .width(100.dp),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 1.dp, vertical = 1.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (seriesCode.isEmpty()) {
                                        Text(
                                            text = "ABC",
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.Light,
                                            color = Color.LightGray,
                                            textAlign = TextAlign.Center,
                                            fontFamily = SourceCodePro
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(topMarginSub))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = {
                            areaCode = ""
                            plateNumber = ""
                            seriesCode = ""
                            areaCodeFocusRequester.requestFocus()
                        },
                        modifier = Modifier
                            .weight(0.2f)
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Clear",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Button(
                        onClick = {
                            if (areaCode.isNotEmpty() && plateNumber.isNotEmpty() && seriesCode.isNotEmpty()) {
                                vehicleData = VehicleData(
                                    platNumber = "$areaCode $plateNumber $seriesCode",
                                    status = "Belum Bayar",
                                    vehicleType = "Mobil",
                                    amount = "100.000"
                                )
                                showVehicleDetail = true
                            }
                        },
                        modifier = Modifier
                            .weight(0.8f),
                        shape = RoundedCornerShape(8.dp),
                        enabled = areaCode.isNotEmpty() && plateNumber.isNotEmpty() && seriesCode.isNotEmpty()
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(
                                text = "Check",
                                fontSize = buttonTextSize,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
        }

        if (showVehicleDetail && vehicleData != null) {
            VehicleDetailCard(
                vehicleData = vehicleData!!,
                onQRISClick = { onNavigateToPaymentQris(Screen.PaymentQris.route) },
                onCashClick = { onNavigateToPaymentCash(Screen.PaymentCash.route) },
                modifier = Modifier.constrainAs(detailCard) {
                    top.linkTo(platField.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }

/*        VehicleDetailCard(
            vehicleData = vehicleData ?: VehicleData(
                platNumber = "F1234ABC",
                status = "Belum Bayar",
                vehicleType = "Mobil",
                amount = "100.000"
            ),
                onQRISClick = { onNavigateToPaymentQris(Screen.PaymentQris.route) },
                onCashClick = { onNavigateToPaymentCash(Screen.PaymentCash.route) },
            modifier = Modifier.constrainAs(detailCard) {
                top.linkTo(platField.bottom, margin = 4.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )*/
    }
}

@Composable
fun VehicleDetailCard(
    vehicleData: VehicleData,
    onQRISClick: () -> Unit,
    onCashClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoadingQRISPayment by remember { mutableStateOf(false) }
    var isLoadingCashPayment by remember { mutableStateOf(false) }
    var shouldQRISPayment by remember { mutableStateOf(false) }
    var shouldCashPayment by remember { mutableStateOf(false) }

    if (shouldQRISPayment) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500)
            isLoadingQRISPayment = false
            onQRISClick()
            shouldQRISPayment = false
        }
    } else if (shouldCashPayment) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500)
            isLoadingCashPayment = false
            onCashClick()
            shouldCashPayment = false
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Informasi Tagihan",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            VehicleDetailRow("Status", vehicleData.status)
            VehicleDetailRow("Jenis Kendaraan", vehicleData.vehicleType)
            VehicleDetailRow("Tagihan", "Rp ${vehicleData.amount}")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Metode Pembayaran",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        isLoadingQRISPayment = true
                        shouldQRISPayment = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .size(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    if (isLoadingQRISPayment) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.qris_logo_ic),
                            contentDescription = "QRIS Logo",
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                Button(
                    onClick = {
                        isLoadingCashPayment = true
                        shouldCashPayment = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .size(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    if (isLoadingCashPayment) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "Cash",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

data class VehicleData(
    val platNumber: String = "D12345XYZ",
    val status: String = "Belum Bayar",
    val vehicleType: String = "Mobil",
    val amount: String = "100.000"
)

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun MainDarkScreenPreview() {
    AppTheme(darkTheme = true) {
        MainScreen(isDarkTheme = true)
    }
}