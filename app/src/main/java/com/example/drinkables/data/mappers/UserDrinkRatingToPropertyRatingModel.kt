package com.example.drinkables.data.mappers

import com.example.drinkables.data.bd.UserDrinkRatingEntity
import com.example.drinkables.domain.entities.PropertyModel
import javax.inject.Inject

class UserDrinkRatingToPropertyRatingModel @Inject constructor(): EntityMapper<UserDrinkRatingEntity, PropertyModel.PropertyUserRatingModel> {
    override fun mapEntity(entityFrom: UserDrinkRatingEntity): PropertyModel.PropertyUserRatingModel {
        return PropertyModel.PropertyUserRatingModel(
            userName = entityFrom.name,
            rating = PropertyModel.PropertyRatingModel(
                id = entityFrom.drinkId,
                value = entityFrom.value
            )
        )
    }
}