package com.mjapa21.midtermapp.data.model

data class CategoriesList(
    val categories: List<CategoryListItem>
)

data class CategoryListItem(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)
