package dz.solc.viewtool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import dz.solc.viewtool.R;


/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2018/4/20
 * creat_time: 10:24
 * describe 普通常用dialog
 **/

@SuppressWarnings("all")
public class OftenDialog {

    private TextView tv_cance;
    private TextView tv_suer;
    private TextView tv_title;
    private TextView tv_content;
    private LinearLayout ll_dialog_bg;
    private View view_line;
    private View view_line2;


    private Dialog dialog;
    private View view;

    private boolean autoClose = true;

    private boolean outTouchside = true;

    public OftenDialog(final Context context) {

        dialog = new Dialog(context, R.style.mOftenDialog);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_often_bg, null);
        dialog.setContentView(view);
        DisplayMetrics outMetrics = new DisplayMetrics();
        dialog.setCanceledOnTouchOutside(outTouchside);
        window.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        window.getAttributes().width = (width / 2 + (width / 2) / 2);

        tv_cance = window.findViewById(R.id.tv_cance);
        tv_suer = window.findViewById(R.id.tv_suer);
        tv_title = window.findViewById(R.id.tv_title);
        tv_content = window.findViewById(R.id.tv_content);

        ll_dialog_bg = window.findViewById(R.id.ll_dialog_bg);
        view_line = window.findViewById(R.id.view_line);
        view_line2 = window.findViewById(R.id.view_line2);

        tv_suer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.oftenSure();
                }
                if (autoClose) {
                    dismiss();
                }
            }
        });
        tv_cance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.oftenCancle();
                }
                if (autoClose) {
                    dismiss();
                }
            }
        });
    }

    //提供的样式不满足 外部拿到view 自定义样式
    public View getView() {
        return view;
    }


    /**
     * 设置取消按钮 不可见
     *
     * @return
     */
    public OftenDialog setCancleGone() {
        tv_cance.setVisibility(View.GONE);
        view_line2.setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置确定按钮 不可见
     *
     * @return
     */
    public OftenDialog setSureGone() {
        tv_suer.setVisibility(View.GONE);
        view_line2.setVisibility(View.GONE);
        return this;
    }


    /**
     * @param title   初始化显示的 标题
     * @param content 初始化显示的 内容
     * @return
     */
    public OftenDialog initData(String title, String content) {
        tv_title.setText(title);
        tv_content.setText(content);
        return this;
    }

    /**
     * @param content 初始化显示的 内容
     * @return
     */
    public OftenDialog initData(String content) {
        tv_content.setText(content);
        return this;
    }

    public OftenDialog setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public OftenDialog setTitleGravity(int gravity) {
        tv_title.setGravity(gravity);
        return this;
    }

    public OftenDialog setContent(String content) {
        tv_content.setText(content);
        return this;
    }

    public OftenDialog setContentGravity(int gravity) {
        tv_content.setGravity(gravity);
        return this;
    }

    /**
     * 标题是否可见
     *
     * @param visible
     * @return
     */
    public OftenDialog showTitle(boolean visible) {
        tv_title.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }


    /**
     * 设置dialog 样式动画
     *
     * @param style
     * @return
     */
    public OftenDialog setDialogAnimation(int style) {
        dialog.getWindow().setWindowAnimations(style);
        return this;
    }

    /**
     * 屏幕背景变暗度
     *
     * @param bg_dim 0-1之间 0无变化，1黑屏
     * @return
     */
    public OftenDialog setDialogDim(float bg_dim) {
        dialog.getWindow().setDimAmount(bg_dim);
        return this;
    }

    /**
     * 设置dialog 的宽
     *
     * @param width
     * @return
     */
    public OftenDialog setDialogWidth(int width) {
        Window window = dialog.getWindow();
        window.getAttributes().width = width;
        return this;
    }

    /**
     * 设置dialog 的高
     *
     * @param height
     * @return
     */
    public OftenDialog setDialogHeight(int height) {
        Window window = dialog.getWindow();
        window.getAttributes().height = height;
        return this;
    }

    /**
     * 设置 背景
     *
     * @param res
     * @return
     */
    public OftenDialog setDialogBg(int res) {
        ll_dialog_bg.setBackgroundResource(res);
        return this;
    }

    /**
     * 分割线的颜色
     *
     * @param color
     * @return
     */
    public OftenDialog setLineColor(int color) {
        view_line.setBackgroundColor(color);
        view_line2.setBackgroundColor(color);
        return this;
    }


    /**
     * 显示的位置
     *
     * @param gravity
     * @return
     */
    public OftenDialog setGravity(int gravity) {
        Window window = dialog.getWindow();
        window.setGravity(gravity);
        return this;
    }


    /**
     * 是否自动关闭
     *
     * @param autoClose
     * @return
     */
    public OftenDialog setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
        return this;
    }

    /**
     * 标题字号
     *
     * @param size
     * @return
     */
    public OftenDialog setTitleSize(int size) {
        tv_title.setTextSize(size);
        return this;
    }

    /**
     * 内容字号
     *
     * @param size
     * @return
     */
    public OftenDialog setContentSize(int size) {
        tv_content.setTextSize(size);
        return this;
    }

    /**
     * 标题颜色
     *
     * @param color
     * @return
     */
    public OftenDialog setTitleColor(int color) {
        tv_title.setTextColor(color);
        return this;
    }

    /**
     * 内容颜色
     *
     * @param color
     * @return
     */
    public OftenDialog setContentColor(int color) {
        tv_content.setTextColor(color);
        return this;
    }

    /**
     * 取消按钮字体颜色
     *
     * @param color
     * @return
     */
    public OftenDialog setCancleTextColor(int color) {
        tv_cance.setTextColor(color);
        return this;
    }

    /**
     * 确定按钮字体颜色
     *
     * @param color
     * @return
     */
    public OftenDialog setSureTextColor(int color) {
        tv_suer.setTextColor(color);
        return this;
    }

    /**
     * 取消按钮背景颜色
     *
     * @param color
     * @return
     */

    public OftenDialog setCancleBgColor(int color) {
        tv_cance.setBackgroundColor(color);
        return this;
    }

    /**
     * 确定按钮背景颜色
     *
     * @param color
     * @return
     */
    public OftenDialog setSureBgColor(int color) {
        tv_suer.setBackgroundColor(color);
        return this;
    }


    /**
     * 其他区域是否关闭dialog
     *
     * @param outTouchside
     * @return
     */
    public OftenDialog setOutTouchside(boolean outTouchside) {
        dialog.setCanceledOnTouchOutside(outTouchside);
        return this;
    }


    private OnClickButtonListener listener;

    public OftenDialog setOnClickButtonListener(OnClickButtonListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 确定 和取消按钮的监听事件
     */
    public interface OnClickButtonListener {

        void oftenSure();

        void oftenCancle();

    }

    public void showDialog() {
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }


    public boolean isShowing() {
        if (null != dialog && dialog.isShowing()) {
            return true;
        }
        return false;
    }

    public void dismiss() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}