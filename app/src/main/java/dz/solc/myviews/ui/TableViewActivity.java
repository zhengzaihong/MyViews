package dz.solc.myviews.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dz.utlis.AndroidUtils;
import com.dz.utlis.TimeUtil;
import com.dz.utlis.ToastTool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import dz.solc.myviews.R;
import dz.solc.myviews.bean.PersonInfoBean;
import dz.solc.myviews.uitls.Constans;
import dz.solc.viewtool.adapter.CommonAdapter;
import dz.solc.viewtool.view.tableview.ColumnController;
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

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/7/15
 * create_time: 14:07
 * describe 表格基本功能已具有,更多功能待完善优化.
 **/
@SuppressWarnings("all")
public class TableViewActivity extends AppCompatActivity {

    private TableView<Map<String, Object>> tableView;
    private TableView<PersonInfoBean.DataBean> tableView1;
    private TableView<PersonInfoBean.DataBean> tableView2;
    private Button btGetData;
    private Button btClear;
    private Button btAddData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_tableview);

        tableView = findViewById(R.id.tableView);
        tableView1 = findViewById(R.id.tableView1);
        tableView2 = findViewById(R.id.tableView2);

        btGetData = findViewById(R.id.btGetData);
        btClear = findViewById(R.id.btClear);
        btAddData = findViewById(R.id.btAddData);


        //用于控制特殊列的宽度
        ColumnController controller = new ColumnController();
        //单位注意转换成 dp
        controller.addSpecial(0, 400)
                .addSpecial(3, 800);

        //配置控制器
        tableView.setColumnController(controller);

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableView1.clearData();
            }
        });
        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableView1.clearData();

                PersonInfoBean personInfoBean = JSON.toJavaObject(JSON.parseObject(Constans.testJson1), PersonInfoBean.class);

                List<String> head = new ArrayList<>();
                head.add("姓名阿三发生达大厦大");
                head.add("职位11111111111");
                head.add("时间");
                head.add("待遇");

                tableView1.setHead(head);
                tableView1.setData(personInfoBean.getData());

            }
        });


        btGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseArray<LinkedHashSet<View>> dataViews = tableView.getDataView();

                for (int i = 0; i < dataViews.size(); i++) {
                    LinkedHashSet<View> views = dataViews.get(i);
                    if (null != views) {
                        StringBuffer buffer = new StringBuffer("--------");
                        Iterator<View> iterator = views.iterator();
                        while (iterator.hasNext()) {
                            TextView editText = iterator.next().findViewById(R.id.tvCell);
                            buffer.append(editText.getText().toString()).append("--");
                        }
                        outRedPrint("一行信息：" + buffer.toString());
                    }
                }
            }
        });


        TableViewConfig config1 = new TableViewConfig()
                .setCellsWidth(Utils.dp2px(this, 100))   // 设置单元格的宽度
                .setCellsHeight(Utils.dp2px(this, 40))   //设置单元格的高度
                .setAutoWrapHeight(true)  //自适应高度
                .setShowHead(true)        //是否显表头信息
                .setDivider(0)          //设置分割线高
                .setEditTable(false)    //是否表格中需要编辑
                .setDividerColor(getResources().getColor(R.color.trans))  //设置分割线颜色
                .setCloseCycle(false);   //是否形成分割闭环样式


        TableViewConfig config2 = new TableViewConfig()
                .setCellsWidth(Utils.dp2px(this, 100))   // 设置单元格的宽度
                .setCellsHeight(Utils.dp2px(this, 40))   //设置单元格的高度
                .setDivider(Utils.dp2px(this, 1))          //设置分割线高
                .setDividerColor(getResources().getColor(R.color.sandybrown))  //设置分割线颜色
                .setCloseCycle(true);   //是否形成分割闭环样式


        test();
        test1(config1);
        test2(config2);


        tableView.measureHeight();
        tableView1.measureHeight();
        tableView2.measureHeight();
    }

    private void test() {

        tableView.setOnCellItemClickListener(new OnCellItemClickListener() {
            @Override
            public void onClick(View view, ItemCell itemCell, RowItem rowItem) {

                TextView textView = view.findViewById(R.id.tvCell);
                ToastTool.get().show(itemCell.getCellValue().toString());

                outRedPrint("当前单元格信息:" + itemCell.toString());
                outRedPrint("当前行数据:" + rowItem.toString());
                outRedPrint("当前行数据:" + itemCell.getTag());
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
            public void getView(CommonAdapter.ViewHolder holder, TableView tableView, RowItem rowItem) {

                Map<String, Object> map = (Map<String, Object>) rowItem.getRowData();

                //填充每行数据
                rowItem.addCell(new ItemCell(map.get("id").toString()))
                        .addCell(new ItemCell(TimeUtil.stampstoTime(map.get("createdTime").toString(), "yyyy-MM-dd HH:mm:ss")))
                        .addCell(new ItemCell(map.get("createdBy").toString()))
                        .addCell(new ItemCell(map.get("ls").toString()));


                TableItem item = (TableItem) holder.getConvertView();
                item.buildItem(this, tableView, rowItem);

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
                View view = null;

                //处理不需要编辑的 单元格
                if (cellIndex == 3) {
                    view = AndroidUtils.getView(TableViewActivity.this, R.layout.table_cell_text_view_layout);
                    TextView textView = view.findViewById(R.id.tvCell);
                    textView.setText("操作");
                    obj.setTag(rowItem.getPosition());

                } else {
                    view = AndroidUtils.getView(TableViewActivity.this, R.layout.table_cell_edit_view_layout);
                    EditText editText = view.findViewById(R.id.tvCell);
                    //将填充单元格数据取出 注意和 getView 中的对应关系
                    editText.setText(obj.getCellValue().toString());
                }

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
                return view;
            }

            @Override
            public void getView(CommonAdapter.ViewHolder holder, TableView tableView, RowItem rowItem) {
                PersonInfoBean.DataBean dataBean = (PersonInfoBean.DataBean) rowItem.getRowData();
                rowItem.addCell(new ItemCell(dataBean.getName()))
                        .addCell(new ItemCell(dataBean.getJob()))
                        .addCell(new ItemCell(dataBean.getCreatedTime()))
                        .addCell(new ItemCell(dataBean.getMoney()));

                TableItem item = (TableItem) holder.getConvertView();
                item.buildItem(this, tableView, rowItem);


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
            public void getView(CommonAdapter.ViewHolder holder, TableView tableView, RowItem rowItem) {
                PersonInfoBean.DataBean dataBean = (PersonInfoBean.DataBean) rowItem.getRowData();
                //设置每行数据
                rowItem.addCell(new ItemCell(dataBean.getName()))
                        .addCell(new ItemCell(dataBean.getJob()))
                        .addCell(new ItemCell(dataBean.getCreatedTime()))
                        .addCell(new ItemCell(dataBean.getMoney()));

                TableItem item = (TableItem) holder.getConvertView();
                item.buildItem(this, tableView, rowItem);

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


}
