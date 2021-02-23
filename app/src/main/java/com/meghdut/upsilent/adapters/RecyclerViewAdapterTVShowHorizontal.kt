package com.meghdut.upsilent.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.OnRecyclerViewItemClickListener
import com.meghdut.upsilent.R
import com.meghdut.upsilent.models.TVShow
import com.meghdut.upsilent.network.URLConstants
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by Meghdut Mandal on 09/02/17.
 */
class RecyclerViewAdapterTVShowHorizontal(private val mTVShows: ArrayList<TVShow>, var mContext: Context) : RecyclerView.Adapter<RecyclerViewAdapterTVShowHorizontal.ViewHolder>() {
    private var listener: OnRecyclerViewItemClickListener? = null
    private var verticalPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_cardview_tvshows, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mTVShows != null) {
            holder.tvShowName.text = mTVShows[position].title
            Picasso.get().load(URLConstants.IMAGE_BASE_URL + mTVShows[position].posterPath).into(holder.tvShowThumbnailImage)
            if (mTVShows[position].date.length >= 5) {
                val date = mTVShows[position].date.substring(0, 4)
                holder.tvShowReleaseDate.text = date
            }
            val rating = mTVShows[position].rating.toString().substring(0, 3)
            holder.rating.text = rating
            holder.cv.setOnClickListener { v: View? -> if (listener != null) listener!!.onRecyclerViewItemClicked(verticalPosition, position, holder.tvShowThumbnailImage) }
        }
    }

    override fun getItemCount(): Int {
        return mTVShows.size
    }

    fun setOnItemClickListener(listener: OnRecyclerViewItemClickListener?, verticalPosition: Int) {
        this.listener = listener
        this.verticalPosition = verticalPosition
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cv: CardView = itemView.findViewById(R.id.cardView)
        var tvShowThumbnailImage: ImageView
        var tvShowName: TextView
        var tvShowReleaseDate: TextView
        var rating: TextView

        init {
            tvShowThumbnailImage = itemView.findViewById(R.id.tvShowThumbnailImageView)
            tvShowName = itemView.findViewById(R.id.tvShowNameTextView)
            tvShowReleaseDate = itemView.findViewById(R.id.tvShowReleaseDateTextView)
            rating = itemView.findViewById(R.id.ratingTextView)
        }
    }
}