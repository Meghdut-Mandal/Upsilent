package com.meghdut.upsilent.fragments.downloads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.meghdut.upsilent.R
import com.meghdut.upsilent.databinding.FragmentDownloadBinding
import com.meghdut.upsilent.databinding.LayoutNewDownloadBinding
import com.meghdut.upsilent.databinding.LayoutServerBinding
import com.meghdut.upsilent.fragments.drive.CurrentState
import com.meghdut.upsilent.fragments.drive.GenericAdapter
import com.meghdut.upsilent.fragments.drive.humanReadableByteCountSI
import com.meghdut.upsilent.models.Download
import com.meghdut.upsilent.models.DownloadStatus
import com.meghdut.upsilent.models.UpsilentServer

class DownloadFragment : Fragment() {

    lateinit var fragmentDownloadBinding: FragmentDownloadBinding
    private val downloadsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DownloadsViewModel::class.java)
    }
    private lateinit var downloadsFragmentPageAdapter: DownloadsFragmentPageAdapter
    private val serverAdapter = GenericAdapter(LayoutServerBinding::inflate, ::itemHandler)


    private fun itemHandler(server: UpsilentServer, serverBinding: LayoutServerBinding) {
        val sysInfo = server.sysInfo
        val ic = when {
            sysInfo.osName.contains("Ubuntu") -> R.drawable.ubuntu_ic
            sysInfo.osName.contains("Windows") -> R.drawable.windows_ic
            else -> R.drawable.linux_ic
        }
        serverBinding.serverIcon.setImageResource(ic)
        serverBinding.serverName.text = "${sysInfo.osName} ${if (sysInfo.osName != "Windows 10") sysInfo.osVersion else ""}"
        serverBinding.serverArch.text = sysInfo.osArch
        val diskFree = humanReadableByteCountSI(sysInfo.freeDiskSpace)
        val ramFree = ((sysInfo.freeRamSpace * 100) / sysInfo.totalRamSpace)
        serverBinding.ramTv.text = "$ramFree% "
        serverBinding.spaceTv.text = "$diskFree"
        serverBinding.root.setOnClickListener {
            downloadsViewModel.loadDownloads(server.serverId)
            snackBar("Server Selected")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        fragmentDownloadBinding = FragmentDownloadBinding.inflate(inflater)
        return fragmentDownloadBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        downloadsFragmentPageAdapter = DownloadsFragmentPageAdapter(this)
        fragmentDownloadBinding.apply {
            downloadsViewpager.adapter = downloadsFragmentPageAdapter
            TabLayoutMediator(downloadTabs, downloadsViewpager) { tab, position ->
            }
            addDownloadButton.setOnClickListener {
                openPopUp()
            }
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            serverRecyclerView.layoutManager = linearLayoutManager
            serverRecyclerView.adapter = serverAdapter
        }

        downloadsViewModel.serversLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is CurrentState.Success -> {
                    serverAdapter.submitList(it.result)
                }
                is CurrentState.Error -> {
                    snackBar("Failed to load Servers")
                }
            }
        }


    }


    private fun openPopUp() {
        MaterialDialog(requireContext(), BottomSheet()).show {
            customView(R.layout.layout_new_download)
            cornerRadius(16f)
            LayoutNewDownloadBinding.bind(getCustomView()).apply {
                addButton.setOnClickListener {
                    val title = nameDownload.text.toString()
                    val magnet = magnetDownload.text.toString()
                    when {
                        magnet.isBlank() -> {
                            snackBar("The magnet link can't be empty")
                        }
                        title.isBlank() -> {
                            snackBar("The title can't be empty")
                        }
                        else -> {
                            downloadsViewModel.submitDownload(title, magnet)
                        }
                    }
                }
            }
        }
    }


    private fun snackBar(error: String) {
        Snackbar.make(fragmentDownloadBinding.serverRecyclerView, error, Snackbar.LENGTH_LONG).show()
    }

}