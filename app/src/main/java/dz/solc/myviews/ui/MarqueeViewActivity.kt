package dz.solc.myviews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dz.utlis.ScreenUtils
import dz.solc.myviews.R
import dz.solc.myviews.uitls.ToastTool.show
import dz.solc.myviews.bean.MarqueeBean
import dz.solc.viewtool.view.marqueeview.AutoScrollView
import kotlinx.android.synthetic.main.activity_layout_markview.*


class MarqueeViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_layout_markview)


        val list = mutableListOf<MarqueeBean>()
        list.add(MarqueeBean("这是测试数据1"))
        list.add(MarqueeBean("这是测试数据2"))
        list.add(MarqueeBean("这是测试数据3"))
        list.add(MarqueeBean("这是测试数据4"))


        marqueeview.setTextColor(android.R.color.holo_red_dark)
        marqueeview.startWithList(list)
        marqueeview.setOnItemViewFillListener { textView,o, _ ->
            textView.text = (o as MarqueeBean).content
        }

        marqueeview.setOnItemClickListener { _, textView ->
            show("${textView.text}")
        }


        marqueeview1.setTextColor(R.color.light_blue_100)
        marqueeview1.startWithList(list)
        marqueeview1.setOnItemViewFillListener { textView, `object`, position ->
            textView.text = (`object` as MarqueeBean).content
        }
        marqueeview2.setTextColor(R.color.yellow)
        marqueeview2.startWithList(list)
        marqueeview2.setOnItemViewFillListener { textView, `object`, position ->
            textView.text = (`object` as MarqueeBean).content
        }
        marqueeview3.setTextColor(R.color.darkcyan)
        marqueeview3.startWithList(list)
        marqueeview3.setOnItemViewFillListener { textView, `object`, position ->
            textView.text = (`object` as MarqueeBean).content
        }

        //电影谢幕式滚动效果
        marqueeview5.startScrolled()
        marqueeview5.setViewHight(ScreenUtils.dip2px(this, 120f).toInt())
        marqueeview5.setMarqueeType(AutoScrollView.MarqueeType.LIMIT)

    }
}
