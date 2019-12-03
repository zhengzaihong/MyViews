package dz.solc.viewtool.view.tableview.listener;

import android.view.View;

import java.util.ArrayList;

import dz.solc.viewtool.view.tableview.ItemCell;
/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/11/27
 * creat_time: 17:29
 * describe 响应单元格点击事件的监听
 **/
public interface OnCellItemClickListener {

    /**
     * @param v           当前cell view
     * @param rowPosition 当前所在行
     * @param itemCell    点击的所在行中某个cell
     * @param itemCells   返回当前一整行的数据
     */
    void onClick(View v, int rowPosition, ItemCell itemCell, ArrayList<ItemCell> itemCells);
}
