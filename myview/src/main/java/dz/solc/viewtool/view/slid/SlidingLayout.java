package dz.solc.viewtool.view.slid;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/10/26
 * create_time: 16:58
 * describe: 添加一个 IOS 侧滑退出功能
 **/
public class SlidingLayout extends FrameLayout {

    private Scroller mScroller;
    private Activity mActivity;

    public SlidingLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlidingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    /**
     * 初始化参数
     */
    private void initViews(Context context) {

        mScroller = new Scroller(context);
    }

    /**
     * activity的顶级view是DecorView，所以这里在DecorView的下一级包装了一层SlidingLayout
     * @param activity
     */
    public void replaceCurrentLayout(Activity activity){

        mActivity = activity;
        ViewGroup decorView= (ViewGroup) mActivity.getWindow().getDecorView();
        View child = decorView.getChildAt(0);   //我们DecorView包裹的布局
        decorView.removeView(child);
        this.addView(child);    //将我们DecorView包裹的布局添加到我们自定义的SlidingLayout当中
        decorView.addView(this);
    }

    private int mInterceptDownX;    //按下的位置x的坐标值
    private int mLastInterceptX;    //最后的触摸位置x坐标的值
    private int mLastInterceptY;    //最后的触摸位置y坐标的值

    /**
     * 根据手指移动的距离判断是否拦截触摸事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean mIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mInterceptDownX = x;
                mLastInterceptX = x;
                mLastInterceptY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                int moveX = x - mLastInterceptX;
                int moveY = y - mLastInterceptY;
                //按下的位置的X位置小于屏幕的十分之一，并且x移动的距离大于y移动的距离，就拦截
                if (mInterceptDownX < (getWidth() / 10) && Math.abs(moveX) > Math.abs(moveY)) {
                    mIntercept = true;
                } else {
                    mIntercept = false;
                }
                mLastInterceptX = x;
                mLastInterceptY = y;

                break;
            case MotionEvent.ACTION_UP: //抬起的时候重置参数
                mIntercept = false;
                mInterceptDownX = mLastInterceptX = mLastInterceptY = 0;
                break;
        }
        return mIntercept;
    }

    private int mTouchDownX;
    private int mLastTouchX;
    private int mLastTouchY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean mConsumed = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mTouchDownX = x;
                mLastTouchX = x;
                mLastTouchY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                int moveX = x - mLastTouchX;
                int moveY = y - mLastTouchY;
                if (mTouchDownX < (getWidth() / 10) && Math.abs(moveX) > Math.abs(moveY) && !mConsumed) {
                    mConsumed = true;
                }
                if (mConsumed) {
                    int rightMoveX = (int) (mLastTouchX - event.getX());
                    if ((getScrollX() + rightMoveX) > 0) {  //向左滑动的时候，getScrollX()和rightMoveX都大于0，所以禁止滑动
                        scrollTo(0, 0);
                    } else {
                        scrollBy(rightMoveX, 0);
                    }
                }
                mLastTouchX = x;
                mLastTouchY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mConsumed = false;
                mTouchDownX = mLastTouchX = mLastTouchY = 0;
                if(-getScrollX()<getWidth()/2){ //偏移量不到屏幕宽度的一般，就回到最初的位置
                    scrollBack();
                }else{
                    scrollFinish();
                }
                break;
        }
        return true;
    }


    /**
     * 滑动到最初的位置
     */
    private void scrollBack() {
        int startX = getScrollX();
        int dx = -getScrollX();
        mScroller.startScroll(startX, 0, dx, 0, 300);
        invalidate();
    }

    /**
     * 向右滑动关闭
     */
    private void scrollFinish(){
        int dx = -getScrollX() - getWidth();
        mScroller.startScroll(getScrollX(),0,dx,0,300);
        invalidate();
    }

    @Override
    public void computeScroll() {

        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),0);
            postInvalidate();
        }else if(-getScrollX() >= getWidth()){
            mActivity.finish();
        }
    }
}
