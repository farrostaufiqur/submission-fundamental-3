package com.belajar.github_user_app.ui.profile.follow

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.github_user_app.databinding.FragmentFollowingBinding
import com.belajar.github_user_app.network.ApiConfig
import com.belajar.github_user_app.network.GithubResponse
import com.belajar.github_user_app.recycler.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {
    private val listFollowing = ArrayList<GithubResponse>()
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollowing.layoutManager = layoutManager
        binding.rvFollowing.addItemDecoration(itemDecoration)
        val sharedPref = requireActivity().getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val id = sharedPref.getString(userName, "null")

        getFollowing(id.toString())
    }

    private fun getFollowing(q: String){
        binding.pbFollowing.visibility = View.VISIBLE
        val client= ApiConfig.getApiService().getFollowing(q)
        client.enqueue(object : Callback<List<GithubResponse.User>> {
            override fun onResponse(call: Call<List<GithubResponse.User>>, response: Response<List<GithubResponse.User>>){
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        listFollowing.clear()
                        binding.pbFollowing.visibility = View.GONE
                        if (responseBody.isEmpty()) {
                            Toast.makeText(activity, "Following Kosong", Toast.LENGTH_LONG).show()
                        } else {
                            setFollowing(responseBody)
                        }
                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<GithubResponse.User>>, t: Throwable){
                binding.pbFollowing.visibility = View.GONE
                Toast.makeText(activity, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setFollowing(listUserFollowing: List<GithubResponse.User>) {
        val list = ArrayList <GithubResponse.User>()
        for (userID in listUserFollowing) {
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
        val userAdapter = UserAdapter(list)
        binding.rvFollowing.adapter = userAdapter
    }

    companion object {
        private val TAG = FollowingFragment::class.java.simpleName
        private const val userName = "user_name"
        private const val prefsName = "TEMP_ID"
    }
}