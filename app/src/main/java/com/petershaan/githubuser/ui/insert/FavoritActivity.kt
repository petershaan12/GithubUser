package com.petershaan.githubuser.ui.insert

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.petershaan.githubuser.data.response.ItemsItem
import com.petershaan.githubuser.database.Favorit
import com.petershaan.githubuser.databinding.ActivityFavoritBinding
import com.petershaan.githubuser.ui.DetailActivity
import com.petershaan.githubuser.ui.FollowViewModel
import com.petershaan.githubuser.ui.GithubAdapter

class FavoritActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritBinding
    private val favoritViewModel by viewModels<FavoritViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.rvListFavorit.layoutManager = LinearLayoutManager(this)
        // Inisialisasi adapter dengan daftar kosong
        val adapter = GithubAdapter()
        binding.rvListFavorit.adapter = adapter

        favoritViewModel.getAllFavorit().observe(this) { github ->
            val items = arrayListOf<ItemsItem>()
            github.forEach {
                it.avatarUrl?.let { avatarUrl ->
                    val item = ItemsItem(login = it.username, avatarUrl = avatarUrl, id = 0)
                    items.add(item)
                }
            }

            // Perbarui daftar pada adapter setelah mendapatkan data dari ViewModel
            adapter.submitList(items)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}