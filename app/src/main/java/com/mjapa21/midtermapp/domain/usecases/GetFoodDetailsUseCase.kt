package com.mjapa21.midtermapp.domain.usecases

import com.mjapa21.midtermapp.data.model.RandomMealListItem
import com.mjapa21.midtermapp.data.repository.FoodRepository


class GetFoodDetailsUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(id: String): Result<RandomMealListItem> {
        return repository.getFoodById(id).mapCatching { mealsList ->
            mealsList.meals.first() // the API returns a list with one item, so we take the first item
        }
    }
}