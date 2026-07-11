package com.mjapa21.midtermapp.presentation.pages.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mjapa21.designsystem.components.InfoBox
import com.mjapa21.designsystem.components.SectionHeader
import com.mjapa21.midtermapp.data.RetrofitInstance
import com.mjapa21.midtermapp.data.repository.FoodRepository
import com.mjapa21.midtermapp.domain.usecases.GetFoodByCategoryUseCase
import com.mjapa21.midtermapp.presentation.pages.error.ErrorScreen

@Composable
fun CategoryMealsScreen(
    modifier: Modifier = Modifier,
    category: String,
    onMealClick: (String) -> Unit,
    viewModel: CategoryMealsViewModel = viewModel {
        val repository = FoodRepository(RetrofitInstance.createFoodApi())
        CategoryMealsViewModel(
            getFoodByCategoryUseCase = GetFoodByCategoryUseCase(repository),
            category = category
        )
    }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is CategoryMealsUiState.Loading -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CategoryMealsUiState.Error -> {
            ErrorScreen(
                errorMsg = state.message,
                onTryAgainClick = { viewModel.loadMeals() },
                modifier = modifier
            )
        }

        is CategoryMealsUiState.Success -> {
            CategoryMealsContent(
                category = category,
                meals = state.meals,
                onMealClick = onMealClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun CategoryMealsContent(
    category: String,
    meals: List<com.mjapa21.midtermapp.data.model.MealListItem>,
    onMealClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionHeader(
                title = category,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        if (meals.isEmpty()) {
            item {
                Text(
                    text = "No meals found in this category.",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        } else {
            items(meals) { meal ->
                InfoBox(
                    imageUrl = meal.strMealThumb,
                    title = meal.strMeal,
                    description = meal.strArea?.let { "from $it" },
                    width = null,
                    height = 220.dp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { onMealClick(meal.idMeal) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CategoryMealsScreenPreview() {
    CategoryMealsScreen(category = "Beef", onMealClick = {})
}