package com.mjapa21.midtermapp.presentation.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjapa21.midtermapp.domain.usecases.GetCategoriesUseCase
import com.mjapa21.midtermapp.domain.usecases.GetRandomMealUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getRandomMealUseCase: GetRandomMealUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _navigateToMealDetailsEvent = MutableSharedFlow<String?>()
    val navigateToMealDetailsEvent: SharedFlow<String?> = _navigateToMealDetailsEvent.asSharedFlow()


    private val _navigateToCategoryEvent = MutableSharedFlow<String?>()
    val navigateToCategoryEvent: SharedFlow<String?> = _navigateToCategoryEvent.asSharedFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val categoriesDeferred = async { getCategoriesUseCase.invoke() }
            // 4 random meals fetched concurrently — first one is treated as "featured" (today's pick) by the UI
            val mealDeferreds = List(RANDOM_MEAL_COUNT) { async { getRandomMealUseCase.invoke() } }

            val categoriesResult = categoriesDeferred.await()
            val mealResults = mealDeferreds.awaitAll()
            val randomMeals = mealResults.mapNotNull { it.getOrNull() }

            // we require categories + at least one meal to consider the screen loaded; else we show error
            if (categoriesResult.isSuccess && randomMeals.isNotEmpty()) {
                _uiState.value = HomeUiState.Success(
                    HomeScreenData(
                        categories = categoriesResult.getOrThrow().categories,
                        randomMeals = randomMeals
                    )
                )
            } else {
                _uiState.value = HomeUiState.Error(ERROR_MSG)
            }
        }
    }

    fun onTryAgainClick() {
        loadHomeData()
    }

    fun onMealClick(mealId: String) {
        viewModelScope.launch {
            _navigateToMealDetailsEvent.emit(mealId)
        }
    }

    fun onCategoryClick(category: String) {
        viewModelScope.launch {
            _navigateToCategoryEvent.emit(category)
        }
    }

    private companion object {
        private const val RANDOM_MEAL_COUNT = 4
        private const val ERROR_MSG =
            "Could not load data. Please make sure you are connected to the internet"
    }
}