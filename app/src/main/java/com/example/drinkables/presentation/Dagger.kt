package com.example.drinkables.presentation

import com.example.drinkables.data.api.DrinksApi
import com.example.drinkables.data.api.RetrofitService
import com.example.drinkables.data.api.entities.DrinksApiResponse
import com.example.drinkables.data.mappers.DrinkViewEntityMapper
import com.example.drinkables.data.mappers.IEntityMapper
import com.example.drinkables.data.repositories.DrinkRepository
import com.example.drinkables.data.repositories.IDrinksRepository
import com.example.drinkables.domain.entities.DrinkViewEntity
import com.example.drinkables.presentation.DrinksList.DrinksListFragment
import com.example.drinkables.url
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(fragment: DrinksListFragment)
}

@Module(includes = [RemoteAppModule::class, AppBindModule::class])
class AppModule

@Module
class RemoteAppModule {
    @Provides
    fun provideDrinksApi(): DrinksApi {
        return RetrofitService()
            .getRetrofit(url)
            .create(DrinksApi::class.java)
    }
}

@Module
abstract class AppBindModule {
    @Binds
    abstract fun bindDrinkRepository(
        drinksRepository: DrinkRepository
    ): IDrinksRepository

    @Binds
    abstract fun bindDrinkViewEntityMapper(
        drinksViewEntityMapper: DrinkViewEntityMapper
    ): IEntityMapper<DrinksApiResponse, DrinkViewEntity>
}

