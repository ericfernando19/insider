package com.dicoding.malanginsider.data.csv

import android.content.Context
import com.dicoding.malanginsider.data.pref.Penginapan
import com.opencsv.CSVReader
import java.io.InputStreamReader

fun readPenginapanFromCsv(context: Context): List<Penginapan> {
    val inputStream = context.assets.open("penginapan.csv")
    val reader = CSVReader(InputStreamReader(inputStream))
    val data = reader.readAll()

    val penginapanList = mutableListOf<Penginapan>()

    if (data.isNotEmpty()) {
        for (i in 1 until data.size) {
            val row = data[i]
            if (row.size >= 13) {
                val penginapan = Penginapan(
                    placeId = row[0],
                    name = row[1],
                    address = row[2],
                    category = row[3],
                    city = row[4],
                    reviewsCount = row[5].toIntOrNull() ?: 0,
                    totalScore = row[6].toDoubleOrNull() ?: 0.0,
                    hotelStars = row[7],
                    postalCode = row[8],
                    imageUrl = row[9],
                    url = row[10],
                    latitude = row[11].toDoubleOrNull() ?: 0.0,
                    longitude = row[12].toDoubleOrNull() ?: 0.0
                )
                penginapanList.add(penginapan)
            }
        }
    }
    return penginapanList
}
