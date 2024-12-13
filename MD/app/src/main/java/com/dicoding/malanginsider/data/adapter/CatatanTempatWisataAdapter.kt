package com.dicoding.malanginsider.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.pref.TempatWisata

class CatatanTempatWisataAdapter(
    private var wisataList: List<TempatWisata>,
    private val onItemClick: (TempatWisata) -> Unit
) : RecyclerView.Adapter<CatatanTempatWisataAdapter.CatatanTempatWisataViewHolder>() {

    class CatatanTempatWisataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tv_nama_wisata)
        val tvAlamat: TextView = itemView.findViewById(R.id.tv_alamat_wisata)
        val imgWisata: ImageView = itemView.findViewById(R.id.img_wisata)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanTempatWisataViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tempat_wisata_catatan, parent, false)
        return CatatanTempatWisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatatanTempatWisataViewHolder, position: Int) {
        val tempatWisata = wisataList[position]
        holder.tvNama.text = tempatWisata.nama
        holder.tvAlamat.text = tempatWisata.alamat
        Glide.with(holder.itemView.context)
            .load(tempatWisata.urlgambar)
            .error(R.drawable.gambar)
            .into(holder.imgWisata)
        holder.itemView.setOnClickListener { onItemClick(tempatWisata) }
    }

    fun updateList(newList: List<TempatWisata>) {
        wisataList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = wisataList.size


}