package com.belajar.github_user_app.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.github_user_app.R
import com.belajar.github_user_app.databinding.ActivityMainBinding
import com.belajar.github_user_app.network.GithubResponse
import com.belajar.github_user_app.recycler.UserAdapter
import com.belajar.github_user_app.ui.favorite.FavoriteActivity
import com.belajar.github_user_app.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val listUser = ArrayList<GithubResponse.User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        viewModel.users.observe(this) {
            setGitHubUserData(it)
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.addItemDecoration(itemDecoration)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchField
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                listUser.clear()
                viewModel.searchUser(q)
                val adapter = UserAdapter(listUser)
                binding.rvUser.adapter = adapter
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setGitHubUserData(listUserID: List<GithubResponse.User>) {
        val list = ArrayList<GithubResponse.User>()
        for (userID in listUserID) {
            val user = GithubResponse.User(
                userID.login,
                userID.avatarUrl,
                userID.followers,
                userID.following,
                userID.name,
                userID.company,
                userID.location,
                userID.bio,
                userID.blog,
                userID.publicRepos
            )
            list.add(user)
        }
        val listUserAdapter = UserAdapter(list)
        binding.rvUser.adapter = listUserAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbMain.visibility = View.VISIBLE
        } else {
            binding.pbMain.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.favorites -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }
}