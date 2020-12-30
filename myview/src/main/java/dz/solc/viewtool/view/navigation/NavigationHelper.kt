package dz.solc.viewtool.view.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import dz.solc.viewtool.view.navigation.listener.NavListener

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2018/6/22
 * create_time: 11:08
 * describe 从以前的 基类变更到 工具类中实现
 */
open class NavigationHelper {

    private var bottomTabView: BottomTabView? = null
    private var tabItemViews: List<TabItemView>? = null

    /**
     * 承载每页菜单内容的适配器
     */
    private var adapter: FragmentPagerAdapter? = null


    fun initNav(navListener: NavListener) {

        navListener?.apply {

            val config = bottomViewConfig()
            val viewPager = contentViewPage()

            bottomTabView = navTabView()

            //控制viewPager自带的滑动功能
            viewPager.setScroll(config.isSupportScroll)
            adapter = object : FragmentPagerAdapter(fragmentManager()) {
                override fun getItem(position: Int): Fragment {
                    return fragments()[position]
                }

                override fun getCount(): Int {
                    return fragments().size
                }
            }
            viewPager.adapter = adapter
            tabItemViews = navListener.tabViews()
            tabItemViews?.apply {
                for (itemView in this) {
                    itemView.setConfig(config)
                }
            }
            if (navListener.centerView() == null) {
                bottomTabView?.setTabItemViews(tabItemViews)
            } else {
                bottomTabView?.setTabItemViews(tabItemViews, navListener.centerView())
            }
            viewPager.offscreenPageLimit = config.limitSize
            bottomTabView?.setUpWithViewPager(viewPager)
        }
    }

}