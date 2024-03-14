package com.petershaan.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import com.petershaan.githubuser.data.response.DetailGithubResponse
import com.petershaan.githubuser.data.response.ItemsItem
import com.petershaan.githubuser.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private var position: Int? = 0
    private var username: String? = ""

    private lateinit var binding: FragmentFollowBinding
    private val followViewModel by viewModels<FollowViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false) // Menggunakan binding yang benar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1){
//            binding.sectionLabel.text = "Get Follower $username"
            followViewModel.getFollowers(username)
            followViewModel.getFollowers.observe(viewLifecycleOwner) { followersList ->
                setupRecyclerView(followersList)
            }
        } else {
//            binding.sectionLabel.text = "Get Following $username"
        }
    }

    companion object {
        const val ARG_POSITION = "param1"
        const val ARG_USERNAME = "param2"
    }

    private fun setupRecyclerView(Follow: List<ItemsItem>) {
        val adapter = GithubAdapter()
        adapter.submitList(Follow)
        binding.rvList.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}

//