package dz.solc.viewtool.view.viewgroup;

import android.graphics.Color;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/12
 * create_time: 19:10
 * describe 配置 容器的信息
 **/
public class ViewGroupConfig {

    // 边框线宽
    private int normalStrokeWidth = 0;
    // 选中时边框的宽度
    private int pressStrokeWidth = 0;
    // 边框颜色
    private int normalStrokeColor = Color.TRANSPARENT;
    // 选中时边框颜色
    private int pressStrokeColor = Color.TRANSPARENT;
    // 背景颜色
    private int normalBgColor = 0xff132051;
    // 按下背景颜色
    private int pressedBgColor = 0xff4C6DE1;
    // 圆角半径
    private int cornerRadius = 30;
    //按压时候边框颜色
    private int pressedStrokeColor = Color.TRANSPARENT;

    //动画
    private boolean showAnimation = false;

    //动画时间
    private int animationTime = 500;

    // 设置圆角类型
    private RadiusType radiusType = RadiusType.ALL_RADIUS;


    public enum RadiusType {
        /**
         * 左边_上_下圆角
         */
        LEFT_TOP_BOTTOM_RADIUS(),
        /**
         * 右边_上_下圆角
         */
        RIGHT_TOP_BOOTOM_RADIUS,
        /**
         * 左边_上圆角
         */
        LEFT_TOP_RADIUS,
        /**
         * 左边_下圆角
         */
        LEFT_BOTTOM_RADIUS,

        /**
         * 右边_上圆角
         */
        RIGHT_TOP_RADIUS,

        /**
         * 右边_下圆角
         */
        RIGHT_BOOTOM_RADIUS,

        /**
         * 四边圆角
         */
        ALL_RADIUS,

        /**
         * 无圆角
         */
        NONE_RADIUS


    }

    public int getNormalStrokeWidth() {
        return normalStrokeWidth;
    }

    public void setNormalStrokeWidth(int normalStrokeWidth) {
        this.normalStrokeWidth = normalStrokeWidth;
    }

    public int getPressStrokeWidth() {
        return pressStrokeWidth;
    }

    public void setPressStrokeWidth(int pressStrokeWidth) {
        this.pressStrokeWidth = pressStrokeWidth;
    }

    public int getNormalStrokeColor() {
        return normalStrokeColor;
    }

    public void setNormalStrokeColor(int normalStrokeColor) {
        this.normalStrokeColor = normalStrokeColor;
    }

    public int getPressStrokeColor() {
        return pressStrokeColor;
    }

    public void setPressStrokeColor(int pressStrokeColor) {
        this.pressStrokeColor = pressStrokeColor;
    }

    public int getNormalBgColor() {
        return normalBgColor;
    }

    public void setNormalBgColor(int normalBgColor) {
        this.normalBgColor = normalBgColor;
    }

    public int getPressedBgColor() {
        return pressedBgColor;
    }

    public void setPressedBgColor(int pressedBgColor) {
        this.pressedBgColor = pressedBgColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public int getPressedStrokeColor() {
        return pressedStrokeColor;
    }

    public void setPressedStrokeColor(int pressedStrokeColor) {
        this.pressedStrokeColor = pressedStrokeColor;
    }

    public boolean isShowAnimation() {
        return showAnimation;
    }

    public void setShowAnimation(boolean showAnimation) {
        this.showAnimation = showAnimation;
    }

    public RadiusType getRadiusType() {
        return radiusType;
    }

    public void setRadiusType(RadiusType radiusType) {
        this.radiusType = radiusType;
    }

    public int getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(int animationTime) {
        this.animationTime = animationTime;
    }
}
