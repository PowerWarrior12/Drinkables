package com.example.drinkables.domain.entities

sealed class PropertyModel {
    data class PropertyValueModel(
        val text: String,
    ) : PropertyModel()

    data class PropertyTitleModel(
        val text: String,
    ) : PropertyModel()
}
