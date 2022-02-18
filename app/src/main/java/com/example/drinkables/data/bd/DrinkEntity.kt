package com.example.drinkables.data.bd

import androidx.navigation.Navigator
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink")
data class DrinkEntity(
    @PrimaryKey
    val id : Int
)
