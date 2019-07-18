package dz.solc.viewtool.view.tableview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/7/15
 * creat_time: 11:33
 * describe 用于每行包含cell的容器
 **/
public class TableItem extends LinearLayout {

    private TableView.FillContentListener listener;
    private LinearLayout headBottomLine;

    public TableItem(Context context) {
        super(context);
    }

    public TableItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TableItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void buildItem(TableView.FillContentListener listener, final int rowPosition, boolean isLastItem, ArrayList<ItemCell> itemCells) {

        this.setOrientation(LinearLayout.VERTICAL);
        this.listener = listener;

        this.removeAllViews();
        LinearLayout secondLayout = new LinearLayout(getContext());
        secondLayout.setOrientation(LinearLayout.HORIZONTAL);
        secondLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        final TableViewConfig viewConfig = listener.bindTableView().getViewConfig();

        final boolean isCloseCycle = viewConfig.isCloseCycle();

        if (rowPosition == 0) {
            headBottomLine = new LinearLayout(getContext());
            ViewGroup.LayoutParams headBottomLineParms = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, viewConfig.getDividerHight());
            headBottomLine.setLayoutParams(headBottomLineParms);
            headBottomLine.setOrientation(LinearLayout.HORIZONTAL);
            headBottomLine.setBackground(new ColorDrawable(viewConfig.getDividerColor()));
            this.addView(headBottomLine);
        }

        this.addView(secondLayout);

        for (int i = 0; i < itemCells.size(); i++) {
            final ItemCell itemCell = itemCells.get(i);

            //TODO 外部提供单元格信息
            View view = listener.cellItem(itemCell, rowPosition, i == itemCells.size() - 1);
            ViewGroup.LayoutParams viewGroupParm = new ViewGroup.LayoutParams(viewConfig.getCellWidth(), viewConfig.getCellHight());
            view.setLayoutParams(viewGroupParm);


            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //回调给用户点击的cell
                    if (null != viewConfig.getOnCellItemClickListener()) {
                        viewConfig.getOnCellItemClickListener().onClick(v, rowPosition, itemCell);
                    }
                }
            });
            if (isCloseCycle) {
                if (i == 0) {
                    LinearLayout rowStartLine = new LinearLayout(getContext());
                    rowStartLine.setLayoutParams(new LinearLayout.LayoutParams(viewConfig.getDividerWidth(), LinearLayout.LayoutParams.MATCH_PARENT));
                    rowStartLine.setBackgroundColor(viewConfig.getDividerColor());
                    secondLayout.addView(rowStartLine);
                    secondLayout.addView(view);
                } else if (i == itemCells.size() - 1) {

                    LinearLayout lastPrelineBg = new LinearLayout(getContext());
                    lastPrelineBg.setLayoutParams(new LinearLayout.LayoutParams(viewConfig.getDividerWidth(), LinearLayout.LayoutParams.MATCH_PARENT));
                    lastPrelineBg.setBackgroundColor(viewConfig.getDividerColor());
                    secondLayout.addView(lastPrelineBg);

                    secondLayout.addView(view);

                    LinearLayout lastlineBg = new LinearLayout(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(viewConfig.getDividerWidth(), LinearLayout.LayoutParams.MATCH_PARENT);
                    lastlineBg.setLayoutParams(layoutParams);
                    lastlineBg.setBackgroundColor(viewConfig.getDividerColor());

                    secondLayout.addView(lastlineBg);
                } else {
                    RelativeLayout lineBg = new RelativeLayout(getContext());
                    lineBg.setLayoutParams(new RelativeLayout.LayoutParams(viewConfig.getDividerWidth(), LinearLayout.LayoutParams.MATCH_PARENT));
                    lineBg.setBackgroundColor(viewConfig.getDividerColor());
                    secondLayout.addView(lineBg);
                    secondLayout.addView(view);
                }

            } else {
                secondLayout.addView(view);
                if (i != itemCells.size() - 1) {
                    RelativeLayout lineBg = new RelativeLayout(getContext());
                    lineBg.setLayoutParams(new RelativeLayout.LayoutParams(viewConfig.getDividerWidth(), LinearLayout.LayoutParams.MATCH_PARENT));
                    lineBg.setBackgroundColor(viewConfig.getDividerColor());
                    secondLayout.addView(lineBg);
                }
            }
        }

        //添加listView item 分割线
        RelativeLayout lineDivider = new RelativeLayout(getContext());
        lineDivider.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, viewConfig.getDividerHight()));
        lineDivider.setBackgroundColor(viewConfig.getDividerColor());

        if (isLastItem) {
            if (isCloseCycle) {
                this.addView(lineDivider);
            }
        } else {
            this.addView(lineDivider);
        }
    }
}
