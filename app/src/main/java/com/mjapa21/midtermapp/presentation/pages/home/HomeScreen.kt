package com.mjapa21.midtermapp.presentation.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mjapa21.designsystem.components.CategoryChip
import com.mjapa21.designsystem.components.InfoBox
import com.mjapa21.midtermapp.data.RetrofitInstance
import com.mjapa21.midtermapp.data.repository.FoodRepository
import com.mjapa21.midtermapp.domain.usecases.GetCategoriesUseCase
import com.mjapa21.midtermapp.domain.usecases.GetRandomMealUseCase
import com.mjapa21.midtermapp.presentation.pages.error.ErrorScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel {
        val repository = FoodRepository(RetrofitInstance.createFoodApi())
        HomeScreenViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(repository),
            getRandomMealUseCase = GetRandomMealUseCase(repository)
        )
    }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is HomeUiState.Loading -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is HomeUiState.Error -> {
            ErrorScreen(
                errorMsg = state.message,
                onTryAgainClick = { viewModel.onTryAgainClick() },
                modifier = modifier
            )
        }

        is HomeUiState.Success -> {
            HomeScreenContent(state = state, modifier = modifier)
        }
    }
}

@Composable
private fun HomeScreenContent(state: HomeUiState.Success, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Today's pick",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        InfoBox(
            imageUrl = state.data.randomMeal.strMealThumb,
            title = state.data.randomMeal.strMeal ?: "",
            width = null, // fills available width
            height = 200.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        if (state.data.categories.isEmpty()) {
            Text(
                text = "No categories returned.",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(state.data.categories) { category ->
                    CategoryChip(
                        category = category.strCategory,
                        imageUrl = category.strCategoryThumb,
                        onClick = { /* navigate or select */ }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}