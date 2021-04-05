package com.meghdut.upsilent.utils

import android.view.View

var View.layoutInNavigation: Boolean
    get() = systemUiVisibility.hasBits(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    set(value) {
        systemUiVisibility = if (value) {
            systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        } else {
            systemUiVisibility andInv View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }


fun Int.hasBits(bits: Int): Boolean = this and bits == bits

infix fun Int.andInv(other: Int): Int = this and other.inv()

