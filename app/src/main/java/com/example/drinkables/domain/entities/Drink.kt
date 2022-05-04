package com.example.drinkables.domain.entities

import java.io.Serializable

data class Drink(
    val id: Int = 0,
    val title: String = "",
    var favourites: Boolean = false,
    val description: String = "",
    val imageUrl: String? = "",
    val ibu: Float = 0.0f,
    val ebc: Float = 0.0f,
    val srm: Float = 0.0f,
    val ph: Float = 0.0f,
    val attenuationLevel: Float = 0.0f,
    val boilVolume: BoilVolume = BoilVolume(),
    val foodPairing: List<String> = listOf(),
) : Serializable

data class BoilVolume(
    val value: Float = 0.0f,
    val unit: String = "",
)
