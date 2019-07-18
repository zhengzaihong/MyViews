package dz.solc.viewtool.view.timeview;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2017/12/6
 * creat_time: 15:48
 * describe 用于倒计时的类 TimerView 加强版,用于使用长计时轮训等
 **/

public class TimerViewUpgrade {
    /**
     * false 表示可以重新获取 true表示还在倒计时
     */
    private boolean wait = false;
    /**
     * 保证多次触发只有一条线程
     */
    private boolean singlethread = true;

    /**
     * 记录时间的总长，单位秒
     */
    private int totalTime = 0;
    /**
     * 延时时间,单位毫秒
     */
    private int delaytime = 0;

    /**
     * 创建句柄
     */
    private ScheduledExecutorService threadPool;

    private HandlerMessage handlerMessage = new HandlerMessage();
    /**
     * 倒计时时速度 单位毫秒.
     */
    private int period = 1000;


    private OnAlarmClockListener onAlarmClockListener;

    private TimerViewUpgrade() {
    }

    public int getTime() {

        return totalTime;
    }


    public synchronized static TimerViewUpgrade create() {
        return new TimerViewUpgrade();
    }


    /**
     * @param listener  回调的监听器
     * @param delaytime 延时几秒开始
     * @param totalTime 执行的总长时间
     */
    public void start(final OnAlarmClockListener listener, int delaytime, int totalTime) {

        if (null != listener && singlethread) {
            this.onAlarmClockListener = listener;
            this.delaytime = delaytime;
            this.totalTime = totalTime;
            timeAction();
        } else {
            new Exception("请先设置TimerViewListener时间监听回调");
            return;
        }
    }

    /**
     * 简述下两个方法的区别
     * scheduleAtFixedRate ，是以上一个任务开始的时间计时，period时间过去后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，则当前任务立即执行，
     * 如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行。
     * <p>
     * scheduleWithFixedDelay，是以上一个任务结束时开始计时，period时间过去后，立即执行
     */
    private void timeAction() {

//      改变状态，标示当前已经开始执行任务，避免延迟时间过长的原因导致在外部判断状态出现的异常。
        wait = true;
        singlethread = false;
        try {
            threadPool = Executors.newSingleThreadScheduledExecutor();
            threadPool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    sendMsg();
                }
            }, delaytime, period, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Log.e("输出执行出现异常：", "定时出现异常了");
        }
    }

    /**
     * 关闭倒计时
     * <p>
     * shutdown只是将线程池的状态设置为SHUTWDOWN状态，正在执行的任务会继续执行下去，没有被执行的则中断。
     * shutdownNow则是将线程池的状态设置为STOP，正在执行的任务则被停止，没被执行任务的则返回。
     */
    public void closeTimer() {
        close(true);
    }

    /**结束任务
     * @param callBackEnd,是否触发回调
     */
    public void closeTimer(boolean callBackEnd) {
        close(callBackEnd);
    }

    private void close(boolean callBackEnd) {
        wait = false;
        singlethread = true;

        if (null != onAlarmClockListener && callBackEnd) {
            onAlarmClockListener.endfresh();
            onAlarmClockListener = null;
        }

        if (null != threadPool) {
            threadPool.shutdownNow();
            threadPool = null;
        }
    }


    private int tag = 0xfef00;

    private class HandlerMessage extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == tag) {
                if (totalTime > 0) {
                    totalTime--;
                    if (null != onAlarmClockListener) {
                        onAlarmClockListener.startfresh(totalTime);
                    }
                } else {
                    closeTimer();
                }
            }
        }
    }


    private void sendMsg() {
        Message msg = Message.obtain();
        msg.what = tag;
        handlerMessage.sendMessage(msg);
    }


    /**
     * 间隔多久重新执行，单位毫秒
     *
     * @return
     */
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean isWait() {
        return wait;
    }

    /**
     * 非特殊情况，请不要改状态
     *
     * @param wait
     */
    public void setWait(boolean wait) {
        this.wait = wait;
    }


    public void setOnAlarmClockListener(OnAlarmClockListener onAlarmClockListener) {

        this.onAlarmClockListener = onAlarmClockListener;

    }

    /**
     * 倒计时监听回调
     */
    public interface OnAlarmClockListener {

        void startfresh(int currentime);

        void endfresh();

    }
}
