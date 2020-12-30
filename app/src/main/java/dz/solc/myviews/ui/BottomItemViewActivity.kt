package dz.solc.myviews.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.dz.utlis.ToastTool
import dz.solc.myviews.R
import dz.solc.myviews.ui.fragment.TestFragment1
import dz.solc.myviews.ui.fragment.TestFragment2
import dz.solc.viewtool.view.navigation.BottomTabView
import dz.solc.viewtool.view.mayview.msgview.MsgTextView
import dz.solc.viewtool.view.navigation.TabItemView
import dz.solc.viewtool.view.navigation.listener.ObserverOnTabItemSelectListener
import dz.solc.viewtool.view.mayview.msgview.listener.UpdateTabItemMsgListener
import dz.solc.viewtool.view.navigation.BottomViewConfig
import dz.solc.viewtool.view.navigation.listener.NavListener
import dz.solc.viewtool.view.navigation.NavigationHelper
import dz.solc.viewtool.view.viewpager.NoScrollViewPager
import kotlinx.android.synthetic.main.fragment_tabhome.*

class BottomItemViewActivity : AppCompatActivity(), NavListener, ObserverOnTabItemSelectListener {

    private var titles = arrayListOf<String>("首页", "我的")
    private var helper :NavigationHelper = NavigationHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_tabhome)
        helper.initNav(this)
    }



    override fun fragments(): MutableList<Fragment> {
        var list = mutableListOf<Fragment>()
        list.add(TestFragment1())
        list.add(TestFragment2())
        return list
    }

    override fun contentViewPage(): NoScrollViewPager = viewPager
    override fun navTabView(): BottomTabView {
        return bottomTabView.addObserverOnTabItemSelectListener(this)
    }

//    override fun centerView(): View? {
//        var centerView: ImageView? = ImageView(this)
//        centerView?.setImageResource(R.mipmap.ic_launcher_round)
//        var layoutparm: LinearLayout.LayoutParams = LinearLayout.LayoutParams(200, 400)
//        layoutparm.leftMargin = 60
//        layoutparm.rightMargin = 60
//        layoutparm.bottomMargin = 0
//        centerView?.layoutParams = layoutparm
//        centerView?.setOnClickListener {
//            Toast.makeText(this, "centerView 点击了", Toast.LENGTH_SHORT).show();
//        }
//        return centerView
//    }

    override fun tabViews(): MutableList<TabItemView> {
        val tabItemViews = ArrayList<TabItemView>()
        tabItemViews.add(TabItemView(
                this, titles[0], R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round
        ))
        tabItemViews.add(TabItemView(
                this, titles[1], R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher
        ))

        return tabItemViews
    }


    override fun bottomViewConfig(): BottomViewConfig {
        var config = BottomViewConfig()
        config.isSupportScroll = true
        config.isEnableMsgShow = true
        config.msgTextColor = Color.RED
        config.limitSize=2
        return config
    }

    override fun fragmentManager(): FragmentManager  = supportFragmentManager

    fun updateMsg() {
        // equalsTag 满足条件 后itemView 不是null 后才回调 onUpdateMsg进行更新操作
        bottomTabView.getItemView("44", titles[0], object : UpdateTabItemMsgListener {
            override fun equalsTag(src: Any?, ta: Any?): Boolean {
                return src?.toString() == ta.toString()
            }

            override fun <String> onUpdateMsg(view: MsgTextView?, msg: String) {
                if (null != msg) {
                    view?.show()
                    view?.text = msg.toString()
                } else {
                    view?.hide()
                }
            }
        })
    }

    override fun onSelectedTab(tabItemView: TabItemView?, position: Int) {
        ToastTool.get().show("----->$position")
    }
}