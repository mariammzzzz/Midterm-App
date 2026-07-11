package com.mjapa21.midtermapp.presentation.pages.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjapa21.midtermapp.domain.usecases.GetFoodByCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryMealsViewModel(
    private val getFoodByCategoryUseCase: GetFoodByCategoryUseCase,
    private val category: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryMealsUiState>(CategoryMealsUiState.Loading)
    val uiState: StateFlow<CategoryMealsUiState> = _uiState.asStateFlow()

    init {
        loadMeals()
    }

    fun loadMeals() {
        viewModelScope.launch {
            _uiState.value = CategoryMealsUiState.Loading
            getFoodByCategoryUseCase.invoke(category)
                .onSuccess { mealsList ->
                    val topMeals = mealsList.meals.take(MAX_MEALS)
                    _uiState.value = CategoryMealsUiState.Success(topMeals)
                }
                .onFailure {
                    _uiState.value = CategoryMealsUiState.Error(ERROR_MSG)
                }
        }
    }

    companion object {
        private const val MAX_MEALS = 10
        private const val ERROR_MSG =
            "Could not load meals for this category. Please check your connection and try again."
    }
}