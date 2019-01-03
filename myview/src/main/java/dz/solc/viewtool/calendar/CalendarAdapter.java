package dz.solc.viewtool.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dz.solc.viewtool.R;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2017/12/5
 * creat_time: 21:42
 * describe: 日期适配器
 **/
public abstract class CalendarAdapter<T> extends BaseAdapter {
    private List<Date> listdata;
    private Set<Integer> singdays = new HashSet<>();
    private Context mContext;
    private LayoutInflater inflater;
    private Date nowdate;
    private boolean hideotherday = false;
    private int otherdaycolor = R.color.black_alpha_80;
    private int currendaycolor = R.color.red;
    private int inmonthnotday = R.color.red_200;
    private int layoutId;

    public CalendarAdapter(Context context, List<Date> listdata, int layoutId) {
        this.mContext = context;
        this.listdata = listdata;
        this.layoutId = layoutId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        nowdate = new Date();
    }


    public void updatedays(Set<Integer> singdays) {
        this.singdays = singdays;
        notifyDataSetChanged();
    }

    //设置隐藏其他月份的日期显示
    public CalendarAdapter setHideOtherMonthDay(boolean hideotherday) {
        this.hideotherday = hideotherday;
        return this;
    }

    //设置其他月份的颜色
    public CalendarAdapter setOtherMonthDayColor(int color) {
        this.otherdaycolor = color;
        return this;
    }

    //设置当天的颜色
    public CalendarAdapter setCurrentDayColor(int color) {
        this.currendaycolor = color;
        return this;
    }

    //设置当月非当天的颜色
    public CalendarAdapter setInmonthNotToDayColor(int color) {
        this.inmonthnotday = color;
        return this;
    }

    @Override
    public int getCount() {
        return null != listdata ? listdata.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null != listdata ? listdata.get(i) : i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutId == R.layout.calendar_cell_itme) {
            ViewHolder1 holder;
            if (null == convertView) {
                convertView = inflater.inflate(layoutId, null);
                holder = new ViewHolder1(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder1) convertView.getTag();
            }
            Date date = listdata.get(position);
            holder.tv_date.setText(date.getDate() + "");
            //判断时间是不是当天
            if (nowdate.getDate() == date.getDate() && nowdate.getYear() == date.getYear() && nowdate.getMonth() == date.getMonth() && nowdate.getDay() == date.getDay()) {
                holder.tv_date.setTextColor(currendaycolor);
            } else {
                holder.tv_date.setTextColor(inmonthnotday);
            }
            //如果签到包含则改变字体颜色
            if (singdays.contains(date.getDate())) {
                holder.tv_date.setTextColor(currendaycolor);
            }
            //把不是本月份的号数设置颜色
            if (nowdate.getMonth() != date.getMonth()) {
                holder.tv_date.setTextColor(otherdaycolor);
                if (hideotherday) {
                    holder.tv_date.setVisibility(View.INVISIBLE);
                }
            }

            return convertView;
        } else {
            ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
            convert(holder, position, (T) listdata.get(position));
            return holder.getConvertView();
        }

    }

    private class ViewHolder1 {
        View view;
        TextView tv_date;

        public ViewHolder1(View view) {
            this.view = view;
            tv_date = view.findViewById(R.id.tv_date);
        }
    }

    /**
     * @param holder 是getView中要用来返回的哪个holder，第二个参数是getItem(position)，是list<T>中的那个T，即每行的数据对象
     */
    public abstract void convert(ViewHolder holder, int position, T entity);

    public static class ViewHolder {
        private int mPosition;
        private View mConvertView;
        private SparseArray<View> mViews;

        private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            this.mPosition = position;
            this.mViews = new SparseArray<View>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            mConvertView.setTag(this);
        }

        public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
            if (convertView == null) {
                return new ViewHolder(context, parent, layoutId, position);
            } else {
                ViewHolder holder = (ViewHolder) convertView.getTag();
                holder.mPosition = position;
                return holder;
            }
        }

        //根据id，从xml中找出来
        public <E extends View> E getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);

            }
            return (E) view;
        }

        public View getConvertView() {
            return mConvertView;
        }

        /**
         * 根据textView的id和传来的字符串直接给这个textView赋值
         *
         * @param viewId
         * @param text
         * @return
         */
        public ViewHolder setText(int viewId, String text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }


        public ViewHolder setImageResource(int viewId, int resId) {
            ImageView view = getView(viewId);
            view.setImageResource(resId);
            return this;
        }

        public ViewHolder setImageBitmap(int viewId, Bitmap bitMap) {
            ImageView view = getView(viewId);
            view.setImageBitmap(bitMap);
            return this;
        }


    }
}
