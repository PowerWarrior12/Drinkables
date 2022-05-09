package com.example.drinkables.data.mappers

import com.example.drinkables.data.api.entities.BoilVolumeResponse
import com.example.drinkables.domain.entities.BoilVolume
import javax.inject.Inject

class BoilVolumeResponseToBoilVolumeMapper @Inject constructor() : EntityMapper<BoilVolumeResponse, BoilVolume> {
    override fun mapEntity(entityFrom: BoilVolumeResponse): BoilVolume {
        return BoilVolume(
            value = entityFrom.value,
            unit = entityFrom.unit
        )
    }

}