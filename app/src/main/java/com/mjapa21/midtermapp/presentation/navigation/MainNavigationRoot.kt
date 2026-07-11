package com.mjapa21.midtermapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.mjapa21.midtermapp.presentation.pages.details.MealDetailsScreen
import com.mjapa21.midtermapp.presentation.pages.home.HomeScreen

@Composable
fun MainNavigationRoot() {
    val backStack = rememberNavBackStack(Destinations.Home)
    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberViewModelStoreNavEntryDecorator()
            //NOTE this was needed cause without it same viewmodel was used for each
            //meal and for example: if i clicked meal #1 first go to its details, then go back, and and click meal #3 the details would
            //be of the same meal #1 again. i guess the same viewmodel was used for each details screen
        )
    ) { navKey ->
        when (navKey) {
            is Destinations.Home -> NavEntry(navKey) {
                HomeScreen(onNavigateToMealDetails = { mealId ->
                    backStack.add(Destinations.MealDetails(meadId = mealId))
                })
            }

            is Destinations.MealDetails -> NavEntry(navKey) {
                MealDetailsScreen(
                    mealId = navKey.meadId,
                    onBackClick = { backStack.removeAt(backStack.lastIndex) })
            }

            else -> throw IllegalStateException("Unknown destination: $navKey")

        }
    }
}