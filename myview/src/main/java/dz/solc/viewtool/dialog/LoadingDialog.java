package dz.solc.viewtool.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import dz.solc.viewtool.R;
import dz.solc.viewtool.view.imageview.RotateImageView;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2019/3/29 0029
 * create_time: 9:07
 * describe: 简易加载dialog
 **/
@SuppressWarnings("all")
public class LoadingDialog {

    private Dialog dialog;
    private View view;

    /**
     * 支持转动和 gif 的ImageView
     */
    private RotateImageView rotateImageView;

    /**
     * 提示内容
     */
    private TextView tvTips;

    /**
     * 转动图片
     */
    private boolean rotateIcon = true;

    /**
     * 设置显示的时间 单位秒
     */
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
        rotateImageView = view.findViewById(R.id.rotateImageView);
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
     * 设置 加载icon 的宽高
     *
     * @param width  单位px,图片的宽
     * @param height 单位px,图片的高
     * @return
     */
    public LoadingDialog setLoadingIconWidthHeight(int width, int height) {

        ViewGroup.LayoutParams layoutParams = rotateImageView.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        rotateImageView.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 样式的图片
     *
     * @param resid 图片id
     * @return
     */
    public LoadingDialog setLoadingIcon(int resid) {
        rotateImageView.setBackgroundResource(resid);
        return this;
    }

    /**
     * 样式的图片
     *
     * @param resid 图片id
     * @param isGif 是否是gif 图
     * @return
     */
    public LoadingDialog setLoadingIcon(int resid, boolean isGif) {
        rotateImageView.setBackground(null);
        if (isGif) {
            rotateImageView.setGifResource(resid);
        } else {
            rotateImageView.setBackgroundResource(resid);
        }
        return this;
    }

    /**
     * 是否转动图片配置
     *
     * @param rotate false 可传入一张静态完整效果图
     * @return
     */
    public LoadingDialog setRotateIcon(boolean rotate) {
        rotateIcon = rotate;
        return this;
    }


    /**
     * 设置加载提示框的文本提示
     *
     * @param text
     * @return
     */
    public LoadingDialog setLoadingTips(String text) {
        if (tvTips.getVisibility() == View.GONE) {
            tvTips.setVisibility(View.VISIBLE);
        }
        tvTips.setText(text);
        return this;
    }

    /**
     * 设置加载提示框的文本颜色
     *
     * @param color
     * @return
     */
    public LoadingDialog setLoadingTipsColor(int color) {
        tvTips.setTextColor(color);
        return this;
    }

    /**
     * 设置dialog 样式动画
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

    /**
     * 设置dialog 的宽
     *
     * @param width
     * @return
     */
    public LoadingDialog setDialogWidth(int width) {
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
    public LoadingDialog setDialogHeight(int height) {
        Window window = dialog.getWindow();
        window.getAttributes().height = height;
        return this;
    }

    /**
     * 触碰非dialog区域是否取消显示
     *
     * @param outTouchside
     * @return
     */
    public LoadingDialog setOutTouchside(boolean outTouchside) {
        dialog.setCanceledOnTouchOutside(outTouchside);
        return this;
    }


    /**
     * 设置提示框的最大显示时间
     *
     * @param maxTime
     * @return
     */
    public LoadingDialog setShowMaxTime(int maxTime) {
        this.showMaxTime = maxTime;
        return this;
    }

    public LoadingDialog showDialog() {
        if (null != dialog && !dialog.isShowing()) {
            if (rotateIcon) {
                rotateImageView.rotate();
            } else {
                rotateImageView.stopRotate();
            }
            dialog.show();
            handler.sendEmptyMessageDelayed(1, 1000);
        }
        return this;
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
            } else {
                if (isShowing()) {
                    dismiss();
                }
            }
        }
    };
}
