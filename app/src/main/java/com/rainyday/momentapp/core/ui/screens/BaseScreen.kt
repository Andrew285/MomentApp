package com.rainyday.momentapp.core.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rainyday.momentapp.R
import com.rainyday.momentapp.core.ui.navigation.BottomNavigationBar
import com.rainyday.momentapp.core.ui.navigation.CustomTopAppBar
import com.rainyday.momentapp.core.ui.navigation.HomeNavGraph
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    mainSmallImage: String,
    rootNavController: NavHostController,
    navController: NavHostController = rememberNavController(),
) {
    val topBarTitle = stringResource(R.string.our_story)
    val topBarDescription = stringResource(R.string.with_love_description)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                mainSmallImage = mainSmallImage,
                topBarTitle = topBarTitle,
                topBarDescription = topBarDescription,
                navController = navController
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HomeNavGraph(
                rootNavHostController = rootNavController,
                navHostController = navController,
            )
        }
    }
}