package com.meghdut.upsilent.fragments.drive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.meghdut.upsilent.R
import com.meghdut.upsilent.databinding.FileItemBinding
import com.meghdut.upsilent.databinding.FragmentDrive2Binding
import com.meghdut.upsilent.models.DriveFile
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.text.StringCharacterIterator
import java.util.*


class DriveFragment : Fragment() {

    private lateinit var binding: FragmentDrive2Binding
    private val driveViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DriveViewModel::class.java)
    }

    private val driveAdapter = GenericAdapter(FileItemBinding::inflate, ::driveItemHandler)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentDrive2Binding.inflate(inflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = driveAdapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.title = "My Drive"
        driveViewModel.explorePath("")
        driveViewModel.fileList
        driveViewModel.fileList.observe(viewLifecycleOwner) {
            if (it != null) {
                driveAdapter.submitList(it)
                if (driveViewModel.folderPath.isNotEmpty())
                    binding.toolbar.subtitle = driveViewModel.folderPath.last()
            }
        }
        driveViewModel.currentState.observe(viewLifecycleOwner) {
            when (it) {
                is CurrentState.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is CurrentState.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is CurrentState.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Snackbar.make(binding.recyclerView, "Error ${it.exception.localizedMessage}", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            driveViewModel.goBack()
        }


    }

    private fun driveItemHandler(item: DriveFile, fileBinding: FileItemBinding) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val toDateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        val date = dateFormat.parse(item.modifiedTime)
        fileBinding.nameText.text = item.name
        fileBinding.descriptionText.text = "${toDateFormat.format(date)}  ${humanReadableByteCountSI(item.size?.toLong())}"

        when {
            item.thumbnailLink != null -> Picasso.get().load(item.thumbnailLink).fit().into(fileBinding.thumbnailImage)
            item.mimeType == "application/vnd.google-apps.folder" -> fileBinding.thumbnailImage.setImageResource(R.drawable.folder_ic)
            item.mimeType.contains("video") -> {
                fileBinding.thumbnailImage.setImageResource(R.drawable.file_video_icon)
            }
        }
        fileBinding.badgeImage.visibility = View.GONE
        fileBinding.root.setOnClickListener {
            driveViewModel.explorePath(item.name)
        }
    }

    private fun humanReadableByteCountSI(size: Long?): String {
        if (size == null) return ""
        var bytes = size
        if (-1000 < bytes && bytes < 1000) {
            return "$bytes B"
        }
        val ci = StringCharacterIterator("kMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }
        return java.lang.String.format(Locale.getDefault(), "%.1f %cB", bytes / 1000.0, ci.current())
    }

}