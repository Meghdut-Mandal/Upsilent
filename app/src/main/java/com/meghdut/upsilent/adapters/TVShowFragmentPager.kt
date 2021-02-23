package com.meghdut.upsilent.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.meghdut.upsilent.fragments.CastTVShowFragment
import com.meghdut.upsilent.fragments.InfoAboutTVShowFragment
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Created by Meghdut Mandal on 09/02/17.
 */
class TVShowFragmentPager(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    private val map = HashMap<Int, Fragment>()
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                map[position] = InfoAboutTVShowFragment.newInstance()
                return map[position]!!
            }
            1 -> {
                map[position] = CastTVShowFragment.newInstance()
                return map[position]!!
            }
        }
        throw IllegalArgumentException("Number cant exceed 1")
    }

    override fun getCount(): Int {
        return 2
    }

    fun function(position: Int): Fragment? {
        return map[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "INFO"
            1 -> return "ACTORS"
        }
        return null
    }
}