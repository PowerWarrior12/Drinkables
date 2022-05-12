package com.example.drinkables.data.mappers

import com.example.drinkables.data.bd.DrinksRatingEntity
import com.example.drinkables.domain.entities.PropertyModel
import javax.inject.Inject

class DrinkToDrinkRatingMapper @Inject constructor() :
    EntityMapper<PropertyModel.PropertyRatingModel, DrinksRatingEntity> {
    override fun mapEntity(entityFrom: PropertyModel.PropertyRatingModel): DrinksRatingEntity {
        return DrinksRatingEntity(
            id = entityFrom.id,
            rating = entityFrom.value
        )
    }
}