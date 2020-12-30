package dz.solc.viewtool.view.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.appcompat.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;

import dz.solc.viewtool.R;


/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2018/9/21 0021
 * create_time: 13:41
 * describe: 下划线TextView
 * 
 * 2020-11-1 更新并更名为 LineTextView
 * 1.支持下滑线
 * 2.支持删除线
 **/
public class LineTextView extends AppCompatTextView {

    private Rect mRect =  new Rect();
    private Paint mPaint = new Paint();
    private int underLineCorlor;
    private boolean enableDelectLine;
    private float density;
    private float mStrokeWidth;


    public LineTextView(Context context) {
        this(context, null, 0);
    }

    public LineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //获取屏幕密度
        density = context.getResources().getDisplayMetrics().density;
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineTextView, defStyleAttr, 0);
        underLineCorlor = array.getColor(R.styleable.LineTextView_under_line_color, 0xFFFF0000);
        enableDelectLine = array.getBoolean(R.styleable.LineTextView_enable_delect_line, false);
        mStrokeWidth = array.getDimension(R.styleable.LineTextView_line_height, density * 2);
        
        
        array.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(underLineCorlor);
        mPaint.setStrokeWidth(mStrokeWidth);
        if(enableDelectLine){
            this.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
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

    public void setUnderLineColor(int underLineCorlor) {
        this.underLineCorlor = underLineCorlor;
        freshUi();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom + (int) mStrokeWidth);
    }

    public void setUnderlineHeight(float height) {
        this.mStrokeWidth = height;
        freshUi();
    }

    private void freshUi(){
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(underLineCorlor);
        mPaint.setStrokeWidth(mStrokeWidth);
        invalidate();
    }

}
