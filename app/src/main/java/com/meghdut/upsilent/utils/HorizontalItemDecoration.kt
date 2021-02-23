package com.meghdut.upsilent.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Created by Meghdut Mandal on 01/02/20.
 */
class HorizontalItemDecoration : ItemDecoration {
    private var horizontalSpacing = 0
    private var start = 0
    private var middle = 0
    private var end = 0
    private val type: Int

    constructor(horizontalSpacing: Int) {
        this.horizontalSpacing = horizontalSpacing
        type = 0
    }

    constructor(start: Int, middle: Int, end: Int) {
        this.start = start
        this.end = end
        this.middle = middle
        type = 1
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (type == 0) {
            if (position != state.itemCount - 1) {
                outRect.right = horizontalSpacing
            }
        } else if (type == 1) {
            //First Item
            if (position == 0) {
                outRect.left = start
                if (state.itemCount == 1) {
                    //Single Item
                    outRect.right = end
                } else {
                    outRect.right = middle
                }
            } else if (position == state.itemCount - 1) {
                //Last Item
                outRect.right = end
            } else {
                outRect.right = middle
            }
        }
    }
}