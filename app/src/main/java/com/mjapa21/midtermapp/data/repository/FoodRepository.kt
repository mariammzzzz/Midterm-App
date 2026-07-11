package com.mjapa21.midtermapp.data.repository

import com.mjapa21.midtermapp.data.FoodApi
import com.mjapa21.midtermapp.data.model.CategoriesList
import com.mjapa21.midtermapp.data.model.MealsList
import com.mjapa21.midtermapp.data.model.RandomMealsList

class FoodRepository(private val foodApi: FoodApi) {
    suspend fun getFoodCategories(): Result<CategoriesList> {
        return try {
            val response = foodApi.getFoodCategories()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFoodByCategory(category: String): Result<MealsList> {
        return try {
            val response = foodApi.getFoodByCategory(category)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getRandomMeal(): Result<RandomMealsList> {
        return try {
            val response = foodApi.getRandomMeal()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFoodById(id: String): Result<RandomMealsList> {
        return try {
            val response = foodApi.getFoodById(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}