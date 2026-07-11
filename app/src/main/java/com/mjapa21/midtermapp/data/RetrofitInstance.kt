package com.mjapa21.midtermapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL =
        "https://www.themealdb.com/api/json/v1/1/" //that /1/ is free api key

    fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createFoodApi(): FoodApi {
        return createRetrofitInstance().create(FoodApi::class.java)
    }

}