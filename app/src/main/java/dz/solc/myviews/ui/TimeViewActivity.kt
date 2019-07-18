package dz.solc.myviews.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.dz.utlis.JavaUtils
import dz.solc.myviews.R
import dz.solc.myviews.uitls.ToastTool
import dz.solc.viewtool.view.timeview.LinkTask
import dz.solc.viewtool.view.timeview.TimerView
import dz.solc.viewtool.view.timeview.TimerViewUpgrade
import kotlinx.android.synthetic.main.activity_layout_timeview.*

class TimeViewActivity : AppCompatActivity(), TimerView.OnAlarmClockListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_layout_timeview)

        var timerView = TimerView.create()

//      一般用法1
        tv_timego.setOnClickListener {
            if (timerView.isWait) {
                ToastTool.show("请${timerView.time}秒后再试")
            } else {
                timerView.start(object : TimerView.OnAlarmClockListener {
                    override fun startfresh(currentime: Int) {

                        tv_timego.text = "${currentime}s后再试"
                    }

                    override fun endfresh() {
                        tv_timego.text = "获取验证码"
                    }
                }, 60) //时间任意传入，一般验证码60秒获取一次
            }
        }

        timestop.setOnClickListener {
            timerView.closeTimer()
        }


//      一般用法2
        var timerView1 = TimerView.create()
        var count = 0
        tv_timego1.setOnClickListener {

            if (!timerView1.isWait) {

                timerView1.start(object : TimerView.OnAlarmClockListener {

                    override fun startfresh(currentime: Int) {
                        if (currentime % 2 == 0) {
                            count++
                            tv_timego1.text = "刷新次数$count"
                        }
                    }

                    override fun endfresh() {
                        tv_timego1.text = "每隔2秒刷新"
                    }
                }, Int.MAX_VALUE)
            }


        }

        timestop1.setOnClickListener {
            timerView1.closeTimer()
            count = 0
        }


//      用法3
        var timerView2 = TimerView.create()
        timerView2.speed = 500
        tv_timego2.setOnClickListener {

            if (!timerView2.isWait) {
                timerView2.start(this, 10)
            }
        }


//      TimerView线程池版
        var timeViewUp = TimerViewUpgrade.create()
//        timeViewUp.period = 100
        tv_timego3.setOnClickListener {
            if (timeViewUp.isWait) {
                ToastTool.show("请${timeViewUp.time}秒后再试")
            } else {
                timeViewUp.start(object : TimerViewUpgrade.OnAlarmClockListener {
                    override fun startfresh(currentime: Int) {

                        JavaUtils.outRedPrint("当前值：$currentime")

                        tv_timego3.text = "${currentime}s后再试"
                    }

                    override fun endfresh() {
                        tv_timego3.text = "获取验证码"
                    }
                }, 1000, 60)
            }
        }

        timestop3.setOnClickListener {
            timeViewUp.closeTimer()

        }


//      秒杀倒计时控件
        countdowntextview.setTextInfoListener { textView, content ->
            //字体样式交给开发者自由定制
            val s = SpannableString("秒杀倒计时: $content")
            s.setSpan(RelativeSizeSpan(1f), 0, s.length, 0)
            s.setSpan(StyleSpan(Typeface.ITALIC), 0, s.length, 0)
            s.setSpan(ForegroundColorSpan(Color.rgb(51, 181, 229)), 0, s.length, 0)
            textView.text = s
        }

        countdowntextview.init("%s", 18000)
        countdowntextview.start(0)


        var taskLink = LinkTask<String>()

        taskLink.add("sssss")
        taskLink.add("bbbbb")
        taskLink.add("ccccc")
        taskLink.add("ddddd")
        taskLink.add("eeeee")
        taskLink.add("fffff")

        taskStop.setOnClickListener {

            if (taskLink.taskRun) {
                taskStop.text = "开始"
                taskLink.taskRun = false
            } else {
                taskLink.startTask(1000)
                taskStop.text = "停止"
            }

        }

        taskLink.setActionTaskListener(object : LinkTask.ActionTaskListener<String> {
            override fun endTask() {
                taskStop.text = "开始"
            }

            override fun callBackTask(task: String?) {
                tv_task.text = "任务：$task"
            }
        })
    }


    override fun startfresh(currentime: Int) {
        tv_timego2.text = "当前时间$currentime"
    }

    override fun endfresh() {
        tv_timego2.text = "倒计时加速一倍"
    }


}
