package com.example.drinkables.data.bd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_drink_rating")
data class UserDrinkRatingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val drinkId: Int,
    val value: Int
)
