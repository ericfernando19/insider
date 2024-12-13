package com.dicoding.malanginsider.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.pref.Restoran
import com.dicoding.malanginsider.ui.detail.DetailRestoran

class RestoranAdapter(private var restoranList: List<Restoran>) :
    RecyclerView.Adapter<RestoranAdapter.RestoranViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestoranViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restoran, parent, false)
        return RestoranViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestoranViewHolder, position: Int) {
        val restoran = restoranList[position]
        holder.bind(restoran)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailRestoran::class.java)
            intent.putExtra("extra_restoran", restoran)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return restoranList.size
    }

    fun updateData(newRestoranList: List<Restoran>) {
        restoranList = newRestoranList
        notifyDataSetChanged()
    }

    inner class RestoranViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivRestoran: ImageView = itemView.findViewById(R.id.iv_restoran)
        private val tvRestoranName: TextView = itemView.findViewById(R.id.tv_restoran_name)
        private val tvRating: TextView = itemView.findViewById(R.id.tv_rating)

        fun bind(restoran: Restoran) {
            tvRestoranName.text = restoran.title
            tvRating.text = restoran.totalScore.toString()
            Glide.with(itemView.context)
                .load(restoran.imageUrl)
                .error(R.drawable.restoran)
                .into(ivRestoran)
        }
    }
}
