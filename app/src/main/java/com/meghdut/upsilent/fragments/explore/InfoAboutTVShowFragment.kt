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
    private lateinit var aboutTvShowTextView: TextView
    private lateinit var firstAirDateTextView: TextView
    private lateinit var lastAirDateTextView: TextView
    private lateinit var createdByTextView: TextView
    private lateinit var showTypeTextView: TextView
    private lateinit var showStatusTextView: TextView
    private lateinit var noReviewTextView: TextView
    private lateinit var mainTrailerTvShowsThumbnails: ArrayList<Trailer>
    private lateinit var trailorsRecyclerView: RecyclerView
//    var context: Context? = null
private var recyclerAdapterTVShowTrailer: RecyclerAdapterTVShowTrailer? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_info_tvshow, container, false)
//        context = activity
        aboutTvShowTextView = v.findViewById(R.id.aboutTvShowTextView)
        firstAirDateTextView = v.findViewById(R.id.firstAirDateTextView)
        lastAirDateTextView = v.findViewById(R.id.lastAirDateTextView)
        createdByTextView = v.findViewById(R.id.createdByTextView)
        showStatusTextView = v.findViewById(R.id.showStatusTextView)
        showTypeTextView = v.findViewById(R.id.showTypeTextView)
        noReviewTextView = v.findViewById(R.id.noTVTrailerTextView)
        trailorsRecyclerView = v.findViewById(R.id.trailorsTvShowRecyclerView)
        return v
    }

    fun setUIArguements(args: Bundle) {
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
            mainTrailerTvShowsThumbnails = args.getSerializable("TRAILER_THUMBNAILS") as ArrayList<Trailer>
            if (mainTrailerTvShowsThumbnails.size == 0) {
                noReviewTextView.visibility = View.VISIBLE
                noReviewTextView.text = "No Trailers are currently available."
            } else {
                val HorizontalManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                trailorsRecyclerView.layoutManager = HorizontalManager
                trailorsRecyclerView.addItemDecoration(HorizontalItemDecoration(dpToPx(requireContext(), 16), dpToPx(requireContext(), 6), dpToPx(requireContext(), 16)))
                recyclerAdapterTVShowTrailer = RecyclerAdapterTVShowTrailer(mainTrailerTvShowsThumbnails, requireContext())
                trailorsRecyclerView.adapter = recyclerAdapterTVShowTrailer
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