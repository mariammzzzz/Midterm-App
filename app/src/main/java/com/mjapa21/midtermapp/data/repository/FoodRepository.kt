package com.mjapa21.midtermapp.data.repository

import com.mjapa21.midtermapp.data.FoodApi
import com.mjapa21.midtermapp.data.model.CategoriesList

class FoodRepository(private val foodApi: FoodApi) {

    suspend fun getFoodCategories(): Result<CategoriesList> {
        return try {
            val response = foodApi.getFoodCategories()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}