package com.passerby.cfttestproject.bussiness.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RequestsEntity::class], exportSchema = false, version = 3)
abstract class BINAppDB : RoomDatabase() {

    abstract fun getRequestDao(): RequestDao

    companion object {
        @Volatile
        private var INSTANCE: BINAppDB? = null
        fun getDatabase(context: Context): BINAppDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BINAppDB::class.java,
                    "requests_database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}