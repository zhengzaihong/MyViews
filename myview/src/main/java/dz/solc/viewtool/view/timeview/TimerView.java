package dz.solc.viewtool.view.timeview;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2017/12/6
 * create_time: 15:48
 * describe 用于倒计时的类 不绑定任何view
 **/

public class TimerView {
    /**
     * false 表示可以重新获取 true表示还在倒计时
     */
    private boolean wait = false;
    /**
     * 保证多次触发只有一条线程
     */
    private boolean singlethread = true;

    private int initime = 0;

    private HandlerMessage handlerMessage;
    /**
     * 创建句柄 避免每次接受到事件创建定时器和任务
     */
    private Timer timer = new Timer();
    private AsyncTimerTask asyncTimerTask;
    /**
     * 倒计时时速度 单位毫秒.
     */
    private int speed = 1000;


    private TimerView() {
    }

    public int getTime() {
        return handlerMessage != null ? handlerMessage.currentime : 0;
    }


    public synchronized static TimerView create() {
        return new TimerView();
    }


    //启动倒计时
    public void start(final OnAlarmClockListener listener, int time) {
        if (null != listener && singlethread) {
            handlerMessage = new HandlerMessage(listener, time);
            Message msg = Message.obtain();
            msg.what = 1;
            handlerMessage.sendMessage(msg);
        }
    }

    //关闭倒计时
    public void closeTimer() {
        if (null != asyncTimerTask) {
            asyncTimerTask.cancel();
        }

        //触发结束回调
        handlerMessage.setCurrentime(1);
        sendMsg();
    }

    private class HandlerMessage extends Handler {
        private OnAlarmClockListener listener;
        private int currentime;

        public HandlerMessage(OnAlarmClockListener listener, int time) {
            this.listener = listener;
            this.currentime = time;
        }

        public void setCurrentime(int currentime) {
            this.currentime = currentime;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                currentime--;
                if (currentime == 0) {
                    wait = false;
                    currentime = initime;
                    listener.endfresh();
                    singlethread = true;
                } else {
                    wait = true;
                    singlethread = false;
                    listener.startfresh(currentime);
                    //取消掉一次性task
                    if (null != asyncTimerTask) {
                        asyncTimerTask.cancel();
                        asyncTimerTask = null;
                    }
                    timer.schedule(asyncTimerTask = new AsyncTimerTask(), speed);
                }
            }
        }
    }

    private class AsyncTimerTask extends TimerTask {
        @Override
        public void run() {
            sendMsg();
        }
    }

    private void sendMsg() {
        Message msg = Message.obtain();
        msg.what = 1;
        handlerMessage.sendMessage(msg);
    }


    /**
     * 获取加速度
     *
     * @return
     */
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    /**
     * 倒计时监听回调
     */
    public interface OnAlarmClockListener {

        void startfresh(int time);

        void endfresh();

    }
}
