package com.mjapa21.midtermapp.presentation.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjapa21.midtermapp.domain.usecases.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeScreenViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            getCategoriesUseCase.invoke()
                .onSuccess { result ->
                    _uiState.value = HomeUiState.Success(result.categories)
                }
                .onFailure { _ ->
                    _uiState.value =
                        HomeUiState.Error("Could not load data. Please make sure you are connected to the internet")
                }
        }
    }

    fun onTryAgainClick() {
        loadCategories()
    }
}