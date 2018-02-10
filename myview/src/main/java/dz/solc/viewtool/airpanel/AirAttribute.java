package dz.solc.viewtool.airpanel;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import dz.solc.viewtool.R;

/**
* creat_user: zhengzaihong
* Email:1096877329@qq.com
* creat_date: 2018/2/10
* creat_time: 15:03
* describe
**/
class AirAttribute {
    int panelMinHeight;
    int panelMaxHeight;

    static AirAttribute obtain(final View view, final AttributeSet attrs,
                               final int defStyleAttr, final int defStyleRes,
                               int[] attrValues, int attrMinIndex, int attrMaxIndex) {
        TypedArray a = view.getContext().obtainStyledAttributes(attrs, attrValues, defStyleAttr, defStyleRes);
        Resources resources = view.getResources();
        int minHeight = a.getDimensionPixelOffset(attrMinIndex,
                resources.getDimensionPixelOffset(R.dimen.airPanelMinHeight));
        int maxHeight = a.getDimensionPixelOffset(attrMaxIndex,
                resources.getDimensionPixelOffset(R.dimen.airPanelMaxHeight));
        a.recycle();
        AirAttribute attribute = new AirAttribute();
        attribute.panelMinHeight = minHeight;
        attribute.panelMaxHeight = maxHeight;
        return attribute;
    }
}
