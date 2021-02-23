package com.meghdut.upsilent.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.R
import com.meghdut.upsilent.adapters.RecyclerViewAdapterReviews
import com.meghdut.upsilent.models.Review
import java.util.*

/**
 * Created by Meghdut Mandal on 14/03/17.
 */
class ReviewsFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapterReviews: RecyclerViewAdapterReviews? = null
//    private var context: Context? = null
    private var reviewsMain: ArrayList<Review>? = null
    private var noReviewTextView: TextView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        context = activity
        val v = inflater.inflate(R.layout.fragment_reviews, container, false)
        recyclerView = v.findViewById(R.id.reviewsRecyclerView)
        noReviewTextView = v.findViewById(R.id.noReviewsTextView)
        reviewsMain = ArrayList()
        return v
    }

    fun setUIArguements(args: Bundle) {
        val activity: Activity? = activity
        activity?.runOnUiThread {
            reviewsMain = args.getSerializable("REVIEWS") as ArrayList<Review>?
            if (reviewsMain!!.size == 0) {
                noReviewTextView!!.visibility = View.VISIBLE
                noReviewTextView!!.text = "No Reviews are currently available for this movie."
            } else {
                recyclerViewAdapterReviews = RecyclerViewAdapterReviews(reviewsMain!!, requireContext())
                recyclerView!!.adapter = recyclerViewAdapterReviews
                val verticalManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView!!.layoutManager = verticalManager
            }
        }
    }

    companion object {
        fun newInstance(): ReviewsFragment {
            return ReviewsFragment()
        }
    }
}