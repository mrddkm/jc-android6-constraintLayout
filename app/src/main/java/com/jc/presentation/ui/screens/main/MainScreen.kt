package com.jc.presentation.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jc.presentation.ui.screens.shared.FooterSection
import com.jc.presentation.ui.screens.shared.HeaderSection
import com.jc.presentation.ui.screens.shared.MainSection
import com.jc.presentation.ui.theme.ConstraintLayoutTheme

data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val description: String,
    val onClick: () -> Unit = {}
)

@Composable
fun MainScreen(
    onBackClick: () -> Unit = {},
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
        HeaderSection(
            title = "Dashboard",
            onBackClick = onBackClick,
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
            content = {
                MainContent()
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
fun MainContent() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (welcomeText, menuGrid, statusCard) = createRefs()

        // Welcome Text
        Text(
            text = "Welcome to Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(welcomeText) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Menu Grid
        val menuItems = listOf(
            MenuItem(
                title = "Payment",
                icon = Icons.Default.Payment,
                description = "Process payments"
            ),
            MenuItem(
                title = "Print",
                icon = Icons.Default.Print,
                description = "Print documents"
            ),
            MenuItem(
                title = "Reports",
                icon = Icons.Default.Dashboard,
                description = "View reports"
            ),
            MenuItem(
                title = "Settings",
                icon = Icons.Default.Settings,
                description = "App settings"
            )
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.constrainAs(menuGrid) {
                top.linkTo(welcomeText.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(statusCard.top, margin = 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            items(menuItems) { item ->
                MenuItemCard(item = item)
            }
        }

        // Status Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .constrainAs(statusCard) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "System Status",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "All systems operational",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItem) {
    Card(
        onClick = item.onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.description,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ConstraintLayoutTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenDarkPreview() {
    ConstraintLayoutTheme(darkTheme = true) {
        MainScreen(isDarkTheme = true)
    }
}