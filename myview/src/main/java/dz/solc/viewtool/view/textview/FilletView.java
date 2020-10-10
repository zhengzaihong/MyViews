package dz.solc.viewtool.view.textview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;

import dz.solc.viewtool.R;
import dz.solc.viewtool.interfaces.MyTextWatcher;
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
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2019/4/24 0024
 * create_time: 17:55
 * describe:支持任意边圆角的View,保留TextView全部特性，减少开发中大量的却写xml配置文件来改变背景色文字颜色等，
 *
 * 2020-10-9 更新完善部分功能
 *
 * 1.支持背景渐变，渐变方向全部支持
 * 2.支持绑定EditText 控件状态切换
 *
 * todo 待完善部分，支持渐变背景则不支持按压效果
 *
 * 举例：
 *  <dz.solc.viewtool.view.textview.FilletView
 *         android:id="@+id/mFilletView"
 *         android:layout_width="@dimen/len_200"
 *         android:layout_height="@dimen/len_50"
 *         android:layout_marginLeft="@dimen/len_10"
 *         android:layout_marginRight="@dimen/len_10"
 *         android:gravity="center"
 *         android:text="渐变TextView"
 *         app:f_usegradient="true"
 *         app:f_startColor="@color/colorAccent"
 *         app:f_centerColor="@color/purple_800"
 *         app:f_endColor="@color/yellow"
 *         app:f_orientation="left_right"
 *         app:f_cornerRadius="@dimen/len_30"
 *         app:f_normalBgColor="@color/light_blue_200"
 *         app:f_normalTextColor="@color/white"
 *         app:f_pressedBgColor="@color/white_alpha_80"
 *         app:f_pressedTextColor="@color/white"
 *         app:f_radius_type="f_all_radius"
 *         app:f_showAnimation="false"/>
 *
 *
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

        //读取属性值
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
        int orientation = ta.getInt(R.styleable.FilletTextViewConfig_f_orientation, -1);
        if (orientation > 0) {
            GradientDrawable.Orientation[] orientations = GradientDrawable.Orientation.values();
            for (int i = 0; i < orientations.length; i++) {
                if (radiusType == orientations[i].ordinal()) {
                    configBean.setOrientation(orientations[i]);
                    break;
                }
            }
        }

        configBean.setUserGradient(ta.getBoolean(R.styleable.FilletTextViewConfig_f_usegradient, false));
        configBean.setStartColor(ta.getColor(R.styleable.FilletTextViewConfig_f_startColor, configBean.getStartColor()));
        configBean.setEndColor(ta.getColor(R.styleable.FilletTextViewConfig_f_endColor, configBean.getEndColor()));
        configBean.setCenterColor(ta.getColor(R.styleable.FilletTextViewConfig_f_centerColor, configBean.getCenterColor()));


        ta.recycle();
        initView();
    }


    private void initView() {
        // 初始化画笔
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        // 线宽
        mPaint.setStrokeWidth(configBean.getStrokeWidth());
        setBackground(getPressedSelector());
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
            Log.e(TAG, "<<-------------配置文件不能为空-------------->>");
            return;
        }
        this.configBean = configBean;
        // 设置背景
        initView();
    }

    public FilletConfig getConfig() {
        return configBean;
    }


    private Drawable getPressedSelector() {
        //TODO 目前只做了 按下和抬起状态
        Drawable pressed = createShape(configBean.getPressedBgColor());
        Drawable normal = createShape(configBean.getNormalBgColor());
        StateListDrawable drawable = new StateListDrawable();

        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        drawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{android.R.attr.state_selected}, pressed);
        drawable.addState(new int[]{android.R.attr.state_checked}, pressed);
        drawable.addState(new int[]{}, normal);

        if (configBean.isShowAnimation()) {
            // 设置状态选择器过度动画/渐变选择器/渐变动画
            drawable.setEnterFadeDuration(configBean.getShowAnimationTime());
            drawable.setExitFadeDuration(configBean.getShowAnimationTime());

        }
        return drawable;
    }

    private GradientDrawable createShape(int color) {
        GradientDrawable drawable;
        if (configBean.isUserGradient()) {
            if (configBean.getCenterColor() != -1) {
                //设置了中间色
                int colors[] = {configBean.getStartColor(), configBean.getCenterColor(), configBean.getEndColor()};
                drawable = new GradientDrawable(configBean.getOrientation(), colors);
            } else {
                int colors[] = {configBean.getStartColor(), configBean.getEndColor()};
                drawable = new GradientDrawable(configBean.getOrientation(), colors);
            }
        } else {
            drawable = new GradientDrawable();
            // 设置背景颜色
            drawable.setColor(color);
        }

        // 设置边框颜色
        drawable.setStroke(configBean.getStrokeWidth(), configBean.getStrokeColor());
        FilletConfig.RadiusType radiusType = configBean.getRadiusType();
        float radius = configBean.getCornerRadius();
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initView();
    }

    /**
     * 提供一个简易的状态切换方法
     *
     * @param editText 外部传入的编辑框
     * @param length   需要满足的长度，不满足长度设置不可用控件
     */
    public void bindEditText(EditText editText, final int length) {
        this.bindEditText(editText, length, false);
    }

    /**
     * @param editText 外部传入的编辑框
     * @param length   需要满足的长度，不满足长度设置不可用控件
     * @param firstDisable 如果是首次进入 输入框可能为空的情况设置不可用
     */
    public void bindEditText(EditText editText, final int length, boolean firstDisable) {
        if (firstDisable) {
            openOrClose(0, length);
        }
        editText.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                openOrClose(s.length(), length);
            }
        });
    }

    public void openOrClose(int length, int matchLength) {
        if (length >= matchLength) {
            this.setClickable(true);
            this.setPressed(false);
            this.setFocusable(false);
        } else {
            this.setClickable(false);
            this.setPressed(true);
            this.setFocusable(true);
        }
    }
}