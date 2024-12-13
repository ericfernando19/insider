package com.dicoding.malanginsider.data.csv


import android.content.Context
import android.util.Log
import com.dicoding.malanginsider.data.pref.JamBuka
import com.dicoding.malanginsider.data.pref.TempatWisata
import java.io.BufferedReader
import java.io.InputStreamReader

fun bacaDataTempatWisata(context: Context): List<TempatWisata> {
    val tempatWisataList = mutableListOf<TempatWisata>()
    val inputStream = context.assets.open("wisata.csv")
    val reader = BufferedReader(InputStreamReader(inputStream))

    reader.useLines { lines ->
        lines.drop(1).forEachIndexed { index, line ->
            try {
                val parts = line.split(";")
                if (parts.size >= 16) {
                    val jamBuka = mutableListOf<JamBuka>()
                    for (i in 14 until parts.size step 2) {
                        if (i + 1 < parts.size) {
                            jamBuka.add(JamBuka(parts[i], parts[i + 1]))
                        }
                    }

                    val tempatWisata = TempatWisata(
                        nama = parts[1],
                        rating = parts[2].replace(",", ".").toDouble(),
                        ulasan = parts[3].toInt(),
                        kategori = parts[4],
                        deskripsi = parts[5],
                        kota = parts[6],
                        alamat = parts[7],
                        kodePos = parts[8],
                        urlgambar = parts[9],
                        url = parts[10],
                        latitude = parts[11].replace(",", ".").toDouble(),
                        longitude = parts[12].replace(",", ".").toDouble(),
                        telepon = parts[13],
                        jamBuka = jamBuka
                    )
                    tempatWisataList.add(tempatWisata)
                    Log.d("CSV Parsing", "Data added: ${tempatWisata.nama}")
                } else {
                    Log.e("CSV Parsing", "Invalid row at line ${index + 2}: $line")
                }
            } catch (e: Exception) {
                Log.e("CSV Parsing", "Error parsing row at line ${index + 2}: $line", e)
            }
        }
    }
    Log.d("CSV Parsing", "Total rows parsed: ${tempatWisataList.size}")
    return tempatWisataList
}


fun parseJamBuka(jamBukaString: String): List<JamBuka> {
    val jamBukaList = mutableListOf<JamBuka>()
    val jamBukaArray = jamBukaString.split(";")
    for (jamBuka in jamBukaArray) {
        val hariJam = jamBuka.split(":")
        if (hariJam.size == 2) {
            jamBukaList.add(JamBuka(hari = hariJam[0], jam = hariJam[1]))
        }
    }
    return jamBukaList
}
