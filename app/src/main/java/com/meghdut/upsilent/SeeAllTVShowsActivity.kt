package com.meghdut.upsilent

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.adapters.RecyclerAdapterSeeAllTvshows
import com.meghdut.upsilent.models.TVShow
import com.meghdut.upsilent.network.ApiService
import com.meghdut.upsilent.network.TVShowResponse
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

class SeeAllTVShowsActivity : AppCompatActivity() {
    var recyclerAdapterSeeAllTvshows: RecyclerAdapterSeeAllTvshows? = null
    var tvShows: ArrayList<TVShow>? = null
    private var tvShowType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_all_activity_tvshows)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val slide = Slide(Gravity.BOTTOM)
        window.enterTransition = slide
        window.allowEnterTransitionOverlap = true
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val intent = intent
        tvShows = intent.getSerializableExtra("ABCD") as ArrayList<TVShow>?
        tvShowType = intent.getStringExtra("TVSHOW_TYPE")
        title = tvShowType
        val recyclerView = findViewById<RecyclerView>(R.id.seeAllActivityRecyclerViewTVShows)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recyclerView.addItemDecoration(SpacesItemDecoration(spacingInPixels))
        recyclerAdapterSeeAllTvshows = RecyclerAdapterSeeAllTvshows(tvShows, this)
        val gridLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(this, 16), true))
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = recyclerAdapterSeeAllTvshows
        val scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadmoreData(page)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun loadmoreData(page: Int) {
        val retrofit = Retrofit.Builder()
                .baseUrl(URLConstants.TVSHOW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiService::class.java)
        if (tvShowType == "Airing Today") {
            val call = service.getAiringToday(URLConstants.API_KEY, page)
            call.enqueue(object : Callback<TVShowResponse> {
                override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                    //Log.i("ABC2", "FUN");
                    val tvShowList = response.body()!!.tvShows
                    for (obj in tvShowList) {
                        tvShows!!.add(obj)
                    }
                    recyclerAdapterSeeAllTvshows!!.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {}
            })
        } else if (tvShowType == "On Air") {
            val call = service.getOnAir(URLConstants.API_KEY, page)
            call.enqueue(object : Callback<TVShowResponse> {
                override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                    val tvShowList = response.body()!!.tvShows
                    for (obj in tvShowList) {
                        tvShows!!.add(obj)
                    }
                    recyclerAdapterSeeAllTvshows!!.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {}
            })
        } else if (tvShowType == "Popular Shows") {
            val call = service.getPopular(URLConstants.API_KEY, page)
            call.enqueue(object : Callback<TVShowResponse> {
                override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                    //Log.i("ABC2", "FUN");
                    val tvShowList = response.body()!!.tvShows
                    for (obj in tvShowList) {
                        tvShows!!.add(obj)
                    }
                    recyclerAdapterSeeAllTvshows!!.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {}
            })
        } else if (tvShowType == "Top Rated Shows") {
            val call = service.getTopRated(URLConstants.API_KEY, page)
            call.enqueue(object : Callback<TVShowResponse> {
                override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                    //Log.i("ABC2", "FUN");
                    val tvShowList = response.body()!!.tvShows
                    for (obj in tvShowList) {
                        tvShows!!.add(obj)
                    }
                    recyclerAdapterSeeAllTvshows!!.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {}
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}