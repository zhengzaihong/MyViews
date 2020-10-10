package dz.solc.viewtool.airpanel;

import android.app.Activity;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2018/2/10
 * create_time: 15:03
 * describe
 **/

interface Contract extends AirPanel.Boss {
    interface Panel extends AirPanel.Boss {
        void adjustPanelHeight(int heightMeasureSpec);
    }

    interface Helper extends Panel, AirPanel.PanelListener {
        int calculateHeightMeasureSpec(int heightMeasureSpec);

        void setup(Activity activity);
    }
}
