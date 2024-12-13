package com.dicoding.malanginsider.ui.catatan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.malanginsider.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.search.SearchBar

class Catatan : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton
    private lateinit var searchBar: SearchBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_catatan, container, false)

        recyclerView = view.findViewById(R.id.rv_tempat_catatan)
        fabAddNote = view.findViewById(R.id.fab_add_note)
        searchBar = view.findViewById(R.id.search_bar)

        setupRecyclerView()
        setupListeners()

        return view
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupListeners() {
        fabAddNote.setOnClickListener {
            navigateToAddNote()
        }

        searchBar.setOnClickListener {
        }
    }

    private fun navigateToAddNote() {
        val intent = Intent(requireContext(), AddNoteActivity::class.java)
        startActivity(intent)
    }

    private fun getDummyData(): List<String> {
        return listOf("Note 1", "Note 2", "Note 3", "Note 4")
    }
}