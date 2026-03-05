package com.rainyday.momentapp.core.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star

object NavItems {
    val bottomNavItems = listOf(
        NavItem(
            icon = Icons.Default.Home,
            title = "Події",
            route = "events"
        ),
        NavItem(
            icon = Icons.Default.Star,
            title = "Бажане",
            route = "wishlist"
        ),
        NavItem(
            icon = Icons.Default.Person,
            title = "Статистика",
            route = "stats"
        )
    )
}