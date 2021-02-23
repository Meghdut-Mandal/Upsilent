package com.meghdut.upsilent.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.AboutTVShowActivity
import com.meghdut.upsilent.R
import com.meghdut.upsilent.models.TVShow
import com.meghdut.upsilent.network.URLConstants
import com.meghdut.upsilent.utils.IntentConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.see_all_recyclerview_item.view.*
import java.util.*

/**
 * Created by Meghdut Mandal on 12/02/17.
 */

class RecyclerAdapterSeeAllTvshows(private val mTvShows: ArrayList<TVShow>?, internal var mContext: Context) : RecyclerView.Adapter<RecyclerAdapterSeeAllTvshows.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.see_all_recyclerview_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mTvShows != null) {
            holder.itemView.nameTV.text = mTvShows[position].title
            Picasso.get().load(URLConstants.IMAGE_BASE_URL + mTvShows[position].posterPath).into(holder.itemView.thumbnailIV)
            if (mTvShows[position].date.length >= 5) {
                val date = mTvShows[position].date.substring(0, 4)
                holder.itemView.releaseDateTV.text = date
            }
            val rating = mTvShows[position].rating.toString()
            holder.itemView.ratingTV.text = rating
            holder.itemView.rootCV.setOnClickListener { v ->
                val intent = Intent()
                val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity, holder.itemView.thumbnailIV, holder.itemView.thumbnailIV.transitionName).toBundle()
                intent.putExtra(IntentConstants.INTENT_KEY_TVSHOW_ID, mTvShows[position].id)
                intent.putExtra(IntentConstants.INTENT_KEY_POSTER_PATH, mTvShows[position].posterPath)
                intent.putExtra(IntentConstants.INTENT_KEY_TVSHOW_NAME, mTvShows[position].title)
                intent.setClass(mContext, AboutTVShowActivity::class.java)
                mContext.startActivity(intent, bundle)
            }

        }

    }


    override fun getItemCount(): Int {
        return mTvShows?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
