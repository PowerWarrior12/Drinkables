package com.example.drinkables.data.api.entities

import com.google.gson.annotations.SerializedName

data class DrinkResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url")
    val imageUrl: String
)
