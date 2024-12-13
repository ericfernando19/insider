package com.dicoding.malanginsider.ui.catatan

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.adapter.CatatanTempatWisataAdapter
import com.dicoding.malanginsider.data.csv.bacaDataTempatWisata
import com.dicoding.malanginsider.data.pref.TempatWisata

class AddNoteActivity : AppCompatActivity() {

    private lateinit var tempatWisataAdapter: CatatanTempatWisataAdapter
    private lateinit var wisataList: List<TempatWisata>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val rvWisata = findViewById<RecyclerView>(R.id.rv_wisata)
        val searchView = findViewById<SearchView>(R.id.et_search)
        val btnBack = findViewById<ImageButton>(R.id.btn_back)

        wisataList = bacaDataTempatWisata(this)

        tempatWisataAdapter = CatatanTempatWisataAdapter(wisataList) { tempatWisata ->
            navigateToAddNoteDetail(tempatWisata)
        }

        rvWisata.layoutManager = LinearLayoutManager(this)
        rvWisata.adapter = tempatWisataAdapter

        // SearchView Listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = if (!newText.isNullOrEmpty()) {
                    wisataList.filter { tempat ->
                        tempat.nama.contains(newText, ignoreCase = true)
                    }
                } else {
                    wisataList
                }
                tempatWisataAdapter.updateList(filteredList)
                return true
            }
        })

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun navigateToAddNoteDetail(tempatWisata: TempatWisata) {
        val intent = Intent(this, AddNoteDetailActivity::class.java).apply {
            putExtra("TEMPAT_WISATA", tempatWisata)
        }
        startActivity(intent)
    }
}
