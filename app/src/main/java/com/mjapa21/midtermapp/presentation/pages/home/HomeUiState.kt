package com.mjapa21.midtermapp.presentation.pages.home

import com.mjapa21.midtermapp.data.model.CategoryListItem
import com.mjapa21.midtermapp.data.model.RandomMealListItem

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val data: HomeScreenData) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

data class HomeScreenData(
    val categories: List<CategoryListItem>,
    val randomMeals: List<RandomMealListItem>
)