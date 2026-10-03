package com.example.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ExOwnBlue
import com.example.ui.theme.ExOwnBlueContainer
import com.example.ui.viewmodel.AppScreen

@Composable
fun ExOwnBottomBar(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.testTag("exown_bottom_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        // Home
        NavigationBarItem(
            selected = currentScreen == AppScreen.HOME,
            onClick = { onNavigate(AppScreen.HOME) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = "Home",
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == AppScreen.HOME) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ExOwnBlue,
                selectedTextColor = ExOwnBlue,
                indicatorColor = ExOwnBlueContainer
            ),
            modifier = Modifier.testTag("nav_home")
        )

        // Explore
        NavigationBarItem(
            selected = currentScreen == AppScreen.EXPLORE,
            onClick = { onNavigate(AppScreen.EXPLORE) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.EXPLORE) Icons.Filled.Explore else Icons.Outlined.Explore,
                    contentDescription = "Explore"
                )
            },
            label = {
                Text(
                    text = "Explore",
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == AppScreen.EXPLORE) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ExOwnBlue,
                selectedTextColor = ExOwnBlue,
                indicatorColor = ExOwnBlueContainer
            ),
            modifier = Modifier.testTag("nav_explore")
        )

        // Sell (Featured Action)
        NavigationBarItem(
            selected = currentScreen == AppScreen.SELL,
            onClick = { onNavigate(AppScreen.SELL) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Sell or Exchange",
                    tint = ExOwnBlue,
                    modifier = Modifier.size(28.dp)
                )
            },
            label = {
                Text(
                    text = "Sell",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = ExOwnBlue
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ExOwnBlue,
                selectedTextColor = ExOwnBlue,
                indicatorColor = ExOwnBlueContainer
            ),
            modifier = Modifier.testTag("nav_sell")
        )

        // Services (Housing, PGs, Roommates, Services)
        NavigationBarItem(
            selected = currentScreen == AppScreen.SERVICES || currentScreen == AppScreen.HOUSING_DETAIL,
            onClick = { onNavigate(AppScreen.SERVICES) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.SERVICES) Icons.Filled.Apartment else Icons.Outlined.Apartment,
                    contentDescription = "Services"
                )
            },
            label = {
                Text(
                    text = "Services",
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == AppScreen.SERVICES) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ExOwnBlue,
                selectedTextColor = ExOwnBlue,
                indicatorColor = ExOwnBlueContainer
            ),
            modifier = Modifier.testTag("nav_services")
        )

        // Profile
        NavigationBarItem(
            selected = currentScreen == AppScreen.PROFILE || currentScreen == AppScreen.SAVED,
            onClick = { onNavigate(AppScreen.PROFILE) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                    contentDescription = "Profile"
                )
            },
            label = {
                Text(
                    text = "Profile",
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen == AppScreen.PROFILE) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ExOwnBlue,
                selectedTextColor = ExOwnBlue,
                indicatorColor = ExOwnBlueContainer
            ),
            modifier = Modifier.testTag("nav_profile")
        )
    }
}
