package dz.solc.viewtool.view.editview.config;

import android.graphics.Color;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2019/4/24 0024
 * create_time: 16:20
 * describe: 自定义可圆角的TextView配置文件
 **/
public class FilletEditViewConfig {

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

    //正常文字颜色
    private int normalTextColor = Color.WHITE;

    //按压时候的文字颜色
    private int pressedTextColor = Color.WHITE;

    //按压时候边框颜色
    private int pressedStrokeColor = Color.TRANSPARENT;

    // 边框颜色是否跟随文字颜色
    private boolean followTextColor;

    //记录零时的边框值
    private int undeterMinedStrokeColor = normalStrokeColor;

    private boolean showAnimation = false;

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

    public boolean isFollowTextColor() {
        return followTextColor;
    }

    public void setFollowTextColor(boolean followTextColor) {
        this.followTextColor = followTextColor;
    }

    public RadiusType getRadiusType() {
        return radiusType;
    }

    public void setRadiusType(RadiusType radiusType) {
        this.radiusType = radiusType;
    }
}
