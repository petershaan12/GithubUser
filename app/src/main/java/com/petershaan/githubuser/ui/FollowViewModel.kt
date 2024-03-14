package com.petershaan.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petershaan.githubuser.data.response.ItemsItem
import com.petershaan.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers: LiveData<List<ItemsItem>> = _listFollowers
    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowViewModel"
        private const val GITHUB_NAME = "Peter"
    }

    init {
        getFollowers(GITHUB_NAME)
    }

    fun getFollowers(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(name)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, "onResponse: Response successful, total items: ${responseBody.size}")
                        _listFollowers.value =  responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}