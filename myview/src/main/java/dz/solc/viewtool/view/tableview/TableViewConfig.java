package dz.solc.viewtool.view.tableview;

import android.graphics.Color;

import dz.solc.viewtool.view.tableview.listener.OnCellItemClickListener;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/7/15
 * create_time: 11:35
 * describe 表格控件的配置文件
 **/

@SuppressWarnings("all")
public class TableViewConfig {

    /**
     * 表格的分割线宽
     */
    private int dividerWidth = 1;
    /**
     * 表格的分割线高
     */
    private int dividerHeight = 1;

    /**
     * 分割线颜色
     */
    private int dividerColor = Color.WHITE;

    /**
     * 表头宽
     */
    private int headViewWidth = 80;
    /**
     * 表头高
     */
    private int headViewHeight = 40;

    /**
     * 单元格宽
     */
    private int cellWidth = 80;


    /**
     * 单元格高
     */
    private int cellHeight = 40;


    /**
     * 此属性会忽略设置的宽高 按照权重分配
     */
    private boolean enableWeight=false;

    /**
     * 设置单元格的权重比，
     */
    private Float[] weightHead;

    private Float[] weightBody;



    /**
     * 是否形成闭环分割
     */
    private boolean closeCycle = false;

    /**
     * 显不显示头信息
     */
    private boolean showHead = true;

    /**
     * 是否自适应高度
     *
     * 该值为true  cellHight 和 headViewHeight 将失效i
     */
    private boolean autoWrapHeight = false;


    /**
     * 是否需要编辑表格，如果需要则配置该属性为真
     */
    private boolean isEditTable = false;


    /**
     * 设置分割的外边距(慎用)
     */
    private int dividerMargin = 0;

    /**
     * 单元格的单击事件监听
     */
    private OnCellItemClickListener onCellItemClickListener;


    public OnCellItemClickListener getOnCellItemClickListener() {
        return onCellItemClickListener;
    }

    public void setOnCellItemClickListener(OnCellItemClickListener onCellItemClickListener) {
        this.onCellItemClickListener = onCellItemClickListener;
    }

    public boolean isShowHead() {
        return showHead;
    }

    public TableViewConfig setShowHead(boolean showHead) {
        this.showHead = showHead;
        return this;
    }

    public boolean isAutoWrapHeight() {
        return autoWrapHeight;
    }

    public TableViewConfig setAutoWrapHeight(boolean autoWrapHeight) {
        this.autoWrapHeight = autoWrapHeight;
        return this;
    }

    public TableViewConfig setCellsWidth(int cellsWidth) {
        setHeadViewWidth(cellsWidth);
        setCellWidth(cellsWidth);
        return this;
    }


    public TableViewConfig setCellsHeight(int cellsHeight) {
        setHeadViewHeight(cellsHeight);
        setCellHeight(cellsHeight);
        return this;
    }

    public int getDividerWidth() {
        return dividerWidth;
    }

    public TableViewConfig setDivider(int divider) {
        setDividerWidth(divider);
        setDividerHeight(divider);
        return this;
    }

    public TableViewConfig setDividerWidth(int dividerWidth) {
        this.dividerWidth = dividerWidth;
        return this;
    }

    public int getDividerHeight() {
        return dividerHeight;
    }

    public TableViewConfig setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
        return this;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public TableViewConfig setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        return this;
    }

    public int getHeadViewWidth() {
        return headViewWidth;
    }

    public TableViewConfig setHeadViewWidth(int headViewWidth) {
        this.headViewWidth = headViewWidth;
        return this;
    }

    public int getHeadViewHeight() {
        return headViewHeight;
    }

    public TableViewConfig setHeadViewHeight(int headViewHeight) {
        this.headViewHeight = headViewHeight;
        return this;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public TableViewConfig setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
        return this;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public TableViewConfig setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
        return this;
    }

    public boolean isEditTable() {
        return isEditTable;
    }

    public TableViewConfig setEditTable(boolean editTable) {
        isEditTable = editTable;
        return this;
    }


    public boolean isCloseCycle() {
        return closeCycle;
    }

    public TableViewConfig setCloseCycle(boolean closeCycle) {
        this.closeCycle = closeCycle;
        return this;
    }

    public int getDividerMargin() {
        return dividerMargin;
    }

    public TableViewConfig setDividerMargin(int dividerMargin) {
        this.dividerMargin = dividerMargin;
        return this;
    }


    public boolean isEnableWeight() {
        return enableWeight;
    }

    public TableViewConfig setEnableWeight(boolean enableWeight) {
        this.enableWeight = enableWeight;
        return  this;
    }

    public Float[] getWeightHead() {
        return weightHead;
    }

    public TableViewConfig setWeightHead(Float[] weightHead) {
        this.weightHead = weightHead;
        return this;
    }

    public Float[] getWeightBody() {
        return weightBody;
    }

    public TableViewConfig setWeightBody(Float[] weightBody) {
        this.weightBody = weightBody;
        return this;
    }
}
