package com.meghdut.upsilent.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.meghdut.upsilent.fragments.explore.MoviesFragment
import com.meghdut.upsilent.fragments.explore.TVShowsFragment

/**
 * Created by Meghdut Mandal on 06/02/17.
 */
class MainFragmentPager(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Movies"
            1 -> return "TV Shows"
        }
        return null
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return MoviesFragment()
            1 -> return TVShowsFragment()
        }
        throw  IllegalArgumentException("The number cant exceed 1")
    }
}