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
import com.meghdut.upsilent.adapters.RecyclerViewAdapterTVShowCast
import com.meghdut.upsilent.models.Cast
import java.util.*

/**
 * Created by Meghdut Mandal on 18/03/17.
 */
class CastTVShowFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapterTVShowCast: RecyclerViewAdapterTVShowCast
    private lateinit var tvShowCastMain: ArrayList<Cast>
    private lateinit var noCastTextView: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_cast_tvshow, container, false)
        recyclerView = v.findViewById(R.id.tvShowCastRecyclerView)
        noCastTextView = v.findViewById(R.id.noCastTextView)
        tvShowCastMain = ArrayList()
        return v
    }

    fun setUIArguements(args: Bundle) {
       requireActivity().runOnUiThread {
            tvShowCastMain = args.getSerializable("TV_SHOW_CAST") as ArrayList<Cast>
            if (tvShowCastMain.size == 0) {
                noCastTextView.visibility = View.VISIBLE
                noCastTextView.text = "No cast is currently available for this show."
            } else {
                recyclerViewAdapterTVShowCast = RecyclerViewAdapterTVShowCast(tvShowCastMain, requireContext())
                recyclerView.adapter = recyclerViewAdapterTVShowCast
                val verticalManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView.layoutManager = verticalManager
            }
        }
    }

    companion object {
        fun newInstance(): CastTVShowFragment {
            return CastTVShowFragment()
        }
    }
}