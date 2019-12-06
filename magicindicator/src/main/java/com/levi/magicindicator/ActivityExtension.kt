package com.levi.magicindicator

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 *@author Levi
 *@date 2019-07-09
 *@desc activity 扩展
 */
fun Context.moveTo(clazz: Class<*>, bundle: Bundle = Bundle(), flag: Int = 0, forResultCode: Int = -1) {
    var flag = flag
    if (flag and Intent.FLAG_ACTIVITY_NEW_TASK == 0) {
        flag = flag or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    if (forResultCode == -1) {
        if (this is FragmentActivity) {
            this.startActivityForResult(Intent(this, clazz).putExtras(bundle).addFlags(flag), forResultCode)
        }
    } else {
        startActivity(Intent(this, clazz).putExtras(bundle).addFlags(flag))
    }
}

/**
 * 获取到Drawable
 */
fun Context.getResDrawable(@DrawableRes resId: Int): Drawable {
    return ContextCompat.getDrawable(this, resId) ?: ColorDrawable(resId)
}

fun Context.getResColor(resId: Int): Int {
    ContextCompat.getColor(this, resId)
    return ContextCompat.getColor(this, resId)

}

/**
 * 移除Fragment
 */
fun FragmentActivity.removeFragment(fragment: Fragment) {
    if (isDestroyed || isFinishing) {
        return
    }
    supportFragmentManager.apply {
        beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss()
    }
}

/**
 * 根据手机的屏幕属性从 dip 的单位 转成为 px(像素)
 *
 * @param value
 * @return
 */
fun Context.dip2px(value: Float): Int {
    val displayMetrics = resources.displayMetrics
    return (value * displayMetrics.density + 0.5f).toInt()
}

/**
 * 屏幕的宽度
 */
fun Context.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

/**
 * 屏幕的高度
 */
fun Context.getScreenHeight(): Int {
    return resources.displayMetrics.heightPixels
}



