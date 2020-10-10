package dz.solc.viewtool.view.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import dz.solc.viewtool.R;

import static dz.solc.viewtool.view.viewgroup.ViewGroupConfig.RadiusType.ALL_RADIUS;
import static dz.solc.viewtool.view.viewgroup.ViewGroupConfig.RadiusType.LEFT_BOTTOM_RADIUS;
import static dz.solc.viewtool.view.viewgroup.ViewGroupConfig.RadiusType.LEFT_TOP_BOTTOM_RADIUS;
import static dz.solc.viewtool.view.viewgroup.ViewGroupConfig.RadiusType.LEFT_TOP_RADIUS;
import static dz.solc.viewtool.view.viewgroup.ViewGroupConfig.RadiusType.NONE_RADIUS;
import static dz.solc.viewtool.view.viewgroup.ViewGroupConfig.RadiusType.RIGHT_BOOTOM_RADIUS;
import static dz.solc.viewtool.view.viewgroup.ViewGroupConfig.RadiusType.RIGHT_TOP_BOOTOM_RADIUS;
import static dz.solc.viewtool.view.viewgroup.ViewGroupConfig.RadiusType.RIGHT_TOP_RADIUS;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/12
 * create_time: 19:09
 * describe 支持圆角的 线性布局
 **/
@SuppressWarnings("all")
public class RadiusLinearLayout extends LinearLayout {

    private static final String TAG = RadiusLinearLayout.class.getSimpleName();
    private ViewGroupConfig configBean = new ViewGroupConfig();
    private Paint mPaint = new Paint();

    public RadiusLinearLayout(Context context) {
        this(context, null);
    }

    public RadiusLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadiusLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 读取属性值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewGroupConfig);
        configBean.setNormalBgColor(ta.getColor(R.styleable.ViewGroupConfig_vg_normalBgColor, Color.TRANSPARENT));
        configBean.setPressedBgColor(ta.getColor(R.styleable.ViewGroupConfig_vg_pressedBgColor, configBean.getPressedBgColor()));
        configBean.setNormalStrokeWidth(ta.getDimensionPixelSize(R.styleable.ViewGroupConfig_vg_normalStrokeWidth, 0));
        configBean.setPressStrokeWidth(ta.getDimensionPixelSize(R.styleable.ViewGroupConfig_vg_pressStrokeWidth, 0));
        configBean.setNormalStrokeColor(ta.getColor(R.styleable.ViewGroupConfig_vg_normalStrokeColor, Color.TRANSPARENT));
        configBean.setPressStrokeColor(ta.getColor(R.styleable.ViewGroupConfig_vg_pressStrokeColor, Color.TRANSPARENT));
        configBean.setCornerRadius(ta.getDimensionPixelSize(R.styleable.ViewGroupConfig_vg_cornerRadius, 0));

        configBean.setShowAnimation(ta.getBoolean(R.styleable.ViewGroupConfig_vg_showAnimation, false));
        configBean.setAnimationTime(ta.getInteger(R.styleable.ViewGroupConfig_vg_animationTime, 500));

        int radiusType = ta.getInt(R.styleable.ViewGroupConfig_vg_radius_type, -1);

        if (radiusType >= 0) {
            ViewGroupConfig.RadiusType[] enumType = ViewGroupConfig.RadiusType.values();
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

        setBackground(getPressedSelector(configBean));
    }

    /**
     * 设置样式配置参数
     *
     * @param configBean
     */
    public void setConfig(ViewGroupConfig configBean) {

        if (null == configBean) {
            Log.e(TAG, "<<-------------配置文件不能为空-------------->>");
            return;
        }

        this.configBean = configBean;
        // 设置背景
        initView();
    }

    public ViewGroupConfig getConfig(){
        return configBean;
    }



    private Drawable getPressedSelector(ViewGroupConfig config) {
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
            drawable.setEnterFadeDuration(configBean.getAnimationTime());
            drawable.setExitFadeDuration(configBean.getAnimationTime());
        }
        return drawable;
    }

    private GradientDrawable createShape(int color, ViewGroupConfig config, boolean isNormal) {
        GradientDrawable drawable = new GradientDrawable();
        ViewGroupConfig.RadiusType radiusType = config.getRadiusType();
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

}
