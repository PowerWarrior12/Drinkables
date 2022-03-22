package com.example.drinkables.domain.entities

data class Drink(
    val id: Int = 0,
    val title: String = "",
    var favourites: Boolean = false,
    val description: String = "",
    val imageUrl: String? = ""
)
