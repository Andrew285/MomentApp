package com.rainyday.momentapp.core.ui.navigation

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rainyday.momentapp.features.events.ui.screens.EventsScreen
import com.rainyday.momentapp.features.stats.ui.screens.StatsScreen
import com.rainyday.momentapp.features.wishlist.ui.screens.WishlistScreen

@Composable
fun BoxScope.HomeNavGraph(
    rootNavHostController: NavHostController,
    navHostController: NavHostController
) {
     NavHost(
        navController = navHostController,
        startDestination = HomeScreen.Events.route,
        builder = {
            composable(HomeScreen.Events.route) {
                EventsScreen(
                    onAddEventScreenNavigate = {
                        rootNavHostController.navigate(RootScreen.AddEvent.route)
                    }
                )
            }

            composable(HomeScreen.Wishlist.route) {
                WishlistScreen()
            }

            composable(HomeScreen.Stats.route) {
                StatsScreen()
            }
        }
    )
}

sealed class HomeScreen(val route: String) {
    data object Events: HomeScreen("events")
    data object Wishlist: HomeScreen("wishlist")
    data object Stats: HomeScreen("stats")
}