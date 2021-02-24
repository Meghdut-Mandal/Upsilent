package com.meghdut.upsilent.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.AboutMovieActivity
import com.meghdut.upsilent.OnRecyclerViewItemClickListener
import com.meghdut.upsilent.R
import com.meghdut.upsilent.SeeAllMoviesActivity
import com.meghdut.upsilent.network.MovieResponse
import com.meghdut.upsilent.utils.AppUtil
import com.meghdut.upsilent.utils.HorizontalItemDecoration
import com.meghdut.upsilent.utils.IntentConstants

/**
 * Created by Meghdut Mandal on 07/01/17.
 */
class RecyclerViewAdapterMain(private val mMovies: ArrayList<MovieResponse>, var mContext: Context) : RecyclerView.Adapter<RecyclerViewAdapterMain.ViewHolder>(), OnRecyclerViewItemClickListener {
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_second, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mMovies.size > position) {
            if (getItemViewType(position) == 0) {
                holder.movieType.text = "Popular Movies"
                holder.seeAlltextView.text = "See all"
                holder.seeAlltextView.setOnClickListener { v: View? ->
                    val intent = Intent()
                    val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity).toBundle()
                    intent.putExtra("ABCD", mMovies[position].movies)
                    intent.putExtra("MOVIETYPE", "Popular Movies")
                    intent.setClass(mContext, SeeAllMoviesActivity::class.java)
                    mContext.startActivity(intent, bundle)
                }
                recyclerViewAdapter = RecyclerViewAdapter(mMovies[position].movies, mContext)
                val horizontalManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
                holder.horizontalRecyclerView.addItemDecoration(HorizontalItemDecoration(AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2)))
                holder.horizontalRecyclerView.layoutManager = horizontalManager
                holder.horizontalRecyclerView.adapter = recyclerViewAdapter
                recyclerViewAdapter!!.setOnItemClickListener(this, position)
            } else if (getItemViewType(position) == 1) {
                holder.movieType.text = "Now Playing"
                holder.seeAlltextView.text = "See all"
                holder.seeAlltextView.setOnClickListener { v: View? ->
                    val intent = Intent()
                    val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity).toBundle()
                    intent.setClass(mContext, SeeAllMoviesActivity::class.java)
                    intent.putExtra("ABCD", mMovies[position].movies)
                    intent.putExtra("MOVIETYPE", "Now Playing")
                    mContext.startActivity(intent, bundle)
                }
                recyclerViewAdapter = RecyclerViewAdapter(mMovies[position].movies, mContext)
                val horizontalManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                holder.horizontalRecyclerView.addItemDecoration(HorizontalItemDecoration(AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2)))
                holder.horizontalRecyclerView.layoutManager = horizontalManager
                holder.horizontalRecyclerView.adapter = recyclerViewAdapter
                recyclerViewAdapter!!.setOnItemClickListener(this, position)
            } else if (getItemViewType(position) == 2) {
                holder.movieType.text = "Top Rated Movies"
                holder.seeAlltextView.text = "See all"
                holder.seeAlltextView.setOnClickListener { v: View? ->
                    val intent = Intent()
                    val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity).toBundle()
                    intent.putExtra("ABCD", mMovies[position].movies)
                    intent.putExtra("MOVIETYPE", "Top Rated Movies")
                    intent.setClass(mContext, SeeAllMoviesActivity::class.java)
                    mContext.startActivity(intent, bundle)
                }
                recyclerViewAdapter = RecyclerViewAdapter(mMovies[position].movies, mContext)
                val horizontalManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                holder.horizontalRecyclerView.addItemDecoration(HorizontalItemDecoration(AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2)))
                holder.horizontalRecyclerView.layoutManager = horizontalManager
                holder.horizontalRecyclerView.adapter = recyclerViewAdapter
                recyclerViewAdapter!!.setOnItemClickListener(this, position)
            } else if (getItemViewType(position) == 3) {
                if (mMovies[position] != null) {
                    holder.movieType.text = "Upcoming Movies"
                    holder.seeAlltextView.text = "See all"
                    holder.seeAlltextView.setOnClickListener { v: View? ->
                        val intent = Intent()
                        val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity).toBundle()
                        intent.putExtra("ABCD", mMovies[position].movies)
                        intent.putExtra("MOVIETYPE", "Upcoming Movies")
                        intent.setClass(mContext, SeeAllMoviesActivity::class.java)
                        mContext.startActivity(intent, bundle)
                    }
                    recyclerViewAdapter = RecyclerViewAdapter(mMovies[position].movies, mContext)
                    val horizontalManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    holder.horizontalRecyclerView.addItemDecoration(HorizontalItemDecoration(AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2)))
                    holder.horizontalRecyclerView.layoutManager = horizontalManager
                    holder.horizontalRecyclerView.adapter = recyclerViewAdapter
                    recyclerViewAdapter!!.setOnItemClickListener(this, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    override fun getItemViewType(position: Int): Int {
        return position % 4
    }

    override fun onRecyclerViewItemClicked(verticalPosition: Int, horizontalPosition: Int, view: View?) {
        val intent = Intent()
        val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity, view, view?.transitionName).toBundle()
        //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle();
        intent.setClass(mContext, AboutMovieActivity::class.java)
        intent.putExtra(IntentConstants.INTENT_KEY_MOVIE_ID, mMovies[verticalPosition].movies[horizontalPosition].id)
        intent.putExtra(IntentConstants.INTENT_KEY_POSTER_PATH, mMovies[verticalPosition].movies[horizontalPosition].posterPath)
        intent.putExtra(IntentConstants.INTENT_KEY_MOVIE_NAME, mMovies[verticalPosition].movies[horizontalPosition].title)
        mContext.startActivity(intent, bundle)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieType: TextView = itemView.findViewById(R.id.movieTypeTextView)
        var horizontalRecyclerView: RecyclerView
        var seeAlltextView: TextView = itemView.findViewById(R.id.seeAllTextView)

        init {
            horizontalRecyclerView = itemView.findViewById(R.id.activityMainRecyclerViewHorizontal)
        }
    }
}