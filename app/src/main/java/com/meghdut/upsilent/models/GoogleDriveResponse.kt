package com.meghdut.upsilent.models

data class GoogleDriveResponse(
        val curPageIndex: Int,
        val `data`: Data,
        val nextPageToken: String?
)

data class Data(
        val files: List<DriveFile>
)