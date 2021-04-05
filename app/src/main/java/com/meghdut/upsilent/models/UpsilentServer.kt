package com.meghdut.upsilent.models

import com.meghdut.upsilent.utils.Identifiable

data class UpsilentServer(
        val serverId: String,
        var lastCheckedTime: Long,
        val isPrivate: Boolean,
        val sysInfo: SysInfo
): Identifiable {
    constructor() : this("", 0, false, SysInfo())
    override val id: String
        get() = serverId
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
