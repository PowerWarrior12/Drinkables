package com.example.drinkables.data.mappers

import com.example.drinkables.data.bd.DrinkEntity
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class DrinkEntityToDrinkMapper @Inject constructor() :
    EntityMapper<DrinkEntity, Drink> {
    override fun mapEntity(entityFrom: DrinkEntity): Drink {
        return Drink(
            id = entityFrom.id,
            title = entityFrom.title,
            description = entityFrom.description,
            imageUrl = entityFrom.imageUrl
        )
    }
}