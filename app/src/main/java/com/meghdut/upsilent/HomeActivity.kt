package com.meghdut.upsilent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import com.androidisland.views.ArcBottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun navController() = findNavController(R.id.navHostFragment)

    override fun onResume() {
        super.onResume()
        val navigationView = findViewById<ArcBottomNavigationView>(R.id.bottomNavigationView)
        setUpBottomNav(navigationView)
        navigationView.setOnNavigationItemSelectedListener {
            navController().navigate(it.itemId)
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun setUpBottomNav(navigationView: ArcBottomNavigationView) {
        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        val fabMovieSearch = findViewById<FloatingActionButton>(R.id.searchMovieFabButton)
        val fabTvShowSearch = findViewById<FloatingActionButton>(R.id.searchTvShowFabButton)
        val searchMovieTV = findViewById<TextView>(R.id.searchMovieTV)
        val searchShowTV = findViewById<TextView>(R.id.searchShowTV)
        val translucentV = findViewById<View>(R.id.translucentV)

        navigationView.buttonClickListener = {
            isOpen = if (isOpen) {
                translucentV.visibility = View.GONE
                navigationView.buttonIcon = ResourcesCompat.getDrawable(resources, R.drawable.fab_search, theme)
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
                navigationView.buttonIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_close, theme)
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
            intent.setClass(this, SearchMovieActivity::class.java)
            startActivity(intent)
        }
        fabTvShowSearch.setOnClickListener { v: View? ->
            val intent = Intent()
            intent.setClass(this, SearchTVShowsActivity::class.java)
            startActivity(intent)
        }
    }
}