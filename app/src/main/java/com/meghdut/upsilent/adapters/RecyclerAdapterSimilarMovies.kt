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
import com.meghdut.upsilent.AboutMovieActivity
import com.meghdut.upsilent.R
import com.meghdut.upsilent.models.Movie
import com.meghdut.upsilent.network.URLConstants
import com.meghdut.upsilent.utils.IntentConstants
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by Meghdut Mandal on 19/02/17.
 */
class RecyclerAdapterSimilarMovies(private val mSimilarMovies: ArrayList<Movie>?, var mContext: Context) : RecyclerView.Adapter<RecyclerAdapterSimilarMovies.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.similar_movies_item_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mSimilarMovies != null) {
            holder.similarMoviesNameTextView.text = mSimilarMovies[position].title
            Picasso.get().load(URLConstants.IMAGE_BASE_URL + mSimilarMovies[position].posterPath).into(holder.similarMoviesThumbnailImageView)
            if (mSimilarMovies[position].date.length >= 5) {
                val date = mSimilarMovies[position].date.substring(0, 4)
                holder.similarMoviesReleaseDateTextView.text = date
            }
            val rating = mSimilarMovies[position].rating.toString()
            holder.similarmoviesRatingTextView.text = rating
            holder.similarMoviesCardView.setOnClickListener { v: View? ->
                val intent = Intent()
                val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity, holder.similarMoviesThumbnailImageView, holder.similarMoviesThumbnailImageView.transitionName).toBundle()
                intent.setClass(mContext, AboutMovieActivity::class.java)
                intent.putExtra(IntentConstants.INTENT_KEY_MOVIE_ID, mSimilarMovies[position].id)
                intent.putExtra(IntentConstants.INTENT_KEY_POSTER_PATH, mSimilarMovies[position].posterPath)
                intent.putExtra(IntentConstants.INTENT_KEY_MOVIE_NAME, mSimilarMovies[position].title)
                mContext.startActivity(intent, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return mSimilarMovies!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var similarMoviesCardView: CardView = itemView.findViewById(R.id.similarMoviesCardView)
        var similarMoviesThumbnailImageView: ImageView
        var similarMoviesNameTextView: TextView
        var similarMoviesReleaseDateTextView: TextView
        var similarmoviesRatingTextView: TextView

        init {
            similarMoviesThumbnailImageView = itemView.findViewById(R.id.similarMoviesThumbnailImageView)
            similarMoviesNameTextView = itemView.findViewById(R.id.similarMoviesNameTextView)
            similarMoviesReleaseDateTextView = itemView.findViewById(R.id.similarMoviesReleaseDateTextView)
            similarmoviesRatingTextView = itemView.findViewById(R.id.similarmoviesRatingTextView)
        }
    }
}