package com.belajar.github_user_app.recycler

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.belajar.github_user_app.R
import com.belajar.github_user_app.databinding.ItemRowUserBinding
import com.belajar.github_user_app.network.GithubResponse
import com.belajar.github_user_app.ui.profile.ProfileActivity
import com.bumptech.glide.Glide

class UserAdapter(private val listUser: MutableList <GithubResponse.User>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var sharedPref: SharedPreferences

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRowUserBinding.bind(itemView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder) {
            val (login, avatarUrl) = listUser[position]
            sharedPref = holder.itemView.context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            binding.tvUsername.text = login
            Glide.with(holder.itemView.context)
                .load(avatarUrl)
                .circleCrop()
                .into(holder.binding.tvAvatar)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ProfileActivity::class.java)
                sharedUser(login)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = listUser.size

    private fun sharedUser(id: String) {
        val shared: SharedPreferences.Editor = sharedPref.edit()
        shared.putString(userName, id)
        shared.apply()
    }

    companion object {
        private const val prefsName = "TEMP_ID"
        private const val userName = "user_name"
    }
}