package dz.solc.viewtool.view.tableview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.LinkedHashSet;
import java.util.List;

import dz.solc.viewtool.view.tableview.listener.FillContentListener;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/7/15
 * create_time: 11:33
 * describe 用于每行包含cell的容器
 **/

@SuppressWarnings("all")
public class TableItem extends LinearLayout {

    //TODO 记录所有Views 方便后续做 表修改获取数据功能
    private LinkedHashSet<View> mViews = new LinkedHashSet<>();

    public TableItem(Context context) {
        this(context, null);
    }

    public TableItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOrientation(LinearLayout.VERTICAL);

    }

    public void buildItem(FillContentListener listener, TableView tableView, final RowItem rowItem) {

        //先移除全部旧数据显示
        this.removeAllViews();
        this.mViews.clear();

        final int rowPosition = rowItem.getPosition();
        final List<ItemCell> itemCells = rowItem.getCells();
        final boolean isLastItem = rowItem.isLastItem();

        final ColumnController controller = tableView.getColumnController();
        final TableViewConfig viewConfig = tableView.getViewConfig();
        final boolean isCloseCycle = viewConfig.isCloseCycle();
        final int dividerHeight = viewConfig.getDividerHeight();
        final int dividerWidth = viewConfig.getDividerWidth();
        final int cellWidth = viewConfig.getCellWidth();
        final int cellHeight = viewConfig.getCellHeight();

        boolean autoWrapHeight = viewConfig.isAutoWrapHeight();



        final LinearLayout secondLayout = new LinearLayout(getContext());
        secondLayout.setOrientation(LinearLayout.HORIZONTAL);

        LayoutParams  secondLayoutParm = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if(viewConfig.isEnableWeight()){
            float weightSum = 0;
            for (Float aFloat : viewConfig.getWeightBody()) {
                weightSum = weightSum+aFloat;
            }

            secondLayout.setWeightSum(weightSum);
        }
        secondLayout.setLayoutParams(secondLayoutParm);


        LinearLayout headBottomLine;

        if (rowPosition == 0) {
            headBottomLine = new LinearLayout(getContext());
            ViewGroup.LayoutParams headBottomLineParms = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight);
            headBottomLine.setLayoutParams(headBottomLineParms);
            headBottomLine.setOrientation(LinearLayout.HORIZONTAL);
            headBottomLine.setBackground(new ColorDrawable(viewConfig.getDividerColor()));
            this.addView(headBottomLine);
        }




        int tempWidth;
        for (int i = 0; i < itemCells.size(); i++) {

            final ItemCell itemCell = itemCells.get(i);
            //获取到外部提供单元格信息
            View view = listener.cellItem(itemCell, i, rowItem);


            //按照权重分配
            if(viewConfig.isEnableWeight()){
                LayoutParams cellParms = new LayoutParams(0, cellHeight);
                if (autoWrapHeight) {
                    cellParms.height = LayoutParams.MATCH_PARENT;
                    if (!isLastItem) {
                        cellParms.bottomMargin = viewConfig.getDividerMargin();
                    }
                }

                //设置单个cell 权重
                cellParms.weight = viewConfig.getWeightBody()[i];
                cellParms.width = 0;
                view.setLayoutParams(cellParms);
            }
            else{
                //非权重宽度 则可以处理特殊某列宽度

                int specialWidth = -1;
                tempWidth = cellWidth;
                if (null != controller && controller.isContainsKey(i)) {
                    specialWidth = controller.getSpecialWidth(i);
                }
                if (specialWidth >= 0) {
                    tempWidth = specialWidth;
                }
                LayoutParams cellParms = new LayoutParams(tempWidth, cellHeight);
                if (autoWrapHeight) {
                    cellParms.width = tempWidth;
                    cellParms.height = LayoutParams.MATCH_PARENT;

                    //TODO 增加一个可控制分割的边距
                    if (!isLastItem) {
                        cellParms.bottomMargin = viewConfig.getDividerMargin();
                    }
                } else {
                    cellParms = new LayoutParams(tempWidth, cellHeight);
                }
                view.setLayoutParams(cellParms);
            }

            view.setMinimumHeight(cellHeight);

            if(!view.hasOnClickListeners()){
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //回调给用户点击的cell
                        //新增返回一整行数据
                        if (null != viewConfig.getOnCellItemClickListener()) {
                            viewConfig.getOnCellItemClickListener().onClick(v, itemCell, rowItem);
                        }
                    }
                });
            }

            if (isCloseCycle) {
                if (i == 0) {
                    LinearLayout rowStartLine = new LinearLayout(getContext());
                    rowStartLine.setLayoutParams(new LinearLayout.LayoutParams(dividerWidth, LinearLayout.LayoutParams.MATCH_PARENT));
                    rowStartLine.setBackgroundColor(viewConfig.getDividerColor());
                    secondLayout.addView(rowStartLine);
                    secondLayout.addView(view);
                } else if (i == itemCells.size() - 1) {

                    LinearLayout lastPrelineBg = new LinearLayout(getContext());
                    lastPrelineBg.setLayoutParams(new LinearLayout.LayoutParams(dividerWidth, LinearLayout.LayoutParams.MATCH_PARENT));
                    lastPrelineBg.setBackgroundColor(viewConfig.getDividerColor());
                    secondLayout.addView(lastPrelineBg);

                    secondLayout.addView(view);

                    LinearLayout lastlineBg = new LinearLayout(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dividerWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                    lastlineBg.setLayoutParams(layoutParams);
                    lastlineBg.setBackgroundColor(viewConfig.getDividerColor());

                    secondLayout.addView(lastlineBg);
                } else {
                    RelativeLayout lineBg = new RelativeLayout(getContext());
                    lineBg.setLayoutParams(new RelativeLayout.LayoutParams(dividerWidth, LinearLayout.LayoutParams.MATCH_PARENT));
                    lineBg.setBackgroundColor(viewConfig.getDividerColor());
                    secondLayout.addView(lineBg);
                    secondLayout.addView(view);
                }

            } else {
                secondLayout.addView(view);
                if (i != itemCells.size() - 1) {
                    RelativeLayout lineBg = new RelativeLayout(getContext());
                    lineBg.setLayoutParams(new RelativeLayout.LayoutParams(dividerWidth, LinearLayout.LayoutParams.MATCH_PARENT));
                    lineBg.setBackgroundColor(viewConfig.getDividerColor());
                    secondLayout.addView(lineBg);
                }
            }
            //添加到记录中mViews
            mViews.add(view);
        }

        //添加每行数据到视图记录
        tableView.addDataView(rowPosition, mViews);

        //添加listView item 分割线
        RelativeLayout lineDivider = new RelativeLayout(getContext());
        lineDivider.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, dividerHeight));
        lineDivider.setBackgroundColor(viewConfig.getDividerColor());

        if (isLastItem) {
            if (isCloseCycle) {
                this.addView(lineDivider);
            }
        } else {
            this.addView(lineDivider);
        }


        this.addView(secondLayout);

    }
}
