package com.arkhe.presentation.ui.screens.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Refresh
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.arkhe.base.R
import com.arkhe.core.utils.ConsLang
import com.arkhe.domain.model.ThemeMode
import com.arkhe.presentation.navigation.Screen
import com.arkhe.presentation.state.ThemeUiState
import com.arkhe.presentation.ui.components.SourceCodePro
import com.arkhe.presentation.ui.screens.shared.FooterSection
import com.arkhe.presentation.ui.screens.shared.HeaderSection
import com.arkhe.presentation.ui.screens.shared.LoadingScreen
import com.arkhe.presentation.ui.screens.shared.MainSection
import com.arkhe.presentation.ui.screens.shared.ext.AboutDialog
import com.arkhe.presentation.ui.screens.shared.ext.SettingsProfileBottomSheet
import com.arkhe.presentation.ui.screens.shared.ext.UserProfile
import com.arkhe.presentation.ui.theme.AppSize
import com.arkhe.presentation.viewmodel.LanguageViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    onNavigateToPaymentQris: (String) -> Unit = {},
    onNavigateToPaymentCash: (String) -> Unit = {},
    onSignOut: () -> Unit = {},
    headerPercent: Float = 0.10f,
    footerPercent: Float = 0.08f,
    isTablet: Boolean = false,
    viewModelLanguage: LanguageViewModel = koinViewModel(),
    uiStateTheme: ThemeUiState,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    var showAboutDialog by remember { mutableStateOf(false) }
    var showSettingsBottomSheet by remember { mutableStateOf(false) }

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
                    isTablet = isTablet,
                    viewModelLanguage = viewModelLanguage,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            isTablet = isTablet,
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
                    isTablet = isTablet,
                    viewModelLanguage = viewModelLanguage
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

        FooterSection(
            isTablet = isTablet,
            onAboutClick = { showAboutDialog = true },
            onSettingsClick = { showSettingsBottomSheet = true },
            currentUserProfile = UserProfile(
                username = "GAENTA",
                fullName = "Gaenta Sinergi Sukses"
            ),
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

    if (showAboutDialog) {
        AboutDialog(
            onDismissRequest = { showAboutDialog = false },
            isTablet = isTablet
        )
    }

    if (showSettingsBottomSheet) {
        SettingsProfileBottomSheet(
            onDismissRequest = { showSettingsBottomSheet = false },
            isTablet = isTablet,
            currentUserProfile = UserProfile(
                username = "GAENTA",
                fullName = "Gaenta Sinergi Sukses"
            ),
            uiStateTheme = uiStateTheme,
            onThemeSelected = onThemeSelected,
        )
    }
}

@Composable
fun MainHeaderSection(
    onSignOut: () -> Unit,
    isTablet: Boolean = false,
    viewModelLanguage: LanguageViewModel,
    modifier: Modifier,
) {
    val appSize = AppSize(isTablet = isTablet)
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(appSize.horizontalPadding / 4)
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
                    .size(appSize.iconSize * 1.3f)
                    .padding(appSize.horizontalPadding / 8),
                contentScale = ContentScale.Fit
            )
            Text(
                text = viewModelLanguage.getLocalizedString(ConsLang.APP_CLIENT),
                fontSize = appSize.bodyTextSize * 1.3f,
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
                imageVector = Icons.Outlined.Lock,
                contentDescription = "Sign Out",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(appSize.iconSize)
            )
        }
    }
}

@Composable
fun MainContent(
    onNavigateToPaymentQris: (String) -> Unit,
    onNavigateToPaymentCash: (String) -> Unit,
    isTablet: Boolean = false,
    viewModelLanguage: LanguageViewModel,
) {
    val appSize = AppSize(isTablet = isTablet)

    val languageState by viewModelLanguage.languageState.collectAsState()

    var showVehicleDetail by remember { mutableStateOf(false) }
    var vehicleData by remember { mutableStateOf<VehicleData?>(null) }
    var isLoadingButton by remember { mutableStateOf(false) }

    var areaCode by remember { mutableStateOf("") }
    var plateNumber by remember { mutableStateOf("") }
    var seriesCode by remember { mutableStateOf("") }
    val areaCodeFocusRequester = remember { FocusRequester() }

    AnimatedContent(
        targetState = languageState.isChangingLanguage,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "language_change_animation"
    ) { isLoading ->
        if (isLoading) {
            LoadingScreen()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = appSize.verticalPadding / 4)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.client_kab_cianjur_ic),
                            contentDescription = "Client",
                            modifier = Modifier
                                .size(appSize.logoSize)
                                .padding(appSize.horizontalPadding / 10),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = viewModelLanguage.getLocalizedString(ConsLang.APP_REGION),
                            fontSize = appSize.bodyTextSize,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                Text(
                    text = viewModelLanguage.getLocalizedString(ConsLang.APP_PRODUCT_NAME),
                    fontSize = appSize.titleSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = appSize.horizontalPadding,
                            vertical = appSize.verticalPadding / 2
                        )
                )

                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = appSize.cardElevation),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = appSize.horizontalPadding)
                ) {
                    Column(
                        modifier = Modifier.padding(
                            horizontal = appSize.horizontalPadding / 2,
                            vertical = appSize.verticalPadding / 2
                        ),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = viewModelLanguage.getLocalizedString(ConsLang.VEHICLE_PLATE),
                                fontSize = appSize.captionTextSize,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(appSize.buttonHeight)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(appSize.roundedCornerShapeSize / 2)
                                    )
                                    .padding(horizontal = 2.dp, vertical = 2.dp)
                                    .border(
                                        width = 1.2.dp,
                                        color = Color.Black,
                                        shape = RoundedCornerShape(appSize.roundedCornerShapeSize / 2)
                                    ),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BasicTextField(
                                    value = areaCode,
                                    onValueChange = {
                                        if (it.length <= 2) areaCode = it.uppercase()
                                    },
                                    textStyle = TextStyle(
                                        fontSize = appSize.titleSize,
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
                                        .width(if (isTablet) 80.dp else 60.dp)
                                        .focusRequester(areaCodeFocusRequester),
                                    decorationBox = { innerTextField ->
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    color = Color.White,
                                                    shape = RoundedCornerShape(appSize.roundedCornerShapeSize)
                                                )
                                                .padding(horizontal = 1.dp, vertical = 1.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (areaCode.isEmpty()) {
                                                Text(
                                                    text = "F",
                                                    fontSize = appSize.titleSize,
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
                                        fontSize = appSize.titleSize,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        fontFamily = SourceCodePro
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    singleLine = true,
                                    modifier = Modifier.width(if (isTablet) 160.dp else 120.dp),
                                    decorationBox = { innerTextField ->
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    color = Color.White,
                                                    shape = RoundedCornerShape(appSize.roundedCornerShapeSize)
                                                )
                                                .padding(horizontal = 1.dp, vertical = 1.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (plateNumber.isEmpty()) {
                                                Text(
                                                    text = "8939",
                                                    fontSize = appSize.titleSize,
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
                                        fontSize = appSize.titleSize,
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
                                        .width(if (isTablet) 120.dp else 100.dp),
                                    decorationBox = { innerTextField ->
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    color = Color.White,
                                                    shape = RoundedCornerShape(appSize.roundedCornerShapeSize)
                                                )
                                                .padding(horizontal = 1.dp, vertical = 1.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (seriesCode.isEmpty()) {
                                                Text(
                                                    text = "ABC",
                                                    fontSize = appSize.titleSize,
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
                        Spacer(modifier = Modifier.height(appSize.verticalPadding / 2))
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
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        areaCode = ""
                                        plateNumber = ""
                                        seriesCode = ""
                                        showVehicleDetail = false
                                        vehicleData = null
                                        areaCodeFocusRequester.requestFocus()
                                    },
                                    modifier = Modifier
                                        .weight(0.2f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Refresh,
                                        contentDescription = "Clear",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.size(appSize.iconSize)
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
                                    ),
                                    enabled = areaCode.isNotEmpty() && plateNumber.isNotEmpty() && seriesCode.isNotEmpty()
                                ) {
                                    if (isLoadingButton) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(appSize.circularProgressIndicatorSize),
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    } else {
                                        Text(
                                            text = viewModelLanguage.getLocalizedString(ConsLang.CHECK_BUTTON),
                                            fontSize = appSize.buttonTextSize,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    }
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
                        isTablet = isTablet,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = appSize.horizontalPadding)
                            .padding(top = appSize.verticalPadding / 2)
                    )
                }
            }
        }
    }
}

@Composable
fun VehicleDetailCard(
    vehicleData: VehicleData,
    onQRISClick: () -> Unit,
    onCashClick: () -> Unit,
    isTablet: Boolean,
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

    val appSize = AppSize(isTablet = isTablet)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = appSize.horizontalPadding / 2,
                vertical = appSize.verticalPadding / 4
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = appSize.cardElevation)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                horizontal = appSize.horizontalPadding,
                vertical = appSize.verticalPadding / 2
            )
        ) {
            Text(
                text = "Informasi Tagihan",
                fontSize = appSize.captionTextSize,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            VehicleDetailRow("Status", vehicleData.status, isTablet)
            VehicleDetailRow("Jenis Kendaraan", vehicleData.vehicleType, isTablet)
            VehicleDetailRow("Tagihan", "Rp ${vehicleData.amount}", isTablet)

            Spacer(modifier = Modifier.height(appSize.verticalPadding / 2))

            Text(
                text = "Metode Pembayaran",
                fontSize = appSize.captionTextSize,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(appSize.horizontalPadding / 2),
            ) {
                Button(
                    onClick = {
                        isLoadingQRISPayment = true
                        shouldQRISPayment = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .size(appSize.buttonHeight),
                    shape = RoundedCornerShape(appSize.roundedCornerShapeSize),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    if (isLoadingQRISPayment) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(appSize.circularProgressIndicatorSize),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.qris_ic),
                            contentDescription = "QRIS",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(appSize.iconSize * 2),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
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
                        .size(appSize.buttonHeight),
                    shape = RoundedCornerShape(appSize.roundedCornerShapeSize),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    if (isLoadingCashPayment) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(appSize.circularProgressIndicatorSize),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "Cash",
                            fontSize = appSize.buttonTextSize,
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
    value: String,
    isTablet: Boolean
) {
    val appSize = AppSize(isTablet = isTablet)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = appSize.verticalPadding / 8),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = appSize.bodyTextSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = appSize.bodyTextSize,
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

/*@Preview(
    name = "Smartphone",
    widthDp = 360,
    heightDp = 640,
    showBackground = true,
)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}*/
