package com.meghdut.upsilent.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.R
import com.meghdut.upsilent.YouTubeActivity
import com.meghdut.upsilent.models.Trailer
import com.meghdut.upsilent.network.URLConstants
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by Meghdut Mandal on 03/03/17.
 */
class RecyclerAdapterTVShowTrailer(private val mTrailerTvShowsArraylist: ArrayList<Trailer>?, var mContext: Context) : RecyclerView.Adapter<RecyclerAdapterTVShowTrailer.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.trailer_thumbnail_image_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mTrailerTvShowsArraylist != null) {
            Picasso.get().load(URLConstants.TRAILER_THUMBNAIL_IMAGE_URL + mTrailerTvShowsArraylist[position].key + "/hqdefault.jpg").into(holder.trailerThumbnail)
            holder.trailerThumbnailName.text = mTrailerTvShowsArraylist[position].name
            holder.trailerCardView.setOnClickListener { v: View? ->
                val intent = Intent()
                intent.setClass(mContext, YouTubeActivity::class.java)
                intent.putExtra("VIDEO_KEY", mTrailerTvShowsArraylist[position].key)
                mContext.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return mTrailerTvShowsArraylist!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var trailerCardView: CardView
        var trailerThumbnail: ImageView = itemView.findViewById(R.id.trailerThumbnail)
        var trailerThumbnailName: TextView

        init {
            trailerCardView = itemView.findViewById(R.id.trailerCardView)
            trailerThumbnailName = itemView.findViewById(R.id.trailerThumbnailName)
        }
    }
}