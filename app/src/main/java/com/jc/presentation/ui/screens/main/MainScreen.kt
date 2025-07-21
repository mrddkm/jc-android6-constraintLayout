package com.jc.presentation.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jc.presentation.ui.screens.shared.FooterSection
import com.jc.presentation.ui.screens.shared.MainSection

@Composable
fun MainScreen(
    onNavigateToPayment: (String) -> Unit = {},
    onSignOut: () -> Unit = {},
    onThemeToggle: () -> Unit = {},
    isDarkTheme: Boolean = false
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (header, main, footer) = createRefs()

        val topGuideline = createGuidelineFromTop(0.15f)
        val bottomGuideline = createGuidelineFromBottom(0.10f)

        // HEADER SECTION (15%)
        MainHeaderSection(
            onSignOut = onSignOut,
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

        // MAIN SECTION (75%)
        MainSection(
            contentMain = {
                MainContent(
                    onNavigateToPayment = onNavigateToPayment
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

        // FOOTER SECTION (10%)
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
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF1E1E1E)
            else Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (appName, signOutButton) = createRefs()

            Text(
                text = "Parkir App",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color.Black,
                modifier = Modifier.constrainAs(appName) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

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
                    tint = if (isDarkTheme) Color.White else Color.Black
                )
            }
        }
    }
}

@Composable
fun MainContent(
    onNavigateToPayment: (String) -> Unit
) {
    var platNumber by remember { mutableStateOf("") }
    var showVehicleDetail by remember { mutableStateOf(false) }
    var vehicleData by remember { mutableStateOf<VehicleData?>(null) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        val (title, platField, checkButton, detailCard) = createRefs()

        // Title
        Text(
            text = "Cek Kendaraan",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Plat Number Input Field
        OutlinedTextField(
            value = platNumber,
            onValueChange = { platNumber = it.uppercase() },
            label = { Text("Nomor Plat Kendaraan") },
            placeholder = { Text("Contoh: B 1234 XYZ") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(platField) {
                    top.linkTo(title.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // Check Button
        Button(
            onClick = {
                if (platNumber.isNotBlank()) {
                    // Simulate vehicle data
                    vehicleData = VehicleData(
                        platNumber = platNumber,
                        status = "Belum Bayar",
                        vehicleType = "Mobil",
                        amount = "100.000"
                    )
                    showVehicleDetail = true
                }
            },
            enabled = platNumber.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .constrainAs(checkButton) {
                    top.linkTo(platField.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = "Cek",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Vehicle Detail Card (shown after check)
        if (showVehicleDetail && vehicleData != null) {
            VehicleDetailCard(
                vehicleData = vehicleData!!,
                onQRISClick = { onNavigateToPayment("qris") },
                onCashClick = { onNavigateToPayment("tunai") },
                modifier = Modifier.constrainAs(detailCard) {
                    top.linkTo(checkButton.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

@Composable
fun VehicleDetailCard(
    vehicleData: VehicleData,
    onQRISClick: () -> Unit,
    onCashClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Detail Kendaraan",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            VehicleDetailRow("Plat Nomor", vehicleData.platNumber)
            VehicleDetailRow("Status", vehicleData.status)
            VehicleDetailRow("Jenis Kendaraan", vehicleData.vehicleType)
            VehicleDetailRow("Tagihan", "Rp ${vehicleData.amount}")

            Spacer(modifier = Modifier.height(16.dp))

            // Payment Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onQRISClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("QRIS")
                }

                Button(
                    onClick = onCashClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Tunai")
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
            .padding(vertical = 4.dp),
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
            fontWeight = FontWeight.Medium
        )
    }
}

data class VehicleData(
    val platNumber: String,
    val status: String,
    val vehicleType: String,
    val amount: String
)

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}