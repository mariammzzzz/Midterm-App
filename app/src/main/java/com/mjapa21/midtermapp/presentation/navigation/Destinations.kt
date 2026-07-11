package com.mjapa21.midtermapp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
sealed interface Destinations : NavKey {
    @Serializable
    data object Home : Destinations

    @Serializable
    data class CategoryDetails(val categoryId: String) : Destinations //todo check this later


    @Serializable
    data class MealDetails(val meadId: String) : Destinations //todo check this later
}