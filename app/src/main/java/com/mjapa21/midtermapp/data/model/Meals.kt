package com.mjapa21.midtermapp.data.model


data class MealsList(
    val meals: List<MealListItem>
)

data class MealListItem(
    val strMeal: String,
    val strMealThumb: String?,
    val idMeal: String,
    val strArea: String? // where the meal is from, e.g. Italian, American, etc
)


data class RandomMealsList(
    val meals: List<RandomMealListItem>
)

data class RandomMealListItem(
    val idMeal: String,
    val strMeal: String?,
    val strCountry: String?,
    val strInstructions: String?,
    val strMealThumb: String?
)