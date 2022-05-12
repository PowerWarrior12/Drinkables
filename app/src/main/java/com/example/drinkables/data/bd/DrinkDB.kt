package com.example.drinkables.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

const val DB_VERSION = 2

@Database(entities = [DrinkEntity::class, DrinksRatingEntity::class], version = DB_VERSION)
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
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE 'drink_rating' (" +
                            "'id' INT(10) NOT NULL," +
                            "'rating' INT(2) NOT NULL," +
                            "PRIMARY KEY ('id'));"
                )
            }
        }
    }
}