package dz.solc.viewtool.view.radio;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.util.Log;

import dz.solc.viewtool.R;

import static dz.solc.viewtool.view.radio.RadioButtonViewConfig.RadiusType.ALL_RADIUS;
import static dz.solc.viewtool.view.radio.RadioButtonViewConfig.RadiusType.LEFT_BOTTOM_RADIUS;
import static dz.solc.viewtool.view.radio.RadioButtonViewConfig.RadiusType.LEFT_TOP_BOTTOM_RADIUS;
import static dz.solc.viewtool.view.radio.RadioButtonViewConfig.RadiusType.LEFT_TOP_RADIUS;
import static dz.solc.viewtool.view.radio.RadioButtonViewConfig.RadiusType.NONE_RADIUS;
import static dz.solc.viewtool.view.radio.RadioButtonViewConfig.RadiusType.RIGHT_BOOTOM_RADIUS;
import static dz.solc.viewtool.view.radio.RadioButtonViewConfig.RadiusType.RIGHT_TOP_BOOTOM_RADIUS;
import static dz.solc.viewtool.view.radio.RadioButtonViewConfig.RadiusType.RIGHT_TOP_RADIUS;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/13
 * creat_time: 10:03
 * describe 支持圆角和背景切换的 RadioButton
 **/

@SuppressWarnings("all")
public class RadioButtonPlus extends AppCompatRadioButton {

    private RadioButtonViewConfig configBean;
    private Paint mPaint = new Paint();

    public RadioButtonPlus(Context context) {
        this(context, null);
    }

    public RadioButtonPlus(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.configBean = new RadioButtonViewConfig();
        // 读取属性值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadioButtonPlusStyle);
        configBean.setNormalBgColor(ta.getColor(R.styleable.RadioButtonPlusStyle_rbp_normalBgColor, Color.TRANSPARENT));
        configBean.setPressedBgColor(ta.getColor(R.styleable.RadioButtonPlusStyle_rbp_pressedBgColor, configBean.getPressedBgColor()));
        configBean.setStrokeWidth(ta.getDimensionPixelSize(R.styleable.RadioButtonPlusStyle_rbp_strokeWidth, 0));
        configBean.setStrokeColor(ta.getColor(R.styleable.RadioButtonPlusStyle_rbp_strokeColor, Color.TRANSPARENT));
        configBean.setCornerRadius(ta.getDimensionPixelSize(R.styleable.RadioButtonPlusStyle_rbp_cornerRadius, 0));
        configBean.setFollowTextColor(ta.getBoolean(R.styleable.RadioButtonPlusStyle_rbp_followTextColor, false));

        configBean.setNormalTextColor(ta.getColor(R.styleable.RadioButtonPlusStyle_rbp_normalTextColor, configBean.getNormalTextColor()));
        configBean.setPressedTextColor(ta.getColor(R.styleable.RadioButtonPlusStyle_rbp_pressedTextColor, configBean.getPressedTextColor()));

        configBean.setShowAnimation(ta.getBoolean(R.styleable.RadioButtonPlusStyle_rbp_showAnimation, false));

        int radiusType = ta.getInt(R.styleable.RadioButtonPlusStyle_rbp_radius_type, -1);

        if (radiusType >= 0) {
            RadioButtonViewConfig.RadiusType[] enumType = RadioButtonViewConfig.RadiusType.values();
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
        setTextColor(createColorStateList(configBean.getNormalTextColor(), configBean.getPressedTextColor()));
        // 设置边框线的颜色, 如果声明为边框跟随文字颜色且当前边框颜色与文字颜色不同时重新设置边框颜色
        if (configBean.isFollowTextColor() && configBean.getStrokeColor() != getCurrentTextColor())
            configBean.setStrokeColor(getCurrentTextColor());
    }


    /**
     * 设置样式配置参数
     *
     * @param configBean
     */
    public void setConfig(RadioButtonViewConfig configBean) {
        if (null == configBean) {
            Log.e(this.getClass().getSimpleName(), "<<-------------配置文件不能为空-------------->>");
            return;
        }
        this.configBean = configBean;
        // 设置背景
        initView();
    }


    private Drawable getPressedSelector(RadioButtonViewConfig config) {
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

        // 设置状态选择器过度动画/渐变选择器/渐变动画

        if (config.isShowAnimation()) {
            drawable.setEnterFadeDuration(config.getAnimationTime());
            drawable.setExitFadeDuration(config.getAnimationTime());
        }


        return drawable;
    }

    private GradientDrawable createShape(int color, RadioButtonViewConfig config) {
        GradientDrawable drawable = new GradientDrawable();
        RadioButtonViewConfig.RadiusType radiusType = config.getRadiusType();
        float radius = config.getCornerRadius();
        if (radiusType == LEFT_TOP_BOTTOM_RADIUS) {
            //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
            drawable.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius, radius});
        } else if (radiusType == LEFT_TOP_RADIUS) {
            drawable.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, 0, 0});
        } else if (radiusType == LEFT_BOTTOM_RADIUS) {
            drawable.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, radius, radius});
        } else if (radiusType == RIGHT_TOP_BOOTOM_RADIUS) {
            drawable.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
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
    private ColorStateList createColorStateList(int normal, int pressed) {

        int[] colors = new int[]{normal, pressed, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{-android.R.attr.state_checked};
        states[1] = new int[]{android.R.attr.state_checked};
        states[2] = new int[]{};
        ColorStateList colorStateList = new ColorStateList(states, colors);

        return colorStateList;
    }

}