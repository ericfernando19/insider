package com.dicoding.malanginsider.ui.restoran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.malanginsider.data.adapter.RestoranAdapter
import com.dicoding.malanginsider.data.csv.readRestoranCSV
import com.dicoding.malanginsider.data.pref.Restoran
import com.dicoding.malanginsider.databinding.FragmentRestoranBinding

class RestoranFragment : Fragment() {

    private var _binding: FragmentRestoranBinding? = null
    private val binding get() = _binding!!
    private lateinit var restoranAdapter: RestoranAdapter
    private var restoranList = listOf<Restoran>()
    private var filteredList = listOf<Restoran>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestoranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadData()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        restoranAdapter = RestoranAdapter(emptyList())
        binding.rvRestoran.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = restoranAdapter
        }
    }

    private fun loadData() {
        restoranList = readRestoranCSV(requireContext())
        filteredList = restoranList
        restoranAdapter.updateData(filteredList)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterData(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterData(newText)
                return true
            }
        })
    }

    private fun filterData(query: String?) {
        filteredList = if (query.isNullOrEmpty()) {
            restoranList
        } else {
            restoranList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
        restoranAdapter.updateData(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
