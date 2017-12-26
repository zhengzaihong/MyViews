package dz.solc.viewtool;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
* creat_user: zhengzaihong
* Email:1096877329@qq.com
* creat_date: 2017/12/6
* creat_time: 15:48
* describe 用于倒计时的类 不绑定任何view
**/

public class TimerViewGroup {
    //false 表示可以重新获取 true表示还在倒计时
    public boolean iswait = false;
    //保证多次触发只有一条线程
    private boolean singlethread = true;
    private int initime = 0;
    //sp文件名
    private static String sptagname;

    private static SharedPreferences preferences;
    private TimerViewGroup() {
    }

    private static TimerViewGroup instance = null;

    public static TimerViewGroup getinstance(Application context,String sptag) {
        if (null == instance) {
            synchronized (TimerViewGroup.class) {
                if (null == instance) {
                    instance = new TimerViewGroup();
                    sptagname =sptag;
                    preferences = context.getSharedPreferences(sptagname,Context.MODE_PRIVATE);
                }
            }
        }
        return instance;
    }

    /**
     * 该方法在退出程序时调用 即在application 中做处理
     * 或是在不想保存倒计时 时调用
     */
    public void cleartimeInfo(Application context){
        preferences = context.getSharedPreferences(sptagname,Context.MODE_PRIVATE);
        preferences.edit().clear();
    }

    public void start(final TimerViewGroupListener listener, int time) {
        int oldtime =  preferences.getInt("time",0);
        initime = time;
        if(oldtime==0){
            if (null != listener && singlethread) {
                HandlerMessage handlerMessage = new HandlerMessage(listener, time);
                Message msg = Message.obtain();
                msg.what = 1;
                handlerMessage.sendMessage(msg);
            }
        }else{
            if (null != listener && singlethread) {
                HandlerMessage handlerMessage = new HandlerMessage(listener, oldtime);
                Message msg = Message.obtain();
                msg.what = 1;
                handlerMessage.sendMessage(msg);
            }
        }
    }

    private class HandlerMessage extends Handler {
        private HandlerMessage handlerMessage;
        private TimerViewGroupListener listener;
        private int currentime;

        public HandlerMessage(TimerViewGroupListener listener, int time) {
            handlerMessage = this;
            this.listener = listener;
            this.currentime = time;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                currentime--;
                if (currentime == 0) {
                    iswait = false;
                    currentime = initime;
                    listener.endfresh(currentime);
                    singlethread = true;
                    preferences.edit().putInt("time",0).commit();
                } else {
                    iswait = true;
                    singlethread = false;
                    listener.startfresh(currentime);
                    preferences.edit().putInt("time",currentime).commit();
                    sendMessage(handlerMessage);
                }
            }
        }

        private void sendMessage(final HandlerMessage handlerMessage) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    handlerMessage.sendMessage(msg);
                }
            }, 1000);
        }
    }


    /**
     * Activity 或 Fragment中实现该接口
     */
   public interface TimerViewGroupListener {
        void startfresh(int currentime);

        void endfresh(int currentime);
    }
}
