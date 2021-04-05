package com.meghdut.upsilent.models

import com.meghdut.upsilent.utils.Identifiable

data class Download(
        override val id: String,
        val title: String,
        val magnet: String,
        var downloadProgress: DownloadProgress?,
        var status: String
) :Identifiable{
    @Suppress("unused")
    constructor() : this("", "", "", null, "")
}


object DownloadStatus {
    const val NOT_ASSIGNED = "not_assigned"
    const val STARTED = "started"
    const val ONGOING = "ongoing"
    const val COMPLETED = "completed"
    const val ERROR = "error"
}
