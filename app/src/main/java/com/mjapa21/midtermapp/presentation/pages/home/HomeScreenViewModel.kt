package com.mjapa21.midtermapp.presentation.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjapa21.midtermapp.domain.usecases.GetCategoriesUseCase
import com.mjapa21.midtermapp.domain.usecases.GetRandomMealUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getRandomMealUseCase: GetRandomMealUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val categoriesDeferred = async { getCategoriesUseCase.invoke() }
            val randomMealDeferred = async { getRandomMealUseCase.invoke() }

            val categoriesResult = categoriesDeferred.await()
            val randomMealResult = randomMealDeferred.await()

            if (categoriesResult.isSuccess && randomMealResult.isSuccess) {
                _uiState.value = HomeUiState.Success(
                    HomeScreenData(
                        categories = categoriesResult.getOrThrow().categories,
                        randomMeal = randomMealResult.getOrThrow()
                    )
                )
            } else {
                _uiState.value = HomeUiState.Error(
                    "Could not load data. Please make sure you are connected to the internet"
                )
            }
        }
    }

    fun onTryAgainClick() {
        loadHomeData()
    }
}