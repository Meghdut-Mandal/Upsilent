package com.meghdut.upsilent.fragments.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meghdut.upsilent.adapters.RecyclerViewAdapterTVShowCast
import com.meghdut.upsilent.databinding.FragmentCastTvshowBinding
import com.meghdut.upsilent.models.Cast
import java.util.*

/**
 * Created by Meghdut Mandal on 18/03/17.
 */
class CastTVShowFragment : Fragment() {
    private lateinit var recyclerViewAdapterTVShowCast: RecyclerViewAdapterTVShowCast
    private lateinit var tvShowCastMain: ArrayList<Cast>

    private var _binding : FragmentCastTvshowBinding?= null
    private val binding get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentCastTvshowBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    fun setUIArguements(args: Bundle) {
       requireActivity().runOnUiThread {
           tvShowCastMain = args.getSerializable("TV_SHOW_CAST") as ArrayList<Cast>
            if (tvShowCastMain.size == 0) {
                binding.noCastTextView.visibility = View.VISIBLE
                binding.noCastTextView.text = "No cast is currently available for this show."
            } else {
                recyclerViewAdapterTVShowCast = RecyclerViewAdapterTVShowCast(tvShowCastMain, requireContext())
                binding.tvShowCastRecyclerView.adapter = recyclerViewAdapterTVShowCast
                val verticalManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.tvShowCastRecyclerView.layoutManager = verticalManager
            }
        }
    }

    companion object {
        fun newInstance(): CastTVShowFragment {
            return CastTVShowFragment()
        }
    }
}