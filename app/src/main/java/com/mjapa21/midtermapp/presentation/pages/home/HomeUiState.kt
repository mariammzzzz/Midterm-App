package com.mjapa21.midtermapp.presentation.pages.home

import com.mjapa21.midtermapp.data.model.CategoryListItem

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val categories: List<CategoryListItem>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}