package com.petershaan.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoritDao {
    @Insert
    suspend fun insert(favorite: Favorit)
    @Update
    suspend fun update(favorite: Favorit)
    @Delete
    suspend fun delete(favorite: Favorit)
    @Query("SELECT * from favorite_users")
    fun getAllFavorit(): LiveData<List<Favorit>>
}