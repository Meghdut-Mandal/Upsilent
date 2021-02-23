package com.meghdut.upsilent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.R
import com.meghdut.upsilent.adapters.RecyclerViewAdapterMain
import com.meghdut.upsilent.network.ApiService
import com.meghdut.upsilent.network.MovieResponse
import com.meghdut.upsilent.network.URLConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Meghdut Mandal on 06/02/17.
 */
class MoviesFragment : Fragment() {
    private lateinit var allMovies: ArrayList<MovieResponse>
    private var recyclerViewAdapterMain: RecyclerViewAdapterMain? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_movies, container, false)
        val recyclerView: RecyclerView = v.findViewById(R.id.activityMainVerticalRecyclerView)
        allMovies = arrayListOf()
        recyclerViewAdapterMain = RecyclerViewAdapterMain(allMovies, requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = recyclerViewAdapterMain
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val retrofit = Retrofit.Builder()
                .baseUrl(URLConstants.MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiService::class.java)
        val call = service.getPopularMovies(URLConstants.API_KEY, 1)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieList = response.body()!!.movies
                val popularMovies = MovieResponse()
                popularMovies.movies = movieList
                allMovies.add(popularMovies)
                recyclerViewAdapterMain!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
        })
        val call1 = service.getNowPlayingMovies(URLConstants.API_KEY, 1)
        call1.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieList = response.body()!!.movies
                val nowPlayingMovies = MovieResponse()
                nowPlayingMovies.movies = movieList
                allMovies.add(nowPlayingMovies)
                recyclerViewAdapterMain!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
        })
        val call2 = service.getTopRatedMovies(URLConstants.API_KEY, 1)
        call2.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieList = response.body()!!.movies
                val topRatedMovies = MovieResponse()
                topRatedMovies.movies = movieList
                allMovies.add(topRatedMovies)
                recyclerViewAdapterMain!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
        })
        val call3 = service.getUpcomingMovies(URLConstants.API_KEY, 1)
        call3.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieList = response.body()!!.movies
                val upcomingMovies = MovieResponse()
                upcomingMovies.movies = movieList
                allMovies.add(upcomingMovies)
                recyclerViewAdapterMain!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
        })
    }
}