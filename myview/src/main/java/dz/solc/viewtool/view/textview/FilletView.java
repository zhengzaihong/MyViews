package dz.solc.viewtool.view.textview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import dz.solc.viewtool.R;
import dz.solc.viewtool.view.textview.config.FilletConfig;

import static dz.solc.viewtool.view.textview.config.FilletConfig.RadiusType.ALL_RADIUS;
import static dz.solc.viewtool.view.textview.config.FilletConfig.RadiusType.LEFT_BOTTOM_RADIUS;
import static dz.solc.viewtool.view.textview.config.FilletConfig.RadiusType.LEFT_TOP_BOTTOM_RADIUS;
import static dz.solc.viewtool.view.textview.config.FilletConfig.RadiusType.LEFT_TOP_RADIUS;
import static dz.solc.viewtool.view.textview.config.FilletConfig.RadiusType.NONE_RADIUS;
import static dz.solc.viewtool.view.textview.config.FilletConfig.RadiusType.RIGHT_BOOTOM_RADIUS;
import static dz.solc.viewtool.view.textview.config.FilletConfig.RadiusType.RIGHT_TOP_BOOTOM_RADIUS;
import static dz.solc.viewtool.view.textview.config.FilletConfig.RadiusType.RIGHT_TOP_RADIUS;


/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2019/4/24 0024
 * creat_time: 17:55
 * describe:支持任意边圆角的View,保留TextView全部特性，减少开发中大量的却写xml配置文件来改变背景色文字颜色等，
 **/

@SuppressWarnings("all")
public class FilletView extends AppCompatTextView {

    private static final String TAG = FilletView.class.getSimpleName();

    private FilletConfig configBean = new FilletConfig();
    private Paint mPaint = new Paint();

    public FilletView(Context context) {
        this(context, null);
    }

    public FilletView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 读取属性值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FilletTextViewConfig);
        configBean.setNormalBgColor(ta.getColor(R.styleable.FilletTextViewConfig_f_normalBgColor, Color.TRANSPARENT));
        configBean.setPressedBgColor(ta.getColor(R.styleable.FilletTextViewConfig_f_pressedBgColor, configBean.getPressedBgColor()));
        configBean.setStrokeWidth(ta.getDimensionPixelSize(R.styleable.FilletTextViewConfig_f_strokeWidth, 0));
        configBean.setStrokeColor(ta.getColor(R.styleable.FilletTextViewConfig_f_strokeColor, Color.TRANSPARENT));
        configBean.setCornerRadius(ta.getDimensionPixelSize(R.styleable.FilletTextViewConfig_f_cornerRadius, 0));
        configBean.setFollowTextColor(ta.getBoolean(R.styleable.FilletTextViewConfig_f_followTextColor, false));

        configBean.setNormalTextColor(ta.getColor(R.styleable.FilletTextViewConfig_f_normalTextColor, configBean.getNormalTextColor()));
        configBean.setPressedTextColor(ta.getColor(R.styleable.FilletTextViewConfig_f_pressedTextColor, configBean.getPressedTextColor()));
        configBean.setShowAnimation(ta.getBoolean(R.styleable.FilletTextViewConfig_f_showAnimation, false));
        configBean.setShowAnimationTime(ta.getInteger(R.styleable.FilletTextViewConfig_f_animationTime, 500));

        int radiusType = ta.getInt(R.styleable.FilletTextViewConfig_f_radius_type, -1);

        if (radiusType >= 0) {
            FilletConfig.RadiusType[] enumType = FilletConfig.RadiusType.values();
            for (int i = 0; i < enumType.length; i++) {
                if (radiusType == enumType[i].ordinal()) {
                    configBean.setRadiusType(enumType[i]);
                    break;
                }
            }
        }
        ta.recycle();

        initView();
    }



    private void initView() {
        // 初始化画笔
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        // 线宽
        mPaint.setStrokeWidth(configBean.getStrokeWidth());

        setBackground(getPressedSelector(configBean));
        setTextColor(createColorStateList(configBean.getNormalTextColor(), configBean.getPressedTextColor()
                , configBean.getPressedTextColor(), configBean.getNormalTextColor()));
        // 设置边框线的颜色, 如果声明为边框跟随文字颜色且当前边框颜色与文字颜色不同时重新设置边框颜色
        if (configBean.isFollowTextColor() && configBean.getStrokeColor() != getCurrentTextColor())
            configBean.setStrokeColor(getCurrentTextColor());
    }

    /**
     * 设置样式配置参数
     *
     * @param configBean
     */
    public void setConfig(FilletConfig configBean) {

        if (null == configBean) {
            Log.e(TAG,"<<-------------配置文件不能为空-------------->>");
            return;
        }
        // 设置背景
        initView();
    }


    private Drawable getPressedSelector(FilletConfig config) {
        //TODO 目前只做了 按下和抬起状态
        Drawable pressed = createShape(config.getPressedBgColor(), config);
        Drawable normal = createShape(config.getNormalBgColor(), config);
        StateListDrawable drawable = new StateListDrawable();

        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        drawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{android.R.attr.state_selected}, pressed);
        drawable.addState(new int[]{android.R.attr.state_checked}, pressed);
        drawable.addState(new int[]{}, normal);

        if (config.isShowAnimation()) {
            // 设置状态选择器过度动画/渐变选择器/渐变动画
            drawable.setEnterFadeDuration(config.getShowAnimationTime());
            drawable.setExitFadeDuration(config.getShowAnimationTime());

        }
        return drawable;
    }

    private GradientDrawable createShape(int color, FilletConfig config) {
        GradientDrawable drawable = new GradientDrawable();
        FilletConfig.RadiusType radiusType = config.getRadiusType();
        float radius = config.getCornerRadius();
        if (radiusType == LEFT_TOP_BOTTOM_RADIUS) {
            //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
            drawable.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius, radius});
        } else if (radiusType == RIGHT_TOP_BOOTOM_RADIUS) {
            drawable.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
        } else if (radiusType == LEFT_BOTTOM_RADIUS) {
            drawable.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, radius, radius});
        } else if (radiusType == LEFT_TOP_RADIUS) {
            drawable.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, 0, 0});
        } else if (radiusType == RIGHT_TOP_RADIUS) {
            drawable.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, 0, 0});
        } else if (radiusType == RIGHT_BOOTOM_RADIUS) {
            drawable.setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, 0, 0});
        } else if (radiusType == ALL_RADIUS) {
            //设置4个角的弧度
            drawable.setCornerRadius(radius);
        } else if (radiusType == NONE_RADIUS) {
            drawable.setCornerRadius(0);
        }
        // 设置背景颜色
        drawable.setColor(color);
        // 设置边框颜色
        drawable.setStroke(config.getStrokeWidth(), config.getStrokeColor());
        return drawable;
    }


    /**
     * 对TextView设置不同状态时其文字颜色。
     */
    private ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable, normal};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

}