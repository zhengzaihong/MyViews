package dz.solc.viewtool.view.tableview;

import android.content.Context;
import android.graphics.Color;

import dz.solc.viewtool.view.utils.Utils;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/7/15
 * creat_time: 11:35
 * describe 表格控件的配置文件
 **/
public class TableViewConfig {

    /**
     * 表格的分割线宽
     */
    private int dividerWidth = 1;
    /**
     * 表格的分割线高
     */
    private int dividerHight = 1;

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
    private int cellHight = 40;

    /**
     * 是否形成闭环分割
     */
    private boolean closeCycle = false;


    private Context mContext;


    private TableView.OnCellItemClickListener onCellItemClickListener;

    public TableViewConfig(Context mContext) {
        this.mContext = mContext;

    }

    public TableViewConfig setCellsWidth(int cellsWidth) {
        setHeadViewWidth(cellsWidth);
        setCellWidth(cellsWidth);
        return this;
    }


    public TableViewConfig setCellsHight(int cellsHight) {
        setHeadViewHeight(cellsHight);
        setCellHight(cellsHight);
        return this;
    }

    public int getDividerWidth() {
        return dividerWidth;
    }

    public TableViewConfig setDivider(int divider) {
        setDividerWidth(divider);
        setDividerHight(divider);
        return this;
    }

    public TableViewConfig setDividerWidth(int dividerWidth) {
        this.dividerWidth = Utils.dp2px(mContext, dividerWidth);
        return this;
    }

    public int getDividerHight() {
        return dividerHight;
    }

    public TableViewConfig setDividerHight(int dividerHight) {
        this.dividerHight = Utils.dp2px(mContext, dividerHight);
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
        this.headViewWidth = Utils.dp2px(mContext, headViewWidth);
        return this;
    }

    public int getHeadViewHeight() {
        return headViewHeight;
    }

    public TableViewConfig setHeadViewHeight(int headViewHeight) {
        this.headViewHeight = Utils.dp2px(mContext, headViewHeight);
        return this;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public TableViewConfig setCellWidth(int cellWidth) {
        this.cellWidth = Utils.dp2px(mContext, cellWidth);
        return this;
    }

    public int getCellHight() {
        return cellHight;
    }

    public TableViewConfig setCellHight(int cellHight) {
        this.cellHight = Utils.dp2px(mContext, cellHight);
        return this;
    }

    public TableView.OnCellItemClickListener getOnCellItemClickListener() {
        return onCellItemClickListener;
    }

    public void setOnCellItemClickListener(TableView.OnCellItemClickListener onCellItemClickListener) {
        this.onCellItemClickListener = onCellItemClickListener;
    }

    public boolean isCloseCycle() {
        return closeCycle;
    }

    public TableViewConfig setCloseCycle(boolean closeCycle) {
        this.closeCycle = closeCycle;
        return this;
    }
}
