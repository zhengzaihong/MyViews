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
    public View getView(){
        return view;
    }


    public OftenDialog cancleGone() {
        tv_cance.setVisibility(View.GONE);
        view_line2.setVisibility(View.GONE);
        return this;
    }

    public OftenDialog sureGone() {
        tv_suer.setVisibility(View.GONE);
        view_line2.setVisibility(View.GONE);
        return this;
    }


    public OftenDialog initData(String title, String content) {
        tv_title.setText(title);
        tv_content.setText(content);
        return this;
    }

    public OftenDialog initData(String content) {
        tv_content.setText(content);
        return this;
    }

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

    public OftenDialog setDialogWidth(int width) {
        Window window = dialog.getWindow();
        window.getAttributes().width = width;
        return this;
    }

    public OftenDialog setDialogHeight(int height) {
        Window window = dialog.getWindow();
        window.getAttributes().height = height;
        return this;
    }

    public OftenDialog setDialogBg(int res) {
        ll_dialog_bg.setBackgroundResource(res);
        return this;
    }

    public OftenDialog setLineColor(int color) {
        view_line.setBackgroundColor(color);
        view_line2.setBackgroundColor(color);
        return this;
    }


    public OftenDialog setGravity(int gravity) {
        Window window = dialog.getWindow();
        window.setGravity(gravity);
        return this;
    }


    public OftenDialog showTitle(boolean visible) {
        tv_title.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }


    public OftenDialog setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
        return this;
    }

    public OftenDialog setTitleSize(int size) {
        tv_title.setTextSize(size);
        return this;
    }

    public OftenDialog setContentSize(int size) {
        tv_content.setTextSize(size);
        return this;
    }

    public OftenDialog setTitleColor(int color) {
        tv_title.setTextColor(color);
        return this;
    }

    public OftenDialog setContentColor(int color) {
        tv_content.setTextColor(color);
        return this;
    }

    public OftenDialog setCancleTextColor(int color) {
        tv_cance.setTextColor(color);
        return this;
    }

    public OftenDialog setSureTextColor(int color) {
        tv_suer.setTextColor(color);
        return this;
    }
    public OftenDialog setCancleBgColor(int color) {
        tv_cance.setBackgroundColor(color);
        return this;
    }

    public OftenDialog setSureBgColor(int color) {
        tv_suer.setBackgroundColor(color);
        return this;
    }


    public OftenDialog setOutTouchside(boolean outTouchside) {
        dialog.setCanceledOnTouchOutside(outTouchside);
        return this;
    }


    private OnClickButtonListener listener;

    public OftenDialog setOnClickButtonListener(OnClickButtonListener listener) {
        this.listener = listener;
        return this;
    }

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