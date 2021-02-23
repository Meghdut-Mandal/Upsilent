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
import com.meghdut.upsilent.AboutTVShowActivity
import com.meghdut.upsilent.OnRecyclerViewItemClickListener
import com.meghdut.upsilent.R
import com.meghdut.upsilent.SeeAllTVShowsActivity
import com.meghdut.upsilent.network.TVShowResponse
import com.meghdut.upsilent.utils.AppUtil
import com.meghdut.upsilent.utils.HorizontalItemDecoration
import com.meghdut.upsilent.utils.IntentConstants

/**
 * Created by Meghdut Mandal on 09/02/17.
 */
class RecyclerViewAdapterTVShow(private val mTVShows: Array<TVShowResponse?>?, var mContext: Context) : RecyclerView.Adapter<RecyclerViewAdapterTVShow.ViewHolder>(), OnRecyclerViewItemClickListener {
    private var recyclerViewAdapter: RecyclerViewAdapterTVShowHorizontal? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.acitivity_main_second_tvshows, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mTVShows != null && mTVShows.size > position) {
            if (getItemViewType(position) == 0) {
                if (mTVShows[position] != null) {
                    holder.tvShowType.text = "Airing Today"
                    holder.seeAlltextView.text = "See all"
                    holder.seeAlltextView.setOnClickListener { v: View? ->
                        val intent = Intent()
                        val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity).toBundle()
                        intent.putExtra("ABCD", mTVShows[position]!!.tvShows)
                        intent.putExtra("TVSHOW_TYPE", "Airing Today")
                        intent.setClass(mContext, SeeAllTVShowsActivity::class.java)
                        mContext.startActivity(intent, bundle)
                    }
                    recyclerViewAdapter = RecyclerViewAdapterTVShowHorizontal(mTVShows[position]!!.tvShows, mContext)
                    holder.horizontalRecyclerView.adapter = recyclerViewAdapter
                    val horizontalManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    holder.horizontalRecyclerView.addItemDecoration(HorizontalItemDecoration(AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2)))
                    holder.horizontalRecyclerView.layoutManager = horizontalManager
                    recyclerViewAdapter!!.setOnItemClickListener(this, position)
                }
            } else if (getItemViewType(position) == 1) {
                if (mTVShows[position] != null) {
                    holder.tvShowType.text = "On Air"
                    holder.seeAlltextView.text = "See all"
                    holder.seeAlltextView.setOnClickListener { v: View? ->
                        val intent = Intent()
                        val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity).toBundle()
                        intent.setClass(mContext, SeeAllTVShowsActivity::class.java)
                        intent.putExtra("ABCD", mTVShows[position]!!.tvShows)
                        intent.putExtra("TVSHOW_TYPE", "On Air")
                        mContext.startActivity(intent, bundle)
                    }
                    recyclerViewAdapter = RecyclerViewAdapterTVShowHorizontal(mTVShows[position]!!.tvShows, mContext)
                    val horizontalManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    holder.horizontalRecyclerView.addItemDecoration(HorizontalItemDecoration(AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2)))
                    holder.horizontalRecyclerView.layoutManager = horizontalManager
                    holder.horizontalRecyclerView.adapter = recyclerViewAdapter
                    recyclerViewAdapter!!.setOnItemClickListener(this, position)
                }
            } else if (getItemViewType(position) == 2) {
                if (mTVShows[position] != null) {
                    holder.tvShowType.text = "Popular Shows"
                    holder.seeAlltextView.text = "See all"
                    holder.seeAlltextView.setOnClickListener { v: View? ->
                        val intent = Intent()
                        val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity).toBundle()
                        intent.putExtra("ABCD", mTVShows[position]!!.tvShows)
                        intent.putExtra("TVSHOW_TYPE", "Popular Shows")
                        intent.setClass(mContext, SeeAllTVShowsActivity::class.java)
                        mContext.startActivity(intent, bundle)
                    }
                    recyclerViewAdapter = RecyclerViewAdapterTVShowHorizontal(mTVShows[position]!!.tvShows, mContext)
                    val horizontalManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    holder.horizontalRecyclerView.addItemDecoration(HorizontalItemDecoration(AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2)))
                    holder.horizontalRecyclerView.layoutManager = horizontalManager
                    holder.horizontalRecyclerView.adapter = recyclerViewAdapter
                    recyclerViewAdapter!!.setOnItemClickListener(this, position)
                }
            } else if (getItemViewType(position) == 3) {
                if (mTVShows[position] != null) {
                    holder.tvShowType.text = "Top Rated Shows"
                    holder.seeAlltextView.text = "See all"
                    holder.seeAlltextView.setOnClickListener { v: View? ->
                        val intent = Intent()
                        val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity).toBundle()
                        intent.putExtra("ABCD", mTVShows[position]!!.tvShows)
                        intent.putExtra("TVSHOW_TYPE", "Top Rated Shows")
                        intent.setClass(mContext, SeeAllTVShowsActivity::class.java)
                        mContext.startActivity(intent, bundle)
                    }
                    recyclerViewAdapter = RecyclerViewAdapterTVShowHorizontal(mTVShows[position]!!.tvShows, mContext)
                    holder.horizontalRecyclerView.adapter = recyclerViewAdapter
                    val horizontalManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    holder.horizontalRecyclerView.addItemDecoration(HorizontalItemDecoration(AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2), AppUtil.dpToPx(mContext, 2)))
                    holder.horizontalRecyclerView.layoutManager = horizontalManager
                    recyclerViewAdapter!!.setOnItemClickListener(this, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mTVShows!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return position % 4
    }

    override fun onRecyclerViewItemClicked(verticalPosition: Int, horizontalPosition: Int, view: View?) {
        val intent = Intent()
        val bundle = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity, view, view?.transitionName).toBundle()
        intent.setClass(mContext, AboutTVShowActivity::class.java)
        intent.putExtra(IntentConstants.INTENT_KEY_TVSHOW_ID, mTVShows!![verticalPosition]!!.tvShows[horizontalPosition].id)
        intent.putExtra(IntentConstants.INTENT_KEY_POSTER_PATH, mTVShows[verticalPosition]!!.tvShows[horizontalPosition].posterPath)
        intent.putExtra(IntentConstants.INTENT_KEY_TVSHOW_NAME, mTVShows[verticalPosition]!!.tvShows[horizontalPosition].title)
        mContext.startActivity(intent, bundle)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvShowType: TextView = itemView.findViewById(R.id.tvShowTypeTextView)
        var horizontalRecyclerView: RecyclerView
        var seeAlltextView: TextView

        init {
            seeAlltextView = itemView.findViewById(R.id.seeAllTextView)
            horizontalRecyclerView = itemView.findViewById(R.id.activityMainRecyclerViewHorizontal)
        }
    }
}