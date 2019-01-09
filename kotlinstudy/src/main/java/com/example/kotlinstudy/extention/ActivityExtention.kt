package com.example.kotlinstudy.extention

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * @author lt
 * @package com.example.kotlinstudy.extention
 * @date 2019/1/4 2:19 PM
 * @describe TODO
 * @project
 */
fun Context.moveTo(c: Class<*>, bundle: Bundle = Bundle(), flag: Int = 0) {
    var mFlag = flag
    if (mFlag and Intent.FLAG_ACTIVITY_NEW_TASK == 0) {
        mFlag = mFlag or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(Intent(this, c).putExtras(bundle).setFlags(flag))
}
