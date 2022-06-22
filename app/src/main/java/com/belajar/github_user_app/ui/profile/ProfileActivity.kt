package com.belajar.github_user_app.ui.profile

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.belajar.github_user_app.R
import com.belajar.github_user_app.R.string.tab_text_1
import com.belajar.github_user_app.R.string.tab_text_2
import com.belajar.github_user_app.database.ConfigDatabase
import com.belajar.github_user_app.databinding.ActivityProfileBinding
import com.belajar.github_user_app.network.ApiConfig
import com.belajar.github_user_app.network.GithubResponse
import com.belajar.github_user_app.ui.favorite.FavoriteActivity
import com.belajar.github_user_app.ui.profile.follow.SectionsPagerAdapter
import com.belajar.github_user_app.ui.settings.SettingsActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private var isFavorite = false
    private lateinit var userResponse: GithubResponse.User
    private val  viewModel by viewModels<ProfileViewModel> {
        ProfileViewModel.Factory(ConfigDatabase(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = this.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val userPref = sharedPref.getString(userName, "null")

        if (userPref != null) {
            setData(userPref)
            viewModel.getFavorite(userPref)
        } else {
            setData("Null")
        }

        viewModel.isFavorite.observe(this) {
            showFavorite(it)
        }

        showFavorite(isFavorite)
        binding.favorite.setOnClickListener{
            viewModel.saveUser(userResponse)
        }
        viewPagerConfig()
    }

    private fun setData(q: String){
        showLoading(true)
        val client= ApiConfig.getApiService().getUser(q)
        client.enqueue(object : Callback<GithubResponse.User> {
            override fun onResponse(call: Call<GithubResponse.User>, response: Response<GithubResponse.User>){
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null){
                        userResponse = responseBody
                        binding.apply {
                            val profileUsername = tvProfileUsername
                            val profileAvatar = tvProfileAvatar
                            val profileName = tvProfileName
                            val profileCompany = tvProfileCompany
                            val profileLocation = tvProfileLocation
                            val profileBio = tvProfileBio
                            val profileBlog = tvProfileBlog
                            val profileRepo = tvProfileRepo
                            if (responseBody.name.isNullOrBlank()) {
                                profileName.text = "-"
                            } else {
                                profileName.text = responseBody.name
                            }

                            if (responseBody.location.isNullOrBlank()) {
                                profileLocation.text = "-"
                            } else {
                                profileLocation.text = responseBody.location
                            }

                            if (responseBody.company.isNullOrBlank()) {
                                profileCompany.text = "-"
                            } else {
                                profileCompany.text = responseBody.company
                            }

                            if (responseBody.bio.isNullOrBlank()) {
                                profileBio.text = "-"
                            } else {
                                profileBio.text = responseBody.bio
                            }

                            if (responseBody.blog.isNullOrBlank()) {
                                profileBlog.text = "-"
                            } else {
                                profileBlog.text = responseBody.blog
                            }
                            profileUsername.text = responseBody.login
                            profileRepo.text = responseBody.publicRepos.toString()
                            Glide.with(this@ProfileActivity)
                                .load(responseBody.avatarUrl)
                                .circleCrop()
                                .into(profileAvatar)
                            Log.d(TAG, "Profile Data: ${responseBody.login}, ${responseBody.avatarUrl}, ${responseBody.followers}, ${responseBody.following}, ${responseBody.name}, ${responseBody.company}, ${responseBody.location}, ${responseBody.bio}, ${responseBody.blog}, ${responseBody.publicRepos}")
                            showLoading(false)
                        }
                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                    showLoading(false)
                }
            }
            override fun onFailure(call: Call<GithubResponse.User>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                showLoading(false)
            }
        })
    }

    private fun viewPagerConfig() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this@ProfileActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.favorites -> {
                val intent = Intent(this@ProfileActivity, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }

    private fun showFavorite(isFavorite: Boolean) {
        binding.apply {
            if (isFavorite) {
                favorite.changeIconColor(R.color.red)
            } else {
                favorite.changeIconColor(R.color.white)
            }
        }
    }

    private fun FloatingActionButton.changeIconColor(@ColorRes color: Int){
        imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                pbProfile.visibility = View.VISIBLE
            } else {
                pbProfile.visibility = View.GONE
            }
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            tab_text_1,
            tab_text_2
        )
        private const val TAG = "ProfileActivity"
        private const val prefsName = "TEMP_ID"
        private const val userName = "user_name"
    }
}