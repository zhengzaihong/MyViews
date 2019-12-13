package dz.solc.viewtool.view.radio;

import android.graphics.Color;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2019/4/24 0024
 * creat_time: 16:20
 * describe: 自定义菜单的配置文件
 **/

@SuppressWarnings("all")
public class RadioButtonViewConfig {

    // 边框线宽
    private int strokeWidth = 1;
    // 边框颜色
    private int strokeColor = Color.TRANSPARENT;
    // 背景颜色
    private int normalBgColor = 0xff132051;
    // 按下背景颜色
    private int pressedBgColor = 0xff4C6DE1;
    // 圆角半径
    private int cornerRadius = 30;

    //正常文字颜色
    private int normalTextColor = Color.WHITE;

    //按压时候的文字颜色
    private int pressedTextColor = Color.WHITE;

    //按压时候边框颜色
    private int pressedStrokeColor = Color.TRANSPARENT;

    // 边框颜色是否跟随文字颜色
    private boolean followTextColor;

    //记录零时的边框值
    private int undeterMinedStrokeColor = strokeColor;

    // 设置圆角类型
    private RadiusType radiusType = RadiusType.NONE_RADIUS;

    private boolean showAnimation = false;
    //动画时间
    private int animationTime = 500;

    public enum RadiusType {

        /**
         * 左边_上_下圆角
         */

        LEFT_TOP_BOTTOM_RADIUS,
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

    public boolean isShowAnimation() {
        return showAnimation;
    }

    public void setShowAnimation(boolean showAnimation) {
        this.showAnimation = showAnimation;
    }

    public int getUndeterMinedStrokeColor() {
        return undeterMinedStrokeColor;
    }

    public void setUndeterMinedStrokeColor(int undeterMinedStrokeColor) {
        this.undeterMinedStrokeColor = undeterMinedStrokeColor;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
    }

    public int getPressedTextColor() {
        return pressedTextColor;
    }

    public void setPressedTextColor(int pressedTextColor) {
        this.pressedTextColor = pressedTextColor;
    }

    public int getPressedStrokeColor() {
        return pressedStrokeColor;
    }

    public void setPressedStrokeColor(int pressedStrokeColor) {
        this.pressedStrokeColor = pressedStrokeColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
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

    public boolean isFollowTextColor() {
        return followTextColor;
    }

    public void setFollowTextColor(boolean mFollowTextColor) {
        this.followTextColor = mFollowTextColor;
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
