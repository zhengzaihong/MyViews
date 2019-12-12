package dz.solc.viewtool.view.editview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import dz.solc.viewtool.R;
import dz.solc.viewtool.view.editview.config.FilletEditViewConfig;

import static dz.solc.viewtool.view.editview.config.FilletEditViewConfig.RadiusType.ALL_RADIUS;
import static dz.solc.viewtool.view.editview.config.FilletEditViewConfig.RadiusType.LEFT_BOTTOM_RADIUS;
import static dz.solc.viewtool.view.editview.config.FilletEditViewConfig.RadiusType.LEFT_TOP_BOTTOM_RADIUS;
import static dz.solc.viewtool.view.editview.config.FilletEditViewConfig.RadiusType.LEFT_TOP_RADIUS;
import static dz.solc.viewtool.view.editview.config.FilletEditViewConfig.RadiusType.NONE_RADIUS;
import static dz.solc.viewtool.view.editview.config.FilletEditViewConfig.RadiusType.RIGHT_BOOTOM_RADIUS;
import static dz.solc.viewtool.view.editview.config.FilletEditViewConfig.RadiusType.RIGHT_TOP_BOOTOM_RADIUS;
import static dz.solc.viewtool.view.editview.config.FilletEditViewConfig.RadiusType.RIGHT_TOP_RADIUS;


/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2019/4/24 0024
 * creat_time: 17:55
 * describe:支持任意边圆角的编辑EditText 可设置背景和前景色,
 * 保留EditText全部特性，减少开发中大量的编写xml配置文件来改变背景色文字颜色等，
 **/

@SuppressWarnings("all")
public class FilletEditView extends AppCompatEditText {

    private FilletEditViewConfig configBean = new FilletEditViewConfig();
    private Paint mPaint = new Paint();

    public FilletEditView(Context context) {
        this(context, null);
    }

    public FilletEditView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 读取属性值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FilletEditViewConfig);
        configBean.setNormalBgColor(ta.getColor(R.styleable.FilletEditViewConfig_e_normalBgColor, Color.TRANSPARENT));
        configBean.setPressedBgColor(ta.getColor(R.styleable.FilletEditViewConfig_e_pressedBgColor, configBean.getPressedBgColor()));
        configBean.setNormalStrokeWidth(ta.getDimensionPixelSize(R.styleable.FilletEditViewConfig_e_normalStrokeWidth, 0));
        configBean.setPressStrokeWidth(ta.getDimensionPixelSize(R.styleable.FilletEditViewConfig_e_pressStrokeWidth, 0));
        configBean.setNormalStrokeColor(ta.getColor(R.styleable.FilletEditViewConfig_e_normalStrokeColor, Color.TRANSPARENT));
        configBean.setPressStrokeColor(ta.getColor(R.styleable.FilletEditViewConfig_e_pressStrokeColor, Color.TRANSPARENT));
        configBean.setCornerRadius(ta.getDimensionPixelSize(R.styleable.FilletEditViewConfig_e_cornerRadius, 0));
        configBean.setFollowTextColor(ta.getBoolean(R.styleable.FilletEditViewConfig_e_followTextColor, false));

        configBean.setNormalTextColor(ta.getColor(R.styleable.FilletEditViewConfig_e_normalTextColor, configBean.getNormalTextColor()));
        configBean.setPressedTextColor(ta.getColor(R.styleable.FilletEditViewConfig_e_pressedTextColor, configBean.getPressedTextColor()));
        configBean.setShowAnimation(ta.getBoolean(R.styleable.FilletEditViewConfig_e_showAnimation, false));

        int radiusType = ta.getInt(R.styleable.FilletEditViewConfig_e_radius_type, -1);

        if (radiusType >= 0) {
            FilletEditViewConfig.RadiusType[] enumType = FilletEditViewConfig.RadiusType.values();
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
        mPaint.setStrokeWidth(configBean.getNormalStrokeWidth());

        setBackgroundDrawable(getPressedSelector(configBean));
        setTextColor(createColorStateList(configBean.getNormalTextColor(), configBean.getPressedTextColor()
                , configBean.getPressedTextColor(), configBean.getNormalTextColor()));
        // 设置边框线的颜色, 如果声明为边框跟随文字颜色且当前边框颜色与文字颜色不同时重新设置边框颜色
        if (configBean.isFollowTextColor() && configBean.getNormalStrokeColor() != getCurrentTextColor())
            configBean.setNormalStrokeColor(getCurrentTextColor());

    }

    /**
     * 设置样式配置参数
     *
     * @param configBean
     */
    public void setConfig(FilletEditViewConfig configBean) {
        this.configBean = configBean;
        // 设置背景
        initView();
    }


    private Drawable getPressedSelector(FilletEditViewConfig config) {
        //TODO 目前只做了 按下和抬起状态

        Drawable normal = createShape(config.getNormalBgColor(), config, true);
        Drawable pressed = createShape(config.getPressedBgColor(), config, false);
        StateListDrawable drawable = new StateListDrawable();

        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        drawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{android.R.attr.state_hovered}, pressed);
        drawable.addState(new int[]{android.R.attr.state_focused}, pressed);
        drawable.addState(new int[]{android.R.attr.state_checked}, pressed);

        drawable.addState(new int[]{}, normal);

        if (config.isShowAnimation()) {
            // 设置状态选择器过度动画/渐变选择器/渐变动画
            drawable.setEnterFadeDuration(500);
            drawable.setExitFadeDuration(500);

        }
        return drawable;
    }

    private GradientDrawable createShape(int color, FilletEditViewConfig config, boolean isNormal) {
        GradientDrawable drawable = new GradientDrawable();
        FilletEditViewConfig.RadiusType radiusType = config.getRadiusType();
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
        if (isNormal) {
            drawable.setStroke(config.getNormalStrokeWidth(), config.getNormalStrokeColor());
        } else {
            drawable.setStroke(config.getPressStrokeWidth(), config.getPressStrokeColor());
        }

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