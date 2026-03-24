package com.rainyday.momentapp.core.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rainyday.momentapp.features.events.ui.components.TopAppBarTitleComponent
import kotlin.collections.contains

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    mainSmallImage: String,
    topBarTitle: String,
    topBarDescription: String,
    navController: NavController
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
        TopAppBar(
            title = {
                Column {
                    TopAppBarTitleComponent(
                        image = mainSmallImage,
                        title = topBarTitle,
                        description = topBarDescription
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        thickness = 1.dp,
                        color = Color.LightGray
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            colors = TopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                navigationIconContentColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            ),
        )
    }
}