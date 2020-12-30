package dz.solc.viewtool.view.navigation;

import android.graphics.Color;

import dz.solc.viewtool.view.mayview.msgview.MsgPosition;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/10/13
 * create_time: 10:19
 * describe: 新增配置文件 使用更加灵活便捷
 **/
public class BottomViewConfig {


    /**
     * 是否支持滑动切换底部导航
     */
    private boolean supportScroll = false;

    /**
     * 图片菜单距离顶部的边距
     */
    private int menueIconMarginTop = 0;
    private int menueMarginBottom = 0;

    /**
     * 导航菜单文字的大小
     */
    private int menueTextSize = 16;


    /**
     * 是否支持显示消息数
     */
    private boolean enableMsgShow = false;

    /**
     * 消息的显示位置
     */
    private MsgPosition msgPosition = MsgPosition.POSITION_TOP_RIGHT;

    /**
     * 消息默认margin
     */
    private int margin = 0;
    /**
     * 默认左右padding
     */
    private int leftRightPadding = 0;
    /**
     * 默认添加控件的圆角
     */
    private int msgBgCorner = 5;
    /**
     * 消息背景色
     */
    private int msgBgColor = Color.TRANSPARENT;
    /**
     * 消息的文字颜色
     */
    private int msgTextColor = Color.WHITE;

    /**
     * 消息字体大小
     */
    private int msgTextSize = 10;

    /**
     * 缓存机制
     */
    private int limitSize = 1;

    public int getLimitSize() {
        return limitSize;
    }

    public void setLimitSize(int limitSize) {
        this.limitSize = limitSize;
    }

    public boolean isSupportScroll() {
        return supportScroll;
    }

    public void setSupportScroll(boolean supportScroll) {
        this.supportScroll = supportScroll;
    }

    public int getMenueIconMarginTop() {
        return menueIconMarginTop;
    }

    public void setMenueIconMarginTop(int menueIconMarginTop) {
        this.menueIconMarginTop = menueIconMarginTop;
    }

    public int getMenueMarginBottom() {
        return menueMarginBottom;
    }

    public void setMenueMarginBottom(int menueMarginBottom) {
        this.menueMarginBottom = menueMarginBottom;
    }

    public int getMenueTextSize() {
        return menueTextSize;
    }

    public void setMenueTextSize(int menueTextSize) {
        this.menueTextSize = menueTextSize;
    }

    public boolean isEnableMsgShow() {
        return enableMsgShow;
    }

    public void setEnableMsgShow(boolean enableMsgShow) {
        this.enableMsgShow = enableMsgShow;
    }

    public MsgPosition getMsgPosition() {
        return msgPosition;
    }

    public void setMsgPosition(MsgPosition msgPosition) {
        this.msgPosition = msgPosition;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getLeftRightPadding() {
        return leftRightPadding;
    }

    public void setLeftRightPadding(int leftRightPadding) {
        this.leftRightPadding = leftRightPadding;
    }

    public int getMsgBgCorner() {
        return msgBgCorner;
    }

    public void setMsgBgCorner(int msgBgCorner) {
        this.msgBgCorner = msgBgCorner;
    }

    public int getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(int msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

    public int getMsgTextColor() {
        return msgTextColor;
    }

    public void setMsgTextColor(int msgTextColor) {
        this.msgTextColor = msgTextColor;
    }

    public int getMsgTextSize() {
        return msgTextSize;
    }

    public void setMsgTextSize(int msgTextSize) {
        this.msgTextSize = msgTextSize;
    }
}
