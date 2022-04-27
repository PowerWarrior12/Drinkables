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
    val imageUrl: String,
    @SerializedName("ibu")
    val ibu: Float,
    @SerializedName("ebc")
    val ebc: Float,
    @SerializedName("srm")
    val srm: Float,
    @SerializedName("ph")
    val ph: Float,
    @SerializedName("attenuation_level")
    val attenuationLevel: Float,
    @SerializedName("boil_volume")
    val boilVolume: BoilVolumeResponse,
    @SerializedName("food_pairing")
    val foodPairing: List<String>,
)

data class BoilVolumeResponse(
    @SerializedName("value")
    val value: Float,
    @SerializedName("unit")
    val unit: String,
)
