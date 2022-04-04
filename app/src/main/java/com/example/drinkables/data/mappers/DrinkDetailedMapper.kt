package com.example.drinkables.data.mappers

import com.example.drinkables.data.api.entities.DrinkResponse
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class DrinkDetailedMapper @Inject constructor() :
    EntityMapper<DrinkResponse, Drink> {
    override fun mapEntity(entityFrom: DrinkResponse): Drink {
        return Drink(
            id = entityFrom.id,
            title = entityFrom.name,
            description = entityFrom.description,
            imageUrl = entityFrom.imageUrl
        )
    }
}