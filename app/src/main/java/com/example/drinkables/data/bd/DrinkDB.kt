package com.example.drinkables.data.bd

import android.content.Context
import android.os.Build
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.drinkables.domain.entities.Drink

const val DB_VERSION = 1

@Database(entities = [DrinkEntity::class], version = DB_VERSION)
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
                    //.addMigrations(MIGRATION_1_2)
                    .build()
            }
        }

        private val MIGRATION_1_2 = object: Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE drink ADD COLUMN title VARCHAR(40) NOT NULL DEFAULT '';" +
                            "ALTER TABLE drink COLUMN description VARCHAR(1000) NOT NULL DEFAULT '';" +
                            "ALTER TABLE drink COLUMN imageUrl VARCHAR(200);"
                )
            }
        }
    }

}