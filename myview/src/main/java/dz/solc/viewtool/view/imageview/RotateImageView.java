package dz.solc.viewtool.view.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import dz.solc.viewtool.view.mayview.GifImageView;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2017/12/6
 * create_time: 10:28
 * describe 让图片转起来的view
 **/
public class RotateImageView extends GifImageView {

    private RotateAnimation rotateAnimation;

    public RotateImageView(Context paramContext) {
        super(paramContext);
    }

    public RotateImageView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
//        rotate();
    }

    /**
     * 开启转动
     */
    public void rotate() {
        this.rotateAnimation = new RotateAnimation(0.0F, 360000.0F, 1, 0.5F, 1, 0.5F);
        this.rotateAnimation.setDuration(1000000L);
        this.rotateAnimation.setInterpolator(new LinearInterpolator());
        this.rotateAnimation.setFillAfter(true);
        startAnimation(this.rotateAnimation);
    }

    /**
     * 停止转动
     */
    public void stopRotate() {
        clearAnimation();
    }
}