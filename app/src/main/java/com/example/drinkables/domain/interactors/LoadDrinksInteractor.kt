package com.example.drinkables.domain.interactors

import com.example.drinkables.data.repositories.DrinksRepository
import com.example.drinkables.data.repositories.FavouriteDrinksRepository
import com.example.drinkables.domain.entities.Drink
import com.example.drinkables.domain.common.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class LoadDrinksInteractor @Inject constructor(
    private val repository: DrinksRepository
) {
    suspend fun run(): Result<MutableList<Drink>> {
        return repository.loadDrinks()
    }
}