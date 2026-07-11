package com.mjapa21.midtermapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.mjapa21.midtermapp.presentation.pages.home.HomeScreen

@Composable
fun MainNavigationRoot() {
    val backStack = rememberNavBackStack(Destinations.Home) //todo add default page
    NavDisplay(backStack = backStack) { navKey ->
        when (navKey) {
            is Destinations.Home -> NavEntry(navKey) {
                HomeScreen()
            }

            else -> throw IllegalStateException("Unknown destination: $navKey")

        }
    }
}