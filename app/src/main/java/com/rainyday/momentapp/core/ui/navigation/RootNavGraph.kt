package com.rainyday.momentapp.core.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rainyday.momentapp.core.ui.screens.BaseScreen
import com.rainyday.momentapp.features.events.ui.screens.AddEventScreen

@Composable
fun RootNavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = RootScreen.Home.route,
        modifier = Modifier.fillMaxSize(),
        builder = {

            composable(RootScreen.Home.route) {
                BaseScreen(
                    mainSmallImage = "",
                    rootNavController = navHostController
                )
            }

            composable(RootScreen.AddEvent.route) {
                AddEventScreen(
                    onClose = {
                        navHostController.popBackStack()
                    }
                )
            }
        }
    )
}

sealed class RootScreen(val route: String) {
    data object Home: RootScreen("home")
    data object AddEvent: RootScreen("add_event")
}