package com.example.kotlinstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinstudy.extention.moveTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mValues:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("kotlin", sum(1, 2).toString())
        main(1, 2, 3, 4, 5)
        isNull("1")
        var oneMillion = 1_000_000
        whenUse(oneMillion)
        arrayTest()
        tv.text = mValues
        btn_to_coroutine.setOnClickListener {
            this.moveTo(SuspendActivity::class.java,Bundle(),0)
        }
    }

    fun sum(a: Int, b: Int) {
        print(a + b)
    }

    fun sum1(a: Int, b: Int) = a + b

    fun main(vararg v: Int) {
        for (va in v) {
            Log.e("kotlin", va.toString())
        }
    }

    fun isNull(str: String) {
        if (str != "") {
            var age1 = str.toInt()
            Log.e("kotlin", age1.toString())
        }

    }

    override fun onResume() {
        super.onResume()
        Log.e("kotlin", "onResume")
    }

    fun whenUse(x: Int) {
        when (x) {
            1 -> Log.e("kotlin", "$x is x")
            2 -> Log.e("kotlin", "$x is x")
            else -> {
                Log.e("kotlin", "x!=1 x!=2 x = $x")
            }
        }
    }

    fun arrayTest() {
        val a = arrayOf(1, 2, 3)
        val b = Array(10) { i -> (i * 2) }
        var c = arrayListOf<String>()
        c.add("1")
        c.add("2")
        var d = IntArray(10) { i -> (i * 10) }
        for ((index, value) in a.withIndex()) {
            Log.e("kotlin", "a array index=$index  value = $value")
        }
    }
}