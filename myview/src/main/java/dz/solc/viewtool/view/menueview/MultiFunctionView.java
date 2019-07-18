package dz.solc.viewtool.view.menueview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import dz.solc.viewtool.R;
import dz.solc.viewtool.view.menueview.config.MultiFunctionConfig;
import dz.solc.viewtool.view.textview.config.FilletConfig;

import static dz.solc.viewtool.view.menueview.config.MultiFunctionConfig.RadiusType.ALL_RADIUS;
import static dz.solc.viewtool.view.menueview.config.MultiFunctionConfig.RadiusType.LEFT_BOTTOM_RADIUS;
import static dz.solc.viewtool.view.menueview.config.MultiFunctionConfig.RadiusType.LEFT_TOP_BOTTOM_RADIUS;
import static dz.solc.viewtool.view.menueview.config.MultiFunctionConfig.RadiusType.LEFT_TOP_RADIUS;
import static dz.solc.viewtool.view.menueview.config.MultiFunctionConfig.RadiusType.NONE_RADIUS;
import static dz.solc.viewtool.view.menueview.config.MultiFunctionConfig.RadiusType.RIGHT_BOOTOM_RADIUS;
import static dz.solc.viewtool.view.menueview.config.MultiFunctionConfig.RadiusType.RIGHT_TOP_BOOTOM_RADIUS;
import static dz.solc.viewtool.view.menueview.config.MultiFunctionConfig.RadiusType.RIGHT_TOP_RADIUS;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2019/4/24 0024
 * creat_time: 17:55
 * describe: 仿ios UISegmentedControl自定义控件 支持各种不同维度的圆角,保留chekBox全部特性
 * 选中和非选中文字需要改变颜色 和平常使用checkBox 定义xml文字颜色一样或者代码方式动态setTextColor方式或者通过配置文件即可
 * <p>
 * 单独使用时可以减少需要设置背景样式的按钮的xml样式定制，当该控件配合listView 或者RecycleView使用时 想要控件之间有分割，可以通过
 * 设置 divider 或者 DividerItemDecoration达到效果
 * <p>
 * 单独使用建议使用本库提供的FilletView 控件
 **/

@SuppressLint("AppCompatCustomView")
public class MultiFunctionView extends CheckBox {

    private MultiFunctionConfig configBean;
    private Paint mPaint = new Paint();

    public MultiFunctionView(Context context) {
        this(context, null);
    }

    public MultiFunctionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiFunctionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.configBean = new MultiFunctionConfig();
        // 读取属性值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MultiFunctionView);
        configBean.setNormalBgColor(ta.getColor(R.styleable.MultiFunctionView_normalBgColor, Color.TRANSPARENT));
        configBean.setPressedBgColor(ta.getColor(R.styleable.MultiFunctionView_pressedBgColor, configBean.getPressedBgColor()));
        configBean.setStrokeWidth(ta.getDimensionPixelSize(R.styleable.MultiFunctionView_strokeWidth, 0));
        configBean.setStrokeColor(ta.getColor(R.styleable.MultiFunctionView_strokeColor, Color.TRANSPARENT));
        configBean.setCornerRadius(ta.getDimensionPixelSize(R.styleable.MultiFunctionView_cornerRadius, 0));
        configBean.setFollowTextColor(ta.getBoolean(R.styleable.MultiFunctionView_followTextColor, false));

        configBean.setNormalTextColor(ta.getColor(R.styleable.MultiFunctionView_normalTextColor, configBean.getNormalTextColor()));
        configBean.setPressedTextColor(ta.getColor(R.styleable.MultiFunctionView_pressedTextColor, configBean.getPressedTextColor()));

        configBean.setShowAnimation(ta.getBoolean(R.styleable.MultiFunctionView_showAnimation, false));

        int radiusType = ta.getInt(R.styleable.MultiFunctionView_radius_type, 0);

        if (radiusType >= 0) {
            MultiFunctionConfig.RadiusType[] enumType = MultiFunctionConfig.RadiusType.values();
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
    public void setConfig(MultiFunctionConfig configBean) {
        this.configBean = configBean;
        // 设置背景
        initView();
    }


    public static Drawable getPressedSelector(MultiFunctionConfig config) {
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
        drawable.setEnterFadeDuration(500);
        drawable.setExitFadeDuration(500);

        return drawable;
    }

    public static GradientDrawable createShape(int color, MultiFunctionConfig config) {
        GradientDrawable drawable = new GradientDrawable();
        MultiFunctionConfig.RadiusType radiusType = config.getRadiusType();
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
    public static ColorStateList createColorStateList(int normal, int pressed) {

        int[] colors = new int[]{normal, pressed, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{-android.R.attr.state_checked};
        states[1] = new int[]{android.R.attr.state_checked};
        states[2] = new int[]{};
        ColorStateList colorStateList = new ColorStateList(states, colors);

        return colorStateList;
    }

}