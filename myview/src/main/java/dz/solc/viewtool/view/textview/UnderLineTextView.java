package dz.solc.viewtool.view.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;

import dz.solc.viewtool.R;


/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2018/9/21 0021
 * creat_time: 13:41
 * describe: 下划线TextView
 **/
public class UnderLineTextView extends AppCompatTextView {

    private Rect mRect;
    private Paint mPaint;
    private int mColor;
    private float density;
    private float mStrokeWidth;
    private float marginTop;

    public UnderLineTextView(Context context) {
        this(context, null, 0);
    }

    public UnderLineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //获取屏幕密度
        density = context.getResources().getDisplayMetrics().density;
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UnderlinedTextView, defStyleAttr, 0);
        mColor = array.getColor(R.styleable.UnderlinedTextView_underlineColor, 0xFFFF0000);
        mStrokeWidth = array.getDimension(R.styleable.UnderlinedTextView_underlineWidth, density * 2);
        array.recycle();

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mStrokeWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //得到TextView显示有多少行
        int count = getLineCount();

        //得到TextView的布局
        final Layout layout = getLayout();

        float x_start;
        float x_stop;
        float x_diff;
        float firstCharInLine, lastCharInLine;


        for (int i = 0; i < count; i++) {

            //getLineBounds得到这一行的外包矩形,这个字符的顶部Y坐标就是rect的top 底部Y坐标就是rect的bottom
            int baseline = getLineBounds(i, mRect);
            firstCharInLine = layout.getLineStart(i);
            lastCharInLine = layout.getLineEnd(i);

            //要得到这个字符的左边X坐标 用layout.getPrimaryHorizontal
            //得到字符的右边X坐标用layout.getSecondaryHorizontal
            x_start = layout.getPrimaryHorizontal((int) firstCharInLine);
            x_diff = layout.getPrimaryHorizontal((int) (firstCharInLine + 1)) - x_start;
            x_stop = layout.getPrimaryHorizontal((int) (lastCharInLine - 1)) + x_diff;

            float startY = baseline + mStrokeWidth;
            float stopY = baseline + mStrokeWidth;

            canvas.drawLine(x_start, startY, x_stop, stopY, mPaint);

        }
        super.onDraw(canvas);

    }

    public int getUnderLineColor() {
        return mColor;
    }

    public void setUnderLineColor(int mColor) {
        this.mColor = mColor;
        invalidate();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom + (int) mStrokeWidth);
    }

    public float getUnderlineWidth() {
        return mStrokeWidth;
    }

    public void setUnderlineWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        invalidate();
    }

}
