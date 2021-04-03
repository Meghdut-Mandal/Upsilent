package com.meghdut.upsilent.models

data class DriveFile(
        val mimeType: String,
        val modifiedTime: String,
        val name: String,
        val size: String?,
        val thumbnailLink: String?
)