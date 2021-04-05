package com.meghdut.upsilent.fragments.downloads

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.meghdut.upsilent.fragments.downloads.DownloadListFragment.Companion.DOWNLOAD_TYPE
import com.meghdut.upsilent.models.DownloadStatus

class DownloadsFragmentPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    val downloadTypes = listOf(DownloadStatus.NOT_ASSIGNED + " " + DownloadStatus.ONGOING + " " + DownloadStatus.STARTED,
            DownloadStatus.COMPLETED,
            DownloadStatus.ERROR)

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = DownloadListFragment()
        fragment.arguments = Bundle().apply {
            putString(DOWNLOAD_TYPE, downloadTypes[position])
        }
        return fragment
    }

    fun getName(position: Int) = downloadTypes[position]
}