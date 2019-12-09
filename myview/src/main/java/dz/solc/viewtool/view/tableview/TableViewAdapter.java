package dz.solc.viewtool.view.tableview;


import dz.solc.viewtool.adapter.CommonAdapter;
import dz.solc.viewtool.view.tableview.listener.FillContentListener;


/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/7/15
 * creat_time: 11:35
 * describe 表格适配器
 **/
public class TableViewAdapter extends CommonAdapter {

    private FillContentListener contentListener;
    private TableView tableView;
    private TableViewConfig viewConfig;

    public TableViewAdapter(TableView tableView, FillContentListener listener) {
        super(tableView.getContext(), listener.itemLayout());
        this.contentListener = listener;
        this.tableView = tableView;
        this.viewConfig = tableView.getViewConfig();

    }

    @Override
    public void convert(ViewHolder holder, int position, Object entity) {

        //可编辑的表格，第一行为过滤行 不做任何处理
        if (viewConfig.isEditTable()
                && position == 0
                && viewConfig.isShowHead()) {

            tableView.addDataView(0, null);
            return;
        }


        //构建一行数据
        RowItem rowItem = (RowItem) entity;
        //清除掉可能 缓存的数据
        rowItem.clearCells()
                .setPosition(position)
                .setLastItem(position == datas.size() - 1);

        contentListener.getView(holder, rowItem);
    }

}
