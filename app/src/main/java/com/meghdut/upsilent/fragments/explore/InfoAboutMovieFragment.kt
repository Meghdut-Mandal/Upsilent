package com.meghdut.upsilent.fragments.explore

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meghdut.upsilent.adapters.RecyclerAdapterMovieTrailer
import com.meghdut.upsilent.adapters.RecyclerAdapterSimilarMovies
import com.meghdut.upsilent.databinding.FragmentInfoMovieBinding
import com.meghdut.upsilent.models.Movie
import com.meghdut.upsilent.models.Trailer
import com.meghdut.upsilent.utils.AppUtil.dpToPx
import com.meghdut.upsilent.utils.HorizontalItemDecoration
import java.util.*

/**
 * Created by Meghdut Mandal on 24/01/17.
 */
class InfoAboutMovieFragment : Fragment() {
//    private lateinit var abouFilmTextView: TextView
//    private lateinit var releasedTextView: TextView
//    private lateinit var budgetTextView: TextView
//    private lateinit var noReviewMovieTextView: TextView
//    private lateinit var noSimilarMoviesTextView: TextView
//    private lateinit var revenueTextView: TextView
//    private lateinit var trailorsRecyclerView: RecyclerView
//    private lateinit var similarMoviesRecyclerView: RecyclerView
    private var recyclerAdapterMovieTrailer: RecyclerAdapterMovieTrailer? = null
    private var recyclerAdapterSimilarMovies: RecyclerAdapterSimilarMovies? = null
    private var mainSimilarMovies: ArrayList<Movie>? = null
    private var mainTrailerMoviesThumbnails: ArrayList<Trailer>? = null
    private var infoAboutMovieFragmentListener: InfoAboutMovieFragmentListener? = null

    private var _binding : FragmentInfoMovieBinding?= null
    private val binding get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        infoAboutMovieFragmentListener = context as InfoAboutMovieFragmentListener
    }

    interface InfoAboutMovieFragmentListener {
        fun onSeeAllSimilarMoviesClicked()
    }

    fun setInfoAboutMovieFragmentListener(listener: InfoAboutMovieFragmentListener?) {
        infoAboutMovieFragmentListener = listener
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
////        context = activity
//        val v = inflater.inflate(R.layout.fragment_info_movie, container, false)
//        abouFilmTextView = v.findViewById(R.id.aboutFilmTextView)
//        releasedTextView = v.findViewById(R.id.releasedTextView)
//        budgetTextView = v.findViewById(R.id.budgetTextView)
//        noReviewMovieTextView = v.findViewById(R.id.noReviewMovieTextView)
//        revenueTextView = v.findViewById(R.id.revenueTextView)
//        noSimilarMoviesTextView = v.findViewById(R.id.noSimilarMoviesTextView)
//        trailorsRecyclerView = v.findViewById(R.id.trailorsRecyclerView)
//        similarMoviesRecyclerView = v.findViewById(R.id.similarMoviesRecyclerView)
//        val seeAlltextViewMovieInfofragment = v.findViewById<TextView>(R.id.seeAllTextViewMovieInfoFragment)
//        seeAlltextViewMovieInfofragment.setOnClickListener { v1: View? -> infoAboutMovieFragmentListener!!.onSeeAllSimilarMoviesClicked() }
//        return v
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentInfoMovieBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun setUIArguements(args: Bundle) {
        requireActivity().runOnUiThread {
            if (args.getBoolean("INFO")) {
                binding.aboutFilmTextView.text = args.getString("OVERVIEW")
                val releaseDate = dateGenerator(args.getString("RELEASE_DATE"))
                binding.releasedTextView.text = releaseDate
                val budget = "£" + args.getLong("BUDGET")
                binding.budgetTextView.text = budget
                val revenue = "£" + args.getLong("REVENUE")
                binding.revenueTextView.text = revenue
                mainTrailerMoviesThumbnails = args.getSerializable("TRAILER_THUMBNAILS") as ArrayList<Trailer>?
                if (mainTrailerMoviesThumbnails!!.size == 0) {
                    binding.noReviewMovieTextView.visibility = View.VISIBLE
                    binding.noReviewMovieTextView.text = "No Trailers are currently available."
                } else {
                    recyclerAdapterMovieTrailer = RecyclerAdapterMovieTrailer(mainTrailerMoviesThumbnails, requireContext())
                    val HorizontalManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.trailorsRecyclerView.addItemDecoration(HorizontalItemDecoration(dpToPx(requireContext(), 16), dpToPx(requireContext(), 6), dpToPx(requireContext(), 16)))
                    binding.trailorsRecyclerView.layoutManager = HorizontalManager
                    binding.trailorsRecyclerView.adapter = recyclerAdapterMovieTrailer
                }
            } else if (args.getBoolean("SIMILAR")) {
                mainSimilarMovies = args.getSerializable("SIMILAR_MOVIES") as ArrayList<Movie>?
                if (mainSimilarMovies!!.size == 0) {
                    binding.noSimilarMoviesTextView.visibility = View.VISIBLE
                    binding.noSimilarMoviesTextView.text = "No Similar Movies are currently available."
                } else {
                    recyclerAdapterSimilarMovies = RecyclerAdapterSimilarMovies(mainSimilarMovies, requireContext())
                    val HorizontalManager1 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.similarMoviesRecyclerView.addItemDecoration(HorizontalItemDecoration(dpToPx(requireContext(), 16), dpToPx(requireContext(), 6), dpToPx(requireContext(), 16)))
                    binding.similarMoviesRecyclerView.layoutManager = HorizontalManager1
                    binding.similarMoviesRecyclerView.adapter = recyclerAdapterSimilarMovies
                }
            }
        }
    }

    private fun dateGenerator(date: String?): String {
        return if (date!!.length == 9 || date.length == 10) {
            val month = date.substring(5, 7)
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
            ans + " " + date.substring(8, 10) + "," + " " + date.substring(0, 4)
        } else "NA"
    }

    companion object {
        fun newInstance(): InfoAboutMovieFragment {
            return InfoAboutMovieFragment()
        }
    }
}