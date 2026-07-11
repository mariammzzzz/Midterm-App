package com.mjapa21.midtermapp.presentation.pages.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjapa21.midtermapp.domain.usecases.GetFoodDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val getFoodDetailsUseCase: GetFoodDetailsUseCase,
    private val mealId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<MealDetailsUiState>(MealDetailsUiState.Loading)
    val uiState: StateFlow<MealDetailsUiState> = _uiState.asStateFlow()

    init {
        loadMeal()
    }

    fun loadMeal() {
        viewModelScope.launch {
            _uiState.value = MealDetailsUiState.Loading
            getFoodDetailsUseCase.invoke(mealId)
                .onSuccess { meal ->
                    _uiState.value = MealDetailsUiState.Success(meal)
                }
                .onFailure {
                    _uiState.value = MealDetailsUiState.Error(
                        "Could not load this meal. Please check your connection and try again."
                    )
                }
        }
    }
}