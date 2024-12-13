package com.dicoding.malanginsider.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.FavoritePlace

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val favoriteList = mutableListOf<FavoritePlace>()

    fun submitList(list: List<FavoritePlace>) {
        favoriteList.clear()
        favoriteList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(place: FavoritePlace) {
        }
    }
}
