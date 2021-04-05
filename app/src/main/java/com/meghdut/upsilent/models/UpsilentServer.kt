package com.meghdut.upsilent.models

data class UpsilentServer(
        val serverId: String,
        var lastCheckedTime: Long,
        val isPrivate: Boolean,
        val sysInfo: SysInfo
) {
    constructor() : this("", 0, false, SysInfo())
}

data class SysInfo(
        val osName: String,
        val osVersion: String,
        val osArch: String,
        val cores: Int,
        val totalDiskSpace: Long,
        val freeDiskSpace: Long,
        val totalRamSpace: Long,
        val freeRamSpace: Long
) {
    constructor() : this("", "", "", 3, 0, 0, 0, 0)
}
