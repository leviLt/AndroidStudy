package com.levi.magicindicator

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*


/**
 *@author Levi
 *@date 2019-12-07
 *@desc
 */
class ListAdapter constructor(private val data: MutableList<String>, private val context: Context?) : RecyclerView.Adapter<ListAdapter.InnerViewHolder>() {

    var click: (() -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, null, false)
        return InnerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (data.isEmpty()) 0 else data.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.tv?.text = data[position]
        holder.right?.setOnClickListener {
            movePositionToFirst(holder.adapterPosition)
        }
    }

    inner class InnerViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var tv: TextView? = null
        var right: ImageView? = null

        init {
            tv = item.findViewById(R.id.item_tv)
            right = item.findViewById(R.id.right)
        }
    }

    fun setOnClickListener(listener: () -> Unit) {
        click = listener
    }

    fun movePositionToFirst(fromPosition: Int) {
        val newData = mutableListOf<String>()
        val element = data[fromPosition]
        newData.addAll(data)
        newData.remove(element)
        newData.add(0, element)
        val diffResult = DiffUtil.calculateDiff(DiffCallBack(data, newData), true)
        diffResult.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newData)
        //先移除再添加
        click?.apply {
            this.invoke()
        }
    }


}