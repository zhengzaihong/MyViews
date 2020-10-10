package dz.solc.viewtool.view.imageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import dz.solc.viewtool.R;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2017/12/6
 * create_time: 10:28
 * describe:支持圆角的view
 **/
@SuppressLint("AppCompatCustomView")
public class RoundIconView extends ImageView {

    private final RectF roundRect = new RectF();
    private float corner = 5;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();


    public RoundIconView(Context context) {
        this(context, null);
    }

    public RoundIconView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.RoundIconView);

        corner = ta.getDimensionPixelSize(R.styleable.RoundIconView_corner, (int) corner);

        ta.recycle();
        init();
    }


    private void init() {
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
        float density = getResources().getDisplayMetrics().density;
        corner = corner * density;
    }

    public void setCorner(float corner) {
        this.corner = corner;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0, 0, w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(roundRect, corner, corner, zonePaint);
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }

}
