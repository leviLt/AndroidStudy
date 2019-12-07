package com.levi.magicindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_magicindicator.*

/**
 *@author Levi
 *@date 2019-12-06
 *@desc
 */
class MagicindicatorFragment private constructor() : Fragment() {


    private lateinit var itemTouch: ItemTouchHelper
    var list = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14")

    var diffResult: DiffUtil.DiffResult? = null


    companion object {
        /**
         * 创建
         */
        fun newInstance(): Fragment {
            return MagicindicatorFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_magicindicator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        val adapter = ListAdapter(list, context)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.isSmoothScrollbarEnabled = true
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.adapter = adapter
        adapter.setOnClickListener {
            recycler_view.scrollToPosition(0)
        }
//        var callback = SimpleItemTouch { from: Int, to: Int ->
//            adapter.movePositionToFirst(from, to)
//        }
//
//        itemTouch = ItemTouchHelper(callback)
//        itemTouch.attachToRecyclerView(recycler_view)
    }

}