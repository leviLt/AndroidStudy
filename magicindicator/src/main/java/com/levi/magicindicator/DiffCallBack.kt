package com.levi.magicindicator

import androidx.recyclerview.widget.DiffUtil

/**
 *@author Levi
 *@date 2019-12-07
 *@desc
 */
class DiffCallBack constructor(val oldData: List<String>, val newData: List<String>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }

}