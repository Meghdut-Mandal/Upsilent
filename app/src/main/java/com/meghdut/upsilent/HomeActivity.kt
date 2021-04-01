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
import com.meghdut.upsilent.databinding.ActivityAboutMovieBinding
import com.meghdut.upsilent.databinding.ActivityAboutTvshowBinding
import com.meghdut.upsilent.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding


    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.exploreFragment -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.exploreFragment)
                }
                R.id.settingsFragment -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.settingsFragment)
                }
                R.id.driveFragment -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.driveFragment)
                }

                R.id.downloadFragment -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.downloadFragment)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        binding.bottomNavigationView.buttonClickListener = {
            isOpen = if (isOpen) {
                binding.translucentV.visibility = View.GONE
                binding.bottomNavigationView.buttonIcon = ResourcesCompat.getDrawable(resources, R.drawable.fab_search, theme)
                binding.searchMovieFabButton.startAnimation(fabClose)
                binding.searchTvShowFabButton.startAnimation(fabClose)
                binding.searchMovieTV.visibility = View.INVISIBLE
                binding.searchShowTV.visibility = View.INVISIBLE
                binding.searchMovieTV.startAnimation(fabClose)
                binding.searchMovieTV.startAnimation(fabClose)
                binding.searchMovieFabButton.isClickable = false
                binding.searchTvShowFabButton.isClickable = false
                false
            } else {
                binding.translucentV.visibility = View.VISIBLE
                binding.bottomNavigationView.buttonIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_close, theme)
                binding.searchMovieFabButton.startAnimation(fabOpen)
                binding.searchTvShowFabButton.startAnimation(fabOpen)
                binding.searchMovieTV.visibility = View.VISIBLE
                binding.searchMovieTV.visibility = View.VISIBLE
                binding.searchMovieTV.startAnimation(fabOpen)
                binding.searchMovieTV.startAnimation(fabOpen)
                binding.searchMovieFabButton.isClickable = true
                binding.searchTvShowFabButton.isClickable = true
                true
            }
        }
        binding.searchMovieFabButton.setOnClickListener { v: View? ->
            val intent = Intent()
            intent.setClass(this, SearchMovieActivity::class.java)
            startActivity(intent)
        }
        binding.searchTvShowFabButton.setOnClickListener { v: View? ->
            val intent = Intent()
            intent.setClass(this, SearchTVShowsActivity::class.java)
            startActivity(intent)
        }
    }
}