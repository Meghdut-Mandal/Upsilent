/*
 * Copyright (c) 2020 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package com.meghdut.upsilent.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.Color.alpha
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat
import androidx.core.graphics.ColorUtils
import com.meghdut.upsilent.R
import com.meghdut.upsilent.utils.getColorByAttr
import com.meghdut.upsilent.utils.shortAnimTime
import kotlin.math.roundToInt
import android.graphics.Color as AndroidColor


object CheckableItemBackground {
    // We need an <animated-selector> (AnimatedStateListDrawable) with an item drawable referencing
    // a ColorStateList that adds an alpha to our primary color, which is a theme attribute. We
    // currently don't have any compat handling for ColorStateList inside drawable on pre-23,
    // although AppCompatResources do have compat handling for inflating ColorStateList directly.
    // Note that the <selector>s used in Material Components are color resources, so they are
    // inflated as ColorStateList instead of StateListDrawable and don't have this problem.
    @SuppressLint("RestrictedApi")
    fun create(context: Context): Drawable =
        AnimatedStateListDrawableCompat().apply {
            val shortAnimTime = context.shortAnimTime
            setEnterFadeDuration(shortAnimTime)
            setExitFadeDuration(shortAnimTime)
            val primaryColor = context.getColorByAttr(R.attr.colorPrimary)
            val checkedColor = primaryColor.asColor().withModulatedAlpha(0.12f).value
            addState(intArrayOf(android.R.attr.state_checked), ColorDrawable(checkedColor))
            addState(intArrayOf(), ColorDrawable(TRANSPARENT))
        }
}
inline class Color(@ColorInt val value: Int)

fun Int.asColor(): Color = Color(this)

val Color.alpha: Int
    @IntRange(from = 0, to = 255)
    get() = AndroidColor.alpha(value)

fun Color.withAlpha(@IntRange(from = 0, to = 255) alpha: Int): Color =
        ColorUtils.setAlphaComponent(value, alpha).asColor()

fun Color.withModulatedAlpha(@FloatRange(from = 0.0, to = 1.0) alphaModulation: Float): Color {
    val alpha = (alpha * alphaModulation).roundToInt()
    return ((alpha shl 24) or (value and 0x00FFFFFF)).asColor()
}

val Color.red: Int
    @IntRange(from = 0, to = 255)
    get() = AndroidColor.red(value)

val Color.green: Int
    @IntRange(from = 0, to = 255)
    get() = AndroidColor.green(value)

val Color.blue: Int
    @IntRange(from = 0, to = 255)
    get() = AndroidColor.blue(value)

fun Color.compositeOver(background: Color): Color =
        ColorUtils.compositeColors(value, background.value).asColor()
