package com.example.drinkables.domain.entities

data class UsersRatings(
    val user: User,
    val ratings: List<PropertyModel.PropertyRatingModel>
)
