package com.meghdut.upsilent.fragments.explore

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meghdut.upsilent.adapters.RecyclerViewAdapterReviews
import com.meghdut.upsilent.databinding.FragmentReviewsBinding
import com.meghdut.upsilent.models.Review
import java.util.*

/**
 * Created by Meghdut Mandal on 14/03/17.
 */
class ReviewsFragment : Fragment() {
    //   private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapterReviews: RecyclerViewAdapterReviews? = null

    private var _binding : FragmentReviewsBinding?= null
    private val binding get() = _binding!!

//    private var context: Context? = null
    private var reviewsMain: ArrayList<Review>? = null
//    private var noReviewTextView: TextView? = null

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
////        context = activity
//        val v = inflater.inflate(R.layout.fragment_reviews, container, false)
//        recyclerView = v.findViewById(R.id.reviewsRecyclerView)
//        noReviewTextView = v.findViewById(R.id.noReviewsTextView)
//        reviewsMain = ArrayList()
//        return v
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentReviewsBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    fun setUIArguements(args: Bundle) {
        val activity: Activity? = activity
        activity?.runOnUiThread {
            reviewsMain = args.getSerializable("REVIEWS") as ArrayList<Review>?
            if (reviewsMain!!.size == 0) {
                binding.noReviewsTextView!!.visibility = View.VISIBLE
                binding.noReviewsTextView!!.text = "No Reviews are currently available for this movie."
            } else {
                recyclerViewAdapterReviews = RecyclerViewAdapterReviews(reviewsMain!!, requireContext())
                binding.reviewsRecyclerView!!.adapter = recyclerViewAdapterReviews
                val verticalManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.reviewsRecyclerView!!.layoutManager = verticalManager
            }
        }
    }

    companion object {
        fun newInstance(): ReviewsFragment {
            return ReviewsFragment()
        }
    }
}