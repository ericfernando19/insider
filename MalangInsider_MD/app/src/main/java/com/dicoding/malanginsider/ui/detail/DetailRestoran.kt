package com.dicoding.malanginsider.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.pref.Restoran

class DetailRestoran : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_restoran)

        val restoran = intent.getParcelableExtra<Restoran>("extra_restoran")

        val ivRestoranDetail: ImageView = findViewById(R.id.Detail_gamabar_restoran)
        val tvRestoranName: TextView = findViewById(R.id.detail_nama_restoran)
        val ratingBar: RatingBar = findViewById(R.id.detail_rating_restoran)
        val tvAlamat: TextView = findViewById(R.id.detal_alamat_restoran)
        val tvDeskripsi: TextView = findViewById(R.id.detail_deskripsi_restoran)
        val tvJamBuka: TextView = findViewById(R.id.jam_buka)

        val btnMap: ImageButton = findViewById(R.id.btnMap)
        val tvOpenInMap: TextView = findViewById(R.id.tvOpenInMap)
        val btnBack: ImageButton = findViewById(R.id.btnBack)

        restoran?.let {
            Glide.with(this)
                .load(it.imageUrl)
                .error(R.drawable.restoran)
                .into(ivRestoranDetail)

            tvRestoranName.text = it.title
            ratingBar.rating = it.totalScore.toFloat()
            tvAlamat.text = it.address
            tvDeskripsi.text = it.description

            val openingHoursText = it.openingHours.entries.joinToString("\n") { entry ->
                "${entry.key}: ${entry.value}"
            }
            tvJamBuka.text = openingHoursText
        }

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
            openGoogleMaps(restoran?.url)
        }

        btnMap.setOnClickListener(onClickListener)
        tvOpenInMap.setOnClickListener(onClickListener)
    }
}
