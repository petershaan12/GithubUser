package com.petershaan.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.petershaan.githubuser.data.response.ItemsItem
import com.petershaan.githubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
    private lateinit var binding: FragmentFollowBinding
    private val followViewModel by viewModels<FollowViewModel>()

    private var position: Int = 0
    private var username: String = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false) // Menggunakan binding yang benar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }
        if (position == 1){
            followViewModel.getFollowing(username)
            followViewModel.listFollowing.observe(viewLifecycleOwner) { followingList ->
                setupRecyclerView(followingList)
            }
        } else {
            followViewModel.getFollowers(username)
            followViewModel.listFollowers.observe(viewLifecycleOwner) { followersList ->
                setupRecyclerView(followersList)
            }
        }
        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setupRecyclerView(follow: List<ItemsItem>) {
        binding.rvListFollow.layoutManager = LinearLayoutManager(requireContext())
        val adapter = GithubAdapter()
        adapter.submitList(follow)
        binding.rvListFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

//