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

    public TableViewAdapter(TableView tableView, FillContentListener listener) {
        super(tableView.getContext(), listener.itemLayout());
        this.contentListener = listener;
    }

    @Override
    public void convert(ViewHolder holder, int position, Object entity) {

        // 构建一行数据
        RowItem rowItem = (RowItem) entity;
        rowItem.setPosition(position)
                .setLastItem(position == datas.size() - 1);

        contentListener.getView(holder, rowItem);
    }

}
