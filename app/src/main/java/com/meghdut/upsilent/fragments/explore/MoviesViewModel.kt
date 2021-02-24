package com.meghdut.upsilent.fragments.explore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.meghdut.upsilent.network.ApiService
import com.meghdut.upsilent.network.MovieResponse
import com.meghdut.upsilent.network.URLConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val retrofit = Retrofit.Builder()
            .baseUrl(URLConstants.MOVIE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val service = retrofit.create(ApiService::class.java)

    val moviesLiveData = MutableLiveData<MovieResponse>()


    fun loadData() {
        val call = service.getPopularMovies(URLConstants.API_KEY, 1)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieList = response.body()!!.movies
                val popularMovies = MovieResponse()
                popularMovies.movies = movieList
                moviesLiveData.postValue(popularMovies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
        })
        val call1 = service.getNowPlayingMovies(URLConstants.API_KEY, 1)
        call1.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieList = response.body()!!.movies
                val nowPlayingMovies = MovieResponse()
                nowPlayingMovies.movies = movieList
                moviesLiveData.postValue(nowPlayingMovies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
        })
        val call2 = service.getTopRatedMovies(URLConstants.API_KEY, 1)
        call2.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieList = response.body()!!.movies
                val topRatedMovies = MovieResponse()
                topRatedMovies.movies = movieList
                moviesLiveData.postValue(topRatedMovies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
        })
        val call3 = service.getUpcomingMovies(URLConstants.API_KEY, 1)
        call3.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieList = response.body()!!.movies
                val upcomingMovies = MovieResponse()
                upcomingMovies.movies = movieList
                moviesLiveData.postValue(upcomingMovies)
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
        })
    }
}