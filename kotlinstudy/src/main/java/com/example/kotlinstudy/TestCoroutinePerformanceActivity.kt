package com.example.kotlinstudy

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_performance.*
import kotlinx.coroutines.*

/**
 * @author lt
 * @package com.example.kotlinstudy
 * @date 2019/2/28 1:26 PM
 * @describe TODO
 * @project
 */
class TestCoroutinePerformanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance)
        init()
    }

    private fun init() {
        btnCoroutineTest.setOnClickListener {
            coroutineTest()
        }

        btnThreadTest.setOnClickListener {
            threadTest()
        }
    }

    private fun coroutineTest() {
        GlobalScope.launch {
            repeat(100000) {
                launch {
                    delay(1000L)
                    Log.e("Test Coroutine : ", it.toString())
                    withContext(Dispatchers.Main) {
                        // 主线程
                        //再开一个协程在子线程
                        withContext(Dispatchers.IO) {

                        }
                    }
                }
            }
        }
    }

    private fun threadTest() {

        repeat(100000) {
            Thread {
                Thread.sleep(1000L)
                Log.e("Test Thread :", it.toString())
            }.start()
        }
    }
}