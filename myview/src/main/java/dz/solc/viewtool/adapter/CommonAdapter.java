package dz.solc.viewtool.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2018/11/30 0030
 * creat_time: 17:42
 * describe: 简化listView 的适配器
 **/
@SuppressWarnings("all")
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> datas;
    protected LayoutInflater mInflater;
    protected int xmlId;

    public CommonAdapter(Context context) {
        this(context, 0);
    }

    public CommonAdapter(Context context, int xmlId) {
        this(context, xmlId, null);
    }

    public CommonAdapter(Context context, int xmlId, List datas) {
        this.mContext = context;
        this.datas = datas == null ? new ArrayList<>() : datas;
        this.mInflater = LayoutInflater.from(context);
        this.xmlId = xmlId;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    public List<T> getDatas() {
        return datas;
    }


    public void setNewData(@Nullable List<T> data) {
        this.datas = data == null ? new ArrayList<T>() : data;
        notifyDataSetChanged();
    }

    public void replaceData(@NonNull Collection<? extends T> data) {
        datas.clear();
        datas.addAll(data);
        notifyDataSetChanged();
    }


    public void addData(@NonNull Collection<? extends T> data) {
        datas.addAll(data);
        notifyDataSetChanged();
    }

    public void setArrayData(T... data) {

        setNewData((Arrays.asList(data)));
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, xmlId, position);
        convert(holder, position, getItem(position));
        return holder.getConvertView();
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
            this.mViews = new SparseArray<>();
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
        public ViewHolder setText(int viewId, CharSequence text) {
            TextView tv = getView(viewId);
            tv.setText(null == text ? "" : text);
            return this;
        }

        public ViewHolder setTextColor(int viewId, int color) {
            TextView tv = getView(viewId);
            tv.setTextColor(color);
            return this;
        }

        public ViewHolder setTextAndColor(int viewId, CharSequence text, int color) {
            TextView tv = getView(viewId);
            tv.setText(null == text ? "" : text);
            tv.setTextColor(color);
            return this;
        }


        public ViewHolder setTextEmptyGone(int viewId, CharSequence text, int color) {
            TextView tv = getView(viewId);
            if (TextUtils.isEmpty(text)) {
                tv.setVisibility(View.GONE);
                return this;
            } else {
                tv.setVisibility(View.VISIBLE);
            }

            tv.setText(null == text ? "" : text);
            tv.setTextColor(color);
            return this;
        }

        public ViewHolder setTextEmptyGone(int viewId, CharSequence text) {
            TextView tv = getView(viewId);
            if (TextUtils.isEmpty(text)) {
                tv.setVisibility(View.GONE);
                return this;
            } else {
                tv.setVisibility(View.VISIBLE);
            }
            tv.setText(null == text ? "" : text);
            return this;
        }


        public ViewHolder setBackgroundResource(int viewId, int resId) {
            ImageView view = getView(viewId);
            view.setBackgroundResource(resId);
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
