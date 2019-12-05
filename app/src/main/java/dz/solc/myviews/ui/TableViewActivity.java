package dz.solc.myviews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dz.utlis.AndroidUtils;
import com.dz.utlis.JavaUtils;
import com.dz.utlis.ScreenUtils;
import com.dz.utlis.TimeUtil;
import com.dz.utlis.ToastTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dz.solc.myviews.R;
import dz.solc.myviews.bean.PersonInfoBean;
import dz.solc.myviews.uitls.Constans;
import dz.solc.viewtool.adapter.CommonAdapter;
import dz.solc.viewtool.view.tableview.ItemCell;
import dz.solc.viewtool.view.tableview.RowItem;
import dz.solc.viewtool.view.tableview.TableItem;
import dz.solc.viewtool.view.tableview.TableView;
import dz.solc.viewtool.view.tableview.TableViewConfig;
import dz.solc.viewtool.view.tableview.listener.FillContentListener;
import dz.solc.viewtool.view.tableview.listener.OnCellItemClickListener;
import dz.solc.viewtool.view.utils.Utils;

import static com.dz.utlis.JavaUtils.getMap4Json;
import static com.dz.utlis.JavaUtils.outRedPrint;
import static com.dz.utlis.TimeUtil.stampstoTime;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/7/15
 * creat_time: 14:07
 * describe 表格基本功能已具有,更多功能待完善优化.
 **/
@SuppressWarnings("all")
public class TableViewActivity extends AppCompatActivity {

    private TableView<Map<String, Object>> tableView;
    private TableView<Map<String, Object>> tableView3;
    private TableView<PersonInfoBean.DataBean> tableView1;
    private TableView<PersonInfoBean.DataBean> tableView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_tableview);

        tableView = findViewById(R.id.tableView);
        tableView1 = findViewById(R.id.tableView1);
        tableView2 = findViewById(R.id.tableView2);
        tableView3 = findViewById(R.id.tableView3);


        TableViewConfig config1 = new TableViewConfig()
                .setCellsWidth(Utils.dp2px(this, 80))   // 设置单元格的宽度
                .setCellsHeight(Utils.dp2px(this, 40))   //设置单元格的高度
                .setAutoWrapHeight(true)  //自适应高度
                .setShowHead(true)        //是否显表头信息
                .setDivider(0)          //设置分割线高
                .setDividerColor(getResources().getColor(R.color.red))  //设置分割线颜色
                .setCloseCycle(false);   //是否形成分割闭环样式

        TableViewConfig config2 = new TableViewConfig()
                .setCellsWidth(Utils.dp2px(this, 80))   // 设置单元格的宽度
                .setCellsHeight(Utils.dp2px(this, 40))   //设置单元格的高度
                .setDivider(Utils.dp2px(this, 1))          //设置分割线高
                .setDividerColor(getResources().getColor(R.color.sandybrown))  //设置分割线颜色
                .setCloseCycle(true);   //是否形成分割闭环样式


        test();

        test1(config1);

        test2(config2);

        test3();

        tableView.measureHeight();
        tableView1.measureHeight();
        tableView2.measureHeight();
        tableView3.measureHeight();
    }

    private void test3() {

        tableView3.setOnCellItemClickListener(new OnCellItemClickListener() {
            @Override
            public void onClick(View view, ItemCell itemCell, RowItem rowItem) {

                TextView textView = view.findViewById(R.id.tvCell);
                textView.setText("点击");
                ToastTool.get().show(itemCell.getCellValue().toString());

                outRedPrint("当前单元格信息:" + itemCell.toString());
                outRedPrint("当前行数据:" + rowItem.toString());
            }

        });

        tableView3.setFillContentListener(new FillContentListener() {
            @Override
            public View addHead(Object o) {
                TextView view = (TextView) AndroidUtils.getView(TableViewActivity.this, R.layout.table_view_head_text_view_layout);
                view.setText(o.toString());
                return view;

            }

            @Override
            public void getView(CommonAdapter.ViewHolder holder, RowItem rowItem) {

                Map<String, Object> valueInfo = (Map<String, Object>) rowItem.getRowData();

                ArrayList<ItemCell> itemCells = new ArrayList();

                try {

                    ItemCell itemCell = new ItemCell(stampstoTime(valueInfo.get("dataTime").toString(), "yyyy-MM-dd"));
                    itemCells.add(itemCell);

                    ItemCell itemCell2 = new ItemCell(valueInfo.get("dipXChange"));
                    itemCells.add(itemCell2);

                    ItemCell itemCell1 = new ItemCell(valueInfo.get("dipX"));
                    itemCells.add(itemCell1);


                    ItemCell itemCell3 = new ItemCell(valueInfo.get("dipXRate"));
                    itemCells.add(itemCell3);

                    ItemCell itemCell5 = new ItemCell(valueInfo.get("dipYChange"));
                    itemCells.add(itemCell5);

                    ItemCell itemCell4 = new ItemCell(valueInfo.get("dipY"));
                    itemCells.add(itemCell4);


                    ItemCell itemCell6 = new ItemCell(valueInfo.get("dipYRate"));
                    itemCells.add(itemCell6);

                    TableItem item = (TableItem) holder.getConvertView();
                    item.buildItem(this,rowItem.setCells(itemCells));

                } catch (Exception e) {
                    e.printStackTrace();
                    outRedPrint("数据解析错误了哦");
                }

//                //填充设置每行数据
//                rowItem .addCell(new ItemCell(map.get("id").toString()))
//                        .addCell(new ItemCell(TimeUtil.stampstoTime(map.get("createdTime").toString(), "yyyy-MM-dd HH:mm:ss")))
//                        .addCell(new ItemCell(map.get("createdBy").toString()))
//                        .addCell(new ItemCell(map.get("ls").toString()));
//
//
//                TableItem item = (TableItem) holder.getConvertView();
//                item.buildItem(this, rowItem);
//
//                JavaUtils.outRedPrint("TableView"+rowItem.toString());
//


            }

            @Override
            public View cellItem(ItemCell obj, int cellIndex, RowItem rowItem) {
                View view = AndroidUtils.getView(TableViewActivity.this, R.layout.table_cell_text_view_layout);
                TextView textView = view.findViewById(R.id.tvCell);

                //将填充单元格数据取出 注意和 getView 中的对应关系
                textView.setText(obj.getCellValue().toString());

                if (rowItem.getPosition() % 2 == 0) {
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
                return tableView3;
            }

        });


        String unitInfo = "mm";

        List<String> headList = new ArrayList<>();
        headList.add("时间");

        headList.add("X轴本次变化" + "(" + unitInfo + ")");
        headList.add("X轴累计变化" + "(" + unitInfo + ")");
        headList.add("X轴本次速率" + "(" + unitInfo + "/d)");

        headList.add("Y轴本次变化" + "(" + unitInfo + ")");
        headList.add("Y轴累计变化" + "(" + unitInfo + ")");
        headList.add("Y轴本次速率" + "(" + unitInfo + "/d)");

        tableView3.setViewConfig(initConfig(headList.size()));

        tableView3.setHead(headList);
        tableView3.setData(getData());

    }

    private int cellWidth = 80;
    //初始化配置参数
    private TableViewConfig initConfig(int size) {

        //单位均为dp
        TableViewConfig config = new TableViewConfig()
                .setCellsHeight((int) ScreenUtils.dip2px(TableViewActivity.this,40))      //设置单元格的高度
                .setDivider(1)          //设置分割线高
                .setDividerColor(getResources().getColor(R.color.white))  //设置分割线颜色
                .setCloseCycle(true);   //是否形成分割闭环样式

        if (size <= 4 && size > 0) {
            cellWidth = (int) (ScreenUtils.px2dip(TableViewActivity.this, ScreenUtils.getScreenWidth(TableViewActivity.this)) / size) - 4;
            config.setCellsWidth((int) ScreenUtils.dip2px(TableViewActivity.this,cellWidth));   // 设置单元格的宽度
        } else {
            config.setCellsWidth((int) ScreenUtils.dip2px(TableViewActivity.this,cellWidth));
        }
        return config;
    }

    private void test() {

        tableView.setOnCellItemClickListener(new OnCellItemClickListener() {
            @Override
            public void onClick(View view, ItemCell itemCell, RowItem rowItem) {

                TextView textView = view.findViewById(R.id.tvCell);
                textView.setText("点击");
                ToastTool.get().show(itemCell.getCellValue().toString());

                outRedPrint("当前单元格信息:" + itemCell.toString());
                outRedPrint("当前行数据:" + rowItem.toString());
            }

        });

        tableView.setFillContentListener(new FillContentListener() {
            @Override
            public View addHead(Object o) {
                TextView view = (TextView) AndroidUtils.getView(TableViewActivity.this, R.layout.table_view_head_text_view_layout);
                view.setText(o.toString());
                return view;

            }

            @Override
            public void getView(CommonAdapter.ViewHolder holder, RowItem rowItem) {

                Map<String, Object> map = (Map<String, Object>) rowItem.getRowData();

                JavaUtils.outRedPrint("TableView:"+rowItem.getCells().size());

                 //填充设置每行数据
                rowItem .addCell(new ItemCell(map.get("id").toString()))
                        .addCell(new ItemCell(TimeUtil.stampstoTime(map.get("createdTime").toString(), "yyyy-MM-dd HH:mm:ss")))
                        .addCell(new ItemCell(map.get("createdBy").toString()))
                        .addCell(new ItemCell(map.get("ls").toString()));


                TableItem item = (TableItem) holder.getConvertView();
                item.buildItem(this, rowItem);

                JavaUtils.outRedPrint("TableView"+rowItem.toString());

//                //或者重新构建一行数据
//                RowItem rowItem1 = new RowItem(
//                        rowItem.getPosition(),
//                        rowItem.isLastItem(),
//                        new ItemCell(map.get("id").toString()),
//                        new ItemCell(TimeUtil.stampstoTime(map.get("createdTime").toString(), "yyyy-MM-dd HH:mm:ss")),
//                        new ItemCell(map.get("createdBy").toString()),
//                        new ItemCell(map.get("ls").toString()));
//
//                TableItem item = (TableItem) holder.getConvertView();
//                item.buildItem(this, rowItem1);


            }

            @Override
            public View cellItem(ItemCell obj, int cellIndex, RowItem rowItem) {
                View view = AndroidUtils.getView(TableViewActivity.this, R.layout.table_cell_text_view_layout);
                TextView textView = view.findViewById(R.id.tvCell);

                //将填充单元格数据取出 注意和 getView 中的对应关系
                textView.setText(obj.getCellValue().toString());

                if (rowItem.getPosition() % 2 == 0) {
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



        List<String> head = new ArrayList<>();
        List<Map<String, Object>> content = getContent();
        for (int i = 0; i < 4; i++) {
            head.add(content.get(i).get("name").toString());
        }

        tableView.setHead(head);
        tableView.setData(content);

    }


    private void test1(final TableViewConfig config) {

        tableView1.setViewConfig(config);

        tableView1.setFillContentListener(new FillContentListener() {
            @Override
            public View addHead(final Object obj) {
                TextView view = (TextView) AndroidUtils.getView(TableViewActivity.this, R.layout.table_view_head_text_view_layout1);
                view.setText(obj.toString());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastTool.get().show(obj.toString());
                    }
                });

                return view;
            }

            @Override
            public void getView(CommonAdapter.ViewHolder holder, RowItem rowItem) {

                PersonInfoBean.DataBean dataBean = (PersonInfoBean.DataBean) rowItem.getRowData();

                rowItem.addCell(new ItemCell(dataBean.getName()))
                        .addCell(new ItemCell(dataBean.getJob()))
                        .addCell(new ItemCell(dataBean.getCreatedTime()))
                        .addCell(new ItemCell(dataBean.getMoney()));

                TableItem item = (TableItem) holder.getConvertView();
                item.buildItem(this, rowItem);


//                //或者重新构建一行数据
//                RowItem rowItem1 = new RowItem(rowItem.getPosition(),
//                        rowItem.isLastItem(),
//                        new ItemCell(dataBean.getName()),
//                        new ItemCell(dataBean.getJob()),
//                        new ItemCell(dataBean.getCreatedTime()),
//                        new ItemCell(dataBean.getMoney()));
//                TableItem item = (TableItem) holder.getConvertView();
//                item.buildItem(this, rowItem1);


            }

            @Override
            public View cellItem(ItemCell obj, int cellIndex, RowItem rowItem) {

                //这里可做更多View 替换。
                View view = AndroidUtils.getView(TableViewActivity.this, R.layout.table_cell_text_view_layout1);

                TextView textView = view.findViewById(R.id.tvCell);
                textView.setText(obj.getCellValue().toString());
                if (rowItem.getPosition() % 2 == 0) {
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
        head.add("姓名阿三发生达大厦大");
        head.add("职位11111111111");
        head.add("时间");
        head.add("待遇");

        tableView1.setHead(head);
        tableView1.setData(personInfoBean.getData());


    }

    private void test2(TableViewConfig config) {

        tableView2.setViewConfig(config);
        tableView2.setFillContentListener(new FillContentListener() {
            @Override
            public View addHead(Object obj) {
                TextView view = (TextView) AndroidUtils.getView(TableViewActivity.this, R.layout.table_view_head_text_view_layout1);
                view.setText(obj.toString());
                return view;
            }

            @Override
            public void getView(CommonAdapter.ViewHolder holder, RowItem rowItem) {

                PersonInfoBean.DataBean dataBean = (PersonInfoBean.DataBean) rowItem.getRowData();


                //设置每行数据
                rowItem.addCell(new ItemCell(dataBean.getName()))
                        .addCell(new ItemCell(dataBean.getJob()))
                        .addCell(new ItemCell(dataBean.getCreatedTime()))
                        .addCell(new ItemCell(dataBean.getMoney()));


                TableItem item = (TableItem) holder.getConvertView();
                item.buildItem(this, rowItem);

            }

            @Override
            public View cellItem(ItemCell obj, int cellIndex, RowItem rowItem) {

                View view = AndroidUtils.getView(TableViewActivity.this, R.layout.table_cell_text_view_layout1);

                TextView textView = view.findViewById(R.id.tvCell);
                textView.setText(obj.getCellValue().toString());

                if (rowItem.getPosition() % 2 == 0) {
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

        tableView2.setHead(head);
        tableView2.setData(personInfoBean.getData());

    }




    private List<Map<String, Object>> getContent() {
        List<Map<String, Object>> datas = new ArrayList<>();

        JSONArray jsonArray = JSON.parseArray(Constans.testJson);

        for (int i = 0; i < jsonArray.size(); i++) {
            datas.add(getMap4Json(jsonArray.get(i).toString()));
        }
        // outRedPrint(Constans.testJson);
        return datas;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> datas = new ArrayList<>();

        JSONArray jsonArray = JSON.parseArray(Constans.testData);

        for (int i = 0; i < jsonArray.size(); i++) {
            datas.add(getMap4Json(jsonArray.get(i).toString()));
        }
        return datas;
    }


}
