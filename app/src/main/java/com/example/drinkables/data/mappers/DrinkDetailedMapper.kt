package com.example.drinkables.data.mappers

import com.example.drinkables.data.api.entities.BoilVolumeResponse
import com.example.drinkables.data.api.entities.DrinkResponse
import com.example.drinkables.domain.entities.BoilVolume
import com.example.drinkables.domain.entities.Drink
import javax.inject.Inject

class DrinkDetailedMapper @Inject constructor() :
    EntityMapper<DrinkResponse, Drink> {
    override fun mapEntity(entityFrom: DrinkResponse): Drink {
        return Drink(
            id = entityFrom.id,
            title = entityFrom.name,
            description = entityFrom.description,
            imageUrl = entityFrom.imageUrl,
            ebc = entityFrom.ebc,
            ibu = entityFrom.ibu,
            srm = entityFrom.srm,
            ph = entityFrom.ph,
            attenuationLevel = entityFrom.attenuationLevel,
            foodPairing = entityFrom.foodPairing,
            boilVolume = BoilVolumeResponseToBoilVolume().mapEntity(entityFrom.boilVolume)
        )
    }

    private class BoilVolumeResponseToBoilVolume : EntityMapper<BoilVolumeResponse, BoilVolume> {
        override fun mapEntity(entityFrom: BoilVolumeResponse): BoilVolume {
            return BoilVolume(
                value = entityFrom.value,
                unit = entityFrom.unit
            )
        }

    }
}