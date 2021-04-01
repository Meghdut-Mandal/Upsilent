package com.meghdut.upsilent.models

data class UpsilentServer(
    val serverId: String,
    var lastCheckedTime: Long,
    val isPrivate: Boolean
)
