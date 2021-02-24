package com.meghdut.upsilent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.meghdut.upsilent.R
import com.meghdut.upsilent.adapters.MainFragmentPager


class ExploreFragment : Fragment() {
    private lateinit var containerVP: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title= getString(R.string.app_name)
        val mainTL = view.findViewById<TabLayout>(R.id.mainTL)
        mainTL.addTab(mainTL.newTab())
        mainTL.addTab(mainTL.newTab())
        val mainFragmentPager = MainFragmentPager(childFragmentManager)
        containerVP = view.findViewById(R.id.containerVP)
        containerVP.adapter = mainFragmentPager
        mainTL.setupWithViewPager(containerVP)
        mainTL.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                containerVP.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

}