package com.example.drinkables.domain.interactors

import android.util.Log
import com.example.drinkables.data.repositories.DrinksRatingRemoteRepository
import com.example.drinkables.data.repositories.UserLocalRepository
import com.example.drinkables.domain.common.Result
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateOrUpdateUserInteractor @Inject constructor(
    private val drinksRatingRemoteRepository: DrinksRatingRemoteRepository,
    private val userLocalRepository: UserLocalRepository
) {
    suspend fun run(userName: String): Boolean {

        val lastUserName = userLocalRepository.getUserName()
        var result: Boolean? = null

        val flowUser = if (lastUserName == null) {
            drinksRatingRemoteRepository.tryToCreateUser(userName)
        } else if (lastUserName != userName) {
            drinksRatingRemoteRepository.updateUser(lastUserName, userName)
        } else return true

        suspend fun computeUpdate() {
            coroutineScope {
                this.launch {
                    flowUser.collect {
                        Log.d("MyTag", "String")
                        if (it is Result.Error) {
                            result = false
                        } else if (it is Result.Success) {
                            result = it.data
                        }
                        this.coroutineContext.job.cancel()
                    }
                }
            }
        }

        computeUpdate()
        if (result == true) {
            userLocalRepository.createOrUpdateUser(userName)
        }

        return result ?: false
    }
}