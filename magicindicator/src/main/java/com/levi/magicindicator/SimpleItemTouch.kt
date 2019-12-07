package com.levi.magicindicator

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 *@author Levi
 *@date 2019-12-07
 *@desc
 */
class SimpleItemTouch(var callback: ((from: Int, to: Int) -> Unit)? = null) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        var drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(drag, 0)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        callback?.apply {
            this(viewHolder.adapterPosition, target.adapterPosition)
        }
        Log.e("SimpleItemTouch","fromPosition==="+viewHolder.adapterPosition+"  toPosition==="+target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

}