package com.meghdut.upsilent

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.adapters.RecyclerViewAdapterSeeAllActivity
import com.meghdut.upsilent.models.Movie
import com.meghdut.upsilent.network.ApiService
import com.meghdut.upsilent.network.MovieResponse
import com.meghdut.upsilent.network.URLConstants
import com.meghdut.upsilent.utils.AppUtil.dpToPx
import com.meghdut.upsilent.utils.EndlessRecyclerViewScrollListener
import com.meghdut.upsilent.utils.GridSpacingItemDecoration
import com.meghdut.upsilent.utils.SpacesItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class SeeAllMoviesActivity : AppCompatActivity() {
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerViewAdapterSeeAllActivity
    lateinit var movies: ArrayList<Movie>
    private lateinit var movieType: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_all_activity_movie)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val slide = Slide(Gravity.BOTTOM)
        window.enterTransition = slide
        window.allowEnterTransitionOverlap = true
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val intent = intent
        movies = intent.getSerializableExtra("ABCD") as ArrayList<Movie>
        movieType = intent.getStringExtra("MOVIETYPE")!!
        title = movieType
        recyclerView = findViewById(R.id.seeAllActivityRecyclerViewMovies)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recyclerView.addItemDecoration(SpacesItemDecoration(spacingInPixels))
        adapter = RecyclerViewAdapterSeeAllActivity(movies, this)
        val gridLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(this, 16), true))
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadMoreData(page)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun loadMoreData(page: Int) {
        val retrofit = Retrofit.Builder()
                .baseUrl(URLConstants.MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiService::class.java)
        if (movieType == "Popular Movies") {
            val call = service.getPopularMovies(URLConstants.API_KEY, page)
            call.enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    //Log.i("ABC2", "FUN");
                    val movieList = response.body()!!.movies
                    for (obj in movieList) {
                        movies.add(obj)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
            })
        } else if (movieType == "Now Playing") {
            val call = service.getNowPlayingMovies(URLConstants.API_KEY, page)
            call.enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    val movieList = response.body()!!.movies
                    for (obj in movieList) {
                        movies.add(obj)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
            })
        } else if (movieType == "Top Rated Movies") {
            val call = service.getTopRatedMovies(URLConstants.API_KEY, page)
            call.enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    //Log.i("ABC2", "FUN");
                    val movieList = response.body()!!.movies
                    for (obj in movieList) {
                        movies.add(obj)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
            })
        } else if (movieType == "Upcoming Movies") {
            val call = service.getUpcomingMovies(URLConstants.API_KEY, page)
            call.enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    val movieList = response.body()!!.movies
                    for (obj in movieList) {
                        movies.add(obj)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}