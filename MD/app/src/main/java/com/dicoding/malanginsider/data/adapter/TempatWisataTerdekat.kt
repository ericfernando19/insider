package com.dicoding.malanginsider.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.pref.TempatWisata

class TempatWisataTerdekat(
    private val wisataList: List<TempatWisata>,
    private val context: Context,
    private val calculateDistance: (Double, Double, Double, Double) -> Double
) : RecyclerView.Adapter<TempatWisataTerdekat.WisataViewHolder>() {

    inner class WisataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNamaWisata)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvAlamatWisata)
        val tvJarak: TextView = itemView.findViewById(R.id.tvJarakWisata)
        val ivGambar: ImageView = itemView.findViewById(R.id.imageViewWisata)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_wisata_terdekat, parent, false)
        return WisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        val wisata = wisataList[position]
        holder.tvNama.text = wisata.nama
        holder.tvAlamat.text = wisata.alamat

        val distance = calculateDistance(
            wisata.latitude, wisata.longitude,
            wisata.latitude, wisata.longitude
        )

        holder.tvJarak.text = String.format("Jarak: %.2f km", distance)

        Glide.with(context).load(wisata.urlgambar).into(holder.ivGambar)

    }

    override fun getItemCount(): Int = wisataList.size
}
