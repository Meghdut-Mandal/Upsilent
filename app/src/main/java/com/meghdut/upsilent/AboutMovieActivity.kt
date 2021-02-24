package com.meghdut.upsilent

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.meghdut.upsilent.adapters.BannerViewPagerAdapter
import com.meghdut.upsilent.adapters.FragmentPager
import com.meghdut.upsilent.fragments.explore.CastMovieFragment
import com.meghdut.upsilent.fragments.explore.InfoAboutMovieFragment
import com.meghdut.upsilent.fragments.explore.InfoAboutMovieFragment.InfoAboutMovieFragmentListener
import com.meghdut.upsilent.fragments.explore.ReviewsFragment
import com.meghdut.upsilent.models.*
import com.meghdut.upsilent.network.*
import com.meghdut.upsilent.utils.IntentConstants
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AboutMovieActivity : AppCompatActivity(), InfoAboutMovieFragmentListener {
    private lateinit var bannerViewPagerAdapter: BannerViewPagerAdapter
    private lateinit var allBannerImageFullLinks: ArrayList<String>
    lateinit var poster: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    var currentpage = 0
    private var fragmentPager = FragmentPager(supportFragmentManager)
    private lateinit var movieNameTextView: TextView
    lateinit var genreTextView: TextView
    lateinit var releaseDateTextView: TextView
    lateinit var runTimeTextView: TextView
    var doFirst = true
    lateinit var radioGroup: RadioGroup
    private var movie_id = 0
    private lateinit var movieName: String
    var mainSimilarMovies = ArrayList<Movie>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_movie)
        title = ""
        val intent = intent
        movie_id = intent.getIntExtra(IntentConstants.INTENT_KEY_MOVIE_ID, 0)
        val posterPath = intent.getStringExtra(IntentConstants.INTENT_KEY_POSTER_PATH)
        movieName = intent.getStringExtra(IntentConstants.INTENT_KEY_MOVIE_NAME)!!
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val infoAboutMovieFragment = fragmentPager.function(0) as InfoAboutMovieFragment
        infoAboutMovieFragment.setInfoAboutMovieFragmentListener(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        allBannerImageFullLinks = ArrayList()

        // Set up the ViewPager with the sections adapter.
        //mViewPager.setAdapter(mSectionsPagerAdapter);
        val mBannerViewPager = findViewById<ViewPager>(R.id.pager)
        radioGroup = findViewById(R.id.radioGroup)
        bannerViewPagerAdapter = BannerViewPagerAdapter(this, allBannerImageFullLinks)
        mBannerViewPager.adapter = bannerViewPagerAdapter
        mBannerViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (currentpage == 0 && doFirst) {
                    val rb = radioGroup.getChildAt(0) as RadioButton
                    rb.setButtonDrawable(R.drawable.ic_radio_button_checked)
                    doFirst = false
                }
            }

            override fun onPageSelected(position: Int) {
                currentpage = if (position > currentpage) {
                    val rb = radioGroup.getChildAt(position) as RadioButton
                    rb.setButtonDrawable(R.drawable.ic_radio_button_checked)
                    val rbi = radioGroup.getChildAt(currentpage) as RadioButton
                    rbi.setButtonDrawable(R.drawable.ic_radio_button_unchecked)
                    position
                } else {
                    val rb = radioGroup.getChildAt(position) as RadioButton
                    rb.setButtonDrawable(R.drawable.ic_radio_button_checked)
                    val rbi = radioGroup.getChildAt(currentpage) as RadioButton
                    rbi.setButtonDrawable(R.drawable.ic_radio_button_unchecked)
                    position
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        poster = findViewById(R.id.posterWithBannerImageView)
        Picasso.get().load(URLConstants.IMAGE_BASE_URL + posterPath).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                Palette.from(bitmap).generate { palette: Palette? ->
                    val color = palette!!.getDarkMutedColor(Color.parseColor("#424242"))
                    //Palette.Swatch swatch1 = palette.getDarkVibrantSwatch();
                    val collapsingToolbarLayout = findViewById<View>(R.id.collapsingToolbar) as CollapsingToolbarLayout
                    collapsingToolbarLayout.setBackgroundColor(color)
                    collapsingToolbarLayout.setContentScrimColor(color)
                    tabLayout.setBackgroundColor(palette.getMutedColor(Color.parseColor("#424242")))
                }
                poster.setImageBitmap(bitmap)
            }

            override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
        })
        movieNameTextView = findViewById(R.id.nameTextView)
        movieNameTextView.text = movieName
        genreTextView = findViewById(R.id.genreTextView)
        releaseDateTextView = findViewById(R.id.releaseDateTextView)
        runTimeTextView = findViewById(R.id.runTimeTextView)
        tabLayout = findViewById(R.id.tabLayout)
        mViewPager = findViewById(R.id.container)
        tabLayout.addTab(tabLayout.newTab())
        tabLayout.addTab(tabLayout.newTab())
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        fragmentPager = FragmentPager(supportFragmentManager)
        mViewPager.adapter = fragmentPager
        tabLayout.setupWithViewPager(mViewPager)
        mViewPager.offscreenPageLimit = 3
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        val retrofit = Retrofit.Builder()
                .baseUrl(URLConstants.MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiService::class.java)
        val call = service.getBannerImages(movie_id, URLConstants.API_KEY)
        call.enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                val bannerImagesLinksList = response.body()!!.bannerImageLinks
                for (i in bannerImagesLinksList.indices) {
                    if (i < 8) {
                        allBannerImageFullLinks.add(URLConstants.BANNER_BASE_URL + bannerImagesLinksList[i].bannerImageLink)
                        val radioButton = RadioButton(applicationContext)
                        radioButton.setButtonDrawable(R.drawable.ic_radio_button_unchecked)
                        radioGroup.addView(radioButton)
                    } else break
                }
                bannerViewPagerAdapter.refreshBannerUrls(allBannerImageFullLinks)
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {}
        })
        val call1 = service.getAboutMovie(movie_id, URLConstants.API_KEY, "videos")
        call1.enqueue(object : Callback<AboutMovieResponse> {
            override fun onResponse(call: Call<AboutMovieResponse>, response: Response<AboutMovieResponse>) {
                val genres = response.body()!!.genres
                for (i in genres.indices) {
                    if (i < genres.size - 1) genreTextView.append(genres[i].name + ", ") else genreTextView.append(genres[i].name)
                }
                val aboutMovieResponse = AboutMovieResponse()
                aboutMovieResponse.overview = response.body()!!.overview
                aboutMovieResponse.releaseDate = response.body()!!.releaseDate
                aboutMovieResponse.runTimeOfMovie = response.body()!!.runTimeOfMovie
                aboutMovieResponse.budget = response.body()!!.budget
                aboutMovieResponse.revenue = response.body()!!.revenue
                aboutMovieResponse.genres = response.body()!!.genres
                aboutMovieResponse.video = response.body()!!.video
                val obj = response.body()!!.video!!
                obj.trailers = obj.trailers
                if (aboutMovieResponse.releaseDate.length >= 5) releaseDateTextView.text = aboutMovieResponse.releaseDate.substring(0, 4)
                runTimeTextView.text = (aboutMovieResponse.runTimeOfMovie / 60).toString() + "hrs " + aboutMovieResponse.runTimeOfMovie % 60 + "mins"
                val bundle = Bundle()
                bundle.putBoolean("INFO", true)
                bundle.putString("OVERVIEW", aboutMovieResponse.overview)
                bundle.putString("RELEASE_DATE", aboutMovieResponse.releaseDate)
                bundle.putLong("BUDGET", aboutMovieResponse.budget)
                bundle.putLong("REVENUE", aboutMovieResponse.revenue)
                bundle.putSerializable("TRAILER_THUMBNAILS", obj.trailers)
                val obj1 = fragmentPager.function(0) as InfoAboutMovieFragment
                obj1.setUIArguements(bundle)
            }

            override fun onFailure(call: Call<AboutMovieResponse>, t: Throwable) {}
        })
        val call2 = service.getSimilarMovies(movie_id, URLConstants.API_KEY, 1)
        call2.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val similarMoviesList = response.body()!!.movies
                for (`object` in similarMoviesList) {
                    mainSimilarMovies.add(`object`)
                }
                val bundle = Bundle()
                bundle.putBoolean("SIMILAR", true)
                bundle.putSerializable("SIMILAR_MOVIES", similarMoviesList)
                val obj1 = fragmentPager.function(0) as InfoAboutMovieFragment
                obj1.setUIArguements(bundle)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
        })
        val call3 = service.getCredits(movie_id, URLConstants.API_KEY)
        call3.enqueue(object : Callback<CreditResponse> {
            override fun onResponse(call: Call<CreditResponse>, response: Response<CreditResponse>) {
                val movieCast = response.body()!!.cast
                val args = Bundle()
                args.putSerializable("MOVIE_CAST", movieCast)
                val obj = fragmentPager.function(1) as CastMovieFragment
                obj.setUIArguements(args)
            }

            override fun onFailure(call: Call<CreditResponse>, t: Throwable) {}
        })
        val call4 = service.getReviews(movie_id, URLConstants.API_KEY)
        call4.enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                val reviews = response.body()!!.reviews
                val arguements = Bundle()
                arguements.putSerializable("REVIEWS", reviews)
                val `object` = fragmentPager.function(2) as ReviewsFragment
                `object`.setUIArguements(arguements)
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {}
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSeeAllSimilarMoviesClicked() {
        val intent = Intent()
        intent.setClass(this@AboutMovieActivity, SeeAllSimilarMoviesActivity::class.java)
        intent.putExtra(IntentConstants.INTENT_KEY_MOVIE_ID, movie_id)
        intent.putExtra("MOVIE_NAME", movieName)
        startActivity(intent)
    }
}