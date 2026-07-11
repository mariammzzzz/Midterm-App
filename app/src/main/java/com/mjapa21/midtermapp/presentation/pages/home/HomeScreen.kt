package com.mjapa21.midtermapp.presentation.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mjapa21.designsystem.components.CategoryChip
import com.mjapa21.midtermapp.data.RetrofitInstance
import com.mjapa21.midtermapp.data.repository.FoodRepository
import com.mjapa21.midtermapp.domain.usecases.GetCategoriesUseCase

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel {
        HomeScreenViewModel(GetCategoriesUseCase(FoodRepository(RetrofitInstance.createFoodApi())))
    }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Categories API test",
            style = MaterialTheme.typography.titleLarge
        )

        when (val state = uiState) {
            is HomeUiState.Loading -> CircularProgressIndicator()

            is HomeUiState.Error -> Text(
                text = "Error: ${state.message}",
                color = MaterialTheme.colorScheme.error
            )

            is HomeUiState.Success -> {
                if (state.categories.isEmpty()) {
                    Text("No categories returned.")
                } else {
                    Text("Loaded ${state.categories.size} categories")
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.categories) { category ->
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
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}