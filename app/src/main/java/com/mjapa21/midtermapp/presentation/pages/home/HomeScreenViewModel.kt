package com.mjapa21.midtermapp.presentation.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjapa21.midtermapp.domain.usecases.GetCategoriesUseCase
import com.mjapa21.midtermapp.domain.usecases.GetRandomMealUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
            // 4 random meals fetched concurrently — first one is treated as "featured" by the UI
            val mealDeferreds = List(RANDOM_MEAL_COUNT) { async { getRandomMealUseCase.invoke() } }

            val categoriesResult = categoriesDeferred.await()
            val mealResults = mealDeferreds.awaitAll()
            val randomMeals = mealResults.mapNotNull { it.getOrNull() }

            // require categories + at least one meal to consider the screen loaded
            if (categoriesResult.isSuccess && randomMeals.isNotEmpty()) {
                _uiState.value = HomeUiState.Success(
                    HomeScreenData(
                        categories = categoriesResult.getOrThrow().categories,
                        randomMeals = randomMeals
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

    private companion object {
        const val RANDOM_MEAL_COUNT = 4
    }
}