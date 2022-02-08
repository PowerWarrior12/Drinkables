package com.example.drinkables.data.mappers

import com.example.drinkables.data.api.entities.DrinksApiResponse
import com.example.drinkables.domain.entities.DrinkViewEntity
import javax.inject.Inject

class DrinkViewEntityMapper @Inject constructor() :
    EntityMapper<DrinksApiResponse, DrinkViewEntity> {
    override fun mapEntity(entityFrom: DrinksApiResponse): DrinkViewEntity {
        return DrinkViewEntity(
            id = entityFrom.id,
            title = entityFrom.name
        )
    }
}