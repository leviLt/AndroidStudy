package com.example.kotlinstudy.callback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinstudy.R
import kotlinx.android.synthetic.main.activity_callback_test.*

class CallbackTestActivity : AppCompatActivity() {
    var person: Person = Person()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_callback_test)
        initView()
    }

    private fun initView() {

        btn.setOnClickListener {
            person.setListener {
                tvMessage.text = it
            }
        }
    }
}
