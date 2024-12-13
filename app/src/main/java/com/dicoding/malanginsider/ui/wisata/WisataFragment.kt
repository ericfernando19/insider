package com.dicoding.malanginsider.ui.wisata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.malanginsider.ModelRelevansiHelper
import com.dicoding.malanginsider.data.adapter.TempatWisataAdapter
import com.dicoding.malanginsider.data.csv.bacaDataTempatWisata
import com.dicoding.malanginsider.data.pref.TempatWisata
import com.dicoding.malanginsider.databinding.FragmentWisataBinding
import com.dicoding.malanginsider.ui.detail.DetailWisataAlam
import android.view.inputmethod.InputMethodManager

class WisataFragment : Fragment() {

    private lateinit var binding: FragmentWisataBinding
    private lateinit var adapter: TempatWisataAdapter
    private lateinit var listTempatWisata: List<TempatWisata>
    private lateinit var modelHelper: ModelRelevansiHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWisataBinding.inflate(inflater, container, false)

        modelHelper = ModelRelevansiHelper(requireContext())

        listTempatWisata = bacaDataTempatWisata(requireContext())

        adapter = TempatWisataAdapter(listTempatWisata) { tempatWisata ->
            val intent = Intent(requireContext(), DetailWisataAlam::class.java)
            intent.putExtra("EXTRA_TEMPAT_WISATA", tempatWisata)
            startActivity(intent)
        }
        binding.rvTempatPopuler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvTempatPopuler.adapter = adapter

        val kategoriList = listOf(
            "Semua Kategori", "Wisata Rekreasi", "Taman Kota", "Wisata Budaya",
            "Wisata Alam", "Wisata Religi (Tempat Ibadah)", "Museum", "Wisata Edukasi", "Wisata Sejarah"
        )
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, kategoriList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategori.adapter = spinnerAdapter

        binding.spinnerKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val kategoriYangDiinginkan = kategoriList[position]
                Log.d("SelectedCategory", "Kategori yang dipilih: $kategoriYangDiinginkan")
                filterData(kategoriYangDiinginkan, binding.searchViewWisata.query.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                adapter.updateData(listTempatWisata)
            }
        }

        binding.searchViewWisata.requestFocus()

        binding.searchViewWisata.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchQuery", "Query Submitted: $query")
                hideKeyboard()
                filterData(binding.spinnerKategori.selectedItem.toString(), query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchQuery", "Query Changed: $newText")
                filterData(binding.spinnerKategori.selectedItem.toString(), newText)
                return true
            }
        })

        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                hideKeyboard()
                return@setOnKeyListener true
            }
            false
        }

        return binding.root
    }

    private fun filterData(kategori: String, query: String?) {
        val filteredByCategory = if (kategori == "Semua Kategori") {
            listTempatWisata
        } else {
            listTempatWisata.filter { it.kategori == kategori }
        }

        val filteredByQuery = if (query.isNullOrEmpty()) {
            filteredByCategory
        } else {
            filteredByCategory.filter { it.nama.contains(query, ignoreCase = true) }
        }

        adapter.updateData(filteredByQuery)
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun handleBackPressed(): Boolean {
        hideKeyboard()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        modelHelper.close()
    }
}
