package dz.solc.viewtool.view.tableview.listener;

import android.view.View;

import java.util.ArrayList;

import dz.solc.viewtool.adapter.CommonAdapter;
import dz.solc.viewtool.view.tableview.ItemCell;
import dz.solc.viewtool.view.tableview.RowItem;
import dz.solc.viewtool.view.tableview.TableView;


/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/11/27
 * creat_time: 17:29
 * describe 填充数据的 监听(必须设置，否则不填充)
 **/
public interface FillContentListener {

    /**
     * 表格头view
     *
     * @param obj 包装每个头的信息
     * @return 外部返回该view, 把更多配置交个用户
     */
    View addHead(Object obj);

    /**
     * 每显示一行会触发一次 这个和listView 的adapter 一直
     *
     * @param holder
     * @param rowItem  表格每行的信息
     */

    void getView(CommonAdapter.ViewHolder holder,  RowItem rowItem);

    /**
     * @param obj         当前单元格数据
     * @param cellIndex   当前行数的每个单元格下标
     * @param rowItem     当前行信息
     * @return
     */

    View cellItem(ItemCell obj, int cellIndex, RowItem rowItem);

    /**
     * @return 每个item 的部件文件id
     */
    int itemLayout();

    /**
     * 绑定当前的TableView
     *
     * @return
     */
    TableView bindTableView();
}
