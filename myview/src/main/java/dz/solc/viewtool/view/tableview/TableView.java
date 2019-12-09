package dz.solc.viewtool.view.tableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import dz.solc.viewtool.R;
import dz.solc.viewtool.view.tableview.listener.FillContentListener;
import dz.solc.viewtool.view.tableview.listener.OnCellItemClickListener;

import static dz.solc.viewtool.adapter.UtilAdapter.setListViewHeightBasedOnChildren;


/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/7/11
 * creat_time: 13:42
 * describe  支持横向和纵向滑动的表格控件，目前不支持 单元格合并
 *
 **/
@SuppressWarnings("all")
public class TableView<E> extends HorizontalScrollView {

    private static final String TAG = TableView.class.getSimpleName();

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 表头
     */
    private LinearLayout headLayout;
    /**
     * 数据内容列表
     * 利用 listView 的复用机制，减少性能的开销
     */
    private ListView listView;

    /**
     * 数据源
     */
    private List<E> datas = new ArrayList<>();

    /**
     * 列表适配器
     */
    private TableViewAdapter customeTableViewAdapter;

    /**
     * TableView 的配置文件
     */
    private TableViewConfig viewConfig;

    /**
     * 记录所有的视图
     */
    private SparseArray<LinkedHashSet<View>> itemsView = new SparseArray<LinkedHashSet<View>>();


    public TableView(Context context) {
        this(context, null);
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = this.getContext();
        viewConfig = new TableViewConfig();

        // 读取属性值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabaleViewStyle);


        //单独配置 表头和 表内容的宽高 优先级最高
        viewConfig.setHeadViewHeight(ta.getDimensionPixelSize(R.styleable.TabaleViewStyle_table_head_cell_height,
                ta.getDimensionPixelSize(R.styleable.TabaleViewStyle_table_cells_height, 40)));

        viewConfig.setHeadViewWidth(ta.getDimensionPixelSize(R.styleable.TabaleViewStyle_table_head_cell_width,
                ta.getDimensionPixelSize(R.styleable.TabaleViewStyle_table_cells_width, 80)));

        viewConfig.setCellHeight(ta.getDimensionPixelSize(R.styleable.TabaleViewStyle_table_content_cell_height,
                ta.getDimensionPixelSize(R.styleable.TabaleViewStyle_table_cells_height, 40)));

        viewConfig.setCellWidth(ta.getDimensionPixelSize(R.styleable.TabaleViewStyle_table_content_cell_width,
                ta.getDimensionPixelSize(R.styleable.TabaleViewStyle_table_cells_width, 80)));


        viewConfig.setDivider(ta.getDimensionPixelSize(R.styleable.TabaleViewStyle_table_devider_height, 0));
        viewConfig.setDividerColor(ta.getColor(R.styleable.TabaleViewStyle_table_devider_color, Color.TRANSPARENT));
        viewConfig.setAutoWrapHeight(ta.getBoolean(R.styleable.TabaleViewStyle_table_auto_wrap_height, false));
        viewConfig.setShowHead(ta.getBoolean(R.styleable.TabaleViewStyle_table_visible_head, true));
        viewConfig.setCloseCycle(ta.getBoolean(R.styleable.TabaleViewStyle_table_close_cycle, true));
        viewConfig.setEditTable(ta.getBoolean(R.styleable.TabaleViewStyle_table_need_edit, false));

        //回收资源
        ta.recycle();

        initView();
    }


    private void initView() {

        final LinearLayout wrapView = new LinearLayout(mContext);
        ViewGroup.LayoutParams wrapViewParms = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wrapView.setOrientation(LinearLayout.VERTICAL);
        wrapView.setLayoutParams(wrapViewParms);

        if (viewConfig.isShowHead()) {
            headLayout = new LinearLayout(mContext);
            ViewGroup.LayoutParams headLayoutParms = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headLayout.setLayoutParams(headLayoutParms);
            headLayout.setOrientation(LinearLayout.HORIZONTAL);
            wrapView.addView(headLayout);
        }


        LinearLayout lineBg = new LinearLayout(mContext);
        lineBg.setLayoutParams(new LinearLayout.LayoutParams(viewConfig.getDividerWidth(), LinearLayout.LayoutParams.MATCH_PARENT));
        lineBg.setBackgroundColor(viewConfig.getDividerColor());


        //表格列表
        listView = new ListView(mContext);
        disableOverScrollMode(listView);

        ListView.LayoutParams listViewParms = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(listViewParms);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setVerticalScrollBarEnabled(false);
        listView.setDividerHeight(0);
        listView.setDivider(new ColorDrawable(Color.TRANSPARENT));
        listView.setNestedScrollingEnabled(false);
        wrapView.addView(listView);

        this.addView(wrapView);

    }


    /**
     * 去掉拉动效果
     */
    private void disableOverScrollMode(View view) {
        if (Build.VERSION.SDK_INT < 9) {
            return;
        }
        try {
            Method m = View.class.getMethod("setOverScrollMode", int.class);
            m.setAccessible(true);
            m.invoke(view, 2);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * 设置配置文件
     *
     * @param viewConfig
     */
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
    public void setHead(List<?> headData) {

        if (null != contentListener) {
            //新增加头部显不显示控制
            if (null == headData || headData.size() == 0 || !viewConfig.isShowHead()) {
                return;
            }

            int headViewWidth = viewConfig.getHeadViewWidth();

            int dividerWidth = viewConfig.getDividerWidth();
            int dividerHeight = viewConfig.getDividerHeight();

            boolean autoWrapHeight = viewConfig.isAutoWrapHeight();
            int headViewHeight = autoWrapHeight ? ViewGroup.LayoutParams.WRAP_CONTENT : viewConfig.getHeadViewHeight();

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

            HashMap headMap = new LinkedHashMap();

            for (int i = 0; i < headData.size(); i++) {
                HeadItemCell itemCell = new HeadItemCell(headData.get(i), headViewWidth);
                headMap.put(headMap.size() + "", itemCell);
            }

            boolean isCloseCysle = viewConfig.isCloseCycle();
            int dividerColor = viewConfig.getDividerColor();

            for (int i = 0; i < headMap.size(); i++) {
                if (isCloseCysle) {

                    LinearLayout wrapHeadLayout = new LinearLayout(mContext);
                    wrapHeadLayout.setLayoutParams(params);
                    wrapHeadLayout.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout topLine = new LinearLayout(mContext);
                    if (i == headMap.size() - 1) {
                        topLine.setLayoutParams(new ViewGroup.LayoutParams(headViewWidth + 2 * dividerWidth, dividerHeight));
                    } else {
                        topLine.setLayoutParams(new ViewGroup.LayoutParams(headViewWidth + dividerWidth, dividerHeight));
                    }

                    topLine.setBackgroundColor(dividerColor);

                    wrapHeadLayout.addView(topLine);

                    LinearLayout wrapHeadLayoutContent = new LinearLayout(mContext);
                    wrapHeadLayoutContent.setLayoutParams(params);
                    wrapHeadLayoutContent.setGravity(Gravity.CENTER_VERTICAL);
                    wrapHeadLayoutContent.setOrientation(LinearLayout.HORIZONTAL);

                    wrapHeadLayout.addView(wrapHeadLayoutContent);

                    LinearLayout v_line = new LinearLayout(mContext);
                    v_line.setLayoutParams(new LinearLayout.LayoutParams(dividerWidth, LinearLayout.LayoutParams.MATCH_PARENT));
                    v_line.setOrientation(LinearLayout.VERTICAL);
                    v_line.setBackgroundColor(dividerColor);

                    wrapHeadLayoutContent.addView(v_line);


                    View view = contentListener.addHead(headData.get(i));
                    view.setLayoutParams(new LayoutParams(headViewWidth, headViewHeight));
                    view.setMinimumHeight(viewConfig.getHeadViewHeight());
                    wrapHeadLayoutContent.addView(view);

                    if (i == headMap.size() - 1) {
                        LinearLayout rowlastLine = new LinearLayout(mContext);
                        rowlastLine.setLayoutParams(new LinearLayout.LayoutParams(dividerWidth, LinearLayout.LayoutParams.MATCH_PARENT));
                        rowlastLine.setBackgroundColor(dividerColor);
                        wrapHeadLayoutContent.addView(rowlastLine);
                    }

                    headLayout.addView(wrapHeadLayout);
                } else {
                    View view = contentListener.addHead(headData.get(i));
                    view.setLayoutParams(new LayoutParams(headViewWidth, headViewHeight));
                    view.setMinimumHeight(viewConfig.getHeadViewHeight());
                    headLayout.addView(view);

                    if (i != headMap.size() - 1) {
                        LinearLayout v_line = new LinearLayout(mContext);
                        v_line.setLayoutParams(new LinearLayout.LayoutParams(dividerWidth, LinearLayout.LayoutParams.MATCH_PARENT));
                        v_line.setBackgroundColor(dividerColor);
                        headLayout.addView(v_line);
                    }
                }
            }

            //如果是自适应，则重新配置子元素高度
            if (autoWrapHeight) {
                headLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        int viewCount = headLayout.getChildCount();
                        for (int i = 0; i < viewCount; i++) {
                            View view = headLayout.getChildAt(i);
                            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                            if (layoutParams.height != LinearLayout.LayoutParams.MATCH_PARENT) {
                                layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                view.setLayoutParams(layoutParams);
                            }
                        }
                    }
                });
            }
        } else {
            Log.e(TAG, "please bind FillContentListener setHead before!!");
        }
    }

    /**
     * 设置内容数据
     */

    public void setData(List<E> data) {

        if (null != contentListener) {
            if (null == data || data.size() == 0) {
                return;
            }
            //填充数据
            for (int i = 0; i < data.size(); i++) {
                datas.add((E) new RowItem(data.get(i)));
                Log.e(TAG, new RowItem(data.get(i)).toString());
            }

            addEmptyData(datas);

            customeTableViewAdapter = new TableViewAdapter(this, contentListener);
            listView.setAdapter(customeTableViewAdapter);
            customeTableViewAdapter.setNewData(datas);

        } else {
            Log.e(TAG, "please bind FillContentListener setData before!!");
        }
    }

    /**
     * 如果是在类似 ScrollView 中则需要测量
     */
    public void measureHeight() {
        setListViewHeightBasedOnChildren(listView);
    }

    /**
     * @param headData 表头信息
     * @param data     表格数据
     */
    public void setHeadAndData(List<?> headData, List<E> data) {
        setHead(headData);
        setData(data);
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
     * 添加一行空数据，内部使用
     */
    private void addEmptyData(List<E> data) {

        //如果是可编辑的 TableView 则在第一行添加一行过滤行
        if (viewConfig.isEditTable() && viewConfig.isShowHead()) {
            if (null != data && data.size() > 0) {
                datas.add(0, null);
            }
        }
    }


    /**
     * 替换数据
     *
     * @param data
     */
    public void replaceData(List<E> data) {

        if (null != customeTableViewAdapter) {
            addEmptyData(data);
            customeTableViewAdapter.setNewData(data);
        }
    }

    /**
     * 删除表格数据
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
     * 点击事件建议使用提供的 OnCellItemClickListener
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

        if (null != customeTableViewAdapter) {
            return customeTableViewAdapter.getDatas();
        }
        return null;
    }

    /**
     * 添加数据 Cell 视图
     *
     * @param index 集合的 下标
     * @param view  具体的每行 cell
     */
    protected synchronized void addDataView(int index, LinkedHashSet<View> view) {
        itemsView.put(index, view);
    }


    /**
     * 获取整个表格数据 使用
     *
     * @return
     */
    public SparseArray<LinkedHashSet<View>> getDataView() {
        return itemsView;
    }


    /**
     * 必须设置该回调监听，否则不填充数据
     *
     * @param fillContentListener
     */
    private FillContentListener contentListener;

    public void setFillContentListener(FillContentListener fillContentListener) {
        this.contentListener = fillContentListener;
    }


    /**
     * 设置单击每个单元格的点击事件
     *
     * @param onCellItemClickListener
     */

    private OnCellItemClickListener onCellItemClickListener;

    public void setOnCellItemClickListener(OnCellItemClickListener onCellItemClickListener) {
        this.onCellItemClickListener = onCellItemClickListener;
        viewConfig.setOnCellItemClickListener(onCellItemClickListener);

    }

}
