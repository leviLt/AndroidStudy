package com.example.kotlinstudy.callback

/**
 * @author lt
 * @package com.example.kotlinstudy.callback
 * @date 2019/2/15 4:12 PM
 * @describe TODO
 * @project
 */
class Person {
    val name = Person::class.java.simpleName
    private var listener: ((String) -> Unit)? = null
    fun setListener(mListener: (String) -> Unit) {
        this.listener = mListener
        mListener("$name callback : 测试")
    }
}