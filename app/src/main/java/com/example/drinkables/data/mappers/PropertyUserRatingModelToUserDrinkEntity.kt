package com.example.drinkables.data.mappers

import com.example.drinkables.data.bd.UserDrinkRatingEntity
import com.example.drinkables.domain.entities.PropertyModel
import javax.inject.Inject

class PropertyUserRatingModelToUserDrinkEntity @Inject constructor() :
    EntityMapper<PropertyModel.PropertyUserRatingModel, UserDrinkRatingEntity> {
    override fun mapEntity(entityFrom: PropertyModel.PropertyUserRatingModel): UserDrinkRatingEntity {
        return UserDrinkRatingEntity(
            name = entityFrom.userName,
            drinkId = entityFrom.rating.id,
            value = entityFrom.rating.value
        )
    }
}