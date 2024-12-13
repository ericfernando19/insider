package com.dicoding.malanginsider.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.adapter.TempatWisataTerdekat
import com.dicoding.malanginsider.data.csv.bacaDataTempatWisata
import com.dicoding.malanginsider.data.pref.TempatWisata
import com.dicoding.malanginsider.databinding.DetailWisataAlamBinding

class DetailWisataAlam : AppCompatActivity() {

    private lateinit var binding: DetailWisataAlamBinding
    private lateinit var tempatWisataList: List<TempatWisata>
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailWisataAlamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tempatWisata = intent.getParcelableExtra<TempatWisata>("EXTRA_TEMPAT_WISATA")
        tempatWisata?.let {
            populateDetail(it)
            setupListeners(it)
            tempatWisataList = bacaDataTempatWisata(applicationContext)
            findClosestWisata(it)
        } ?: finish()

        binding.btnFavorite.setOnClickListener {
            toggleFavorite()
        }
    }

    private fun populateDetail(tempatWisata: TempatWisata) {
        binding.detailNamaAlam.text = tempatWisata.nama
        binding.detailDeskripsiAlam.text = tempatWisata.deskripsi
        binding.detalAlamatAlam.text = tempatWisata.alamat
        binding.detailRatingAlam.rating = tempatWisata.rating.toFloat()

        val jamBukaText = tempatWisata.jamBuka
        binding.jamBukaSenin.text = "Senin: ${jamBukaText[0].jam}"
        binding.jamBukaSelasa.text = "Selasa: ${jamBukaText[1].jam}"
        binding.jamBukaRabu.text = "Rabu: ${jamBukaText[2].jam}"
        binding.jamBukaKamis.text = "Kamis: ${jamBukaText[3].jam}"
        binding.jamBukaJumat.text = "Jumat: ${jamBukaText[4].jam}"
        binding.jamBukaSabtu.text = "Sabtu: ${jamBukaText[5].jam}"
        binding.jamBukaMinggu.text = "Minggu: ${jamBukaText[6].jam}"

        Glide.with(this)
            .load(tempatWisata.urlgambar)
            .into(binding.DetailGamabarAlam)
    }

    private fun setupListeners(tempatWisata: TempatWisata) {
        binding.btnBack.setOnClickListener { finish() }

        val openMapAction = {
            openMap(tempatWisata.url)
        }
        binding.btnMap.setOnClickListener { openMapAction() }
        binding.tvOpenInMap.setOnClickListener { openMapAction() }
        binding.btnContactNow.setOnClickListener {
            showContactOptions(tempatWisata.telepon)
        }
    }

    private fun openMap(url: String) {
        try {
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            val chooser = Intent.createChooser(mapIntent, "Pilih aplikasi untuk membuka peta")
            startActivity(chooser)
        } catch (e: Exception) {
            toast("Tidak ada aplikasi yang dapat membuka Google Maps.")
        }
    }

    private fun showContactOptions(phoneNumber: String) {
        val options = arrayOf("Telepon", "WhatsApp")

        android.app.AlertDialog.Builder(this)
            .setTitle("Pilih opsi kontak")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> contactNow(phoneNumber) // Opsi Telepon
                    1 -> openWhatsApp(phoneNumber) // Opsi WhatsApp
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun openWhatsApp(phoneNumber: String) {
        try {
            if (phoneNumber.isNotBlank()) {
                val formattedNumber = phoneNumber.replace(" ", "").replace("-", "")
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://wa.me/$formattedNumber")
                }
                startActivity(intent)
            } else {
                toast("Nomor telepon tidak tersedia.")
            }
        } catch (e: Exception) {
            toast("Gagal membuka WhatsApp.")
        }
    }

    private fun contactNow(phoneNumber: String) {
        try {
            if (phoneNumber.isNotBlank()) {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }
                startActivity(intent)
            } else {
                toast("Nomor telepon tidak tersedia.")
            }
        } catch (e: Exception) {
            toast("Gagal membuka aplikasi telepon.")
        }
    }

    private fun findClosestWisata(selectedWisata: TempatWisata) {
        val sortedWisata = tempatWisataList.sortedBy {
            calculateDistance(selectedWisata.latitude, selectedWisata.longitude, it.latitude, it.longitude)
        }

        val closestWisataList = sortedWisata.take(3)

        setupRecyclerView(selectedWisata, closestWisataList)
    }

    private fun setupRecyclerView(selectedWisata: TempatWisata, closestWisataList: List<TempatWisata>) {
        val adapter = TempatWisataTerdekat(
            wisataList = closestWisataList,
            context = this,
            calculateDistance = { lat2, lon2, lat1, lon1 ->
                calculateDistance(selectedWisata.latitude, selectedWisata.longitude, lat1, lon1)
            }
        )
        binding.recyclerViewWisataTerdekat.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewWisataTerdekat.adapter = adapter
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val radius = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return radius * c
    }

    private fun toggleFavorite() {
        isFavorite = !isFavorite
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
            toast("Berhasil ditambah ke favorit")
        } else {
            binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            toast("Dihapus dari favorit")
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
