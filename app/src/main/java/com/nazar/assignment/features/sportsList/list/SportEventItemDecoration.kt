package com.nazar.assignment.features.sportsList.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nazar.assignment.extensions.dp

class SportEventItemDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.also { adapter ->
            val position = parent.getChildAdapterPosition(view)
            outRect.left = when (position) {
                0 -> 8.dp
                else -> 4.dp
            }
            outRect.right = when (position) {
                adapter.itemCount.minus(1) -> 8.dp
                else -> 4.dp
            }
        }
    }
}
