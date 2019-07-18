package dz.solc.viewtool.view.tableview;

import android.content.Context;

import dz.solc.viewtool.adapter.CommonAdapter;


/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/7/15
 * creat_time: 11:35
 * describe 表格适配器
 **/
public class TableViewAdapter extends CommonAdapter {

    private TableView.FillContentListener contentListener;

    public TableViewAdapter(Context context, TableView.FillContentListener listener) {
        super(context, listener.itemLayout());
        this.contentListener = listener;
    }

    @Override
    public void convert(ViewHolder holder, int position, Object entity) {
        contentListener.getView(holder, position, entity, position == datas.size() - 1);
    }

}
