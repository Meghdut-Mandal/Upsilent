package com.meghdut.upsilent.fragments.downloads

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.meghdut.upsilent.fragments.drive.CurrentState
import com.meghdut.upsilent.models.Download
import com.meghdut.upsilent.models.DownloadStatus
import com.meghdut.upsilent.models.UpsilentServer

class DownloadsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = Firebase.database

    private var serverId: String = ""
    val downloadData = MutableLiveData<CurrentState<List<Download>>>()
    val serversLiveData = MutableLiveData<CurrentState<List<UpsilentServer>>>()

    init {
        database.getReference("servers").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.mapNotNull { it.getValue(UpsilentServer::class.java) }
                serversLiveData.postValue(CurrentState.Success(list))
            }
            override fun onCancelled(error: DatabaseError) {
                serversLiveData.postValue(CurrentState.Error(error.toException()))
            }
        })
    }


    fun loadDownloads(serverId: String) {
        this.serverId = serverId
        downloadData.postValue(CurrentState.Loading)
        database.getReference("downloads/$serverId").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val downloadList = snapshot.children.mapNotNull { it.getValue(Download::class.java) }.toList()
                downloadData.postValue(CurrentState.Success(downloadList))
            }

            override fun onCancelled(error: DatabaseError) {
                downloadData.postValue(CurrentState.Error(error.toException()))
            }
        })
    }


    fun submitDownload(title: String, magnet: String) {
        val download = Download(System.currentTimeMillis().toString(), title, magnet, null, DownloadStatus.NOT_ASSIGNED)
        database.getReference("downloads/$serverId/${download.id}").setValue(download)
    }

}