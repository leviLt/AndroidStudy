package com.levi.magicindicator

import android.content.Context

/**
 *@author Levi
 *@date 2019-10-14
 *@desc
 */
/**
 * Int 转PX
 */
fun Int.toPX(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return (this * displayMetrics.density + 0.5f).toInt()
}

/**
 * Int转sp
 */
fun Int.toSP(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return (this * metrics.scaledDensity).toInt()
}