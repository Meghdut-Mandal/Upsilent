package com.meghdut.upsilent.fragments.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meghdut.upsilent.adapters.RecyclerViewAdapterTVShow
import com.meghdut.upsilent.databinding.FragmentTvshowsBinding
import com.meghdut.upsilent.network.ApiService
import com.meghdut.upsilent.network.TVShowResponse
import com.meghdut.upsilent.network.URLConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Meghdut Mandal on 07/02/17.
 */
class TVShowsFragment : Fragment() {
//    private lateinit var recyclerView: RecyclerView
    lateinit var allTvShows: Array<TVShowResponse?>
    var recyclerViewAdapterTVShow: RecyclerViewAdapterTVShow? = null

    private var _binding : FragmentTvshowsBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //  val v = inflater.inflate(R.layout.fragment_tvshows, container, false)
        _binding= FragmentTvshowsBinding.inflate(inflater,container,false)

        //  recyclerView = v.findViewById(R.id.activityMainVerticalRecyclerView)
        allTvShows = arrayOfNulls(4)
        recyclerViewAdapterTVShow = RecyclerViewAdapterTVShow(allTvShows, requireActivity())
        binding.activityMainVerticalRecyclerView.adapter = recyclerViewAdapterTVShow
        val verticalManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.activityMainVerticalRecyclerView.layoutManager = verticalManager
        val retrofit = Retrofit.Builder()
                .baseUrl(URLConstants.TVSHOW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiService::class.java)
        val call = service.getAiringToday(URLConstants.API_KEY, 1)
        call.enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                val tvShowList = response.body()!!.tvShows
                val airingtoday = TVShowResponse()
                airingtoday.tvShows = tvShowList
                allTvShows[0] = airingtoday
                recyclerViewAdapterTVShow!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {}
        })
        val call1 = service.getOnAir(URLConstants.API_KEY, 1)
        call1.enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                val tvShowList = response.body()!!.tvShows
                val onAir = TVShowResponse()
                if (tvShowList == null) {
                    return
                }
                onAir.tvShows = tvShowList
                allTvShows[1] = onAir
                recyclerViewAdapterTVShow!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {}
        })
        val call2 = service.getPopular(URLConstants.API_KEY, 1)
        call2.enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                val tvShowList = response.body()!!.tvShows
                val popular = TVShowResponse()
                if (tvShowList == null) {
                    return
                }
                popular.tvShows = tvShowList
                allTvShows[2] = popular
                recyclerViewAdapterTVShow!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {}
        })
        val call3 = service.getTopRated(URLConstants.API_KEY, 1)
        call3.enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                val tvShowList = response.body()!!.tvShows
                val topRated = TVShowResponse()
                if (tvShowList == null) {
                    return
                }
                topRated.tvShows = tvShowList
                allTvShows[3] = topRated
                recyclerViewAdapterTVShow!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {}
        })
        return binding.root
    }
}