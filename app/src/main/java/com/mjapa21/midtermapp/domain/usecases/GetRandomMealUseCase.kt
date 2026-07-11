package com.mjapa21.midtermapp.domain.usecases

import com.mjapa21.midtermapp.data.model.RandomMealListItem
import com.mjapa21.midtermapp.data.repository.FoodRepository


class GetRandomMealUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(): Result<RandomMealListItem> {
        return repository.getRandomMeal().mapCatching { randomMealsList ->
            randomMealsList.meals.first()
        }
    }
}