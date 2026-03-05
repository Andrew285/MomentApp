package com.rainyday.momentapp.core.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector


data class NavItem(
    val icon: ImageVector,
    val title: String,
    val route: String,
)