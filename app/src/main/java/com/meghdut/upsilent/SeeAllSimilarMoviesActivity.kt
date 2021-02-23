package com.meghdut.upsilent

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
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
import com.meghdut.upsilent.utils.IntentConstants
import com.meghdut.upsilent.utils.SpacesItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class SeeAllSimilarMoviesActivity : AppCompatActivity() {
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBarSeeAllActivity: ProgressBar
    private lateinit var recyclerViewAdpterSeeAllActivity: RecyclerViewAdapterSeeAllActivity
    private lateinit var movies: ArrayList<Movie>
    private var movie_id = 0
    private lateinit var movieName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_all_activity_movie)
        progressBarSeeAllActivity = findViewById(R.id.progressBarSeeAllActivity)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

//        val  slide =  Slide(Gravity.BOTTOM);
//        window.enterTransition = slide;
//        window.allowEnterTransitionOverlap = true;
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val intent = intent
        progressBarSeeAllActivity.visibility = View.VISIBLE
        //movies = (ArrayList<Movie>) intent.getSerializableExtra("ABCD");
        movie_id = intent.getIntExtra(IntentConstants.INTENT_KEY_MOVIE_ID, 0)
        movieName = intent.getStringExtra("MOVIE_NAME")!!
        title = "Similar to $movieName"
        movies = ArrayList()
        recyclerView = findViewById(R.id.seeAllActivityRecyclerViewMovies)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recyclerView.addItemDecoration(SpacesItemDecoration(spacingInPixels))
        recyclerViewAdpterSeeAllActivity = RecyclerViewAdapterSeeAllActivity(movies, this)
        val gridLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(this, 16), true))
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = recyclerViewAdpterSeeAllActivity
        loadmoreData(1)
        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadmoreData(page)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun loadmoreData(page: Int) {
        val retrofit = Retrofit.Builder()
                .baseUrl(URLConstants.MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiService::class.java)
        val call = service.getSimilarMovies(movie_id, URLConstants.API_KEY, page)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                progressBarSeeAllActivity.visibility = View.GONE
                val movieList = response.body()!!.movies
                for (obj in movieList) {
                    movies.add(obj)
                }
                recyclerViewAdpterSeeAllActivity.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                progressBarSeeAllActivity.visibility = View.GONE
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}