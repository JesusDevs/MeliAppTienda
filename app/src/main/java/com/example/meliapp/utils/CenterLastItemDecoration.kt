package com.example.meliapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CenterLastItemDecoration(private val spanCount: Int, private val itemSpacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position == itemCount - 1) {
            val totalItemWidth = (spanCount * view.width) + ((spanCount - 1) * itemSpacing)
            val extraWidth = parent.width - totalItemWidth

            outRect.left = extraWidth / 2
            outRect.right = extraWidth / 2
        }
    }
}