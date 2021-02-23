package com.meghdut.upsilent

import android.view.View

/**
 * Created by Meghdut Mandal on 18/01/17.
 */
interface OnRecyclerViewItemClickListener {
    fun onRecyclerViewItemClicked(verticalPosition: Int, horizontalPosition: Int, view: View?)
}