package com.petershaan.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorit::class], version = 1)
abstract class FavoritRoomDatabase : RoomDatabase() {
    abstract  fun favoritDao(): FavoritDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoritRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoritRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoritRoomDatabase::class.java, "fav_database")
                        .build()
                }
            }
            return INSTANCE as FavoritRoomDatabase
        }
    }
}