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
    private Object obj;


    public ItemCell(T cellValue) {
        this(cellValue, Color.WHITE);
    }

    public ItemCell(T cellValue, int cellTextColor) {
        this(cellValue, cellTextColor, null);
    }

    public ItemCell(T cellValue, Object obj) {
        this(cellValue, Color.WHITE, obj);
    }

    public ItemCell(T cellValue, int cellTextColor, Object obj) {
        this.cellValue = cellValue;
        this.cellTextColor = cellTextColor;
        this.obj = obj;
    }


    public Object getObject() {
        return obj;
    }

    public ItemCell setObject(Object object) {
        this.obj = object;
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


}
