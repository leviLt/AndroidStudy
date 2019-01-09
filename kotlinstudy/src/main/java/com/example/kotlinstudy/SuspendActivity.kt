package com.example.kotlinstudy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_suspend.*
import kotlinx.coroutines.*

class SuspendActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suspend)
//        initListener()
        main()
        println("main after print")
    }

    private fun initListener() {
        btn_start.apply {

        }

        var job = GlobalScope.launch {
            delay(1000)
            runOnUiThread {
                tv_coroutine_result.text = "2s after changes result"
            }
            Log.e("GlobalScope.launch==", "run stop")
            Log.e(" GlobalScope thread ==", Thread.currentThread().name)
        }
        Log.e("main thread ==", "run")
        Log.e(" thread ==", Thread.currentThread().name)

    }

    fun main() {
        GlobalScope.launch {
            repeat(10) { i ->
                println("I'm sleeping $i ...")
                delay(500)
            }
            delay(1300)
            println("GlobalScope.launch is over")
        }
    }
}
