package com.rainyday.momentapp.core.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rainyday.momentapp.features.events.ui.screens.EventsScreen
import com.rainyday.momentapp.features.stats.ui.screens.StatsScreen
import com.rainyday.momentapp.features.wishlist.ui.screens.WishlistScreen

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = "events",
        modifier = Modifier.padding(padding),
        builder = {
            composable("events") {
                EventsScreen()
            }

            composable("wishlist") {
                WishlistScreen()
            }

            composable("stats") {
                StatsScreen()
            }
        }
    )
}