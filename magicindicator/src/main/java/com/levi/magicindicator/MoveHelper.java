package com.levi.magicindicator;

import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.view.MotionEvent.ACTION_DOWN;

/**
 * @author Levi
 * @date 2019-12-07
 * @desc
 */
public class MoveHelper {
    private static final String TAG_POINT = "MoveHelper";

    /**
     * 模拟用户滑动操作
     *
     * @param view 要触发操作的view
     * @param p1x  滑动的起始点x坐标
     * @param p1y  滑动的起始点y坐标
     * @param p2x  滑动的终点x坐标
     * @param p2y  滑动的终点y坐标
     */
    static void analogUserScroll(View view, final float p1x, final float p1y, final float p2x, final float p2y) {
        Log.e(TAG_POINT, "正在模拟滑屏操作：p1->" + p1x + "," + p1y + ";p2->" + p2x + "," + p2y);
        if (view == null) {
            return;
        }
        long downTime = SystemClock.uptimeMillis();//模拟按下去的时间

        long eventTime = downTime;

        float pX = p1x;
        float pY = p1y;
        float touchTime = 116;//模拟滑动时发生的触摸事件次数

        //平均每次事件要移动的距离
        float perX = (p2x - p1x) / touchTime;
        float perY = (p2y - p1y) / touchTime;

        boolean isReversal = perX < 0 || perY < 0;//判断是否反向：手指从下往上滑动，或者手指从右往左滑动

        //模拟用户按下
        MotionEvent downEvent = MotionEvent.obtain(downTime, eventTime,
                ACTION_DOWN, pX, pY, 0);
        view.onTouchEvent(downEvent);

        //模拟移动过程中的事件
        List<MotionEvent> moveEvents = new ArrayList<>();
        for (int i = 0; i < touchTime; i++) {

            pX += perX;
            pY += perY;
            if ((isReversal && pX < p2x) || (!isReversal && pX > p2x)) {
                pX = p2x;
            }

            if ((isReversal && pY < p2y) || (!isReversal && pY > p2y)) {
                pY = p2y;
            }
            eventTime += 20.0f;//事件发生的时间要不断递增
            MotionEvent moveEvent = getMoveEvent(downTime, eventTime, pX, pY);
            moveEvents.add(moveEvent);
            view.onTouchEvent(moveEvent);
        }

        //模拟手指离开屏幕
        MotionEvent upEvent = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_UP, pX, pY, 0);
        view.onTouchEvent(upEvent);

        //回收触摸事件
        downEvent.recycle();
        for (int i = 0; i < moveEvents.size(); i++) {
            moveEvents.get(i).recycle();
        }
        upEvent.recycle();
    }

    private static MotionEvent getMoveEvent(long downTime, long eventTime, float pX, float pY) {
        return MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, pX, pY, 0);
    }
}
