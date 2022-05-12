package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.DrinksRatingRepository
import com.example.drinkables.domain.entities.PropertyModel
import javax.inject.Inject

class AddOrUpdateRatingInteractor @Inject constructor(private val drinksRatingRepository: DrinksRatingRepository) {
    suspend fun run(drinkRating: PropertyModel.PropertyRatingModel) {
        if (drinksRatingRepository.checkDrinkRating(drinkRating.id)) {
            drinksRatingRepository.updateDrinkRating(drinkRating)
        } else {
            drinksRatingRepository.addDrinkRating(drinkRating)
        }
    }
}