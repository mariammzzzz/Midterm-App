package com.mjapa21.midtermapp.domain.usecases

import com.mjapa21.midtermapp.data.model.MealsList
import com.mjapa21.midtermapp.data.repository.FoodRepository


class GetFoodByCategoryUseCase(private val repository: FoodRepository) {
    suspend fun invoke(category: String): Result<MealsList> = repository.getFoodByCategory(category)
}