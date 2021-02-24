package com.meghdut.upsilent.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.meghdut.upsilent.fragments.explore.CastMovieFragment
import com.meghdut.upsilent.fragments.explore.InfoAboutMovieFragment
import com.meghdut.upsilent.fragments.explore.ReviewsFragment
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Created by Meghdut Mandal on 25/01/17.
 */
class FragmentPager(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    private var map = HashMap<Int, Fragment>()
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment?
        when (position) {
            0 -> {
                fragment = map[position]
                return if (fragment == null) {
                    fragment = InfoAboutMovieFragment.newInstance()
                    map[position] = fragment
                    fragment
                } else {
                    fragment
                }
            }
            1 -> {
                fragment = map[position]
                return if (fragment == null) {
                    fragment = CastMovieFragment.newInstance()
                    map[position] = fragment
                    fragment
                } else {
                    fragment
                }
            }
            2 -> {
                fragment = map[position]
                return if (fragment == null) {
                    fragment = ReviewsFragment.newInstance()
                    map[position] = fragment
                    fragment
                } else {
                    fragment
                }
            }
        }
        throw IllegalArgumentException("Number cant exceed range!!")
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "INFO"
            1 -> return "CAST"
            2 -> return "REVIEWS"
        }
        return null
    }

    fun function(position: Int): Fragment {
        val fragment = map[position]
        return fragment ?: getItem(position)
    }
}