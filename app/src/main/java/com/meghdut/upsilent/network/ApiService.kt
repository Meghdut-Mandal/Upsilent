package com.meghdut.upsilent.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Meghdut Mandal on 12/01/17.
 */
interface ApiService {
    @Headers("access_token: 88484499033")
    @GET("popular")
    fun getPopularMovies(@Query("api_key") api_key: String?,
                         @Query("page") page: Int): Call<MovieResponse>

    @GET("now_playing")
    fun getNowPlayingMovies(@Query("api_key") api_key: String?,
                            @Query("page") page: Int): Call<MovieResponse>

    @GET("top_rated")
    fun getTopRatedMovies(@Query("api_key") api_key: String?,
                          @Query("page") page: Int): Call<MovieResponse>

    @GET("upcoming")
    fun getUpcomingMovies(@Query("api_key") api_key: String?,
                          @Query("page") page: Int): Call<MovieResponse>

    @GET("{id}/images")
    fun getBannerImages(@Path("id") id: Int, @Query("api_key") api_key: String?): Call<ImageResponse>

    @GET("{id}")
    fun getAboutMovie(@Path("id") id: Int, @Query("api_key") api_key: String?,
                      @Query("append_to_response") videos: String?): Call<AboutMovieResponse>

    @GET("{id}/credits")
    fun getCredits(@Path("id") id: Int, @Query("api_key") api_key: String?): Call<CreditResponse>

    @GET("{id}/similar")
    fun getSimilarMovies(@Path("id") id: Int, @Query("api_key") api_key: String?, @Query("page") page: Int): Call<MovieResponse>

    @GET("{id}/reviews")
    fun getReviews(@Path("id") id: Int, @Query("api_key") api_key: String?): Call<ReviewResponse>

    @GET("movie")
    fun getSearchedMovies(@Query("api_key") api_key: String?, @Query("query") query: String?, @Query("page") page: Int): Call<MovieResponse>

    //TV Shows
    @GET("airing_today")
    fun getAiringToday(@Query("api_key") api_key: String?,
                       @Query("page") page: Int): Call<TVShowResponse>

    @GET("on_the_air")
    fun getOnAir(@Query("api_key") api_key: String?,
                 @Query("page") page: Int): Call<TVShowResponse>

    @GET("popular")
    fun getPopular(@Query("api_key") api_key: String?,
                   @Query("page") page: Int): Call<TVShowResponse>

    @GET("top_rated")
    fun getTopRated(@Query("api_key") api_key: String?,
                    @Query("page") page: Int): Call<TVShowResponse>

    @GET("{id}")
    fun getAboutTVShow(@Path("id") id: Int, @Query("api_key") api_key: String?, @Query("append_to_response") videos: String?): Call<AboutTVShowResponse>

    @GET("{id}/credits")
    fun getTvShowCredits(@Path("id") id: Int, @Query("api_key") api_key: String?): Call<CreditResponse>

    @GET("tv")
    fun getSearchedShows(@Query("api_key") api_key: String?, @Query("query") query: String?, @Query("page") page: Int): Call<TVShowResponse>
}