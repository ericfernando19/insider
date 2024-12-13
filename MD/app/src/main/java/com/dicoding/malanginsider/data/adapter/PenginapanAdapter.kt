package com.dicoding.malanginsider.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.pref.Penginapan

class PenginapanAdapter(
    private var penginapanList: List<Penginapan>,
    private val onItemClick: (Penginapan) -> Unit
) : RecyclerView.Adapter<PenginapanAdapter.PenginapanViewHolder>() {

    inner class PenginapanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPenginapan: ImageView = itemView.findViewById(R.id.iv_penginapan)
        val tvName: TextView = itemView.findViewById(R.id.tv_penginapan_name)
        val tvRating: TextView = itemView.findViewById(R.id.tv_rating)

        init {
            itemView.setOnClickListener {
                val penginapan = penginapanList[adapterPosition]
                onItemClick(penginapan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenginapanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_penginapan, parent, false)
        return PenginapanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PenginapanViewHolder, position: Int) {
        val penginapan = penginapanList[position]

        holder.tvName.text = penginapan.name
        holder.tvRating.text = penginapan.totalScore.toString()

        // Load image from URL (gunakan Glide untuk memuat gambar)
        Glide.with(holder.itemView.context)
            .load(penginapan.imageUrl)
            .error(R.drawable.penginapan) // Gambar default jika gagal
            .into(holder.ivPenginapan)
    }

    override fun getItemCount() = penginapanList.size

    // Fungsi untuk memperbarui data
    fun updateList(newList: List<Penginapan>) {
        penginapanList = newList
        notifyDataSetChanged()
    }
}
