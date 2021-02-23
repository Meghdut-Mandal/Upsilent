package com.meghdut.upsilent.utils

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.roundToLong

/**
 * Created by Meghdut Mandal on 01/02/20.
 */
object AppUtil {
    fun isEmptyOrNullString(input: String?): Boolean {
        return input == null || input.trim { it <= ' ' }.isEmpty()
    }

    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }
}