package com.example.drinkables.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

const val DB_VERSION = 1

@Database(entities = [DrinkEntity::class, DrinksRatingEntity::class, UserDrinkRatingEntity::class], version = DB_VERSION)
abstract class DrinkDB : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao

    companion object {
        fun getDatabase(appContext: Context): DrinkDB {
            synchronized(this) {
                return Room.databaseBuilder(
                    appContext,
                    DrinkDB::class.java,
                    DrinkDB::class.java.simpleName
                )
                    .build()
            }
        }
    }
}