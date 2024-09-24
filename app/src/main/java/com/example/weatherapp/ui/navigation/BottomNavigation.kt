package com.example.weatherapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.weatherapp.constants.WeatherConstants

sealed class Screens(val route: String) {
    data object Home : Screens(WeatherConstants.ROUTES.HOME)
    data object Search : Screens(WeatherConstants.ROUTES.SEARCH)
    data object Profile : Screens(WeatherConstants.ROUTES.PROFILE)
}

data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

class BottomNavigation {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screens.Home.route
            ),
            BottomNavigationItem(
                label = "Search",
                icon = Icons.Filled.Search,
                route = Screens.Search.route
            ),
            BottomNavigationItem(
                label = "Profile",
                icon = Icons.Filled.AccountCircle,
                route = Screens.Profile.route
            ),
        )
    }
}