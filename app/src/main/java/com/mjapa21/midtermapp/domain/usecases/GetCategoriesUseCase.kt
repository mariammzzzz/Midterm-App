package com.mjapa21.midtermapp.domain.usecases

import com.mjapa21.midtermapp.data.model.CategoriesList
import com.mjapa21.midtermapp.data.repository.FoodRepository

class GetCategoriesUseCase(private val repository: FoodRepository) {
    suspend fun invoke(): Result<CategoriesList> = repository.getFoodCategories()
}