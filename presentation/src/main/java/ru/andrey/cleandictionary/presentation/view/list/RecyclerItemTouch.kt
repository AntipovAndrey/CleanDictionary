package ru.andrey.cleandictionary.presentation.view.list

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper


class RecyclerItemTouch(dragDirs: Int, swipeDirs: Int, private val cb: (Holder) -> Unit)
    : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(recyclerView: RecyclerView?,
                        viewHolder: RecyclerView.ViewHolder?,
                        target: RecyclerView.ViewHolder?): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        cb(viewHolder as Holder)
    }
}
