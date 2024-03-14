package com.petershaan.githubuser.ui

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.petershaan.githubuser.R
import com.petershaan.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "USER_GITHUB"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        val userLogin = intent.getStringExtra(EXTRA_USER)

        if (userLogin != null) {
            detailViewModel.githubUser(userLogin)
        }

//        Data Untuk Github Detail
        detailViewModel.detailGithubUsers.observe(this, Observer { response ->
            binding.tvUsername.text = response.login
            binding.tvName.text = response.name

            // Variasi Angkanya Bold sisana Engga :)
            val followersCount = response.followers ?: 0
            val followersText = SpannableString("$followersCount followers")
            followersText.setSpan(StyleSpan(Typeface.BOLD), 0, followersCount.toString().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvUserFollowers.text = followersText

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