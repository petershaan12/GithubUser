package com.petershaan.githubuser.ui

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.petershaan.githubuser.R
import com.petershaan.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "USER_GITHUB"
    }
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        val userLogin = intent.getStringExtra(EXTRA_USER)

        if (userLogin != null) {
            detailViewModel.githubUser(userLogin)
        }

        detailViewModel.detailGithubUsers.observe(this, Observer { response ->
            binding.tvUsername.text = response.login
            binding.tvName.text = response.name

            // Variasi Angkanya Bold sisana Engga :)
            val followersCount = response.followers ?: 0
            val followersText = SpannableString("$followersCount followers")
            followersText.setSpan(StyleSpan(Typeface.BOLD), 0, followersCount.toString().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvUserFollowers. = followersText

            val followingCount = response.followers ?: 0
            val followingText = SpannableString("$followingCount following")
            followersText.setSpan(StyleSpan(Typeface.BOLD), 0, followingCount.toString().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvUserFollowing.text = followingText

            Glide.with(binding.root.context)
                .load(response.avatarUrl)
                .into(binding.cardTvAvatar)
        })
    }

    private fun setupActionBar() {
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}