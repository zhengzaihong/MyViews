package dz.solc.viewtool.view.tableview;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/7/11
 * create_time: 13:24
 * describe 表格头
 **/
public class HeadItemCell<T> extends ItemCell {

    private int width;

    public HeadItemCell(T cellValue, int width) {

        super(cellValue);

        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
