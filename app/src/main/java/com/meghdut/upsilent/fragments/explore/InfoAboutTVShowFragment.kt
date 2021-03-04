package com.meghdut.upsilent.fragments.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meghdut.upsilent.adapters.RecyclerAdapterTVShowTrailer
import com.meghdut.upsilent.databinding.FragmentInfoTvshowBinding
import com.meghdut.upsilent.models.TVShowsCreaters
import com.meghdut.upsilent.models.Trailer
import com.meghdut.upsilent.utils.AppUtil.dpToPx
import com.meghdut.upsilent.utils.HorizontalItemDecoration
import java.util.*

/**
 * Created by Meghdut Mandal on 09/02/17.
 */
class InfoAboutTVShowFragment : Fragment() {
//    private lateinit var aboutTvShowTextView: TextView
//    private lateinit var firstAirDateTextView: TextView
//    private lateinit var lastAirDateTextView: TextView
//    private lateinit var createdByTextView: TextView
//    private lateinit var showTypeTextView: TextView
//    private lateinit var showStatusTextView: TextView
//    private lateinit var noReviewTextView: TextView
//    private lateinit var trailorsRecyclerView: RecyclerView

    private lateinit var mainTrailerTvShowsThumbnails: ArrayList<Trailer>
    private var _binding : FragmentInfoTvshowBinding?= null
    private val binding get() = _binding!!


//    var context: Context? = null
private var recyclerAdapterTVShowTrailer: RecyclerAdapterTVShowTrailer? = null
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val v = inflater.inflate(R.layout.fragment_info_tvshow, container, false)
////        context = activity
//        aboutTvShowTextView = v.findViewById(R.id.aboutTvShowTextView)
//        firstAirDateTextView = v.findViewById(R.id.firstAirDateTextView)
//        lastAirDateTextView = v.findViewById(R.id.lastAirDateTextView)
//        createdByTextView = v.findViewById(R.id.createdByTextView)
//        showStatusTextView = v.findViewById(R.id.showStatusTextView)
//        showTypeTextView = v.findViewById(R.id.showTypeTextView)
//        noReviewTextView = v.findViewById(R.id.noTVTrailerTextView)
//        trailorsRecyclerView = v.findViewById(R.id.trailorsTvShowRecyclerView)
//        return v
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentInfoTvshowBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    fun setUIArguements(args: Bundle) {
        requireActivity().runOnUiThread {
            binding.aboutTvShowTextView.text = args.getString("OVERVIEW")
            val firstAirDate = dateGenerator(args.getString("FIRST_AIR_DATE"))
            binding.firstAirDateTextView.text = firstAirDate
            val lastAirDate = dateGenerator(args.getString("LAST_AIR_DATE"))
            binding.lastAirDateTextView.text = lastAirDate
            val obj = args.getSerializable("CREATORS") as ArrayList<TVShowsCreaters>?
            for (i in obj!!.indices) {
                if (i < obj.size - 1) binding.createdByTextView.append(obj[i].name + ", ") else binding.createdByTextView.append(obj[i].name)
            }
            binding.showTypeTextView.text = args.getString("SHOW_TYPE")
            binding.showStatusTextView.text = args.getString("STATUS")
            mainTrailerTvShowsThumbnails = args.getSerializable("TRAILER_THUMBNAILS") as ArrayList<Trailer>
            if (mainTrailerTvShowsThumbnails.size == 0) {
                binding.noTVTrailerTextView.visibility = View.VISIBLE
                binding.noTVTrailerTextView.text = "No Trailers are currently available."
            } else {
                val HorizontalManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.trailorsTvShowRecyclerView.layoutManager = HorizontalManager
                binding.trailorsTvShowRecyclerView.addItemDecoration(HorizontalItemDecoration(dpToPx(requireContext(), 16), dpToPx(requireContext(), 6), dpToPx(requireContext(), 16)))
                recyclerAdapterTVShowTrailer = RecyclerAdapterTVShowTrailer(mainTrailerTvShowsThumbnails, requireContext())
                binding.trailorsTvShowRecyclerView.adapter = recyclerAdapterTVShowTrailer
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