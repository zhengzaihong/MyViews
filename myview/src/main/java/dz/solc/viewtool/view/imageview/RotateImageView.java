package dz.solc.viewtool.view.imageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2017/12/6
 * creat_time: 10:28
 * describe 让图片转起来的view
 **/
@SuppressLint("AppCompatCustomView")
public class RotateImageView extends ImageView {
    private RotateAnimation rotateAnimation;

    public RotateImageView(Context paramContext) {
        super(paramContext);
    }

    public RotateImageView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        rotate();
    }

    public void rotate() {
        this.rotateAnimation = new RotateAnimation(0.0F, 360000.0F, 1, 0.5F, 1, 0.5F);
        this.rotateAnimation.setDuration(1000000L);
        this.rotateAnimation.setInterpolator(new LinearInterpolator());
        this.rotateAnimation.setFillAfter(true);
        startAnimation(this.rotateAnimation);
    }

    public void stopRotate() {
        clearAnimation();
    }
}