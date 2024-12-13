package com.dicoding.malanginsider.data.csv

import android.content.Context
import android.util.Log
import com.dicoding.malanginsider.data.pref.Restoran
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import java.io.InputStreamReader

fun readRestoranCSV(context: Context): List<Restoran> {
    val restoranList = mutableListOf<Restoran>()
    val inputStream = context.assets.open("restoran.csv")

    val parser = CSVParserBuilder()
        .withSeparator(';')
        .build()

    val reader = CSVReaderBuilder(InputStreamReader(inputStream))
        .withCSVParser(parser)
        .build()

    val rows = reader.readAll().drop(1)

    for (row in rows) {
        if (row.size < 19) {
            Log.e("CSVReader", "Baris tidak lengkap: ${row.contentToString()}")
            continue
        }

        try {
            val openingHours = mapOf(
                "minggu" to row[11],
                "senin" to row[12],
                "selasa" to row[13],
                "rabu" to row[14],
                "kamis" to row[15],
                "jumat" to row[16],
                "sabtu" to row[17]
            )

            restoranList.add(
                Restoran(
                    title = row[0],
                    categories = row[1],
                    address = row[2],
                    kgmid = row[3],
                    latitude = row[4].replace(",", ".").toDoubleOrNull() ?: 0.0,
                    longitude = row[5].replace(",", ".").toDoubleOrNull() ?: 0.0,
                    neighborhood = row[6],
                    reviewsCount = row[7].replace(",", "").toIntOrNull() ?: 0,
                    totalScore = row[8].replace(",", ".").toFloatOrNull() ?: 0f,
                    url = row[9],
                    imageUrl = row[10],
                    openingHours = openingHours,
                    permanentlyClosed = row.getOrNull(18)?.toBoolean() ?: false,
                    description = row[19]
                )
            )
        } catch (e: Exception) {
            Log.e("CSVReader", "Kesalahan parsing baris: ${row.contentToString()}", e)
        }
    }
    reader.close()
    return restoranList
}
