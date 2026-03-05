package com.rainyday.momentapp.core.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rainyday.momentapp.R
import com.rainyday.momentapp.core.ui.navigation.BottomNavigationBar
import com.rainyday.momentapp.core.ui.navigation.NavHostContainer
import com.rainyday.momentapp.features.events.ui.components.TopAppBarTitleComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    mainSmallImage: String,
    navController: NavHostController,
) {
    val topBarTitle = stringResource(R.string.our_story)
    val topBarDescription = stringResource(R.string.with_love_description)

    Scaffold(
        topBar = {
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
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: add click event */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
        },
    ) { padding ->
        NavHostContainer(
            navController = navController,
            padding = padding
        )
    }
}