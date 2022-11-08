package com.example.drinkables.presentation.createUser

import androidx.lifecycle.*
import com.example.drinkables.data.repositories.UserLocalRepository
import com.example.drinkables.domain.interactors.ChangeFavouriteDrinkInteractor
import com.example.drinkables.domain.interactors.CreateOrUpdateUserInteractor
import com.example.drinkables.domain.interactors.LoadPagingDrinksInteractor
import com.example.drinkables.presentation.drinksList.DrinksListViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateUserViewModel(
    private val createOrUpdateUserInteractor: CreateOrUpdateUserInteractor,
    private val userLocalRepository: UserLocalRepository
): ViewModel() {

    private val resultMutableLiveData = MutableLiveData<Boolean>()
    private val nameMutableLiveData = MutableLiveData<String>()

    val resultLiveData: LiveData<Boolean> = resultMutableLiveData
    val nameLiveData: LiveData<String> = nameMutableLiveData

    init {
        viewModelScope.launch {
            nameMutableLiveData.postValue(userLocalRepository.getUserName())
        }
    }

    fun createOrUpdateUser(user: String) {
        viewModelScope.launch {
            val result = createOrUpdateUserInteractor.run(user)
            if (result) {
                nameMutableLiveData.postValue(user)
            }
            resultMutableLiveData.postValue(result)
        }
    }

    class CreateUserViewModelFactory @Inject constructor(
        private val createOrUpdateUserInteractor: CreateOrUpdateUserInteractor,
        private val userLocalRepository: UserLocalRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CreateUserViewModel(
                createOrUpdateUserInteractor,
                userLocalRepository
            ) as T
        }
    }

}