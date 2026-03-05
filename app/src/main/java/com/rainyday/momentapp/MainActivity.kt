package com.rainyday.momentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.rainyday.momentapp.core.ui.navigation.NavHostContainer
import com.rainyday.momentapp.core.ui.screens.BaseScreen
import com.rainyday.momentapp.core.ui.theme.MomentAppTheme
import com.rainyday.momentapp.features.events.ui.screens.EventsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MomentAppTheme {
                val navController = rememberNavController()

                BaseScreen(
                    mainSmallImage = "https://images.pexels.com/photos/30926463/pexels-photo-30926463.jpeg",
                    navController = navController,
                )
            }
        }
    }
}
