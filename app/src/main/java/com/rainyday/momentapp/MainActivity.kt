package com.rainyday.momentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.rainyday.momentapp.core.ui.navigation.RootNavGraph
import com.rainyday.momentapp.core.ui.theme.MomentAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MomentAppTheme {
                val navController = rememberNavController()

                RootNavGraph(
                    navHostController = navController,
                )
            }
        }
    }
}
