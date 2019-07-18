package dz.solc.myviews.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dz.utlis.AndroidUtils;
import com.dz.utlis.JavaUtils;
import com.dz.utlis.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dz.solc.myviews.R;
import dz.solc.myviews.bean.PersonInfoBean;
import dz.solc.myviews.uitls.Constans;
import dz.solc.myviews.uitls.ToastTool;
import dz.solc.viewtool.adapter.CommonAdapter;
import dz.solc.viewtool.adapter.UtilAdapter;
import dz.solc.viewtool.view.tableview.ItemCell;
import dz.solc.viewtool.view.tableview.TableItem;
import dz.solc.viewtool.view.tableview.TableView;
import dz.solc.viewtool.view.tableview.TableViewConfig;

import static com.dz.utlis.JavaUtils.getMap4Json;
import static com.dz.utlis.JavaUtils.outRedPrint;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/7/15
 * creat_time: 14:07
 * describe 表格基本功能已具有,更多功能待完善优化.
 **/
public class TableViewActivity extends AppCompatActivity {

    private TableView<Map<String, Object>> tableView;
    private TableView<PersonInfoBean.DataBean> tableView1;
    private TableView<PersonInfoBean.DataBean> tableView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_tableview);

        tableView = findViewById(R.id.tableView);
        tableView1 = findViewById(R.id.tableView1);
        tableView2 = findViewById(R.id.tableView2);


        //单位均为dp
        TableViewConfig config1 = new TableViewConfig(this)
                .setCellsWidth(80)   // 设置单元格的宽度
                .setCellsHight(40)   //设置单元格的高度
                .setDivider(0)          //设置分割线高
                .setDividerColor(getResources().getColor(R.color.trans))  //设置分割线颜色
                .setCloseCycle(false);   //是否形成分割闭环样式

        //单位均为dp
        TableViewConfig config2 = new TableViewConfig(this)
                .setCellsWidth(80)   // 设置单元格的宽度
                .setCellsHight(40)   //设置单元格的高度
                .setDivider(1)          //设置分割线高
                .setDividerColor(getResources().getColor(R.color.sandybrown))  //设置分割线颜色
                .setCloseCycle(false);   //是否形成分割闭环样式


        //单位均为dp
        TableViewConfig config = new TableViewConfig(this)
                .setHeadViewWidth(80)   //设置每个头的宽度
                .setHeadViewHeight(40)  //设置每个头的高度
                .setCellWidth(80)       //设置单元格的宽度
                .setCellHight(40)       //设置单元格的高度
                .setDivider(1)          //设置分割线高
                .setDividerColor(getResources().getColor(R.color.sandybrown))  //设置分割线颜色
                .setCloseCycle(true);   //是否形成分割闭环样式


        test1(config1);

        test2(config2);

        test(config);

        //如果是在ScrollView 中需要测量下listView 高度,一般不会需要这步
        UtilAdapter.setListViewHeightBasedOnChildren(tableView.getListView());
        UtilAdapter.setListViewHeightBasedOnChildren(tableView1.getListView());
        UtilAdapter.setListViewHeightBasedOnChildren(tableView2.getListView());

    }


    private void test1(final TableViewConfig config) {

        tableView1.setViewConfig(config);
        tableView1.setFillContentListener(new TableView.FillContentListener() {
            @Override
            public View addHead(Object obj) {
                TextView view = (TextView) AndroidUtils.getView(TableViewActivity.this, R.layout.table_view_head_text_view_layout1);
                view.setText(obj.toString());
                return view;
            }

            @Override
            public void getView(CommonAdapter.ViewHolder holder, int position, Object obj, boolean islastItem) {

                PersonInfoBean.DataBean dataBean = (PersonInfoBean.DataBean) ((ItemCell) obj).getCellValue();

                ArrayList<ItemCell> itemCells = new ArrayList();
                ItemCell itemCell = new ItemCell(dataBean.getName());
                ItemCell itemCell1 = new ItemCell(dataBean.getJob());
                ItemCell itemCell2 = new ItemCell(dataBean.getCreatedTime());
                ItemCell itemCell3 = new ItemCell(dataBean.getMoney());

                itemCells.add(itemCell);
                itemCells.add(itemCell1);
                itemCells.add(itemCell2);
                itemCells.add(itemCell3);

                TableItem item = (TableItem) holder.getConvertView();
                item.buildItem(this, position, islastItem, itemCells);

            }

            @Override
            public View cellItem(ItemCell obj, int rowPosition, boolean isLastCell) {
                View view = AndroidUtils.getView(TableViewActivity.this, R.layout.table_cell_text_view_layout1);
                TextView textView = view.findViewById(R.id.tvCell);
                textView.setText(obj.getCellValue().toString());
                if (rowPosition % 2 == 0) {
                    view.setBackgroundColor(getResources().getColor(R.color.amber_50));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.wheat));
                }
                return view;
            }

            @Override
            public int itemLayout() {
                return R.layout.table_view_row_item_layout1;
            }

            @Override
            public TableView bindTableView() {
                return tableView1;
            }
        });


        PersonInfoBean personInfoBean = JSON.toJavaObject(JSON.parseObject(Constans.testJson1), PersonInfoBean.class);

        List<String> head = new ArrayList<>();
        head.add("姓名");
        head.add("职位");
        head.add("时间");
        head.add("待遇");

        tableView1.setData(head, personInfoBean.getData());

    }

    private void test2(TableViewConfig config) {

        tableView2.setViewConfig(config);
        tableView2.setFillContentListener(new TableView.FillContentListener() {
            @Override
            public View addHead(Object obj) {
                TextView view = (TextView) AndroidUtils.getView(TableViewActivity.this, R.layout.table_view_head_text_view_layout1);
                view.setText(obj.toString());
                return view;
            }

            @Override
            public void getView(CommonAdapter.ViewHolder holder, int position, Object obj, boolean isLastItem) {

                PersonInfoBean.DataBean dataBean = (PersonInfoBean.DataBean) ((ItemCell) obj).getCellValue();

                ArrayList<ItemCell> itemCells = new ArrayList();
                ItemCell itemCell = new ItemCell(dataBean.getName());
                ItemCell itemCell1 = new ItemCell(dataBean.getJob());
                ItemCell itemCell2 = new ItemCell(dataBean.getCreatedTime());
                ItemCell itemCell3 = new ItemCell(dataBean.getMoney());

                itemCells.add(itemCell);
                itemCells.add(itemCell1);
                itemCells.add(itemCell2);
                itemCells.add(itemCell3);

                TableItem item = (TableItem) holder.getConvertView();
                item.buildItem(this, position, isLastItem, itemCells);

            }

            @Override
            public View cellItem(ItemCell obj, int rowPosition, boolean isLastCell) {
                View view = AndroidUtils.getView(TableViewActivity.this, R.layout.table_cell_text_view_layout1);

                TextView textView = view.findViewById(R.id.tvCell);
                textView.setText(obj.getCellValue().toString());

                if (rowPosition % 2 == 0) {
                    view.setBackgroundColor(getResources().getColor(R.color.amber_50));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.wheat));
                }

                return view;
            }

            @Override
            public int itemLayout() {
                return R.layout.table_view_row_item_layout1;
            }

            @Override
            public TableView bindTableView() {
                return tableView2;
            }
        });


        PersonInfoBean personInfoBean = JSON.toJavaObject(JSON.parseObject(Constans.testJson1), PersonInfoBean.class);


        List<String> head = new ArrayList<>();
        head.add("姓名");
        head.add("职位");
        head.add("时间");
        head.add("待遇");

        tableView2.setData(head, personInfoBean.getData());

    }


    private void test(TableViewConfig config) {

        tableView.setViewConfig(config);

        tableView.setOnCellItemClickListener(new TableView.OnCellItemClickListener() {
            @Override
            public void onClick(View v, int row, ItemCell itemCell) {
                ToastTool.INSTANCE.show(itemCell.getCellValue().toString());
            }
        });

        tableView.setFillContentListener(new TableView.FillContentListener() {
            @Override
            public View addHead(Object o) {
                TextView view = (TextView) AndroidUtils.getView(TableViewActivity.this, R.layout.table_view_head_text_view_layout);
                view.setText(o.toString());
                return view;

            }

            @Override
            public void getView(CommonAdapter.ViewHolder holder, int position, Object obj, boolean isLastItem) {

                Map<String, Object> map = (Map<String, Object>) ((ItemCell) obj).getCellValue();

                ArrayList<ItemCell> itemCells = new ArrayList();
                ItemCell itemCell = new ItemCell(map.get("id").toString());
                ItemCell itemCell1 = new ItemCell(TimeUtil.stampstoTime(map.get("createdTime").toString(), "yyyy-MM-dd HH:mm:ss"));
                ItemCell itemCell2 = new ItemCell(map.get("createdBy").toString());
                ItemCell itemCell3 = new ItemCell(map.get("ls").toString());

                itemCells.add(itemCell);
                itemCells.add(itemCell1);

                itemCells.add(itemCell2);
                itemCells.add(itemCell3);

                TableItem item = (TableItem) holder.getConvertView();
                item.buildItem(this, position, isLastItem, itemCells);

                outRedPrint("------>" + map.toString());

            }

            @Override
            public View cellItem(ItemCell obj, int rowPosition, boolean isLastCell) {
                View view = AndroidUtils.getView(TableViewActivity.this, R.layout.table_cell_text_view_layout);
                TextView textView = view.findViewById(R.id.tvCell);
                textView.setText(obj.getCellValue().toString());

                if (rowPosition % 2 == 0) {
                    view.setBackgroundColor(getResources().getColor(R.color.amber_50));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.wheat));
                }

                return view;
            }

            @Override
            public int itemLayout() {
                return R.layout.table_view_row_item_layout;
            }

            @Override
            public TableView bindTableView() {
                return tableView;
            }

        });

        addData();

    }


    private void addData() {

        List<String> head = new ArrayList<>();
        List<Map<String, Object>> content = getContent();
        for (int i = 0; i < 4; i++) {
            head.add(content.get(i).get("name").toString());
        }
        tableView.setData(head, content);
    }


    private List<Map<String, Object>> getContent() {
        List<Map<String, Object>> datas = new ArrayList<>();

        JSONArray jsonArray = JSON.parseArray(Constans.testJson);

        for (int i = 0; i < jsonArray.size(); i++) {
            datas.add(getMap4Json(jsonArray.get(i).toString()));
        }

        outRedPrint(Constans.testJson);
        return datas;
    }


}
