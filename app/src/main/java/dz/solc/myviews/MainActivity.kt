package dz.solc.myviews

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import dz.solc.viewtool.calendar.CalendarAdapter
import dz.solc.viewtool.calendar.CalendarView
import dz.solc.viewtool.dialog.OftenDialog
import dz.solc.viewtool.dialog.PhotoDialog
import dz.solc.viewtool.view.TimerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

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
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = ArrayList<CharSequence>()
        list.add("app时代迎来瓶颈")
        list.add("app时代迎来瓶颈")
        list.add("app时代迎来瓶颈")
        list.add("app时代迎来瓶颈")
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

        //超简洁自定义日历控件
        calendarview.setWorkDayColor(this.resources.getColor(R.color.orange_100))
                .setRestDayColor(this.resources.getColor(R.color.red_400))
                .setCurrentDayColor(this.resources.getColor(R.color.light_blue_300))
                .setHideOtherMonthDay(false)
                .setInmonthNotToDayColor(this.resources.getColor(R.color.black_alpha_224))
                .setBackGround(R.drawable.bg_corners_dialog_main)
                .setMargin(10, 10, 10, 10)
                .setPading(30, 30, 30, 30)
                .setOnItmeClickListener { parent, view, position, id ->
                    Toast.makeText(this, "" + (parent.adapter.getItem(position) as Date).date, 1).show()
                }
//                外部自定义样式
//                .setAdapterItmeLayout(R.layout.calendar_cell_itme2)
//                .setAdapterDrawListener { holder, position, entity ->
//                    holder.setText(R.id.tv_date1, "22")
//                }
    }
}
