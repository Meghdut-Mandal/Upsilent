package com.meghdut.upsilent.fragments.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.R
import com.meghdut.upsilent.adapters.RecyclerAdapterTVShowTrailer
import com.meghdut.upsilent.models.TVShowsCreaters
import com.meghdut.upsilent.models.Trailer
import com.meghdut.upsilent.utils.AppUtil.dpToPx
import com.meghdut.upsilent.utils.HorizontalItemDecoration
import java.util.*

/**
 * Created by Meghdut Mandal on 09/02/17.
 */
class InfoAboutTVShowFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info_tvshow, container, false)
    }

    fun setUIArguments(args: Bundle) {
        val aboutTvShowTextView: TextView = requireView().findViewById(R.id.aboutTvShowTextView)
        val firstAirDateTextView: TextView = requireView().findViewById(R.id.firstAirDateTextView)
        val lastAirDateTextView: TextView = requireView().findViewById(R.id.lastAirDateTextView)
        val createdByTextView: TextView = requireView().findViewById(R.id.createdByTextView)
        val showTypeTextView: TextView = requireView().findViewById(R.id.showStatusTextView)
        val showStatusTextView: TextView = requireView().findViewById(R.id.showTypeTextView)
        val noReviewTextView: TextView = requireView().findViewById(R.id.noTVTrailerTextView)
        val tailorsRecyclerView: RecyclerView = requireView().findViewById(R.id.trailorsTvShowRecyclerView)

        requireActivity().runOnUiThread {
            aboutTvShowTextView.text = args.getString("OVERVIEW")
            val firstAirDate = dateGenerator(args.getString("FIRST_AIR_DATE"))
            firstAirDateTextView.text = firstAirDate
            val lastAirDate = dateGenerator(args.getString("LAST_AIR_DATE"))
            lastAirDateTextView.text = lastAirDate
            val obj = args.getSerializable("CREATORS") as ArrayList<TVShowsCreaters>?
            for (i in obj!!.indices) {
                if (i < obj.size - 1) createdByTextView.append(obj[i].name + ", ") else createdByTextView.append(obj[i].name)
            }
            showTypeTextView.text = args.getString("SHOW_TYPE")
            showStatusTextView.text = args.getString("STATUS")
            val mainTrailerTvShowsThumbnails = args.getSerializable("TRAILER_THUMBNAILS") as ArrayList<Trailer>
            if (mainTrailerTvShowsThumbnails.size == 0) {
                noReviewTextView.visibility = View.VISIBLE
                noReviewTextView.text = "No Trailers are currently available."
            } else {
                val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                tailorsRecyclerView.layoutManager = linearLayoutManager
                tailorsRecyclerView.addItemDecoration(HorizontalItemDecoration(dpToPx(requireContext(), 16), dpToPx(requireContext(), 6), dpToPx(requireContext(), 16)))
                val recyclerAdapterTVShowTrailer = RecyclerAdapterTVShowTrailer(mainTrailerTvShowsThumbnails, requireContext())
                tailorsRecyclerView.adapter = recyclerAdapterTVShowTrailer
            }
        }
    }

    private fun dateGenerator(date: String?): String {
        val month = date!!.substring(5, 7)
        var ans = ""
        when (month) {
            "01" -> ans = "January"
            "02" -> ans = "February"
            "03" -> ans = "March"
            "04" -> ans = "April"
            "05" -> ans = "May"
            "06" -> ans = "June"
            "07" -> ans = "July"
            "08" -> ans = "August"
            "09" -> ans = "September"
            "10" -> ans = "October"
            "11" -> ans = "November"
            "12" -> ans = "December"
        }
        return ans + " " + date.substring(8, 10) + "," + " " + date.substring(0, 4)
    }

    companion object {
        fun newInstance(): InfoAboutTVShowFragment {
            return InfoAboutTVShowFragment()
        }
    }
}