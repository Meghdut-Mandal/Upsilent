package com.meghdut.upsilent.fragments.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meghdut.upsilent.adapters.RecyclerViewAdapterMovieCast
import com.meghdut.upsilent.databinding.FragmentCastMovieBinding
import com.meghdut.upsilent.models.Cast
import java.util.*

/**
 * Created by Meghdut Mandal on 10/02/17.
 */
class CastMovieFragment : Fragment() {
    private lateinit var recyclerViewAdapterMovieCast: RecyclerViewAdapterMovieCast
    private lateinit var movieCastMain: ArrayList<Cast>
    private lateinit var binding : FragmentCastMovieBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentCastMovieBinding.inflate(inflater,container,false)
        return binding.root

    }

    fun setUIArguements(args: Bundle) {
        this.run {
            movieCastMain = args.getSerializable("MOVIE_CAST") as ArrayList<Cast>

            recyclerViewAdapterMovieCast = RecyclerViewAdapterMovieCast(movieCastMain, requireActivity())
            binding.movieCastRecyclerview.adapter = recyclerViewAdapterMovieCast
            val verticalManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.movieCastRecyclerview.layoutManager = verticalManager
        }
    }

    companion object {
        fun newInstance(): CastMovieFragment {
            return CastMovieFragment()
        }
    }
}