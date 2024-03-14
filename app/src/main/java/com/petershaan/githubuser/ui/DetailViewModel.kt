package com.petershaan.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petershaan.githubuser.data.response.DetailGithubResponse
import com.petershaan.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _detailGithubUsers = MutableLiveData<DetailGithubResponse>()
    val detailGithubUsers: LiveData<DetailGithubResponse> = _detailGithubUsers
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun githubUser(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(name)
        client.enqueue(object : Callback<DetailGithubResponse> {
            override fun onResponse(
                call: Call<DetailGithubResponse>,
                response: Response<DetailGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, "onResponse: Response successful, the id you selected is: ${responseBody.id}")
                        _detailGithubUsers.value =  responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailGithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}