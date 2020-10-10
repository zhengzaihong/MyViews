package dz.solc.viewtool.view.tableview;

import android.graphics.Color;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/7/11
 * create_time: 13:24
 * describe 单元格
 **/

@SuppressWarnings("all")
public class ItemCell<T> {

    /**
     * 单元格的数据
     */
    private T cellValue;
    /**
     * 单元格列号
     */
    private int colnum = 0;

    /**
     * 单元格颜色
     */
    private int cellTextColor;

    /**
     * 携带附加数据
     */
    private Object tag;


    public ItemCell(T cellValue) {
        this(cellValue, Color.WHITE);
    }

    public ItemCell(T cellValue, int cellTextColor) {
        this(cellValue, cellTextColor, null);
    }

    public ItemCell(T cellValue, Object tag) {
        this(cellValue, Color.WHITE, tag);
    }

    public ItemCell(T cellValue, int cellTextColor, Object tag) {
        this.cellValue = cellValue;
        this.cellTextColor = cellTextColor;
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }

    public ItemCell setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public int getCellTextColor() {
        return cellTextColor;
    }

    public ItemCell setCellTextColor(int cellTextColor) {
        this.cellTextColor = cellTextColor;
        return this;
    }

    public int getColnum() {
        return colnum;
    }

    public ItemCell setColnum(int colnum) {
        this.colnum = colnum;
        return this;
    }

    public T getCellValue() {
        return cellValue;
    }

    public ItemCell setCellValue(T value) {
        this.cellValue = value;
        return this;
    }

    @Override
    public String toString() {
        return "ItemCell{" +
                "cellValue=" + cellValue +
                ", colnum=" + colnum +
                ", cellTextColor=" + cellTextColor +
                ", tag=" + tag +
                '}';
    }
}
