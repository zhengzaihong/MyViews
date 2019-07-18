package dz.solc.myviews.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dz.utlis.JavaUtils
import com.dz.utlis.JavaUtils.*
import dz.solc.myviews.R
import dz.solc.myviews.uitls.ToastTool
import dz.solc.viewtool.dialog.LoadingDialog
import dz.solc.viewtool.dialog.OftenDialog
import dz.solc.viewtool.dialog.PhotoDialog
import kotlinx.android.synthetic.main.activity_layout_calendar_view.*
import kotlinx.android.synthetic.main.activity_layout_tips_dialog.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * 该控件还不完善
 */
class CalendarViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_layout_calendar_view)

        //超简洁自定义日历控件
        calendarview.setWorkDayColor(this.resources.getColor(R.color.orange_100))
                .setRestDayColor(this.resources.getColor(R.color.red_400))
                .setCurrentDayColor(this.resources.getColor(R.color.light_blue_300))
                .setHideOtherMonthDay(false)
                .setMultiChoose(2)
                .setInmonthNotToDayColor(this.resources.getColor(R.color.black_alpha_224))
                .setBackGround(R.drawable.bg_corners_dialog_main)
                .setMargin(10, 10, 10, 10)
                .setPading(30, 30, 30, 30)
                .setOnItmeClickListener { parent, view, position, id ->

                    ToastTool.show("当前时间${stampstoTime((parent.adapter.getItem(position) as Date).time.toString(),
                            "yyyy-MM-dd")}  "
                            + (parent.adapter.getItem(position) as Date).date)
                    calendarview.changeDate(parent.adapter.getItem(position) as Date)

                    outRedPrint("----------->" + calendarview.date.toString())

                }


        val nowdate = Date()

        calendarview1.setWorkDayColor(this.resources.getColor(R.color.orange_100))
                .setRestDayColor(this.resources.getColor(R.color.red_400))
                .setCurrentDayColor(this.resources.getColor(R.color.light_blue_300))
                .setInmonthNotToDayColor(this.resources.getColor(R.color.black_alpha_224))
                .setBackGround(R.drawable.bg_corners_dialog_main)
                .setMargin(10, 10, 10, 10)
                .setPading(30, 30, 30, 30)
                .setOnItmeClickListener { parent, view, position, id ->

                    ToastTool.show("当前时间${stampstoTime((parent.adapter.getItem(position) as Date).time.toString(),
                            "yyyy-MM-dd")}")

                    calendarview1.notyDataChange()
                }
//                外部自定义样式
                .setAdapterItmeLayout(R.layout.calendar_cell_itme2)
                .setAdapterDrawListener { holder, position, entity ->

                    var date = (entity as Date)
                    //判断时间是不是当天
                    if (nowdate.date == date.getDate() && nowdate.year == date.getYear()
                            && nowdate.month == date.getMonth() && nowdate.day == date.getDay()) {

                        holder.setTextColor(R.id.tv_date1, this.resources.getColor(R.color.light_blue_300))
                    } else {
                        holder.setTextColor(R.id.tv_date1, this.resources.getColor(R.color.black_alpha_224))
                    }
                    holder.setText(R.id.tv_date1, "" + date.date)

                    if (nowdate.month != date.month) {
                        holder.convertView.visibility = View.INVISIBLE
                    }

                }
    }


    fun stampstoTime(time: String, patten: String): String {
        var t = ""
        try {
            t = SimpleDateFormat(patten).format(Date(java.lang.Long.parseLong(time)))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return t
    }
}
