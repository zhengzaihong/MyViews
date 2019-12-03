package dz.solc.myviews.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.dz.utlis.ScreenUtils
import com.dz.utlis.ToastTool
import com.dz.utlis.UiCompat
import dz.solc.myviews.R
import dz.solc.viewtool.view.toolview.HDividerItemDecoration
import dz.solc.viewtool.view.menueview.MultiFunctionView
import dz.solc.viewtool.view.menueview.config.MultiFunctionConfig
import dz.solc.viewtool.view.menueview.config.MultiFunctionConfig.*
import dz.solc.viewtool.view.menueview.helper.MenueAdapter
import dz.solc.viewtool.view.menueview.helper.MenueViewHolder
import kotlinx.android.synthetic.main.activity_layout_menue_view.*

class MenueViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_layout_menue_view)


        sbutton.setOnCheckedListener { isChecked ->
            Log.v("--------", "选择状态$isChecked")
        }
        addBarMenue()
    }


    var clickPosition = 0
    var clickPosition1 = 0
    var clickPosition2 = 0
    var clickPosition3 = 0

    fun addBarMenue() {

        var listMenue = listOf("倾斜X", "倾斜Y", "倾斜Z")

        val viewConfig = MultiFunctionConfig()
        viewConfig.normalBgColor = UiCompat.getColor(resources, R.color.cyan_300)
        viewConfig.pressedBgColor = UiCompat.getColor(resources, R.color.button_unpress)
        viewConfig.strokeColor = UiCompat.getColor(resources, R.color.transparent)

        viewConfig.strokeWidth = 1
        viewConfig.cornerRadius = ScreenUtils.dip2px(this, 15f).toInt()
        viewConfig.setFollowTextColor(false)

        var adapter = object : MenueAdapter<String, MenueViewHolder>(R.layout.item_layout_list_menue) {
            override fun convert(holder: MenueViewHolder?, item: String) {

                var functionView = holder?.getView<MultiFunctionView>(R.id.tvContent)
                var position = holder?.layoutPosition

                functionView?.let {
                    when (position) {
                        0 -> viewConfig.radiusType = RadiusType.LEFT_TOP_BOTTOM_RADIUS
                        mData.size - 1 -> viewConfig.radiusType = RadiusType.RIGHT_TOP_BOOTOM_RADIUS
                        else -> viewConfig.radiusType = RadiusType.NONE_RADIUS
                    }
                }
                functionView?.setConfig(viewConfig)
                functionView?.text = item
                functionView?.isChecked = clickPosition == position
                holder?.itemView?.setOnClickListener {
                    clickPosition = position!!

//                    ToastTool.show("点击了$item")

                    notifyDataSetChanged()
                }
            }
        }

        adapter.setNewData(listMenue)
        rivChangeRPoint.layoutManager = LinearLayoutManager(this@MenueViewActivity, RecyclerView.HORIZONTAL, false)
        rivChangeRPoint.adapter = adapter


        val viewConfig1 = MultiFunctionConfig()
        viewConfig1.strokeColor = UiCompat.getColor(resources, R.color.green)

        viewConfig1.strokeWidth = 1
        viewConfig1.cornerRadius = 0
        viewConfig1.setFollowTextColor(true)
        var adapter1 = object : MenueAdapter<String, MenueViewHolder>(R.layout.item_layout_list_menue) {
            override fun convert(holder: MenueViewHolder?, item: String) {

                var functionView = holder?.getView<MultiFunctionView>(R.id.tvContent)
                var position = holder?.layoutPosition

                functionView?.setConfig(viewConfig1)
                functionView?.text = item
                functionView?.setTextColor(resources.getColor(R.color.white))

                functionView?.isChecked = clickPosition1 == position
                holder?.itemView?.setOnClickListener {
                    clickPosition1 = position!!
//                    ToastTool.show("点击了$item")
                    notifyDataSetChanged()
                }
            }
        }



        adapter1.setNewData(listMenue)
        rivChangeRPoint1.layoutManager = LinearLayoutManager(this@MenueViewActivity, RecyclerView.HORIZONTAL, false)
        rivChangeRPoint1.adapter = adapter1


        var listMenue2 = listOf("天王", "地虎")
        val viewConfig2 = MultiFunctionConfig()
        viewConfig2.normalBgColor = UiCompat.getColor(resources, R.color.yellow_300)
        viewConfig2.pressedBgColor = UiCompat.getColor(resources, R.color.red)
        viewConfig2.strokeColor = UiCompat.getColor(resources, R.color.green)

        viewConfig2.strokeWidth = 1
        viewConfig2.cornerRadius = ScreenUtils.dip2px(this, 15f).toInt()

        viewConfig2.normalTextColor = UiCompat.getColor(resources, R.color.white)
        viewConfig2.pressedTextColor = UiCompat.getColor(resources, R.color.yellow_300)

        //配置选中和未选择的文字颜色
        viewConfig2.setFollowTextColor(false)
        var adapter2 = object : MenueAdapter<String, MenueViewHolder>(R.layout.item_layout_list_menue) {
            override fun convert(holder: MenueViewHolder?, item: String) {

                var functionView = holder?.getView<MultiFunctionView>(R.id.tvContent)
                var position = holder?.layoutPosition


                functionView?.let {
                    when (position) {
                        0 -> viewConfig2.radiusType = RadiusType.LEFT_TOP_BOTTOM_RADIUS
                        1 -> viewConfig2.radiusType = RadiusType.RIGHT_TOP_BOOTOM_RADIUS
                    }
                }

                functionView?.setConfig(viewConfig2)
                functionView?.text = item
                functionView?.isChecked = clickPosition2 == position
                holder?.itemView?.setOnClickListener {
                    clickPosition2 = position!!
//                    ToastTool.show("点击了$item")
                    notifyDataSetChanged()
                }
            }
        }



        adapter2.setNewData(listMenue2)
        rivChangeRPoint2.layoutManager = LinearLayoutManager(this@MenueViewActivity, RecyclerView.HORIZONTAL, false)
        rivChangeRPoint2.adapter = adapter2




        var listMenue3 = listOf("荤菜", "蔬菜", "美女", "帅哥")
        val viewConfig3 = MultiFunctionConfig()
        viewConfig3.normalBgColor = UiCompat.getColor(resources, R.color.orange_100)
        viewConfig3.pressedBgColor = UiCompat.getColor(resources, R.color.button_unpress)
        viewConfig3.strokeColor = UiCompat.getColor(resources, R.color.green)

        viewConfig3.strokeWidth = 1
        viewConfig3.cornerRadius = ScreenUtils.dip2px(this, 15f).toInt()
        viewConfig3.setFollowTextColor(false)

        //配置选中和未选择的文字颜色
        viewConfig3.normalTextColor = UiCompat.getColor(resources, R.color.white)
        viewConfig3.pressedTextColor = UiCompat.getColor(resources, R.color.yellow_300)

        var adapter3 = object : MenueAdapter<String, MenueViewHolder>(R.layout.item_layout_list_menue) {
            override fun convert(holder: MenueViewHolder?, item: String) {

                var functionView = holder?.getView<MultiFunctionView>(R.id.tvContent)
                var position = holder?.layoutPosition


                functionView?.let {
                    when (position) {
                        0 -> viewConfig3.radiusType = RadiusType.LEFT_TOP_BOTTOM_RADIUS
                        mData.size - 1 -> viewConfig3.radiusType = RadiusType.RIGHT_TOP_BOOTOM_RADIUS
                        else -> viewConfig3.radiusType = RadiusType.NONE_RADIUS
                    }
                }

                functionView?.setConfig(viewConfig3)
                functionView?.text = item


                functionView?.isChecked = clickPosition3 == position
                holder?.itemView?.setOnClickListener {
                    clickPosition3 = position!!
//                    ToastTool.show("点击了$item")
                    notifyDataSetChanged()
                }
            }
        }



        adapter3.setNewData(listMenue3)
        rivChangeRPoint3.layoutManager = LinearLayoutManager(this@MenueViewActivity, RecyclerView.HORIZONTAL, false)
        var dividerItemDecoration = HDividerItemDecoration(LinearLayoutManager.HORIZONTAL)
        dividerItemDecoration.setColor(resources.getColor(R.color.white))
        rivChangeRPoint3.addItemDecoration(dividerItemDecoration)
        rivChangeRPoint3.adapter = adapter3


        var listMenue4 = listOf("主页", "消息", "社区")
        val viewConfig4 = MultiFunctionConfig()
        viewConfig4.strokeWidth = 1
        viewConfig4.cornerRadius = ScreenUtils.dip2px(this, 15f).toInt()
        viewConfig4.setFollowTextColor(false)

        var adapter4 = object : MenueAdapter<String, MenueViewHolder>(R.layout.item_layout_list_menue2) {
            override fun convert(holder: MenueViewHolder?, item: String) {

                var functionView = holder?.getView<MultiFunctionView>(R.id.tvContent)
                var position = holder?.layoutPosition


                functionView?.let {
                    when (position) {
                        0 -> {
                            viewConfig4.normalBgColor = UiCompat.getColor(resources, R.color.button_unpress)
                            viewConfig4.pressedBgColor = UiCompat.getColor(resources, R.color.button_unpress)
                            viewConfig4.strokeColor = UiCompat.getColor(resources, R.color.button_unpress)


                        }
                        1 -> {
                            viewConfig4.normalBgColor = UiCompat.getColor(resources, R.color.red)
                            viewConfig4.pressedBgColor = UiCompat.getColor(resources, R.color.red)
                            viewConfig4.strokeColor = UiCompat.getColor(resources, R.color.red)
                        }


                        else -> {
                            viewConfig4.normalBgColor = UiCompat.getColor(resources, R.color.light_blue_400)
                            viewConfig4.pressedBgColor = UiCompat.getColor(resources, R.color.light_blue_400)
                            viewConfig4.strokeColor = UiCompat.getColor(resources, R.color.light_blue_400)

                        }
                    }
                }
                viewConfig4.radiusType = RadiusType.LEFT_TOP_BOTTOM_RADIUS
                functionView?.setConfig(viewConfig4)
                functionView?.text = item
                functionView?.setTextColor(resources.getColor(R.color.white))

                holder?.itemView?.setOnClickListener {
                    ToastTool.get().show("点击了$item")
                }
            }
        }



        adapter4.setNewData(listMenue4)
        rivChangeRPoint4.layoutManager = LinearLayoutManager(this@MenueViewActivity)
        rivChangeRPoint4.adapter = adapter4


    }


}
