package com.dicoding.malanginsider.ui.penginapan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.malanginsider.data.adapter.PenginapanAdapter
import com.dicoding.malanginsider.data.csv.readPenginapanFromCsv
import com.dicoding.malanginsider.data.pref.Penginapan
import com.dicoding.malanginsider.databinding.FragmentPenginapanBinding
import com.dicoding.malanginsider.ui.detail.DetailPenginapan

class PenginapanFragment : Fragment() {

    private var _binding: FragmentPenginapanBinding? = null
    private val binding get() = _binding!!
    private lateinit var penginapanAdapter: PenginapanAdapter
    private lateinit var penginapanList: List<Penginapan>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPenginapanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadPenginapanData()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        penginapanAdapter = PenginapanAdapter(emptyList()) { penginapan ->
            val intent = Intent(requireContext(), DetailPenginapan::class.java)
            intent.putExtra("penginapan", penginapan)
            startActivity(intent)
        }

        binding.rvPenginapan.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = penginapanAdapter
        }
    }

    private fun loadPenginapanData() {
        penginapanList = readPenginapanFromCsv(requireContext())
        penginapanAdapter.updateList(penginapanList)
    }

    private fun setupSearchView() {
        binding.searchViewPenginapan.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filterPenginapan(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filterPenginapan(it) }
                return true
            }
        })
    }

    private fun filterPenginapan(query: String) {
        val filteredList = penginapanList.filter { penginapan ->
            penginapan.name.contains(query, ignoreCase = true)
        }
        penginapanAdapter.updateList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
