package dz.solc.viewtool.view.tableview;

import android.graphics.Color;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/7/11
 * creat_time: 13:24
 * describe 单元格
 **/
public class ItemCell<T> {

    /**
     * 单元格的对象
     */
    private T cellValue;
    /**
     * 单元格跨列--暂无此功能
     */
    private int cellSpan = 1;
    /**
     * 单元格列号
     */
    private int colNum = 0;
    private int cellTextColor = Color.WHITE;


    public ItemCell(T cellValue) {
        this(cellValue, 1);
    }

    public ItemCell(T cellValue, int cellSpan) {
        this.cellValue = cellValue;
        this.cellSpan = cellSpan;
    }

    public ItemCell(T cellValue, int cellSpan, int cellTextColor) {
        this.cellValue = cellValue;
        this.cellSpan = cellSpan;
        this.cellTextColor = cellTextColor;
    }


    public int getCellTextColor() {
        return cellTextColor;
    }

    public void setCellTextColor(int cellTextColor) {
        this.cellTextColor = cellTextColor;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getColNum() {
        return this.colNum;
    }

    public T getCellValue() {
        return cellValue;
    }

    public void setCellValue(T value) {
        this.cellValue = value;
    }

    public int getCellSpan() {
        return cellSpan;
    }

}
