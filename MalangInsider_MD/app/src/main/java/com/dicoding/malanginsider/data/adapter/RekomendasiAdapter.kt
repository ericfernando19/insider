package com.dicoding.malanginsider.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.pref.Penginapan
import com.dicoding.malanginsider.data.pref.Restoran

class RekomendasiAdapter(private val rekomendasiList: List<Any>, private val onItemClick: (Any) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rekomendasi, parent, false)
        return if (viewType == 0) RestoranViewHolder(view) else PenginapanViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = rekomendasiList[position]
        if (item is Restoran) {
            (holder as RestoranViewHolder).bind(item)
        } else if (item is Penginapan) {
            (holder as PenginapanViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = rekomendasiList.size

    override fun getItemViewType(position: Int): Int {
        return if (rekomendasiList[position] is Restoran) 0 else 1
    }

    inner class RestoranViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restoran: Restoran) {
            Glide.with(itemView.context)
                .load(restoran.imageUrl)
                .into(itemView.findViewById(R.id.imgRekomendasi))
            itemView.findViewById<TextView>(R.id.tvNamaRekomendasi).text = restoran.title
            itemView.findViewById<TextView>(R.id.tvAlamatRekomendasi).text = restoran.address
            itemView.findViewById<TextView>(R.id.tvCategori).text = restoran.categories
            itemView.findViewById<TextView>(R.id.tvJarakRekomendasi).text = "Jarak: ${String.format("%.2f", restoran.distance)} km"
            itemView.setOnClickListener { onItemClick(restoran) }
        }
    }

    inner class PenginapanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(penginapan: Penginapan) {
            Glide.with(itemView.context)
                .load(penginapan.imageUrl)
                .into(itemView.findViewById(R.id.imgRekomendasi))
            itemView.findViewById<TextView>(R.id.tvNamaRekomendasi).text = penginapan.name
            itemView.findViewById<TextView>(R.id.tvAlamatRekomendasi).text = penginapan.address
            itemView.findViewById<TextView>(R.id.tvCategori).text = penginapan.category
            itemView.findViewById<TextView>(R.id.tvJarakRekomendasi).text = "Jarak: ${String.format("%.2f", penginapan.distance)} km"
            itemView.setOnClickListener { onItemClick(penginapan) }
        }
    }

}
