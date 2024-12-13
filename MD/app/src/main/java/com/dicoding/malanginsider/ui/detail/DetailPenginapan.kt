package com.dicoding.malanginsider.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.pref.Penginapan

class DetailPenginapan : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_penginapan)

        val penginapan = intent.getParcelableExtra<Penginapan>("penginapan")

        val ivGambarPenginapan: ImageView = findViewById(R.id.Detail_gamabar_penginapan)
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        val tvNamaPenginapan: TextView = findViewById(R.id.detail_nama_penginapan)
        val tvAlamatPenginapan: TextView = findViewById(R.id.detal_alamat_penginapan)
        val ratingBar: RatingBar = findViewById(R.id.detail_rating_penginapan)
        val tvDeskripsi: TextView = findViewById(R.id.detail_deskripsi_alam)
        val btnContactNow: Button = findViewById(R.id.btnContactNow)
        val btnMap: ImageButton = findViewById(R.id.btnMap)
        val tvOpenInMap: TextView = findViewById(R.id.tvOpenInMap)

        penginapan?.let {
            Glide.with(this)
                .load(it.imageUrl)
                .error(R.drawable.penginapan)
                .into(ivGambarPenginapan)
        }
        tvNamaPenginapan.text = penginapan?.name ?: "Nama Penginapan"
        tvAlamatPenginapan.text = penginapan?.address ?: "Alamat Penginapan"
        ratingBar.rating = (penginapan?.totalScore ?: 0f).toFloat()
        tvDeskripsi.text = penginapan?.category ?: "Deskripsi Penginapan"

        btnBack.setOnClickListener {
            onBackPressed()
        }

        fun openGoogleMaps(url: String?) {
            url?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                intent.setPackage("com.google.android.apps.maps")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    startActivity(browserIntent)
                }
            }
        }

        val onClickListener = View.OnClickListener {
            openGoogleMaps(penginapan?.url)
        }

        btnMap.setOnClickListener(onClickListener)
        tvOpenInMap.setOnClickListener(onClickListener)

        btnContactNow.setOnClickListener {
        }
    }
}
