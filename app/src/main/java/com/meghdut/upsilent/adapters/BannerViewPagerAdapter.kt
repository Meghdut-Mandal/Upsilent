package com.meghdut.upsilent.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.meghdut.upsilent.R
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by Meghdut Mandal on 19/01/17.
 */
class BannerViewPagerAdapter(var mContext: Context, allBannerImageFullLinks: ArrayList<String>) : PagerAdapter() {
    private var mAllBannerImageFullLinks: List<String>
    override fun getCount(): Int {
        return mAllBannerImageFullLinks.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = LayoutInflater.from(container.context).inflate(R.layout.banner_image_view_layout, container, false)
        val bannerImage = v.findViewById<ImageView>(R.id.bannerImage)
        Picasso.get().load(mAllBannerImageFullLinks[position]).into(bannerImage)
        container.addView(v)
        return v
    }

    fun refreshBannerUrls(list: List<String>) {
        mAllBannerImageFullLinks = list
        notifyDataSetChanged()
    }

    init {
        mAllBannerImageFullLinks = allBannerImageFullLinks
    }
}