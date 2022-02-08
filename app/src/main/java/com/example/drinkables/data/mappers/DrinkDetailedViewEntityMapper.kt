package com.example.drinkables.data.mappers

import com.example.drinkables.data.api.entities.DrinkDetailedApiResponse
import com.example.drinkables.domain.entities.DrinkViewEntity
import javax.inject.Inject

class DrinkDetailedViewEntityMapper @Inject constructor() :
    EntityMapper<DrinkDetailedApiResponse, DrinkViewEntity> {
    override fun mapEntity(entityFrom: DrinkDetailedApiResponse): DrinkViewEntity {
        return DrinkViewEntity(
            id = entityFrom.id,
            title = entityFrom.name,
            description = entityFrom.description,
            imageUrl = entityFrom.image_url
        )
    }
}