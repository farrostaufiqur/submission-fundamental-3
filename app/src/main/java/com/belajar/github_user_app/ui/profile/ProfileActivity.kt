package com.belajar.github_user_app.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.belajar.github_user_app.R
import com.belajar.github_user_app.R.string.*
import com.belajar.github_user_app.ui.profile.follow.SectionsPagerAdapter
import com.belajar.github_user_app.databinding.ActivityProfileBinding
import com.belajar.github_user_app.network.ApiConfig
import com.belajar.github_user_app.network.User
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = this.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val userPref = sharedPref.getString(userName, "null")

        if (userPref != null) {
            setData(userPref)
        } else {
            setData("Null")
        }

        viewPagerConfig()
    }

    private fun setData(q: String){
        binding.pbProfile.visibility = View.VISIBLE
        val client= ApiConfig.getApiService().getUser(q)
        client.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>){
                binding.pbProfile.visibility = View.GONE
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null){
                        val profileUsername = binding.tvProfileUsername
                        val profileAvatar = binding.tvProfileAvatar
                        val profileName = binding.tvProfileName
                        val profileCompany = binding.tvProfileCompany
                        val profileLocation = binding.tvProfileLocation
                        val profileBio = binding.tvProfileBio
                        val profileBlog = binding.tvProfileBlog
                        val profileRepo = binding.tvProfileRepo

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
                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                binding.pbProfile.visibility = View.GONE
                Log.e(TAG, "onFailure: ${t.message.toString()}")
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
        menuInflater.inflate(R.menu.option_light_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
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