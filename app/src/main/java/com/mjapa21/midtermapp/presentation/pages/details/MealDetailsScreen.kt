package com.mjapa21.midtermapp.presentation.pages.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mjapa21.designsystem.components.InfoBox
import com.mjapa21.midtermapp.data.RetrofitInstance
import com.mjapa21.midtermapp.data.model.RandomMealListItem
import com.mjapa21.midtermapp.data.repository.FoodRepository
import com.mjapa21.midtermapp.domain.usecases.GetFoodDetailsUseCase
import com.mjapa21.midtermapp.presentation.pages.error.ErrorScreen
import com.mjapa21.designsystem.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailsScreen(
    modifier: Modifier = Modifier,
    mealId: String,
    onBackClick: () -> Unit = {},
    viewModel: MealDetailsViewModel = viewModel {
        val repository = FoodRepository(RetrofitInstance.createFoodApi())
        MealDetailsViewModel(
            getFoodDetailsUseCase = GetFoodDetailsUseCase(repository),
            mealId = mealId
        )
    }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(DesignSystemR.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is MealDetailsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is MealDetailsUiState.Error -> {
                ErrorScreen(
                    errorMsg = state.message,
                    onTryAgainClick = { viewModel.loadMeal() },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is MealDetailsUiState.Success -> {
                MealDetailsContent(
                    modifier = Modifier.padding(innerPadding),
                    meal = state.meal
                )
            }
        }
    }
}

@Composable
private fun MealDetailsContent(
    meal: RandomMealListItem,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            InfoBox(
                imageUrl = meal.strMealThumb,
                width = null,
                height = 260.dp,
                shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp),
            )
        }

        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = meal.strMeal ?: "Untitled meal",
                    style = MaterialTheme.typography.headlineMedium
                )
                if (!meal.strCountry.isNullOrBlank()) {
                    Text(
                        text = meal.strCountry,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = meal.strInstructions?.takeIf { it.isNotBlank() }
                        ?: "No instructions available for this meal.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun MealDetailsScreenPreview() {
    MealDetailsScreen(mealId = "1234")
}