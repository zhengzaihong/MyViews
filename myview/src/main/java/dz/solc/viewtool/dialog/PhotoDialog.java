package dz.solc.viewtool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import dz.solc.viewtool.R;


/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2018/4/20
 * creat_time: 10:24
 * describe 修改头像的dialog
 **/

@SuppressWarnings("all")
public class PhotoDialog {

    //拍照按钮
    private TextView tv_take_photo;
    //相册按钮
    private TextView tv_form_abulm;
    //取消
    private TextView tv_cancle;
    //提示标题
    private TextView tv_title;

    //分割线
    private View view_line_photo;
    private View view_line2_photo;

    private Dialog dialog;
    private View view;

    //窗口跟布局
    private View ll_photo_root_view;
    //主体布局
    private View ll_photo_main_view;


    private boolean autoClose = true;

    private boolean outTouchside = true;


    public static final int TAKEPHOTO = 1;
    public static final int AUBLUM = 2;
    public static final int CANCLE = 3;

    public PhotoDialog(final Context context) {

        dialog = new Dialog(context, R.style.mOftenDialog);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_photo_bg, null);
        dialog.setContentView(view);
        DisplayMetrics outMetrics = new DisplayMetrics();
        dialog.setCanceledOnTouchOutside(outTouchside);
        window.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        window.getAttributes().width = (width - (width / 8));
        ll_photo_root_view = window.findViewById(R.id.ll_photo_root_view);
        ll_photo_main_view = window.findViewById(R.id.ll_photo_main_view);

        tv_title = window.findViewById(R.id.tv_title);
        tv_take_photo = view.findViewById(R.id.tv_take_photo);
        tv_form_abulm = view.findViewById(R.id.tv_form_abulm);
        tv_cancle = view.findViewById(R.id.tv_cancle);
        view_line_photo = view.findViewById(R.id.view_line_photo);
        view_line2_photo = view.findViewById(R.id.view_line2_photo);
        tv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack(TAKEPHOTO);
                if (autoClose) {
                    dismiss();
                }
            }
        });
        tv_form_abulm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack(AUBLUM);
                if (autoClose) {
                    dismiss();
                }
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack(CANCLE);
                if (autoClose) {
                    dismiss();
                }
            }
        });

    }

    private void callBack(int c) {
        if (null != listener) {
            listener.onItemClickListener(c);
        }
    }


    //提供的样式不满足 外部拿到view 自定义样式
    public View getView(){
        return ll_photo_root_view;
    }

    public PhotoDialog initTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public PhotoDialog setDialogAnimation(int style) {
        dialog.getWindow().setWindowAnimations(style);
        return this;
    }

    //屏幕背景变暗度
    public PhotoDialog setDialogDim(float bg_dim) {
        dialog.getWindow().setDimAmount(bg_dim);
        return this;
    }

    public PhotoDialog setDialogWidth(int width) {
        Window window = dialog.getWindow();
        window.getAttributes().width = width;
        return this;
    }

    public PhotoDialog setDialogHeight(int height) {
        Window window = dialog.getWindow();
        window.getAttributes().height = height;
        return this;
    }

    public PhotoDialog setDialogRootBg(int res) {
        ll_photo_root_view.setBackgroundResource(res);
        return this;
    }

    public PhotoDialog setDialogMainBg(int res) {
        ll_photo_main_view.setBackgroundResource(res);
        return this;
    }
    public PhotoDialog setDialogCancleBg(int res) {
        tv_cancle.setBackgroundResource(res);
        return this;
    }


    public PhotoDialog setLineColor(int color) {
        view_line_photo.setBackgroundColor(color);
        view_line2_photo.setBackgroundColor(color);
        return this;
    }


    public PhotoDialog setGravity(int gravity) {
        Window window = dialog.getWindow();
        window.setGravity(gravity);
        return this;
    }


    public PhotoDialog showTitle(boolean visible) {
        tv_title.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }


    public PhotoDialog setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
        return this;
    }

    public PhotoDialog setTitleSize(int size) {
        tv_title.setTextSize(size);
        return this;
    }


    public PhotoDialog setTitleColor(int color) {
        tv_title.setTextColor(color);
        return this;
    }


    public PhotoDialog setButtonColor(int color) {
        tv_take_photo.setTextColor(color);
        tv_form_abulm.setTextColor(color);
        return this;
    }
    public PhotoDialog setButtonSize(int size) {
        tv_take_photo.setTextSize(size);
        tv_form_abulm.setTextSize(size);
        return this;
    }

    public PhotoDialog setCancleButtonColor(int color) {
        tv_cancle.setTextColor(color);
        return this;
    }
    public PhotoDialog setCancleSize(int size) {
        tv_cancle.setTextSize(size);
        return this;
    }

    public PhotoDialog setOutTouchside(boolean outTouchside) {
        dialog.setCanceledOnTouchOutside(outTouchside);
        return this;
    }


    private OnItemClickListener listener;

    public PhotoDialog setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int choose);
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