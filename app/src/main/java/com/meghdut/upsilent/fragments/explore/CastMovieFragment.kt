package com.meghdut.upsilent.fragments.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.R
import com.meghdut.upsilent.adapters.RecyclerViewAdapterMovieCast
import com.meghdut.upsilent.models.Cast
import java.util.*

/**
 * Created by Meghdut Mandal on 10/02/17.
 */
class CastMovieFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapterMovieCast: RecyclerViewAdapterMovieCast
    private lateinit var movieCastMain: ArrayList<Cast>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_cast_movie, container, false)
        recyclerView = v.findViewById(R.id.movieCastRecyclerview)
        return v
    }

    fun setUIArguements(args: Bundle) {
        this.run {
            movieCastMain = args.getSerializable("MOVIE_CAST") as ArrayList<Cast>
            recyclerViewAdapterMovieCast = RecyclerViewAdapterMovieCast(movieCastMain, requireActivity())
            recyclerView.adapter = recyclerViewAdapterMovieCast
            val verticalManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = verticalManager
        }
    }

    companion object {
        fun newInstance(): CastMovieFragment {
            return CastMovieFragment()
        }
    }
}