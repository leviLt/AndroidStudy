package com.example.xpn_switch;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * @author lt
 * @package com.example.xpn_switch
 * @date 2018/11/7 10:27 AM
 * @describe TODO
 * @project
 */
public class ConnectView extends View {
    private static final String TAG = "SwitchView";
    private Context mContext;
    //默认圆环的凹槽的宽度
    private int defaultCircleWidth = dp2px(8);
    //默认矩形到圆环的间隔
    private int defaultCircleIntervalToRectangular = dp2px(20);
    //凹槽圆环的半径
    private int largeCircleRadius;
    //默认宽高
    private int defaultWidth = dp2px(172);
    private int defaultHeigth = dp2px(172);
    //背景画笔
    private Paint mBackgroundPaint;
    //外圈线
    private Paint mOutRingCirclePaint;
    //中间按钮圆
    private Paint mCenterBtnPaint;
    private Path mCenterBtnPath;
    private Path mCenterVerticalLinePath;

    private final int Default_CenterBtn_PaintWidth = dp2px(4);
    private final int Default_CenterBtn_CircleRadius = dp2px(13);


    //中心点坐标
    private Point centerPoint;
    //圆环进度条画笔&Path
    private Path mProgressPath;
    private Paint mProgressPaint;
    private Paint mCrossLinePaint;
    //按钮是否处于按下状态  按下去除阴影  放开显示阴影
    private boolean isDownBtn = false;
    //是否需要画圆环进度条
    private boolean isDrawablePath = false;

    //控制各个动画的过程
    private ValueAnimator mConnectingValueAnimator;
    private ValueAnimator mReConnectingValueAnimator;
    private ValueAnimator mConnectBtnValueAnimator;
    private ValueAnimator mToConnectSuccessValueAnimator;

    private Animator.AnimatorListener mAnimatorListener;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;


    // 测量Path 并截取部分的工具
    private PathMeasure mMeasure;

    private long defaultDuration = 15 * 1000;
    private long defaultReconnectingDuration = 1000;

    private float mValueAnmitor;
    private Handler mAnmitorHandler;


    public enum State {
        Disconnect,
        Connecting,
        Connected,
        ReConnecting,
        ToConnectSuccess
    }

    private State mCurrentState = State.Disconnect;

    private boolean mReconnectingIsStop = false;
    private int mReconnectingCount = 0;


    private static final int ConnectPaintColor = 0xFF56DCF1;
    private static final int DisConnectPaintColor = 0xFFD8D8D8;
    private int BtnState = 0;

    //粒子
    private Paint mDotPaint;

    public ConnectView(Context context) {
        this(context, null);
    }

    public ConnectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConnectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        init();
    }

    private void init() {
        //初始化进来是断开状态
        mCurrentState = State.Disconnect;
        mProgressPath = new Path();
        mMeasure = new PathMeasure();
        //初始化画笔
        initPaint();
        //初始化动画
        initAnimator();
        //初始化Handle
        initHandler();
    }


    private void initPaint() {
        mBackgroundPaint = getPaint();
        mBackgroundPaint.setColor(Color.WHITE);


        mOutRingCirclePaint = getPaint();
        mOutRingCirclePaint.setColor(0xFFE7E6E6);

        mCenterBtnPaint = getPaint();
        mCenterBtnPaint.setColor(DisConnectPaintColor);
        mCenterBtnPaint.setStrokeWidth(Default_CenterBtn_PaintWidth);
        mCenterBtnPaint.setStyle(Paint.Style.STROKE);


        mCrossLinePaint = getPaint();
        mCrossLinePaint.setStrokeWidth(1);
        mCrossLinePaint.setColor(Color.RED);

        mDotPaint = getPaint();
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setColor(ConnectPaintColor);


        mProgressPaint = getPaint();
        mProgressPaint.setColor(ConnectPaintColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(defaultCircleWidth * 2 / 5);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * 初始化我们动画相关的Animator
     */
    private void initAnimator() {
        //connecting动画
        Keyframe keyframe0 = Keyframe.ofFloat(0f, 0.0f);
        Keyframe keyframe1 = Keyframe.ofFloat(0.25f, 0.5f);
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 0.75f);
        Keyframe keyframe3 = Keyframe.ofFloat(0.75f, 0.85f);
        Keyframe keyframe4 = Keyframe.ofFloat(0.97f, 0.97f);

        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress", keyframe0, keyframe1, keyframe2, keyframe3, keyframe4);


        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mCurrentState == State.ToConnectSuccess) {
                    mCurrentState = State.Connected;
                    mAnmitorHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValueAnmitor = (float) animation.getAnimatedValue();
                BtnState = 0;
                if (mCurrentState == State.Connected) {
                    if (mValueAnmitor <= 250 && mValueAnmitor >= 1) {
                        BtnState = 0;
                    } else if (mValueAnmitor <= 650 && mValueAnmitor > 250) {
                        BtnState = 1;
                    } else if (mValueAnmitor <= 1000 && mValueAnmitor > 650) {
                        BtnState = 2;
                    }
                } else if (mCurrentState == State.Connecting) {
                    mValueAnmitor = (float) animation.getAnimatedValue("progress");
                } else if (mCurrentState == State.ToConnectSuccess) {
                    mValueAnmitor = (float) animation.getAnimatedValue();
                }
                invalidate();
            }
        };

        mConnectingValueAnimator = ValueAnimator.ofPropertyValuesHolder(holder).setDuration(defaultDuration);
        mConnectingValueAnimator.setInterpolator(new LinearInterpolator());
        mConnectingValueAnimator.addUpdateListener(mUpdateListener);

        mReConnectingValueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defaultReconnectingDuration);
        mReConnectingValueAnimator.setRepeatCount(Integer.MAX_VALUE / 2);
        mReConnectingValueAnimator.addUpdateListener(mUpdateListener);


        mConnectBtnValueAnimator = ValueAnimator.ofFloat(0, 1000).setDuration(500);
        //线性变化
        mConnectBtnValueAnimator.setInterpolator(new LinearInterpolator());
        mConnectBtnValueAnimator.addListener(mAnimatorListener);
        mConnectBtnValueAnimator.addUpdateListener(mUpdateListener);

        mToConnectSuccessValueAnimator = ValueAnimator.ofFloat(mValueAnmitor, 1);
        mToConnectSuccessValueAnimator.setDuration(200);
        mToConnectSuccessValueAnimator.addListener(mAnimatorListener);
        mToConnectSuccessValueAnimator.addUpdateListener(mUpdateListener);
    }

    /**
     * 控制改变我们的连接状态
     */
    @SuppressLint("HandlerLeak")
    public void initHandler() {
        mAnmitorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (mCurrentState) {
                    //断开连接
                    case Disconnect:
                        mReConnectingValueAnimator.cancel();
                        mConnectingValueAnimator.cancel();
                        mToConnectSuccessValueAnimator.cancel();
                        isDrawablePath = false;
                        mReconnectingCount = 0;
                        mReconnectingIsStop = false;
                        mCenterBtnPaint.setColor(DisConnectPaintColor);
                        invalidate();
                        break;
                    //连接中
                    case Connecting:
                        isDrawablePath = true;
                        mReConnectingValueAnimator.cancel();
                        mConnectBtnValueAnimator.cancel();
                        mToConnectSuccessValueAnimator.cancel();
                        //修改画笔的宽度
                        mProgressPaint.setStrokeWidth(defaultCircleWidth * 2 / 5);
                        //修改下Path
                        initPath();
                        //开启动画
                        mCenterBtnPaint.setColor(DisConnectPaintColor);
                        mConnectingValueAnimator.start();
                        break;
                    //连接成功
                    case Connected:
                        isDrawablePath = true;
                        mReConnectingValueAnimator.cancel();
                        mConnectingValueAnimator.cancel();
                        mToConnectSuccessValueAnimator.cancel();
                        mReconnectingCount = 0;
                        mReconnectingIsStop = false;
                        //修改画笔的宽度
                        mProgressPaint.setStrokeWidth(defaultCircleWidth * 3 / 4);
                        mCenterBtnPaint.setColor(ConnectPaintColor);
                        mConnectBtnValueAnimator.start();
                        break;
                    //重连中
                    case ReConnecting:
                        isDrawablePath = true;
                        mConnectingValueAnimator.cancel();
                        mConnectBtnValueAnimator.cancel();
                        mToConnectSuccessValueAnimator.cancel();
                        //修改画笔的宽度
                        mProgressPaint.setStrokeWidth(defaultCircleWidth * 2 / 5);
                        //修改下Path
                        initPath();
                        mCenterBtnPaint.setColor(DisConnectPaintColor);
                        //开启动画
                        mReConnectingValueAnimator.start();
                        break;
                    case ToConnectSuccess:
                        isDrawablePath = true;
                        mReConnectingValueAnimator.cancel();
                        mConnectingValueAnimator.cancel();
                        mConnectBtnValueAnimator.cancel();
                        //修改画笔的宽度
                        mProgressPaint.setStrokeWidth(defaultCircleWidth * 2 / 5);
                        //修改下Path
                        initPath();
                        mToConnectSuccessValueAnimator = ValueAnimator.ofFloat(mValueAnmitor, 1);
                        mToConnectSuccessValueAnimator.setDuration(200);
                        mToConnectSuccessValueAnimator.addListener(mAnimatorListener);
                        mToConnectSuccessValueAnimator.addUpdateListener(mUpdateListener);
                        mToConnectSuccessValueAnimator.start();
                        break;

                }
            }
        };
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(defaultWidth, defaultHeigth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPoint = new Point(getWidth() / 2, getHeight() / 2);
        largeCircleRadius = getWidth() / 2 - defaultCircleIntervalToRectangular;
        initPath();
    }

    /**
     * 初始化连接进度Path
     */
    private void initPath() {
        RectF rectF = new RectF();
        rectF.set(defaultCircleIntervalToRectangular - defaultCircleWidth / 2 + mProgressPaint.getStrokeWidth() / 2,
                defaultCircleIntervalToRectangular - defaultCircleWidth / 2 + mProgressPaint.getStrokeWidth() / 2,
                getWidth() - defaultCircleIntervalToRectangular + defaultCircleWidth / 2 - mProgressPaint.getStrokeWidth() / 2,
                getHeight() - defaultCircleIntervalToRectangular + defaultCircleWidth / 2 - mProgressPaint.getStrokeWidth() / 2);
        mProgressPath.addArc(rectF, 0, 360);
        mMeasure.setPath(mProgressPath, true);

        mCenterBtnPath = new Path();
        mCenterVerticalLinePath = new Path();
        // 中间的btn按钮 path
        scaleCenterBtnPath(Default_CenterBtn_CircleRadius, Default_CenterBtn_PaintWidth);
    }

    /**
     * 设置中心按钮的Path
     *
     * @param mRedius     中心的按钮半径
     * @param mPaintWidth 绘制的画笔的宽度
     */
    private void scaleCenterBtnPath(float mRedius, float mPaintWidth) {
        mCenterBtnPath.reset();
        mCenterVerticalLinePath.reset();
        mCenterVerticalLinePath.moveTo(centerPoint.x, centerPoint.y);
        mCenterVerticalLinePath.lineTo(centerPoint.x, centerPoint.y - mRedius - mPaintWidth * 2 / 3);

        mCenterBtnPath.moveTo(centerPoint.x - mRedius / 2, centerPoint.y - mRedius - mPaintWidth / 2);
        mCenterBtnPath.lineTo(centerPoint.x - mRedius / 2, centerPoint.y);
        mCenterBtnPath.lineTo(centerPoint.x + mRedius / 2, centerPoint.y);
        mCenterBtnPath.lineTo(centerPoint.x + mRedius / 2, centerPoint.y - mRedius - mPaintWidth / 2);
        mCenterBtnPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //1.绘制圆环
        drawCircleRing(canvas);
        //2.背景圆以及阴影
        drawBackCircle(canvas);
        //3.绘制按钮
        drawSwitch(canvas);
        //4.绘制圆形进度条
        if (isDrawablePath) {
            drawCirclePath(canvas);
        }
    }


    /**
     * 绘制凹槽
     *
     * @param canvas
     */
    private void drawCircleRing(Canvas canvas) {
        mOutRingCirclePaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < defaultCircleWidth; i++) {
            mOutRingCirclePaint.setStrokeWidth(defaultCircleWidth - i);
            mOutRingCirclePaint.setColor(Color.argb(255, 231 + i / 3, 230 + i / 3, 230 + i / 3));
            canvas.drawCircle(centerPoint.x, centerPoint.y, largeCircleRadius, mOutRingCirclePaint);
        }
    }

    /**
     * 绘制阴影以及背景圆
     *
     * @param canvas
     */
    private void drawBackCircle(Canvas canvas) {
        if (!isDownBtn) {
            mBackgroundPaint.setShadowLayer(10, 0, 40, Color.argb(125, 174, 174, 174));
        } else {
            mBackgroundPaint.setShadowLayer(0, 0, 0, 0);
        }
        canvas.drawCircle(centerPoint.x, centerPoint.y, largeCircleRadius - defaultCircleWidth / 2, mBackgroundPaint);
    }

    /**
     * 绘制中间按钮
     *
     * @param canvas
     */
    private void drawSwitch(Canvas canvas) {
        float percent = 1;
        if (mCurrentState == State.Connected) {
            switch (BtnState) {
                case 0:
                    percent = 1 - calcPercent(1, 250, mValueAnmitor);
                    mCenterBtnPaint.setStrokeWidth(percent * Default_CenterBtn_PaintWidth);
                    scaleCenterBtnPath(Default_CenterBtn_CircleRadius * percent, Default_CenterBtn_PaintWidth * percent);
                    break;
                case 1:
                    percent = calcPercent(251, 650, mValueAnmitor);
                    mCenterBtnPaint.setStrokeWidth(percent * Default_CenterBtn_PaintWidth);
                    scaleCenterBtnPath(Default_CenterBtn_CircleRadius * percent, Default_CenterBtn_PaintWidth * percent);
                    drawParticleEffect(canvas, percent);
                    break;
                case 2:
                    mCenterBtnPaint.setStrokeWidth(Default_CenterBtn_PaintWidth);
                    scaleCenterBtnPath(Default_CenterBtn_CircleRadius, Default_CenterBtn_PaintWidth);
                    float percent2 = calcPercent(650, 1000, mValueAnmitor);
                    drawParticleEffect(canvas, percent2);
                    break;
            }
        } else {
            mCenterBtnPaint.setStrokeWidth(Default_CenterBtn_PaintWidth);
            scaleCenterBtnPath(Default_CenterBtn_CircleRadius, Default_CenterBtn_PaintWidth);
        }
        if (percent <= 0.2) {
            percent = 0.2f;
        }
        canvas.save();
        canvas.clipPath(mCenterBtnPath, Region.Op.DIFFERENCE);
        canvas.drawCircle(centerPoint.x, centerPoint.y,
                mCurrentState == State.Connected ? Default_CenterBtn_CircleRadius * percent : Default_CenterBtn_CircleRadius,
                mCenterBtnPaint);
        canvas.restore();
        canvas.drawPath(mCenterVerticalLinePath, mCenterBtnPaint);
    }

    /**
     * @param canvas  画布
     * @param percent 进度
     */
    private void drawParticleEffect(Canvas canvas, float percent) {
        double angleA = 0;
        double angleB = -Math.PI / 20;
        float ringRadiusS = 0;
        float ringRadiusL = 0;
        float dotRS = 0;
        float dotRL = 0;
        if (BtnState == 1) {
            ringRadiusS = Default_CenterBtn_CircleRadius + Default_CenterBtn_CircleRadius * percent;
            ringRadiusL = Default_CenterBtn_CircleRadius + Default_CenterBtn_CircleRadius * percent * 3 / 2;
            if (ringRadiusL >= Default_CenterBtn_CircleRadius * 2) {
                ringRadiusL = Default_CenterBtn_CircleRadius * 2;
            }
            dotRS = Default_CenterBtn_PaintWidth * 2 / 5 * percent;
            dotRL = Default_CenterBtn_PaintWidth / 2 * percent;
        } else if (BtnState == 2) {
            ringRadiusS = Default_CenterBtn_CircleRadius * 2;
            ringRadiusL = Default_CenterBtn_CircleRadius * 2;

            dotRS = Default_CenterBtn_PaintWidth * 2 / 5 * (1 - percent);
            dotRL = Default_CenterBtn_PaintWidth / 2 * (1 - percent);
        }

        canvas.save();
        canvas.translate(centerPoint.x, centerPoint.y);
        for (int i = 0; i < 7; i++) {
            canvas.drawCircle(ringRadiusS * (float) Math.sin(angleA), ringRadiusS * (float) Math.cos(angleA), dotRS, mDotPaint);
            angleA += 2 * Math.PI / 7;
            canvas.drawCircle(ringRadiusL * (float) Math.sin(angleB), ringRadiusL * (float) Math.cos(angleB), dotRL, mDotPaint);
            angleB += 2 * Math.PI / 7;
        }
        canvas.restore();
    }

    /**
     * 绘制连接进度条
     *
     * @param canvas
     */
    private void drawCirclePath(Canvas canvas) {
        Path dstPath = new Path();
        switch (mCurrentState) {
            case Connected:
                canvas.drawPath(mProgressPath, mProgressPaint);
                break;
            case Connecting:
            case ToConnectSuccess:
                dstPath = new Path();
                mMeasure.getSegment(0, mMeasure.getLength() * mValueAnmitor, dstPath, true);
                canvas.save();
                canvas.rotate(90, centerPoint.x, centerPoint.y);
                canvas.drawPath(dstPath, mProgressPaint);
                canvas.restore();
                break;
            case ReConnecting:
                float stop = mMeasure.getLength() * mValueAnmitor;
                float start = (float) (stop - ((0.5 - Math.abs(mValueAnmitor - 0.5)) * mMeasure.getLength()));
                mMeasure.getSegment(start, stop, dstPath, true);
                canvas.save();
                canvas.rotate(90, centerPoint.x, centerPoint.y);
                canvas.drawPath(dstPath, mProgressPaint);
                canvas.restore();
                break;
            case Disconnect:
                //什么都不画
                break;
        }
    }


    /**
     * 获取画笔
     *
     * @return
     */
    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        return paint;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x >= defaultCircleIntervalToRectangular && x <= getWidth() - defaultCircleIntervalToRectangular
                        && y > defaultCircleIntervalToRectangular && y < getHeight() - defaultCircleIntervalToRectangular) {
                    isDownBtn = true;
                    invalidate();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isDownBtn = false;
                if (mCurrentState == State.Disconnect) {
                    mCurrentState = State.Connecting;
                } else {
                    mCurrentState = State.Disconnect;
                }
                mAnmitorHandler.sendEmptyMessage(0);
                break;
        }
        return super.onTouchEvent(event);
    }

    private int dp2px(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }


    //改变bitmap的颜色
    private Bitmap changeBitmapColor(Bitmap oldBitmap, int color) {
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
        int mBitmapWidth = newBitmap.getWidth();
        int mBitmapHeight = newBitmap.getHeight();
        for (int i = 0; i < mBitmapHeight; i++) {
            for (int j = 0; j < mBitmapWidth; j++) {
                int oldColor = newBitmap.getPixel(j, i);
                if (oldColor != 0) {
                    newBitmap.setPixel(j, i, color);
                }
            }
        }
        return newBitmap;
    }

    private float calcPercent(float start, float end, float current) {
        return (current - start) / (end - start);
    }
    /*------------------------------公共方法------------------------------------------*/

    /**
     * 改变当前的连接状态
     *
     * @param state
     */
    public void changeConnectStatus(State state) {
        if (state == State.ToConnectSuccess) {
            if (mCurrentState != State.Connecting && mCurrentState != State.ReConnecting) {
                return;
            }
        }
        if (mCurrentState == state) {
            return;
        }
        mCurrentState = state;
        mAnmitorHandler.sendEmptyMessage(0);
    }

    /**
     * 连接成功
     */
    public void connectSuccess(State state) {
        if (mCurrentState == state) {
            return;
        }
        mCurrentState = state;
        mAnmitorHandler.sendEmptyMessage(0);
    }


    /**
     * 连接失败
     */
    public void connectFail(State state) {
        if (mCurrentState == state) {
            return;
        }
        mCurrentState = state;
        mAnmitorHandler.sendEmptyMessage(0);
    }
}
