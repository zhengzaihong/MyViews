package dz.solc.viewtool.view.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2017/12/26
 * create_time: 10:06
 * describe 抢购倒计时控件
 **/

@SuppressLint("AppCompatCustomView")
public class CountdownTextView extends TextView {

    private long mSeconds;
    private String mStrFormat;
    private Map<Integer, Timer> mTimerMap;
    private TimerTask mTimerTask;
    private final int what_count_down_tick = 1;
    private String TAG = "CountdownTextView";



    public CountdownTextView(Context context) {
        super(context);
    }

    public CountdownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountdownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param format  例如：剩余%s
     * @param seconds
     */
    public void init(String format, long seconds) {
        mTimerMap = new HashMap<>();
        if (!TextUtils.isEmpty(format)) {
            mStrFormat = format;
        }
        mSeconds = seconds; //设置总共的秒数
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mSeconds > 0) {
                    mSeconds--;
                    mHandler.sendEmptyMessage(what_count_down_tick);
                }
            }
        };
    }

    public void start(int position) {
        if (mTimerMap.get(position) == null) {
            Timer timer = new Timer();
            mTimerMap.put(position, timer);
            mTimerMap.get(position).schedule(mTimerTask, 0, 1000);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case what_count_down_tick:
                    String content;
                    if (mSeconds <= 0) {
                        content = String.format(mStrFormat, "00:00:00");
                    } else {
//                        Log.e(TAG, "mSeconds=" + mSeconds + "#what_count_down_tick:" + second2TimeSecond(mSeconds) + "#" + String.format(mStrFormat, second2TimeSecond(mSeconds)));
                        content = (mStrFormat == null ? second2TimeSecond(mSeconds) : String.format(mStrFormat, second2TimeSecond(mSeconds)));
                    }
                    if (null != onTextInfoListener) {
                        onTextInfoListener.setText(CountdownTextView.this,content);
                    }
                    break;
            }
        }
    };

    private OnTextInfoListener onTextInfoListener;

    public interface OnTextInfoListener {

        void setText(TextView textView,String content);
    }

    public void setTextInfoListener(OnTextInfoListener onTextInfoListener) {

        this.onTextInfoListener = onTextInfoListener;
    }


    @Override
    public void removeOnLayoutChangeListener(OnLayoutChangeListener listener) {
        Log.e(TAG, "removeOnLayoutChangeListener");
        super.removeOnLayoutChangeListener(listener);
    }

    @Override
    public void removeOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        Log.e(TAG, "removeOnAttachStateChangeListener");
        super.removeOnAttachStateChangeListener(listener);
    }

    /**
     * 转化为 hh:mm:ss 格式
     *
     * @param second
     * @return
     */
    private String second2TimeSecond(long second) {
        long hours = second / 3600;
        long minutes = (second % 3600) / 60;
        long seconds = second % 60;

        String hourString = "";
        String minuteString = "";
        String secondString = "";

        if (hours < 10) {
            hourString = "0";
            if (hours == 0) {
                hourString += "0";
            } else {
                hourString += String.valueOf(hours);
            }
        } else {
            hourString = String.valueOf(hours);
        }
        if (minutes < 10) {
            minuteString = "0";
            if (minutes == 0) {
                minuteString += "0";
            } else {
                minuteString += String.valueOf(minutes);
            }
        } else {
            minuteString = String.valueOf(minutes);
        }
        if (seconds < 10) {
            secondString = "0";
            if (seconds == 0) {
                secondString += "0";
            } else {
                secondString += String.valueOf(seconds);
            }
        } else {
            secondString = String.valueOf(seconds);
        }
        return hourString + ":" + minuteString + ":" + secondString;
    }
}