package dz.solc.myviews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dz.solc.viewtool.dialog.OftenDialog
import dz.solc.viewtool.dialog.PhotoDialog
import dz.solc.viewtool.view.TimerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), TimerView.TimerViewListener {
    override fun startfresh(currentime: Int) {
        tv_timego.text = "" + currentime + "秒"
    }

    override fun endfresh() {
        tv_timego.text = "点击重试"
    }

    private lateinit var instance: TimerView
    private lateinit var oftendialog: OftenDialog
    private lateinit var photoDialog: PhotoDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = ArrayList<CharSequence>()
        list.add("成都又掀起一股外卖热潮")
        list.add("app时代迎来瓶颈")
        list.add("黄金蛋炒饭正式起了")
        list.add("说出你心中那道菜")
        marqueeview.setTextColor(android.R.color.holo_red_dark)
        marqueeview.startWithList(list)

        countdowntextview.init("%s", 18000)
        countdowntextview.start(0)

        riv_round.setRectAdius(20.0f)
        instance = TimerView.create()

        instance.setSpeed(1000)
        tv_timego.setOnClickListener {
            if (instance.iswait) {
                Toast.makeText(MainActivity@ this, "请" + instance.gettime() + "秒后再试", Toast.LENGTH_SHORT).show();
            } else {
                instance.start(this, 40)
            }

        }
        tv_timestop.setOnClickListener {
            instance.closeTimer()
        }


        oftendialog = OftenDialog(this)
                .initData("温馨提示", "你是不是想我啦？")
                .setOutTouchside(false)
                .setAutoClose(true)


        photoDialog = PhotoDialog(this)
                .initTitle("图像选择")
                .setOutTouchside(false)
                .setAutoClose(true)



        tv_often_dialog.setOnClickListener({
            oftendialog.showDialog()
        })


        tv_photo_dialog.setOnClickListener({
            photoDialog.showDialog()

        })
    }
}
