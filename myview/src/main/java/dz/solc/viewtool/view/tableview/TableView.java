package dz.solc.viewtool.view.tableview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dz.solc.viewtool.adapter.CommonAdapter;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/7/11
 * creat_time: 13:42
 * describe 表格控件
 **/
@SuppressWarnings("all")
public class TableView<E> extends HorizontalScrollView {

    private Context mContext;
    private LinearLayout headLayout;
    private ListView listView;

    private List<E> datas = new ArrayList<>();

    private TableViewAdapter customeTableViewAdapter;

    private TableViewConfig viewConfig;


    public TableView(Context context) {
        this(context, null);
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = this.getContext();

        viewConfig = new TableViewConfig(mContext);


        initView();
    }

    private void initView() {

        final LinearLayout wrapView = new LinearLayout(mContext);
        ViewGroup.LayoutParams wrapViewParms = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wrapView.setOrientation(LinearLayout.VERTICAL);
        wrapView.setLayoutParams(wrapViewParms);

        headLayout = new LinearLayout(mContext);
        ViewGroup.LayoutParams headLayoutParms = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headLayout.setLayoutParams(headLayoutParms);
        headLayout.setOrientation(LinearLayout.HORIZONTAL);


        LinearLayout lineBg = new LinearLayout(mContext);
        lineBg.setLayoutParams(new LinearLayout.LayoutParams(viewConfig.getDividerWidth(), LinearLayout.LayoutParams.MATCH_PARENT));
        lineBg.setBackgroundColor(viewConfig.getDividerColor());


        listView = new ListView(mContext);
        LinearLayout.LayoutParams listViewParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listView.setLayoutParams(listViewParms);
        listView.setCacheColorHint(Color.parseColor("#00000000"));
        listView.setVerticalScrollBarEnabled(false);
        listView.setDividerHeight(0);
        listView.setDivider(new ColorDrawable(Color.TRANSPARENT));

        wrapView.addView(headLayout);
        wrapView.addView(listView);

        this.addView(wrapView);


    }


    public void setViewConfig(TableViewConfig viewConfig) {
        this.viewConfig = viewConfig;
        this.removeAllViews();
        initView();
    }

    public TableViewConfig getViewConfig() {
        return viewConfig;
    }


    /**
     * 添加头部信息
     */
    private void setHead(List headData) {

        if (null == headData || headData.size() == 0) {
            return;
        }

        HashMap headMap = new HashMap();
        for (int i = 0; i < headData.size(); i++) {
            //TODO 头宽待处理
            HeadItemCell itemCell = new HeadItemCell(headData.get(i), 100);
            headMap.put(headMap.size() + "", itemCell);
        }
        if (null != contentListener) {
            boolean isCloseCysle = viewConfig.isCloseCycle();
            for (int i = 0; i < headMap.size(); i++) {
                if (isCloseCysle) {

                    LinearLayout wrapHeadLayout = new LinearLayout(mContext);
                    wrapHeadLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    wrapHeadLayout.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout topLine = new LinearLayout(mContext);
                    if (i == headMap.size() - 1) {
                        topLine.setLayoutParams(new ViewGroup.LayoutParams(viewConfig.getHeadViewWidth() + 2 * viewConfig.getDividerWidth(), viewConfig.getDividerHight()));
                    } else {
                        topLine.setLayoutParams(new ViewGroup.LayoutParams(viewConfig.getHeadViewWidth() + viewConfig.getDividerWidth(), viewConfig.getDividerHight()));
                    }

                    topLine.setBackgroundColor(viewConfig.getDividerColor());

                    wrapHeadLayout.addView(topLine);

                    LinearLayout wrapHeadLayoutContent = new LinearLayout(mContext);
                    wrapHeadLayoutContent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    wrapHeadLayoutContent.setGravity(Gravity.CENTER_VERTICAL);
                    wrapHeadLayoutContent.setOrientation(LinearLayout.HORIZONTAL);

                    wrapHeadLayout.addView(wrapHeadLayoutContent);

                    LinearLayout v_line = new LinearLayout(mContext);
                    v_line.setLayoutParams(new LinearLayout.LayoutParams(viewConfig.getDividerWidth(), LinearLayout.LayoutParams.MATCH_PARENT));
                    v_line.setOrientation(LinearLayout.VERTICAL);
                    v_line.setBackgroundColor(viewConfig.getDividerColor());

                    wrapHeadLayoutContent.addView(v_line);


                    View view = contentListener.addHead(headData.get(i));
                    view.setLayoutParams(new ViewGroup.LayoutParams(viewConfig.getHeadViewWidth(), viewConfig.getHeadViewHeight()));
                    wrapHeadLayoutContent.addView(view);

                    if (i == headMap.size() - 1) {
                        LinearLayout rowlastLine = new LinearLayout(mContext);
                        rowlastLine.setLayoutParams(new LinearLayout.LayoutParams(viewConfig.getDividerWidth(), LinearLayout.LayoutParams.MATCH_PARENT));
                        rowlastLine.setBackgroundColor(viewConfig.getDividerColor());
                        wrapHeadLayoutContent.addView(rowlastLine);
                    }

                    headLayout.addView(wrapHeadLayout);
                } else {
                    View view = contentListener.addHead(headData.get(i));
                    view.setLayoutParams(new ViewGroup.LayoutParams(viewConfig.getHeadViewWidth(), viewConfig.getHeadViewHeight()));
                    headLayout.addView(view);
                    if (i != headMap.size() - 1) {
                        LinearLayout v_line = new LinearLayout(mContext);
                        v_line.setLayoutParams(new LinearLayout.LayoutParams(viewConfig.getDividerWidth(), LinearLayout.LayoutParams.MATCH_PARENT));
                        v_line.setBackgroundColor(viewConfig.getDividerColor());
                        headLayout.addView(v_line);
                    }
                }

            }
        }
    }

    /**
     * 设置内容数据
     */

    public void setData(List headList, List<E> data) {

        //添加头
        setHead(headList);

        //填充数据
        if (null == data || data.size() == 0) {
            return;
        }
        if (null != contentListener) {
            for (int i = 0; i < data.size(); i++) {
                datas.add((E) new ItemCell(data.get(i)));
            }
        }

        customeTableViewAdapter = new TableViewAdapter(mContext, contentListener);
        listView.setAdapter(customeTableViewAdapter);
        customeTableViewAdapter.setNewData(datas);
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(List<E> data) {

        if (null != customeTableViewAdapter && null != data) {
            customeTableViewAdapter.addData(data);
        }
    }

    /**
     * 清楚表格数据
     */
    public void clearData() {
        if (null != customeTableViewAdapter) {
            customeTableViewAdapter.getDatas().clear();
            notifyDataSetChanged();
        }
    }


    public void notifyDataSetChanged() {
        if (null != customeTableViewAdapter) {
            customeTableViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 返回当前列表 如有需要可实现点击等操作
     *
     * @return
     */
    public ListView getListView() {

        return listView;
    }

    /**
     * 返回适配器数据
     *
     * @return
     */

    public List<E> getAdapterData() {
        return customeTableViewAdapter.getDatas();
    }


    private FillContentListener contentListener;


    public void setFillContentListener(FillContentListener fillContentListener) {
        this.contentListener = fillContentListener;
    }


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
         * @param position   当前item的行
         * @param obj        当前item 对象
         * @param islastItem 是否是最后一行数据
         */

        void getView(CommonAdapter.ViewHolder holder, int position, Object obj, boolean islastItem);

        /**
         * @param obj         当前单元格数据对象
         * @param rowPosition 当前行数
         * @param isLastCell  是否是最后一个单元格
         * @return
         */

        View cellItem(ItemCell obj, int rowPosition, boolean isLastCell);

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


    private OnCellItemClickListener onCellItemClickListener;

    public void setOnCellItemClickListener(OnCellItemClickListener onCellItemClickListener) {
        this.onCellItemClickListener = onCellItemClickListener;
        viewConfig.setOnCellItemClickListener(onCellItemClickListener);

    }

    public interface OnCellItemClickListener {
        /**
         * @param v           当前cell view
         * @param rowPosition 当前所在行
         * @param itemCell    点击的所在行中cell
         */
        void onClick(View v, int rowPosition, ItemCell itemCell);
    }

}
