package dz.solc.viewtool.view.marqueeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2018/5/7
 * creat_time: 13:13
 * describe:跑马灯
 **/

@SuppressLint("AppCompatCustomView")
public class MarqueeTextView extends AppCompatTextView {
    private boolean isScroll;

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createView();
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createView();
    }


    public MarqueeTextView(Context context) {
        super(context);
        createView();
    }

    private void createView() {
        setEllipsize(TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
        setFocusableInTouchMode(true);
        setSelected(true);
        setSingleLine();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if (focused) {
            super.onWindowFocusChanged(focused);
        }
    }

    @Override
    public boolean isFocused() {
        return isScroll;
    }

    public void scroll(boolean b) {
        isScroll = b;
        if (isScroll) {
            super.onWindowFocusChanged(true);
        } else {
            super.onWindowFocusChanged(false);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int textWidth = getWidth() - getCompoundPaddingLeft() -
                getCompoundPaddingRight();
        final float lineWidth = this.getLayout().getLineWidth(0);
        final float gap = textWidth / 3.0f;
        float mGhostStart = lineWidth - textWidth + gap;
        float mMaxScroll = mGhostStart + textWidth;
        float mGhostOffset = lineWidth + gap;
        float mFadeStop = lineWidth + textWidth / 6.0f;
        float mMaxFadeScroll = mGhostStart + lineWidth + lineWidth;
        Log.e("point_X", mMaxFadeScroll + "");
        super.onDraw(canvas);
    }
}
