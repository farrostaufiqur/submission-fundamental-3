package com.belajar.github_user_app.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.belajar.github_user_app.databinding.ItemRowUserBinding
import com.belajar.github_user_app.network.GithubResponse
import com.belajar.github_user_app.ui.profile.ProfileActivity
import com.bumptech.glide.Glide

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private lateinit var sharedPref: SharedPreferences
    private val listFavorite = ArrayList<GithubResponse.User>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFavorite(listFavorite: MutableList<GithubResponse.User>) {
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        sharedPref = holder.itemView.context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }
    inner class FavoriteViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubResponse.User) {
            with(binding) {
                tvUsername.text = user.login
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(binding.tvAvatar)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ProfileActivity::class.java)
                    sharedUser(user.login)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
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