package dz.solc.viewtool.airpanel;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2018/2/10
 * creat_time: 15:03
 * describe
 **/

public interface AirPanel {
    interface Sub {
        void openPanel();

        void closePanel();

        boolean isOpen();
    }

    interface Boss extends Sub {
        void setup(PanelListener panelListener);

        void setOnStateChangedListener(OnStateChangedListener listener);
    }

    interface PanelListener {
        void requestHideSoftKeyboard();
    }

    interface OnStateChangedListener {
        void onPanelStateChanged(boolean isOpen);

        void onSoftKeyboardStateChanged(boolean isOpen);
    }
}
