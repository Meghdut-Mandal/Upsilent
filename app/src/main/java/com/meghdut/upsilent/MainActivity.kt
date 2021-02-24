package com.meghdut.upsilent

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.meghdut.upsilent.adapters.MainFragmentPager

class MainActivity : AppCompatActivity() {
    private lateinit var containerVP: ViewPager
    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var translucentV: View
    private lateinit var searchFB: FloatingActionButton
    private lateinit var fabMovieSearch: FloatingActionButton
    private lateinit var fabTvShowSearch: FloatingActionButton
    private lateinit var searchMovieTV: TextView
    private lateinit var searchShowTV: TextView
    private var isOpen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.app_name)
        translucentV = findViewById(R.id.translucentV)
        fabMovieSearch = findViewById(R.id.searchMovieFabButton)
        fabTvShowSearch = findViewById(R.id.searchTvShowFabButton)
        searchMovieTV = findViewById(R.id.searchMovieTV)
        searchShowTV = findViewById(R.id.searchShowTV)
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        val mainTL = findViewById<TabLayout>(R.id.mainTL)
        mainTL.addTab(mainTL.newTab())
        mainTL.addTab(mainTL.newTab())
        val mainFragmentPager = MainFragmentPager(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        containerVP = findViewById(R.id.containerVP)
        containerVP.adapter = mainFragmentPager
        mainTL.setupWithViewPager(containerVP)
        mainTL.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                containerVP.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        searchFB.setOnClickListener { v: View? ->
            isOpen = if (isOpen) {
                translucentV.visibility = View.GONE
                searchFB.setImageResource(R.drawable.fab_search)
                fabMovieSearch.startAnimation(fabClose)
                fabTvShowSearch.startAnimation(fabClose)
                searchMovieTV.visibility = View.INVISIBLE
                searchShowTV.visibility = View.INVISIBLE
                searchMovieTV.startAnimation(fabClose)
                searchShowTV.startAnimation(fabClose)
                fabMovieSearch.isClickable = false
                fabTvShowSearch.isClickable = false
                false
            } else {
                translucentV.visibility = View.VISIBLE
                searchFB.setImageResource(R.drawable.ic_close)
                fabMovieSearch.startAnimation(fabOpen)
                fabTvShowSearch.startAnimation(fabOpen)
                searchMovieTV.visibility = View.VISIBLE
                searchShowTV.visibility = View.VISIBLE
                searchMovieTV.startAnimation(fabOpen)
                searchShowTV.startAnimation(fabOpen)
                fabMovieSearch.isClickable = true
                fabTvShowSearch.isClickable = true
                true
            }
        }
        fabMovieSearch.setOnClickListener { v: View? ->
            val intent = Intent()
            intent.setClass(this@MainActivity, SearchMovieActivity::class.java)
            startActivity(intent)
        }
        fabTvShowSearch.setOnClickListener { v: View? ->
            val intent = Intent()
            intent.setClass(this@MainActivity, SearchTVShowsActivity::class.java)
            startActivity(intent)
        }
    }
}