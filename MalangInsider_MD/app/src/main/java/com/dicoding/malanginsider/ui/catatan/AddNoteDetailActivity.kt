package com.dicoding.malanginsider.ui.catatan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.adapter.RekomendasiAdapter
import com.dicoding.malanginsider.data.csv.readPenginapanFromCsv
import com.dicoding.malanginsider.data.csv.readRestoranCSV
import com.dicoding.malanginsider.data.pref.Penginapan
import com.dicoding.malanginsider.data.pref.Restoran
import com.dicoding.malanginsider.data.pref.TempatWisata
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class AddNoteDetailActivity : AppCompatActivity() {

    private lateinit var tvWisataTerpilih: TextView
    private lateinit var imgWisata: ImageView
    private lateinit var tvAlamatWisata: TextView
    private lateinit var tvRatingWisata: TextView
    private lateinit var etJudulCatatan: EditText
    private lateinit var etCatatan: EditText
    private lateinit var rvRekomendasi: RecyclerView
    private lateinit var btnSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note_detail)

        tvWisataTerpilih = findViewById(R.id.tv_Wisata_Terpilih)
        imgWisata = findViewById(R.id.img_wisata)
        tvAlamatWisata = findViewById(R.id.tv_alamat_wisata)
        tvRatingWisata = findViewById(R.id.tv_rating_wisata)
        etJudulCatatan = findViewById(R.id.et_judul_catatan)
        etCatatan = findViewById(R.id.et_catatan)
        rvRekomendasi = findViewById(R.id.rv_rekomendasi)
        btnSimpan = findViewById(R.id.btn_simpan)

        val tempatWisata = intent.getParcelableExtra<TempatWisata>("TEMPAT_WISATA")
        if (tempatWisata != null) {
            tvWisataTerpilih.text = "Wisata: ${tempatWisata.nama}"
            tvAlamatWisata.text = "Alamat: ${tempatWisata.alamat}"
            tvRatingWisata.text = "Rating: ${tempatWisata.rating}"
            Glide.with(this)
                .load(tempatWisata.urlgambar)
                .placeholder(R.drawable.gambar)
                .error(R.drawable.gambar)
                .into(imgWisata)
            val rekomendasiList = getRekomendasiTerdekat(tempatWisata.latitude, tempatWisata.longitude)
            setupRekomendasiRecyclerView(rekomendasiList)
        } else {
            Toast.makeText(this, "Data wisata tidak tersedia", Toast.LENGTH_SHORT).show()
        }

        btnSimpan.setOnClickListener {
            val judulCatatan = etJudulCatatan.text.toString()
            val isiCatatan = etCatatan.text.toString()

            if (judulCatatan.isEmpty() || isiCatatan.isEmpty()) {
                Toast.makeText(this, "Judul dan Catatan tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Catatan berhasil disimpan!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun getRekomendasiTerdekat(latWisata: Double, lonWisata: Double): List<Any> {
        val restoranList = readRestoranCSV(this)
        val penginapanList = readPenginapanFromCsv(this)

        val rekomendasiList = mutableListOf<Any>()

        restoranList.forEach { restoran ->
            val distance = calculateDistance(latWisata, lonWisata, restoran.latitude, restoran.longitude)
            if (distance <= 2) { // 5 km sebagai filter
                restoran.distance = distance // Menetapkan jarak
                rekomendasiList.add(restoran)
            }
        }

        penginapanList.forEach { penginapan ->
            val distance = calculateDistance(latWisata, lonWisata, penginapan.latitude, penginapan.longitude)
            if (distance <= 2) {
                penginapan.distance = distance
                rekomendasiList.add(penginapan)
            }
        }


        return rekomendasiList
    }

    private fun setupRekomendasiRecyclerView(rekomendasiList: List<Any>) {
        val adapter = RekomendasiAdapter(rekomendasiList) { item ->
            val namaRekomendasi = when (item) {
                is Restoran -> item.title
                is Penginapan -> item.name
                else -> "Rekomendasi Tidak Diketahui"
            }
            Toast.makeText(this, "Dipilih: $namaRekomendasi", Toast.LENGTH_SHORT).show()
        }

        rvRekomendasi.layoutManager = LinearLayoutManager(this)
        rvRekomendasi.adapter = adapter
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        println("Hitung jarak: Lat1 $lat1, Lon1 $lon1, Lat2 $lat2, Lon2 $lon2")
        val R = 6371
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val distance = R * c
        println("Jarak dihitung: $distance km")
        return distance
    }

}
