package com.mjapa21.midtermapp.presentation.pages.details

import com.mjapa21.midtermapp.data.model.RandomMealListItem

sealed interface MealDetailsUiState {
    data object Loading : MealDetailsUiState
    data class Success(val meal: RandomMealListItem) : MealDetailsUiState
    data class Error(val message: String) : MealDetailsUiState
}