package com.rainyday.momentapp.core.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        val homeScreens = listOf(
            HomeScreen.Events.route,
            HomeScreen.Wishlist.route,
            HomeScreen.Stats.route
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val isHomeScreen = homeScreens.contains(currentRoute)

        if (isHomeScreen) {
            NavItems.bottomNavItems.forEach { navItem ->
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.title) },
                    label = {
                        Text(text = navItem.title)
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemColors(
                        selectedIconColor = MaterialTheme.colorScheme.primary, // Icon color when selected
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer, // Icon color when not selected
                        selectedTextColor = MaterialTheme.colorScheme.primary, // Label color when selected
                        selectedIndicatorColor = Color.Unspecified,
                        unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledTextColor = Color.Unspecified // Highlight color for selected item
                    )
                )
            }
        }
    }
}