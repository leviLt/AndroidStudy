package com.levi.magicindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_magicindicator.*

/**
 *@author Levi
 *@date 2019-12-06
 *@desc
 */
class MagicindicatorFragment private constructor() : Fragment() {

    var title: String = ""

    companion object {
        /**
         * 创建
         */
        fun newInstance(title: String): Fragment {
            val fragment = MagicindicatorFragment()
            fragment.title = title
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_magicindicator, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        view.findViewById<TextView>(R.id.tv).text = title
    }
}