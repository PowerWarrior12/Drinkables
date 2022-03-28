package com.example.drinkables.data.bd

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "drink")
data class DrinkEntity(
    @PrimaryKey
    val id : Int,
    val title: String,
    val description: String,
    val imageUrl: String?
)
