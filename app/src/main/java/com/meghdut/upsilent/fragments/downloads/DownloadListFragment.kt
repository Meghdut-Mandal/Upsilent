package com.meghdut.upsilent.fragments.downloads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.meghdut.upsilent.databinding.FragementDownloadListBinding
import com.meghdut.upsilent.databinding.LayoutDownloadBinding
import com.meghdut.upsilent.fragments.drive.CurrentState
import com.meghdut.upsilent.fragments.drive.GenericAdapter
import com.meghdut.upsilent.fragments.drive.humanReadableByteCountSI
import com.meghdut.upsilent.models.Download


class DownloadListFragment : Fragment() {

    lateinit var listBinding: FragementDownloadListBinding

    private val downloadsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DownloadsViewModel::class.java)
    }
    private val downloadAdapter = GenericAdapter(LayoutDownloadBinding::inflate, ::itemHandler)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        listBinding = FragementDownloadListBinding.inflate(inflater)
        return listBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listBinding.downloadList.layoutManager = LinearLayoutManager(context)
        listBinding.downloadList.adapter = downloadAdapter
        arguments?.takeIf { it.containsKey(DOWNLOAD_TYPE) }?.apply {
            val dnwldType = getString(DOWNLOAD_TYPE) ?: ""
            downloadsViewModel.downloadData.observe(viewLifecycleOwner) { downloadState ->
                when {
                    downloadState is CurrentState.Success -> {
                        downloadAdapter.submitList(downloadState.result.filter { dnwldType.contains(it.status) })
                    }
                    downloadState is CurrentState.Error -> {
                        println("Error!! ")
                    }

                }
            }
        }

    }

    private fun itemHandler(download: Download, layoutDownloadBinding: LayoutDownloadBinding) {
        layoutDownloadBinding.downloadTitle.text = download.title
        val sizeStatus = "Downloaded ${humanReadableByteCountSI(download.downloadProgress?.downloaded)} "
        val timeStatus = "Elapsed Time ${download.downloadProgress?.elapsedTime}"
        layoutDownloadBinding.downloadTime.text = timeStatus
        layoutDownloadBinding.downloadProgress.text = sizeStatus
    }


    companion object {
        const val DOWNLOAD_TYPE = "download_type"
    }
}