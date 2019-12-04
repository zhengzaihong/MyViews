package dz.solc.viewtool.view.tableview.listener;

import android.view.View;

import java.util.ArrayList;

import dz.solc.viewtool.view.tableview.ItemCell;
import dz.solc.viewtool.view.tableview.RowItem;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/11/27
 * creat_time: 17:29
 * describe 响应单元格点击事件的监听
 **/
public interface OnCellItemClickListener {

    /**
     * @param v        当前cell view
     * @param itemCell 点击的所在行中某个cell
     * @param rowItem  当前一整行的信息
     */
    void onClick(View v, ItemCell itemCell, RowItem rowItem);
}
