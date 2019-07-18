package dz.solc.viewtool.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


import dz.solc.viewtool.R;
import dz.solc.viewtool.view.imageview.RotateImageView;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2019/3/29 0029
 * creat_time: 9:07
 * describe: 简易加载dialog
 **/

public class LoadingDialog {

    private Dialog dialog;
    private View view;

    private RotateImageView rivLoadingBg;

    //提示内容
    private TextView tvTips;

    //设置显示的最长时间 单位秒
    private int showMaxTime = 10;

    /**
     * 默认的样式
     *
     * @param context
     */
    public LoadingDialog(final Context context) {
        dialog = new Dialog(context, R.style.loading_style);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        rivLoadingBg = view.findViewById(R.id.rivLoadingBg);
        tvTips = view.findViewById(R.id.tvTips);
        dialog.setContentView(view);
        DisplayMetrics outMetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        window.getAttributes().width = (width / 2 + (width / 2) / 2);
    }

    /**
     * 默认不满足样式  使用次构造函数
     *
     * @param context
     * @param layoutView
     */
    public LoadingDialog(final Context context, View layoutView) {
        this.view = layoutView;
        dialog = new Dialog(context, R.style.loading_style);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(view);
        DisplayMetrics outMetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        window.getAttributes().width = (width / 2 + (width / 2) / 2);
    }

    /**
     * 获取外部传入的View
     *
     * @return
     */
    public View getView() {
        return view;
    }


    /**
     * 默认样式的图片
     *
     * @param resid 图片id
     * @return
     */
    public LoadingDialog setLoadingIcon(int resid) {
        if (null != rivLoadingBg) {
            rivLoadingBg.setBackgroundResource(resid);
        }
        return this;
    }

    public LoadingDialog setLoadingTips(String text) {
        if (tvTips.getVisibility() == View.GONE) {
            tvTips.setVisibility(View.VISIBLE);
        }

        tvTips.setText(text);
        return this;
    }

    public LoadingDialog setLoadingTipsColor(int color) {
        tvTips.setTextColor(color);
        return this;
    }

    /**
     * 设置dialog 样式
     *
     * @param style
     * @return
     */
    public LoadingDialog setDialogAnimation(int style) {
        dialog.getWindow().setWindowAnimations(style);
        return this;
    }

    /**
     * 屏幕背景变暗度
     *
     * @param bg_dim 0-1之间 0无变化，1黑屏
     * @return
     */
    public LoadingDialog setDialogDim(float bg_dim) {
        dialog.getWindow().setDimAmount(bg_dim);
        return this;
    }

    public LoadingDialog setDialogWidth(int width) {
        Window window = dialog.getWindow();
        window.getAttributes().width = width;
        return this;
    }

    public LoadingDialog setDialogHeight(int height) {
        Window window = dialog.getWindow();
        window.getAttributes().height = height;
        return this;
    }

    public LoadingDialog setOutTouchside(boolean outTouchside) {
        dialog.setCanceledOnTouchOutside(outTouchside);
        return this;
    }


    public LoadingDialog setShowMaxTime(int maxTime) {
        this.showMaxTime = maxTime;
        return this;
    }


    public void showDialog() {
        if (null != dialog && !dialog.isShowing()) {
            rivLoadingBg.rotate();
            dialog.show();
            handler.sendEmptyMessageDelayed(1, 1000);
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
            currentTime = 0;
        }
    }


    private int currentTime = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (currentTime < showMaxTime) {
                currentTime++;
                handler.sendEmptyMessageDelayed(1, 1000);
            }else {
                if(isShowing()){
                    dismiss();
                }
            }
        }
    };
}
