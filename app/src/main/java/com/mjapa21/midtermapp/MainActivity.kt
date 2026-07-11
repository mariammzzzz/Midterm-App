package com.mjapa21.midtermapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mjapa21.designsystem.components.CategoryChip
import com.mjapa21.midtermapp.data.RetrofitInstance
import com.mjapa21.midtermapp.data.model.CategoryListItem
import com.mjapa21.midtermapp.presentation.navigation.MainNavigationRoot
import com.mjapa21.theme.MidtermAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MidtermAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainNavigationRoot()
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriesTestScreen(modifier: Modifier = Modifier) {
    var categories by remember { mutableStateOf<List<CategoryListItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val foodApi = RetrofitInstance.createFoodApi()
            val response = foodApi.getFoodCategories()
            categories = response.categories
        } catch (e: Exception) {
            errorMessage = e.message ?: "Unknown error"
        } finally {
            isLoading = false
        }
    }

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

        when {
            isLoading -> CircularProgressIndicator()

            errorMessage != null -> Text(
                text = "Error: $errorMessage",
                color = MaterialTheme.colorScheme.error
            )

            categories.isEmpty() -> Text("No categories returned.")

            else -> {
                Text("Loaded ${categories.size} categories")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(categories) { category ->
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

@Preview(showBackground = true)
@Composable
private fun CategoriesTestScreenPreview() {
    MidtermAppTheme {
        CategoriesTestScreen()
    }
}