package com.example.drinkables.domain.entities

sealed class PropertyModel {
    data class PropertyValueModel(
        val text: String,
    ) : PropertyModel()

    data class PropertyTitleModel(
        val text: String,
    ) : PropertyModel()

    data class PropertyRatingModel(
        val id: Int = 0,
        val value: Int = 0
    ) : PropertyModel()

    data class PropertyValueIndicatorModel(
        val value: Float,
        val maxValue: Float
    ) : PropertyModel()

    data class PropertyUserRatingModel(
        val userName: String,
        val rating: PropertyRatingModel
    ) : PropertyModel()
}
