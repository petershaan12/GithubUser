package com.petershaan.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.petershaan.githubuser.database.Favorit
import com.petershaan.githubuser.database.FavoritDao
import com.petershaan.githubuser.database.FavoritRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoritRepository(application: Application){
    private val mFavDao: FavoritDao
    private val executorService: CoroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        val db = FavoritRoomDatabase.getDatabase(application)
        mFavDao = db.favoritDao()
    }

    fun getAllFavorit(): LiveData<List<Favorit>> {
        return mFavDao.getAllFavorit()
    }

    fun insert(favorit: Favorit) {
        executorService.launch { mFavDao.insert(favorit) }
    }
    fun delete(favorit: Favorit) {
        executorService.launch { mFavDao.delete(favorit) }
    }
    fun update(favorit: Favorit) {
        executorService.launch { mFavDao.update(favorit) }
    }
}