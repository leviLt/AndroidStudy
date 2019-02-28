package com.example.kotlinstudy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinstudy.callback.CallbackTestActivity
import com.example.kotlinstudy.extention.moveTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCallback.setOnClickListener {
            this@MainActivity.moveTo(CallbackTestActivity::class.java)
        }
        btnCoroutinePerformance.setOnClickListener {
            //性能测试
            moveTo(TestCoroutinePerformanceActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("kotlin", "onResume")
    }
}