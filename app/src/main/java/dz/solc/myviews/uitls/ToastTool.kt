package dz.solc.myviews.uitls

import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

import com.dz.utlis.AndroidUtils
import com.dz.utlis.ToastUtil
import dz.solc.myviews.App
import dz.solc.myviews.R

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2018/4/4 0004
 * creat_time: 17:34
 * describe: TODO
 */


object ToastTool {

    //小于两秒的提示 认为是重复提示 不做显示
    private var toastTime: Long = 0


    fun timeC(): Boolean {

        if (System.currentTimeMillis() - toastTime > 2000) {
            toastTime = System.currentTimeMillis()
            return true
        } else {
            return false
        }
    }

    fun show(msg: String) {
        if (timeC()) {
            val view = AndroidUtils.getView(App.getinstance(), R.layout.toast_view_layout)
            val tvContent = view.findViewById<TextView>(R.id.tv_contnet)
            tvContent.gravity = Gravity.CENTER
            tvContent.text = msg

            val toastUtil = ToastUtil(App.getinstance(), view, Toast.LENGTH_SHORT)
            toastUtil.toast.setGravity(Gravity.CENTER, 0, 0)
            toastUtil.show()
        }

    }
}
