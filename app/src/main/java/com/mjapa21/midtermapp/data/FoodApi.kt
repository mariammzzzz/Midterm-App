package com.mjapa21.midtermapp.data

import com.mjapa21.midtermapp.data.model.CategoriesList
import com.mjapa21.midtermapp.data.model.MealsList
import com.mjapa21.midtermapp.data.model.RandomMealsList
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("categories.php")
    suspend fun getFoodCategories(): CategoriesList


    @GET("filter.php")
    suspend fun getFoodByCategory(
        @Query("c") category: String
    ): MealsList


    @GET("random.php")
    suspend fun getRandomMeal(): RandomMealsList

    @GET
    suspend fun getFoodById(
        @Query("i") id: String
    ): RandomMealsList
}