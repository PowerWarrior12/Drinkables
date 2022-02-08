package com.example.drinkables.data.mappers

import com.example.drinkables.data.api.entities.DrinksResponse
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class DrinkMapper @Inject constructor() :
    EntityMapper<DrinksResponse, Drink> {
    override fun mapEntity(entityFrom: DrinksResponse): Drink {
        return Drink(
            id = entityFrom.id,
            title = entityFrom.name
        )
    }
}