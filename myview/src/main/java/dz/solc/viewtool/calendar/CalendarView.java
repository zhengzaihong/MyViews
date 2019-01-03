package dz.solc.viewtool.calendar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dz.solc.viewtool.R;


/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2017/12/5
 * creat_time: 21:00
 * describe: 自定义日历控件
 **/
public class CalendarView extends LinearLayout {

    protected Context mContext;
    protected TextView tvSunday;
    protected TextView tvMonday;
    protected TextView tvTuesday;
    protected TextView tvWednesday;
    protected TextView tvThursday;
    protected TextView tvFriday;
    protected TextView tvSaturday;

    protected GridView gridView;
    protected Calendar calendar = Calendar.getInstance();
    protected CalendarAdapter adapter;

    private WatchOtherLayoutDrawListener layoutDrawListener;
    private int defalutItmeLayout = R.layout.calendar_cell_itme;
    protected View view;

    public CalendarView(Context context) {
        super(context);
        this.mContext = context;
        initview();
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initview();
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initview();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        initview();
    }

    private void initview() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.calendar_view_nav, this);
        bindlistener();
        initdata();
    }

    private void bindlistener() {
        gridView = findViewById(R.id.gv_cells);

        tvMonday = findViewById(R.id.tvMonday);
        tvTuesday = findViewById(R.id.tvTuesday);
        tvWednesday = findViewById(R.id.tvWednesday);
        tvThursday = findViewById(R.id.tvThursday);
        tvFriday = findViewById(R.id.tvFriday);


        tvSaturday = findViewById(R.id.tvSaturday);
        tvSunday = findViewById(R.id.tvSunday);
    }


    public CalendarView setMargin(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view.getLayoutParams());
        params.setMargins(left, top, right, bottom);
        view.setLayoutParams(params);

        return this;
    }

    public CalendarView setPading(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view.getLayoutParams());
        view.setPadding(left, top, right, bottom);
        view.setLayoutParams(params);
        return this;
    }


    public CalendarView setBackGround(int res) {
        view.setBackgroundResource(res);
        return this;
    }

    public CalendarView setWorkDayColor(int color) {
        tvMonday.setTextColor(color);
        tvTuesday.setTextColor(color);
        tvWednesday.setTextColor(color);
        tvThursday.setTextColor(color);
        tvFriday.setTextColor(color);
        return this;
    }

    public CalendarView setRestDayColor(int color) {
        tvSaturday.setTextColor(color);
        tvSunday.setTextColor(color);
        return this;
    }

    //周末可能还是另外一种颜色
    public CalendarView setSunDayColor(int color) {
        tvSunday.setTextColor(color);
        return this;
    }

    //设置外置itme样式
    public CalendarView setAdapterItmeLayout(int layout) {
        this.defalutItmeLayout = layout;
        initdata();
        return this;
    }

    //设置外置itme样式
    public CalendarView setOnItmeClickListener(AdapterView.OnItemClickListener listener) {
        gridView.setOnItemClickListener(listener);
        return this;
    }

    //设置外置itme样式
    public CalendarView setAdapterDrawListener(WatchOtherLayoutDrawListener layoutDrawListener) {
        this.layoutDrawListener = layoutDrawListener;
        return this;
    }

    public interface WatchOtherLayoutDrawListener {
        void convert(CalendarAdapter.ViewHolder holder, int position, Object entity);
    }


    //TODO  默认布局文件没使用  如果是外部自定义布局可以 使用该方法 查看上一个月 下一个月
    //上一个月
    public CalendarView preMonth() {
        calendar.add(Calendar.MONTH, -1);
        return this;
    }

    //下一个月
    public CalendarView nextMonth() {
        calendar.add(Calendar.MONTH, 1);
        return this;
    }

    protected void initdata() {
        //用于存储每月的天数
        ArrayList<Date> cells = new ArrayList<>();
        //复制一个日历工具
        Calendar cellcalendar = (Calendar) calendar.clone();
        //指向当月的第一天
        cellcalendar.set(Calendar.DAY_OF_MONTH, 1);
        //计算出当月第一天 星期几
        int predays = cellcalendar.get(Calendar.DAY_OF_WEEK) - 1;
        cellcalendar.add(Calendar.DAY_OF_MONTH, -predays);
        //假如当月第一天可能出现在 周六上，即当月1号在第一行的末尾，则出现6行,则最大有42个单元格。
        int maxsize = 6 * 7;
        while (cells.size() < maxsize) {
            cells.add(cellcalendar.getTime());
            cellcalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        adapter = new CalendarAdapter(mContext, cells, defalutItmeLayout) {
            @Override
            public void convert(ViewHolder holder, int position, Object entity) {
                if (null != layoutDrawListener) {
                    layoutDrawListener.convert(holder, position, entity);
                }
            }
        };
        gridView.setAdapter(adapter);

        //todo  如果需要参与页面滑动  动态计算下gridView 高度
    }

    // 返回当前日历adapter
    public CalendarAdapter getCalendarAdapter() {
        return adapter;
    }

    //设置隐藏其他月份的日期显示
    public CalendarView setHideOtherMonthDay(boolean hideotherday) {
        getCalendarAdapter().setHideOtherMonthDay(hideotherday);
        return this;
    }

    //设置其他月份的颜色
    public CalendarView setOtherMonthDayColor(int color) {
        getCalendarAdapter().setOtherMonthDayColor(color);
        return this;
    }

    //设置当天的颜色
    public CalendarView setCurrentDayColor(int color) {
        getCalendarAdapter().setCurrentDayColor(color);
        return this;
    }

    //设置当月非当天的颜色
    public CalendarView setInmonthNotToDayColor(int color) {
        getCalendarAdapter().setInmonthNotToDayColor(color);
        return this;
    }


}
