package com.example.drinkables.data.mappers

/**
 * Interface to transform entityFrom : F to entityTo : T
 * @param F the entity that needs to be transformed
 * @param T the entity to be transformed into
 */
interface IEntityMapper<F, T> {
    fun mapEntity(entityFrom: F): T
}