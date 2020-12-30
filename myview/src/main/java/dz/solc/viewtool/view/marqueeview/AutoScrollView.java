package dz.solc.viewtool.view.marqueeview;


import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2018/2/22
 * create_time: 20:01
 * describe: 可实现类似电影结束字幕效果，此view只是个容器,真正需要滚动的文字需要放入该ViewGroup节点下,
 * 同时完整滚动完还需要在该ViewGroup的外层在用ScrollView包装下，完整使用请看demo
 **/
public class AutoScrollView extends FrameLayout {

    private final Handler handler = new Handler();
    /**
     * 滚动的时间间隔
     */
    private long duration = 50;
    private boolean isScrolled = true;
    private int currentIndex = 0;
    /**
     * 延迟多久开始滚动
     */
    private long period = 1000;
    private int currentY = -1;
    private double x;
    private double y;

    private boolean isFirstScolled = true;

    /**
     * 测量子View的高度
     */
    private int childViewHight = 0;

    /**
     * 滚动的类型
     */
    private MarqueeType marqueeType = MarqueeType.ONLY_ONCE;


    //记录下布局给的高度值 单位px
    private int viewHeight = 0;

    private int type = -1;

    public AutoScrollView(Context context) {
        this(context, null);
    }

    public AutoScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int Action = event.getAction();
        switch (Action) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                if (type == 0) {
                    setScrolled(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                double moveY = event.getY() - y;
                double moveX = event.getX() - x;
                Log.d("test", "moveY = " + moveY + "  moveX = " + moveX);
                if ((moveY > 20 || moveY < -20) && (moveX < 50 || moveX > -50) && getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (type == 0) {
                    currentIndex = getScrollY();
                    setScrolled(true);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent p_event) {
        Log.d("test", "onInterceptTouchEvent");
        return true;
    }

    /**
     * 判断当前是否为滚动状态
     *
     * @return the isScrolled
     */
    public boolean isScrolled() {
        return isScrolled;
    }

    /**
     * 开启或者关闭自动滚动功能
     *
     * @param isScrolled true为开启，false为关闭
     */
    public void setScrolled(boolean isScrolled) {
        this.isScrolled = isScrolled;

    }

    public void startScrolled() {
        autoScroll();
    }

    /**
     * 获取当前滚动到结尾时的停顿时间，单位：毫秒
     */
    public long getPeriod() {
        return period;
    }

    /**
     * 设置当前滚动到结尾时的停顿时间，单位：毫秒
     */
    public void setPeriod(long period) {
        this.period = period;
    }

    /**
     * 获取当前的滚动速度，单位：毫秒，值越小，速度越快。
     */
    public long getSpeed() {
        return duration;
    }

    /**
     * 设置当前的滚动速度，单位：毫秒，值越小，速度越快。
     */
    public void setSpeed(long speed) {
        this.duration = speed;
    }


    private MyRunnable myRunnable;

    private void autoScroll() {
        handler.postDelayed(myRunnable == null ? myRunnable = new MyRunnable() : myRunnable, duration);
    }


    public class MyRunnable implements Runnable {
        @Override
        public void run() {
            boolean flag = isScrolled;
            if (flag) {
                if (childViewHight > getScrollY()) {
                    if (currentY == getScrollY()) {
                        try {
                            Thread.sleep(period);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        scrollTo(0, currentIndex);
                        handler.postDelayed(myRunnable, period);
                    } else {
                        currentY = getScrollY();
                        handler.postDelayed(myRunnable, duration);
                        currentIndex++;
                        scrollTo(0, currentIndex * 1);
                    }
                    isFirstScolled = false;
                } else {
                    final View view = AutoScrollView.this.getChildAt(0);
                    if (null != view && marqueeType == MarqueeType.LIMIT) {
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                childViewHight = view.getHeight();
                                if (isFirstScolled) {
                                    currentIndex = 0;
                                } else {
                                    //TODO 处理直接一下文字进入效果，应该让文字慢慢平滑进入。
                                    // 设计到的问题,如果是直接测量view高度,和在布局或代码中给的值不匹配，
                                    // 解决方式占时需要外部传入下布局的高度值
                                    currentIndex = -viewHeight;
                                }
                                scrollTo(0, currentIndex);
                                handler.postDelayed(myRunnable, 0);
                            }
                        });
                    } else {
                        scrollTo(0, 0);
                        handler.postDelayed(myRunnable, period);
                    }
                }
            }
        }
    }

    public void setMarqueeType(MarqueeType marqueeType) {
        this.marqueeType = marqueeType;
    }


    public void setViewHeight(int px) {
        viewHeight = px;
        Log.e("AutoScrollView", "布局文件高度：" + px);
    }

    public enum MarqueeType {

        /**
         * 只滚动一次
         */
        ONLY_ONCE,

        /**
         * 无限次重复滚动
         */
        LIMIT

    }
}
