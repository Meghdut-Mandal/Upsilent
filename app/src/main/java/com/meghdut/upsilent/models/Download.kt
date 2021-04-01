package com.meghdut.upsilent.models

data class Download(
        val id: String,
        val title: String,
        val magnet:String,
        var downloadProgress: DownloadProgress?,
        var status: String
)


object DownloadStatus {
    const val NOT_ASSIGNED="not_assigned"
    const val STARTED = "started"
    const val ONGOING = "ongoing"
    const val COMPLETED = "completed"
    const val ERROR = "error"
}
