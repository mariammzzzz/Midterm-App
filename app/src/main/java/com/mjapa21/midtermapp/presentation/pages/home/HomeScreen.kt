package com.mjapa21.midtermapp.presentation.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mjapa21.designsystem.components.CategoryChip
import com.mjapa21.designsystem.components.InfoBox
import com.mjapa21.designsystem.components.SectionHeader
import com.mjapa21.midtermapp.data.RetrofitInstance
import com.mjapa21.midtermapp.data.repository.FoodRepository
import com.mjapa21.midtermapp.domain.usecases.GetCategoriesUseCase
import com.mjapa21.midtermapp.domain.usecases.GetRandomMealUseCase
import com.mjapa21.midtermapp.presentation.pages.error.ErrorScreen
import com.mjapa21.designsystem.R as DesignSyStemR

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel {
        val repository = FoodRepository(RetrofitInstance.createFoodApi())
        HomeScreenViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(repository),
            getRandomMealUseCase = GetRandomMealUseCase(repository)
        )
    },
    onNavigateToMealDetails: (String) -> Unit,
    onNavigateToCategory: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateToMealDetailsEvent.collect { mealId ->
            onNavigateToMealDetails(mealId ?: "")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigateToCategoryEvent.collect { categoryId ->
            onNavigateToCategory(categoryId ?: "")
        }
    }




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
            HomeScreenContent(state = state, modifier = modifier, onMealClick = { mealId ->
                viewModel.onMealClick(mealId)
            }, onCategoryClick = { category -> viewModel.onCategoryClick(category) })
        }
    }
}


@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeUiState.Success,
    onMealClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit
) {
    val featuredMeal = state.data.randomMeals.firstOrNull()
    val moreMeals = state.data.randomMeals.drop(1)

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (featuredMeal != null) {
            item {
                SectionHeader(
                    title = "Today's pick",
                    iconRes = DesignSyStemR.drawable.ic_arrow_next,
                    onClick = { onMealClick(featuredMeal.idMeal) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                InfoBox(
                    imageUrl = featuredMeal.strMealThumb,
                    title = featuredMeal.strMeal ?: "",
                    width = null,
                    height = 200.dp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { onMealClick(featuredMeal.idMeal) }
                )
            }
        }

        item {
            SectionHeader(
                title = "Categories",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            if (state.data.categories.isEmpty()) {
                Text(
                    text = "No categories returned.",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(state.data.categories) { category ->
                        CategoryChip(
                            category = category.strCategory,
                            imageUrl = category.strCategoryThumb,
                            onClick = { onCategoryClick(category.strCategory) }
                        )
                    }
                }
            }
        }

        if (moreMeals.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "More to try from multiple countries",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            items(moreMeals) { meal ->
                InfoBox(
                    imageUrl = meal.strMealThumb,
                    title = meal.strMeal ?: "",
                    description = meal.strCountry?.let { "from $it" } ?: "Unknown",
                    width = null,
                    height = 250.dp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { onMealClick(meal.idMeal) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(onNavigateToMealDetails = {}, onNavigateToCategory = {})
}