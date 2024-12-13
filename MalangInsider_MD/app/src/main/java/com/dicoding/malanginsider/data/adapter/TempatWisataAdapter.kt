package com.dicoding.malanginsider.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.pref.TempatWisata
import com.dicoding.malanginsider.databinding.ItemTempatPopulerBinding

class TempatWisataAdapter(
    private var tempatWisataList: List<TempatWisata>,
    private val onItemClick: (TempatWisata) -> Unit
) : RecyclerView.Adapter<TempatWisataAdapter.TempatWisataViewHolder>() {

    inner class TempatWisataViewHolder(private val binding: ItemTempatPopulerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tempatWisata: TempatWisata) {
            binding.tvPopulerName.text = tempatWisata.nama
            binding.tvRating.text = tempatWisata.rating.toString()

            Glide.with(binding.root.context)
                .load(tempatWisata.urlgambar)
                .error(R.drawable.gambar)
                .into(binding.ivPopuler)

            binding.root.setOnClickListener {
                onItemClick(tempatWisata)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempatWisataViewHolder {
        val binding = ItemTempatPopulerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TempatWisataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TempatWisataViewHolder, position: Int) {
        holder.bind(tempatWisataList[position])
    }

    override fun getItemCount(): Int = tempatWisataList.size

    fun updateData(newTempatWisataList: List<TempatWisata>) {
        tempatWisataList = newTempatWisataList
        notifyDataSetChanged()
    }

}
