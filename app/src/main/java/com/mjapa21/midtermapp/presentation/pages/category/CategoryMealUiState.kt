package com.mjapa21.midtermapp.presentation.pages.category

import com.mjapa21.midtermapp.data.model.MealListItem

sealed interface CategoryMealsUiState {
    data object Loading : CategoryMealsUiState
    data class Success(val meals: List<MealListItem>) : CategoryMealsUiState
    data class Error(val message: String) : CategoryMealsUiState
}