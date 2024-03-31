package com.petershaan.githubuser.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.petershaan.githubuser.R
import com.petershaan.githubuser.database.Favorit
import com.petershaan.githubuser.databinding.ActivityDetailBinding
import com.petershaan.githubuser.ui.insert.FavoritViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoritViewModel by viewModels<FavoritViewModel>()
    private var avatarUrl: String = ""
    private var isFavorited: Boolean = false
    private var favorit: Favorit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val username = intent.getStringExtra(EXTRA_USER)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        if (username != null) {
            detailViewModel.githubUser(username)
            sectionsPagerAdapter.username = username.orEmpty()
        }


        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

//        Data Untuk Github Detail
        detailViewModel.detailGithubUsers.observe(this, Observer { response ->
            binding.tvUsername.text = response.login
            binding.tvName.text = response.name


            // Variasi Angkanya Bold sisana Engga :)
            val followersCount = response.followers ?: 0
            val followersText = SpannableString("$followersCount followers")
            followersText.setSpan(StyleSpan(Typeface.BOLD), 0, followersCount.toString().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvUserFollowers.text = followersText

            val followingCount = response.following ?: 0
            val followingText = SpannableString("$followingCount following")
            followingText.setSpan(StyleSpan(Typeface.BOLD), 0, followingCount.toString().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvUserFollowing.text = followingText

            avatarUrl = response.avatarUrl
            Glide.with(binding.root.context)
                .load(response.avatarUrl)
                .into(binding.cardTvAvatar)
        })

        Log.d(avatarUrl, avatarUrl)


        favoritViewModel.getAllFavorit().observe(this) { favList ->
            favList.forEach { favUser ->
                if (favUser.username == username) {
                    isFavorited = true
                    favorit = favUser
                    binding.favoriteButton.setImageResource(R.drawable.ic_favorit)
                    if (avatarUrl != favUser.avatarUrl) {
                        favUser.avatarUrl = avatarUrl
                        favoritViewModel.update(favUser)
                    }
                    return@forEach
                }
            }
            if (!isFavorited) {
                favorit = Favorit(username ?: "", avatarUrl)
            }
        }


        binding.favoriteButton.setOnClickListener {
            favorit?.let { favUser ->
                if (isFavorited) {
                    favoritViewModel.delete(favUser)
                    binding.favoriteButton.setImageResource(R.drawable.ic_favorit_border)
                } else {
                    favoritViewModel.insert(favUser)
                    binding.favoriteButton.setImageResource(R.drawable.ic_favorit)
                }
                isFavorited = !isFavorited
                Log.d("DetailActivity", "Is favorited: $isFavorited")
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "USER_GITHUB"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}