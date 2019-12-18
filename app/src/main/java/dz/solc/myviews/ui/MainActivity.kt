package dz.solc.myviews.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.dz.utlis.AndroidUtils
import dz.solc.myviews.R
import dz.solc.viewtool.adapter.CommonAdapter
import dz.solc.viewtool.adapter.UtilAdapter
import dz.solc.viewtool.view.tableview.TableView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var menues = arrayListOf("时间控件", "跑马灯控件", "菜单控件",
            "常用提示框", "常用图片View和进度View等","表格控件","其他控件")

    var myMenueAdapter: MyMenueAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        listMenues.setOnItemClickListener { _, _, position, _ ->
            var intent = Intent()
            when (position) {
                0 -> {
                    intent.setClass(this@MainActivity, TimeViewActivity::class.java)
                }
                1 -> {
                    intent.setClass(this@MainActivity, MarqueeViewActivity::class.java)
                }
                2 -> {
                    intent.setClass(this@MainActivity, MenueViewActivity::class.java)
                }
                3 -> {
                    intent.setClass(this@MainActivity, TipDialogActivity::class.java)
                }
                4 -> {
                    intent.setClass(this@MainActivity, ImageViewActivity::class.java)
                }
                5 -> {
                    intent.setClass(this@MainActivity, TableViewActivity::class.java)
                }
                6 -> {
                    intent.setClass(this@MainActivity, MayViewActivity::class.java)
                }
                7 -> {

                }
            }

            startActivity(intent)
        }

        myMenueAdapter = MyMenueAdapter()
        UtilAdapter.setSwingLeftInAnimationAdapter(listMenues,myMenueAdapter)
        myMenueAdapter?.replaceData(menues)


    }

    inner class MyMenueAdapter : CommonAdapter<String>(this@MainActivity, R.layout.item_layout_main_menue) {

        override fun convert(holder: ViewHolder?, position: Int, entity: String?) {
            holder?.setText(R.id.button, entity + "")
        }
    }

}
