package com.example.kotlinstudy

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * @author lt
 * @package com.example.kotlinstudy
 * @date 2019/1/4 2:15 PM
 * @describe TODO
 * @project
 */
class Test {
    fun main() = runBlocking {
        delay(1000)
        Log.e("test", "test main 1000 after")
    }
}