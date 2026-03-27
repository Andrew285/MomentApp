package com.rainyday.momentapp.core.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rainyday.momentapp.core.ui.navigation.RootScreen.UpdateEvent
import com.rainyday.momentapp.core.ui.screens.BaseScreen
import com.rainyday.momentapp.features.events.ui.screens.AddOrUpdateEventScreen
import com.rainyday.momentapp.features.events.ui.screens.EventDetailsScreen

@Composable
fun RootNavGraph(
    isAuthorized: Boolean,
    navHostController: NavHostController,
    showSnackBar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = if (isAuthorized) RootScreen.Home.route else RootScreen.Auth,
        modifier = Modifier.fillMaxSize(),
        builder = {

            composable(RootScreen.Home.route) {
                BaseScreen(
                    mainSmallImage = "",
                    rootNavController = navHostController,
                )
            }

            composable(RootScreen.AddEvent.route) {
                AddOrUpdateEventScreen(
                    onClose = {
                        navHostController.popBackStack()
                    },
                    showSnackBar = showSnackBar
                )
            }

            composable(
                route = RootScreen.EventDetails.route,
                arguments = listOf(
                    navArgument(NavArgString.EVENT_ID) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString(NavArgString.EVENT_ID) ?: return@composable

                EventDetailsScreen(
                    eventId = eventId,
                    onEdit = { id ->
                        navHostController.navigate(UpdateEvent.createRoute(id))
                    },
                    onClose = {
                        navHostController.popBackStack()
                    },
                    showSnackBar = showSnackBar,
                )
            }

            composable(
                route = UpdateEvent.route,
                arguments = listOf(
                    navArgument(name = NavArgString.EVENT_ID) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString(NavArgString.EVENT_ID) ?: return@composable

                AddOrUpdateEventScreen(
                    eventId = eventId,
                    onClose = {
                        navHostController.popBackStack()
                    },
                    showSnackBar = showSnackBar,
                )
            }

            composable(route = RootScreen.Auth.route) {
                val authNavHostController = rememberNavController()
                AuthGraph(
                    navHostController = authNavHostController,
                    showSnackBar = showSnackBar,
                    onSuccess = {
                        navHostController.navigate(RootScreen.Home.route) {
                            popUpTo(RootScreen.Auth.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    )
}

sealed class RootScreen(val route: String) {
    data object Home: RootScreen("home")
    data object Auth: RootScreen("auth")
    data object AddEvent: RootScreen("add_event")
    data object UpdateEvent: RootScreen("${NavArgString.EVENT_UPDATE}/{${NavArgString.EVENT_ID}}") {
        fun createRoute(eventId: String): String {
            return "${NavArgString.EVENT_UPDATE}/$eventId"
        }
    }
    data object EventDetails: RootScreen("${NavArgString.EVENTS}/{${NavArgString.EVENT_ID}}") {
        fun createRoute(eventId: String): String {
            return "${NavArgString.EVENTS}/$eventId"
        }
    }

}

object NavArgString {
    const val EVENT_ID = "eventId"
    const val EVENTS = "events"
    const val EVENT_UPDATE = "event_update"
}