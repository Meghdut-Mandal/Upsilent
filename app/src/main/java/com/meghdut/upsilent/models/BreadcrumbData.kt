

package com.meghdut.upsilent.models

import android.content.Context
import java.nio.file.Path

data class BreadcrumbData(
        val paths: List<Path>,
        val nameProducers: List<(Context) -> String>,
        val selectedIndex: Int
)
