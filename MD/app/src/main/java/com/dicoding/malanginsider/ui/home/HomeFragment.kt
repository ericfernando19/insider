package com.dicoding.malanginsider.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.ui.penginapan.PenginapanFragment
import com.dicoding.malanginsider.ui.restoran.RestoranFragment
import com.dicoding.malanginsider.ui.wisata.WisataFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.viewPager)

        setupViewPagerAndTabs()

        return view
    }

    private fun setupViewPagerAndTabs() {
        val fragments = listOf(
            WisataFragment(),
            PenginapanFragment(),
            RestoranFragment()
        )

        val fragmentNames = listOf("Wisata", "Penginapan", "Restoran")

        viewPager.adapter = object : androidx.viewpager2.adapter.FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment = fragments[position]
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = fragmentNames[position]
        }.attach()
    }
}
