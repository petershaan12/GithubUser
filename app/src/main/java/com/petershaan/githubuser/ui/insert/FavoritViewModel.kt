package com.petershaan.githubuser.ui.insert

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.petershaan.githubuser.data.FavoritRepository
import com.petershaan.githubuser.database.Favorit

class FavoritViewModel(application: Application) : AndroidViewModel(application) {
    private val mFavoritRepository: FavoritRepository = FavoritRepository(application)
    fun getAllFavorit(): LiveData<List<Favorit>> = mFavoritRepository.getAllFavorit()

    fun insert(favorit: Favorit) {
        mFavoritRepository.insert(favorit)
    }

    fun delete(favorit: Favorit) {
        mFavoritRepository.delete(favorit)
    }

    fun update(favorit: Favorit){
        mFavoritRepository.update(favorit)
    }

}