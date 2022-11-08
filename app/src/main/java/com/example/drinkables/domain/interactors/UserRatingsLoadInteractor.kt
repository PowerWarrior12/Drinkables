package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.DrinksRatingRemoteRepository
import com.example.drinkables.data.repositories.DrinksRatingRepository
import javax.inject.Inject

class UserRatingsLoadInteractor @Inject constructor(
    private val remoteRepository: DrinksRatingRemoteRepository,
    private val localRepository: DrinksRatingRepository
) {
    suspend fun run() {
        remoteRepository.loadAllRatings().collect { list ->
            localRepository.updateUsersRatings(list)
        }
    }
}