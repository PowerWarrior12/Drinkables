package com.example.drinkables.data.mappers

import com.example.drinkables.data.bd.DrinkEntity
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class DrinkToDrinkEntityMapper @Inject constructor() :
    EntityMapper<Drink, DrinkEntity> {
    override fun mapEntity(entityFrom: Drink): DrinkEntity {
        return DrinkEntity(
            id = entityFrom.id,
            title = entityFrom.title,
            description = entityFrom.description,
            imageUrl = entityFrom.imageUrl ?: ""
        )
    }
}