package com.meghdut.upsilent

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.adapters.RecyclerAdapterSearchTvShows
import com.meghdut.upsilent.models.TVShow
import com.meghdut.upsilent.network.ApiService
import com.meghdut.upsilent.network.TVShowResponse
import com.meghdut.upsilent.network.URLConstants
import com.meghdut.upsilent.utils.AppUtil.dpToPx
import com.meghdut.upsilent.utils.EndlessRecyclerViewScrollListener
import com.meghdut.upsilent.utils.GridSpacingItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class SearchTVShowsActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var searchBack: ImageButton
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private lateinit var progress: ProgressBar
    private lateinit var recyclerAdapterSearchTvShows: RecyclerAdapterSearchTvShows
    private lateinit var searchResultsRV: RecyclerView
    private lateinit var data: ArrayList<TVShow>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = findViewById(R.id.search_view)
        searchView.queryHint = "Search TVShows"
        searchView.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        searchView.imeOptions = searchView.imeOptions or EditorInfo.IME_ACTION_SEARCH or
                EditorInfo.IME_FLAG_NO_EXTRACT_UI or EditorInfo.IME_FLAG_NO_FULLSCREEN
        progress = findViewById(R.id.progressBar)
        searchResultsRV = findViewById(R.id.searchResultsRV)
        searchBack = findViewById(R.id.searchback)
        data = ArrayList()
        val gridLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        searchResultsRV.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(this, 16), true))
        searchResultsRV.layoutManager = gridLayoutManager
        recyclerAdapterSearchTvShows = RecyclerAdapterSearchTvShows(data, this)
        searchResultsRV.adapter = recyclerAdapterSearchTvShows
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchFor(query, 1)
                scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        searchFor(query, page)
                    }
                }
                searchResultsRV.addOnScrollListener(scrollListener)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    searchResultsRV.visibility = View.GONE
                    progress.visibility = View.GONE
                    data.clear()
                }
                return true
            }
        })
    }

    private fun searchFor(query: String?, page: Int) {
        if (page == 1) progress.visibility = View.VISIBLE
        searchResultsRV.visibility = View.VISIBLE
        searchView.clearFocus()
        val retrofit = Retrofit.Builder()
                .baseUrl(URLConstants.SEARCH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiService::class.java)
        val call = service.getSearchedShows(URLConstants.API_KEY, query, page)
        call.enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                progress.visibility = View.INVISIBLE
                val searchShowsList = response.body()!!.tvShows
                for (obj in searchShowsList) {
                    data.add(obj)
                }
                recyclerAdapterSearchTvShows.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {}
        })
        searchBack.setOnClickListener { v: View? -> onBackPressed() }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (!TextUtils.isEmpty(query)) {
                searchView.setQuery(query, false)
                searchFor(query, 1)
            }
        }
    }
}