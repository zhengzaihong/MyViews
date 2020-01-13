package dz.solc.viewtool.view.tableview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/4
 * creat_time: 9:30
 * describe 每行的信息（新增特性 0.1.92之前版本不具有）
 * 老版本从 ItemCell 和 每行集合中读取每个单元格信息。
 **/

@SuppressWarnings("all")
public class RowItem<T> {

    /**
     * 当前行的数据对象
     */
    private T rowData;
    /**
     * 每一行的数据
     * 该数据集合在 回调 getView之后被填充
     */
    private List<ItemCell> cells = new ArrayList<>();

    /**
     * 记录表格中每一行的下标位置(除开表头)
     */
    private int position = 0;

    /**
     * 是否是整个表格的最后一行
     */
    private boolean isLastItem = false;

    /**
     * 可携带附加数据
     */
    private Object obj;


    public RowItem(T rowData) {
        this.rowData = rowData;
    }


    /**
     * 构造传入数据
     *
     * @param cells
     */
    public RowItem(List<ItemCell> cells) {
        if (cells != null) {
            this.cells = cells;
        }
    }

    /**
     * 构造传入数据
     *
     * @param cells
     */
    public RowItem(ItemCell... cells) {
        if (cells.length > 0) {
            this.cells = Arrays.asList(cells);
        }
    }

    /**
     * @param position   每一行的下标 和表格列表保持一致，除开头部不算
     * @param isLastItem 是否最后一行
     * @param cells      每一行的数据 集合
     */
    public RowItem(int position, boolean isLastItem, List<ItemCell> cells) {
        this.position = position;
        this.isLastItem = isLastItem;
        if (cells != null) {
            this.cells = cells;
        }
    }

    /**
     * @param position   每一行的下标 和表格列表保持一致，除开头部不算
     * @param isLastItem 是否最后一行
     * @param cells      每一行的数据 数组
     */
    public RowItem(int position, boolean isLastItem, ItemCell... cells) {
        this.position = position;
        this.isLastItem = isLastItem;
        if (cells.length > 0) {
            this.cells = Arrays.asList(cells);
        }
    }

    /**
     * @param position   每一行的下标 和表格列表保持一致，除开头部不算
     * @param isLastItem 是否最后一行
     * @param obj        附加数据
     */
    public RowItem(int position, boolean isLastItem, Object obj) {
        this.position = position;
        this.isLastItem = isLastItem;
        this.obj = obj;
    }

    /**
     * 添加一个单元格
     *
     * @param cell 单元格信息
     * @return
     */
    public RowItem addCell(ItemCell cell) {
        cells.add(cell);
        return this;
    }

    /**
     * 添加一个单元格
     *
     * @param cell  单元格信息
     * @param index 添加到指定的位置
     * @return
     */
    public RowItem addCell(ItemCell cell, int index) {
        cells.add(index, cell);
        return this;
    }

    /**
     * 删除一个单元格
     *
     * @param cell 单元格信息
     * @return
     */
    public RowItem removeCell(ItemCell cell) {
        if (cells.contains(cell)) {
            cells.remove(cell);
        }
        return this;
    }

    public RowItem removeCell(int index) {
        cells.remove(index);
        return this;
    }

    public RowItem clearCells() {
        cells.clear();
        return this;
    }


    public RowItem setRowData(T rowData) {
        this.rowData = rowData;
        return this;
    }


    public RowItem setObj(Object obj) {
        this.obj = obj;
        return this;
    }

    public RowItem setPosition(int position) {
        this.position = position;
        return this;
    }

    public RowItem setLastItem(boolean lastItem) {
        isLastItem = lastItem;
        return this;
    }

    public RowItem setCells(List<ItemCell> cells) {
        this.cells = cells;
        return this;
    }

    public List<ItemCell> getCells() {
        return cells;
    }

    public int getPosition() {
        return position;
    }

    public boolean isLastItem() {
        return isLastItem;
    }

    public Object getObj() {
        return obj;
    }

    public T getRowData() {
        return rowData;
    }

    @Override
    public String toString() {
        return "RowItem{" +
                "rowData=" + rowData +
                ", cells=" + cells +
                ", position=" + position +
                ", isLastItem=" + isLastItem +
                ", obj=" + obj +
                '}';
    }
}
