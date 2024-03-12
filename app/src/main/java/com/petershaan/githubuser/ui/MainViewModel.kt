package com.petershaan.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petershaan.githubuser.data.response.GithubResponse
import com.petershaan.githubuser.data.response.ItemsItem
import com.petershaan.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel(){

    private val _github = MutableLiveData<GithubResponse>()
    val github: LiveData<GithubResponse> = _github
    private val _listGithubUsers = MutableLiveData<List<ItemsItem>>()
    val listReview: LiveData<List<ItemsItem>> = _listGithubUsers
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        private const val GITHUB_NAME = "Peter"
    }

    private fun findGithubUser(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getResponUser(name)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, "onResponse: Response successful, total items: ${responseBody.items.size}")
                        _listGithubUsers.value =  response.body()?.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}