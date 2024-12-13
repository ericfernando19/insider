package com.dicoding.malanginsider.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.pref.RecommendationsItem

class RecommendationsAdapter(private val listWisata: List<RecommendationsItem>) :
    RecyclerView.Adapter<RecommendationsAdapter.WisataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recommendation, parent, false)
        return WisataViewHolder(view)
    }


    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        val wisata = listWisata[position]
        holder.bind(wisata)
    }


    override fun getItemCount(): Int = listWisata.size


    inner class WisataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.tvName)
        private val ratingText: TextView = itemView.findViewById(R.id.tvRating)
        private val descriptionText: TextView = itemView.findViewById(R.id.tvDescription)


        fun bind(wisata: RecommendationsItem) {
            nameText.text = wisata.name
            ratingText.text = "Rating: ${wisata.rating}"
            descriptionText.text = wisata.description
        }
    }
}
