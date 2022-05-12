package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.DrinksRatingRepository
import javax.inject.Inject

class LoadDrinkRatingInteractor @Inject constructor(private val drinksRatingRepository: DrinksRatingRepository) {
    fun run(id: Int) = drinksRatingRepository.loadDrinkRating(id)
}