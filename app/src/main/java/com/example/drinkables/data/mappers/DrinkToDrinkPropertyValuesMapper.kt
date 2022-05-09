package com.example.drinkables.data.mappers

import android.content.Context
import com.example.drinkables.R
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.entities.PropertyModel
import javax.inject.Inject

class DrinkToDrinkPropertyValuesMapper @Inject constructor(private val context: Context) :
    EntityMapper<Drink, List<@JvmSuppressWildcards PropertyModel>> {
    override fun mapEntity(entityFrom: Drink): List<PropertyModel> {
        entityFrom.apply {
            var foodPairingText = ""
            foodPairing.forEach { str ->
                foodPairingText += str + "\n"
            }
            return listOf(
                PropertyModel.PropertyTitleModel(context.getString(R.string.boil_volume_label)),
                PropertyModel.PropertyValueModel("${boilVolume.value} ${boilVolume.unit}"),
                PropertyModel.PropertyTitleModel(context.getString(R.string.attenuation_level_label)),
                PropertyModel.PropertyValueModel(attenuationLevel.toString()),
                PropertyModel.PropertyTitleModel(context.getString(R.string.ebc_label)),
                PropertyModel.PropertyValueModel(ebc.toString()),
                PropertyModel.PropertyTitleModel(context.getString(R.string.food_pairing_label)),
                PropertyModel.PropertyValueModel(foodPairingText),
                PropertyModel.PropertyTitleModel(context.getString(R.string.ibu_label)),
                PropertyModel.PropertyValueModel(ibu.toString()),
                PropertyModel.PropertyTitleModel(context.getString(R.string.ph_label)),
                PropertyModel.PropertyValueModel(ph.toString()),
                PropertyModel.PropertyTitleModel(context.getString(R.string.srm_label)),
                PropertyModel.PropertyValueModel(srm.toString()),
            )
        }
    }
}