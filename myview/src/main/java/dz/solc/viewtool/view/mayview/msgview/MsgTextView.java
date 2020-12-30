package dz.solc.viewtool.view.mayview.msgview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TabWidget;

import androidx.appcompat.widget.AppCompatTextView;

import dz.solc.viewtool.view.navigation.BottomViewConfig;


/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/10/13
 * create_time: 10:44
 * describe: 新增加带消息数量的控件
 **/
@SuppressWarnings("all")
public class MsgTextView extends AppCompatTextView {

    private Context context;
    private View target;
    private boolean isShown;
    private Drawable badgeBg;
    private int targetTabIndex;

    private BottomViewConfig parmasConfig = new BottomViewConfig();

    public MsgTextView(Context context) {
        this(context, (AttributeSet) null, android.R.attr.textViewStyle);
    }

    public MsgTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public MsgTextView(Context context, View target) {
        this(context, null, android.R.attr.textViewStyle, target, 0);
    }

    public MsgTextView(Context context, TabWidget target, int index) {
        this(context, null, android.R.attr.textViewStyle, target, index);
    }

    public MsgTextView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, null, 0);
    }

    public MsgTextView(Context context, AttributeSet attrs, int defStyle, View target, int tabIndex) {
        super(context, attrs, defStyle);
        init(context, target, tabIndex);
    }

    private void init(Context context, View target, int tabIndex) {

        this.context = context;
        this.target = target;
        this.targetTabIndex = tabIndex;
        this.isShown = false;
        applyTo(this.target);
    }

    private void applyTo(View target) {

        ViewGroup.LayoutParams lp = target.getLayoutParams();
        ViewParent parent = target.getParent();
        FrameLayout container = new FrameLayout(context);

        if (target instanceof TabWidget) {
            target = ((TabWidget) target).getChildTabViewAt(targetTabIndex);
            this.target = target;

            ((ViewGroup) target).addView(container,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            this.setVisibility(View.GONE);
            container.addView(this);

        } else {
            ViewGroup group = (ViewGroup) parent;
            int index = group.indexOfChild(target);

            group.removeView(target);
            group.addView(container, index, lp);

            container.addView(target);

            this.setVisibility(View.GONE);
            container.addView(this);
            group.invalidate();

        }

    }

    private Drawable getDefaultBackground() {
        int r = dipToPixels(parmasConfig.getMsgBgCorner());
        //外矩形 左上、右上、右下、左下的圆角半径
        float[] outerR = new float[]{r, r, r, r, r, r, r, r};
        RoundRectShape roundRectShape = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
        drawable.getPaint().setColor(parmasConfig.getMsgBgColor());
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        return drawable;
    }

    private void applyLayoutParams() {

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (parmasConfig.getMsgPosition()) {
            case POSITION_TOP_LEFT:
                lp.gravity = Gravity.LEFT | Gravity.TOP;
                lp.setMargins(parmasConfig.getMargin(), parmasConfig.getMargin(), 0, 0);
                break;
            case POSITION_TOP_RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.TOP;
                lp.setMargins(0, 0, parmasConfig.getMargin(), 0);
                break;
            case POSITION_BOTTOM_LEFT:
                lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
                lp.setMargins(parmasConfig.getMargin(), 0, 0, parmasConfig.getMargin());
                break;
            case POSITION_BOTTOM_RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                lp.setMargins(0, 0, parmasConfig.getMargin(), parmasConfig.getMargin());
                break;
            case POSITION_CENTER:
                lp.gravity = Gravity.CENTER;
                lp.setMargins(0, 0, 0, 0);
                break;
            default:
                break;
        }

        setLayoutParams(lp);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setTypeface(Typeface.DEFAULT_BOLD);
        int paddingPixels = dipToPixels(parmasConfig.getLeftRightPadding());
        setPadding(paddingPixels, 0, paddingPixels, 0);
        setTextColor(parmasConfig.getMsgTextColor());
        setGravity(Gravity.CENTER);
        setTextSize(parmasConfig.getMsgTextSize());
        show();
    }

    public View getTarget() {
        return target;
    }

    public void setConfig(BottomViewConfig config) {
        if(null==config){
            return;
        }
        this.parmasConfig = config;
        invalidate();
    }

    @Override
    public boolean isShown() {
        return isShown;
    }

    public void show() {
        show(false, null);
    }

    public void show(Animation anim) {
        show(true, anim);
    }

    public void hide() {
        hide(false, null);
    }

    public void hide(Animation anim) {
        hide(true, anim);
    }


    private void show(boolean animate, Animation anim) {
        if (getBackground() == null) {
            if (badgeBg == null) {
                badgeBg = getDefaultBackground();
            }
            setBackgroundDrawable(badgeBg);
        }
        applyLayoutParams();

        if (animate) {
            this.startAnimation(anim);
        }
        this.setVisibility(View.VISIBLE);
        isShown = true;
    }

    private void hide(boolean animate, Animation anim) {
        this.setVisibility(View.GONE);
        if (animate) {
            this.startAnimation(anim);
        }
        isShown = false;
    }

    private void toggle(boolean animate, Animation animIn, Animation animOut) {
        if (isShown) {
            hide(animate && (animOut != null), animOut);
        } else {
            show(animate && (animIn != null), animIn);
        }
    }

    private int dipToPixels(int dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }


}
