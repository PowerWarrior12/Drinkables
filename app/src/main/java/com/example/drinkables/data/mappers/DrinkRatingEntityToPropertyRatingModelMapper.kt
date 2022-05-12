package com.example.drinkables.data.mappers

import com.example.drinkables.data.bd.DrinksRatingEntity
import com.example.drinkables.domain.entities.PropertyModel
import javax.inject.Inject

class DrinkRatingEntityToPropertyRatingModelMapper @Inject constructor() :
    EntityMapper<DrinksRatingEntity, PropertyModel.PropertyRatingModel> {
    override fun mapEntity(entityFrom: DrinksRatingEntity): PropertyModel.PropertyRatingModel {
        return PropertyModel.PropertyRatingModel(
            id = entityFrom.id,
            value = entityFrom.rating
        )
    }
}