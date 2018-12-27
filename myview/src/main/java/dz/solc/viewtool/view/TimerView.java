package dz.solc.viewtool.view;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2017/12/6
 * creat_time: 15:48
 * describe 用于倒计时的类 不绑定任何view
 **/

public class TimerView {
    //false 表示可以重新获取 true表示还在倒计时
    public boolean iswait = false;
    //保证多次触发只有一条线程
    private boolean singlethread = true;
    private int initime = 0;

    private HandlerMessage handlerMessage;
    //创建句柄 避免每次接受到事件创建定时器和任务
    private Timer timer = new Timer();
    private AsyncTimerTask asyncTimerTask;
    //倒计时时速度 单位毫秒.
    private int speed = 1000;


    private TimerView() {
    }

    public int gettime() {
        return handlerMessage != null ? handlerMessage.currentime : 0;
    }


    public synchronized static TimerView create() {
        return new TimerView();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //启动倒计时
    public void start(final TimerViewListener listener, int time) {
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
        private TimerViewListener listener;
        private int currentime;

        public HandlerMessage(TimerViewListener listener, int time) {
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
                    iswait = false;
                    currentime = initime;
                    listener.endfresh();
                    singlethread = true;
                } else {
                    iswait = true;
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
     * 倒计时监听回调
     */
    public interface TimerViewListener {

        void startfresh(int currentime);

        void endfresh();

    }
}
