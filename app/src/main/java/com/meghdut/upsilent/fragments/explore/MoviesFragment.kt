package com.meghdut.upsilent.fragments.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.upsilent.R
import com.meghdut.upsilent.adapters.RecyclerViewAdapterMain
import com.meghdut.upsilent.network.MovieResponse

/**
 * Created by Meghdut Mandal on 06/02/17.
 */
class MoviesFragment : Fragment() {
    private val allMovies: ArrayList<MovieResponse> = arrayListOf()

    private val moviesViewModel by lazy { ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView: RecyclerView = requireView().findViewById(R.id.activityMainVerticalRecyclerView)
        val recyclerViewAdapterMain = RecyclerViewAdapterMain(allMovies, requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = recyclerViewAdapterMain
        moviesViewModel.moviesLiveData.observe(viewLifecycleOwner) {
            allMovies.add(it)
            recyclerViewAdapterMain.notifyDataSetChanged()
        }
        moviesViewModel.loadData()
    }

}