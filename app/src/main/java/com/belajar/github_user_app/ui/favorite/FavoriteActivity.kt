package com.belajar.github_user_app.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.github_user_app.database.ConfigDatabase
import com.belajar.github_user_app.databinding.ActivityFavoriteBinding
import com.belajar.github_user_app.network.GithubResponse
import com.belajar.github_user_app.recycler.FavoriteAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(ConfigDatabase(this))
    }

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getAllFavorite().observe(this) {
            showLoading(true)
            adapter.setListFavorite(it as MutableList<GithubResponse.User>)
            showLoading(false)
        }

        adapter = FavoriteAdapter()
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbFavorite.visibility = View.VISIBLE
        } else {
            binding.pbFavorite.visibility = View.GONE
        }
    }
}