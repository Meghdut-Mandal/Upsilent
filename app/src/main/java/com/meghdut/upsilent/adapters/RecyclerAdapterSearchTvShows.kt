package com.meghdut.upsilent.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.AboutTVShowActivity
import com.meghdut.upsilent.R
import com.meghdut.upsilent.models.TVShow
import com.meghdut.upsilent.network.URLConstants
import com.meghdut.upsilent.utils.IntentConstants
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by Meghdut Mandal on 31/03/17.
 */
class RecyclerAdapterSearchTvShows(private val mTvShows: ArrayList<TVShow>?, var mContext: Context) : RecyclerView.Adapter<RecyclerAdapterSearchTvShows.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.search_tvshow_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mTvShows != null) {
            holder.name.text = mTvShows[position].title
            Picasso.get().load(URLConstants.IMAGE_BASE_URL + mTvShows[position].posterPath).into(holder.thumbnailImage)
            if (mTvShows[position].date.length >= 5) {
                val date = mTvShows[position].date.substring(0, 4)
                holder.releaseDate.text = date
            }
            val rating = mTvShows[position].rating.toString()
            holder.rating.text = rating
            holder.cv.setOnClickListener { v: View? ->
                val intent = Intent()
                val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity, holder.thumbnailImage, holder.thumbnailImage.transitionName).toBundle()
                intent.setClass(mContext, AboutTVShowActivity::class.java)
                intent.putExtra(IntentConstants.INTENT_KEY_TVSHOW_ID, mTvShows[position].id)
                intent.putExtra(IntentConstants.INTENT_KEY_POSTER_PATH, mTvShows[position].posterPath)
                intent.putExtra(IntentConstants.INTENT_KEY_TVSHOW_NAME, mTvShows[position].title)
                mContext.startActivity(intent, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return mTvShows!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cv: CardView = itemView.findViewById(R.id.cardView)
        var thumbnailImage: ImageView = itemView.findViewById(R.id.thumbnailImageView)
        var name: TextView
        var releaseDate: TextView
        var rating: TextView

        init {
            name = itemView.findViewById(R.id.nameTextView)
            releaseDate = itemView.findViewById(R.id.releaseDateTextView)
            rating = itemView.findViewById(R.id.ratingTextView)
        }
    }
}