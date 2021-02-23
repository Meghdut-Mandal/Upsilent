package com.meghdut.upsilent

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.meghdut.upsilent.adapters.BannerViewPagerAdapter
import com.meghdut.upsilent.adapters.TVShowFragmentPager
import com.meghdut.upsilent.fragments.CastTVShowFragment
import com.meghdut.upsilent.fragments.InfoAboutTVShowFragment
import com.meghdut.upsilent.models.Video
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

class AboutTVShowActivity : AppCompatActivity() {
    private lateinit var mBannerViewPager: ViewPager
    private lateinit var bannerViewPagerAdapter: BannerViewPagerAdapter
    private lateinit var allBannerImageFullLinks: ArrayList<String>
    var poster: ImageView? = null
    private lateinit var tabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    private lateinit var tvShowFragmentPager: TVShowFragmentPager
    private lateinit var tvShowNameTextView: TextView
    lateinit var obj: Video
    var doFirst = true
    var currentPage = 0
    lateinit var genreTextView: TextView
    lateinit var radioGroupTvShow: RadioGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_tvshow)
        title = ""
        val intent = intent
        val tvShow_id = intent.getIntExtra(IntentConstants.INTENT_KEY_TVSHOW_ID, 0)
        val posterPath = intent.getStringExtra(IntentConstants.INTENT_KEY_POSTER_PATH)
        val tvShowName = intent.getStringExtra(IntentConstants.INTENT_KEY_TVSHOW_NAME)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        allBannerImageFullLinks = ArrayList()
        mBannerViewPager = findViewById(R.id.tvShowpager)
        radioGroupTvShow = findViewById(R.id.radioGroupTvShow)
        bannerViewPagerAdapter = BannerViewPagerAdapter(this, allBannerImageFullLinks!!)
        mBannerViewPager.setAdapter(bannerViewPagerAdapter)
        mBannerViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (currentPage == 0 && doFirst) {
                    val rb = radioGroupTvShow.getChildAt(0) as RadioButton
                    rb.setButtonDrawable(R.drawable.ic_radio_button_checked)
                    doFirst = false
                }
            }

            override fun onPageSelected(position: Int) {
                currentPage = if (position > currentPage) {
                    val rb = radioGroupTvShow.getChildAt(position) as RadioButton
                    rb.setButtonDrawable(R.drawable.ic_radio_button_checked)
                    val rbi = radioGroupTvShow.getChildAt(currentPage) as RadioButton
                    rbi.setButtonDrawable(R.drawable.ic_radio_button_unchecked)
                    position
                } else {
                    val rb = radioGroupTvShow.getChildAt(position) as RadioButton
                    rb.setButtonDrawable(R.drawable.ic_radio_button_checked)
                    val rbi = radioGroupTvShow.getChildAt(currentPage) as RadioButton
                    rbi.setButtonDrawable(R.drawable.ic_radio_button_unchecked)
                    position
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        poster = findViewById<View>(R.id.posterWithBannerImageView) as ImageView
        Picasso.get().load(URLConstants.IMAGE_BASE_URL + posterPath).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                Palette.from(bitmap).generate { palette: Palette? ->
                    val color = palette!!.getDarkMutedColor(Color.parseColor("#424242"))
                    //Palette.Swatch swatch1 = palette.getDarkVibrantSwatch();
                    val collapsingToolbarLayout = findViewById<View>(R.id.collapsingToolbar) as CollapsingToolbarLayout
                    collapsingToolbarLayout.setBackgroundColor(color)
                    collapsingToolbarLayout.setContentScrimColor(color)
                    tabLayout!!.setBackgroundColor(palette.getMutedColor(Color.parseColor("#424242")))
                }
                poster!!.setImageBitmap(bitmap)
            }

            override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
        })
        tvShowNameTextView = findViewById(R.id.tvShowNameTextView)
        tvShowNameTextView.setText(tvShowName)
        genreTextView = findViewById(R.id.tvShowgenreTextView)
        tabLayout = findViewById(R.id.tabLayout)
        mViewPager = findViewById(R.id.container)
        tabLayout.addTab(tabLayout.newTab())
        tabLayout.addTab(tabLayout.newTab())
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL)
        tvShowFragmentPager = TVShowFragmentPager(supportFragmentManager)
        mViewPager.setAdapter(tvShowFragmentPager)
        tabLayout.setupWithViewPager(mViewPager)
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mViewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        val retrofit = Retrofit.Builder()
                .baseUrl(URLConstants.TVSHOW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiService::class.java)
        val call = service.getBannerImages(tvShow_id, URLConstants.API_KEY)
        call.enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                val bannerImagesLinksList = response.body()!!.bannerImageLinks
                for (i in bannerImagesLinksList.indices) {
                    if (i < 8) {
                        allBannerImageFullLinks!!.add(URLConstants.BANNER_BASE_URL + bannerImagesLinksList[i].bannerImageLink)
                        val radioButton = RadioButton(applicationContext)
                        radioButton.setButtonDrawable(R.drawable.ic_radio_button_unchecked)
                        radioGroupTvShow.addView(radioButton)
                    } else break
                }
                bannerViewPagerAdapter!!.refreshBannerUrls(allBannerImageFullLinks!!)
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {}
        })
        val call1 = service.getAboutTVShow(tvShow_id, URLConstants.API_KEY, "videos")
        call1.enqueue(object : Callback<AboutTVShowResponse> {
            override fun onResponse(call: Call<AboutTVShowResponse>, response: Response<AboutTVShowResponse>) {
                val genres = response.body()!!.genres
                for (i in genres.indices) {
                    if (i < genres.size - 1) genreTextView.append(genres[i].name + ", ") else genreTextView.append(genres[i].name)
                }
                val aboutTVShowResponse = AboutTVShowResponse()
                aboutTVShowResponse.overview = response.body()!!.overview
                aboutTVShowResponse.firstAirDate = response.body()!!.firstAirDate
                aboutTVShowResponse.lastAirDate = response.body()!!.lastAirDate
                aboutTVShowResponse.episodes = response.body()!!.episodes
                aboutTVShowResponse.seasons = response.body()!!.seasons
                aboutTVShowResponse.tvShowsCreaters = response.body()!!.tvShowsCreaters
                aboutTVShowResponse.showType = response.body()!!.showType
                aboutTVShowResponse.status = response.body()!!.status
                aboutTVShowResponse.video = response.body()!!.video
                obj = response.body()?.video!!
                obj.trailers = obj.trailers
                val bundle = Bundle()
                bundle.putString("OVERVIEW", aboutTVShowResponse.overview)
                bundle.putString("FIRST_AIR_DATE", aboutTVShowResponse.firstAirDate)
                bundle.putString("LAST_AIR_DATE", aboutTVShowResponse.lastAirDate)
                bundle.putInt("EPISODES", aboutTVShowResponse.episodes)
                bundle.putInt("SEASONS", aboutTVShowResponse.seasons)
                bundle.putString("SHOW_TYPE", aboutTVShowResponse.showType)
                bundle.putString("STATUS", aboutTVShowResponse.status)
                bundle.putSerializable("CREATORS", aboutTVShowResponse.tvShowsCreaters)
                Log.i("DUBAI", obj.trailers.toString())
                bundle.putSerializable("TRAILER_THUMBNAILS", obj.trailers)
                val obj = tvShowFragmentPager!!.function(0) as InfoAboutTVShowFragment?
                obj!!.setUIArguements(bundle)
            }

            override fun onFailure(call: Call<AboutTVShowResponse>, t: Throwable) {}
        })
        val call2 = service.getTvShowCredits(tvShow_id, URLConstants.API_KEY)
        call2.enqueue(object : Callback<CreditResponse> {
            override fun onResponse(call: Call<CreditResponse>, response: Response<CreditResponse>) {
                val tvShowCast = response.body()!!.cast
                val args = Bundle()
                args.putSerializable("TV_SHOW_CAST", tvShowCast)
                val obj1 = tvShowFragmentPager!!.function(1) as CastTVShowFragment?
                obj1!!.setUIArguements(args)
            }

            override fun onFailure(call: Call<CreditResponse>, t: Throwable) {}
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}