package com.meghdut.upsilent.fragments.drive

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.meghdut.upsilent.ExoPlayerActivity
import com.meghdut.upsilent.MEDIA_URL
import com.meghdut.upsilent.R
import com.meghdut.upsilent.databinding.FileItemBinding
import com.meghdut.upsilent.databinding.FragmentDrive2Binding
import com.meghdut.upsilent.models.DriveFile
import com.meghdut.upsilent.network.URLConstants.DRIVE_ROOT
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
        binding.swipeRefreshLayout.setOnRefreshListener {
            driveViewModel.refreshList()
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
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
            item.mimeType.contains("text") -> {
                fileBinding.thumbnailImage.setImageResource(R.drawable.file_text_icon)
            }
            item.mimeType == "application/vnd.google-apps.folder" -> fileBinding.thumbnailImage.setImageResource(R.drawable.folder_ic)
            item.mimeType == "application/pdf" -> {
                fileBinding.thumbnailImage.setImageResource(R.drawable.file_pdf_icon)
            }

            item.thumbnailLink != null -> {
                Picasso.get().load(item.thumbnailLink).fit().into(fileBinding.thumbnailImage)
            }
            item.mimeType.contains("video") -> {
                fileBinding.thumbnailImage.setImageResource(R.drawable.file_video_icon)
            }
        }
        fileBinding.badgeImage.visibility = View.GONE
        fileBinding.root.setOnClickListener {
            if (item.mimeType == "application/vnd.google-apps.folder") {
                driveViewModel.explorePath(item.name)
            }
            if ((item.mimeType.contains("video"))) {
                println("ITs a video ")
                val intent = Intent(requireActivity(), ExoPlayerActivity::class.java)
                intent.putExtra(MEDIA_URL, DRIVE_ROOT + driveViewModel.getRelativePath(item.name).substring(1))
                startActivity(intent)
            }
        }
        fileBinding.menuButton.setOnClickListener {
            openPopUp(item, it)
        }

    }

    private fun openPopUp(item: DriveFile, view: View) {
        val popup = PopupMenu(requireActivity(), view)
        popup.menuInflater.inflate(R.menu.file_popup_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.file_copy_link -> {
                    val clipboardManager = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText(item.name, DRIVE_ROOT + driveViewModel.getRelativePath(item.name))
                    clipboardManager.setPrimaryClip(clip)
                }

                R.id.file_open_external -> {
                    val uri = Uri.parse(DRIVE_ROOT + driveViewModel.getRelativePath(item.name))
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, item.mimeType)
                    startActivity(intent)
                }

                R.id.file_share -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, item.name)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, DRIVE_ROOT + driveViewModel.getRelativePath(item.name))
                    requireActivity().startActivity(Intent.createChooser(shareIntent, "Choose App"))
                }

            }

            return@setOnMenuItemClickListener false
        }
        popup.show()

    }


}

fun humanReadableByteCountSI(size: Long?): String {
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
