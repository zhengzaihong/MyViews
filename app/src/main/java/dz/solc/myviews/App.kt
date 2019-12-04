package dz.solc.myviews

import android.app.Application
import android.graphics.Color
import com.dz.utlis.JavaUtils.isdebug
import com.dz.utlis.ScreenUtils.*
import com.dz.utlis.ToastTool
import com.dz.utlis.UiCompat
import com.dz.utlis.view.ToastConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
        isdebug = true


        // Toast 配置
        val config = ToastConfig()
                .setInterval(2000)
                .setRadiusBg(dip2px(this, 30f).toInt())
                .setToastTextColor(Color.WHITE)
                .setToastViewGroupBgColor(UiCompat.getColor(resources,R.color.light_blue_200))
                .setToastTextSize(16)
                .setBgPadding(dip2px(this, 15f).toInt())
                .setShortToast(false)
                .setStrokeWidth(0)
                .setRadiusType(ToastConfig.RadiusType.ALL_RADIUS)
                .setStrokeColor(Color.TRANSPARENT)

        //初始化 Toast工具
        ToastTool.get().initConfig(this, config)

    }
    companion object {

        var application: App? = null

        fun getinstance(): App? {
            return application
        }
    }

}
