package dz.solc.viewtool.view.navigation.listener

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dz.solc.viewtool.view.navigation.BottomTabView
import dz.solc.viewtool.view.navigation.BottomViewConfig
import dz.solc.viewtool.view.navigation.TabItemView
import dz.solc.viewtool.view.viewpager.NoScrollViewPager

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/12/29
 * create_time: 16:19
 * describe: 简化导航 的接口
 */
interface NavListener {
    /**
     * 每页内容集合
     *
     * @return 所以菜单页面
     */
    fun fragments(): List<Fragment>

    /**
     * @return 底部菜单集合
     */
    fun tabViews(): List<TabItemView>

    /**
     * 这里为了满足部分需求支撑 底部菜单可滑动切换而提供
     *
     * @return ViewPager
     */
    fun contentViewPage(): NoScrollViewPager

    /**
     * @return 返回单个BottomTabView 即每个菜单View
     */
    fun navTabView(): BottomTabView?

    /**
     * @return 可额外在底部中间生成一个菜单
     */
    fun centerView(): View?{return null}
    fun bottomViewConfig(): BottomViewConfig
    fun fragmentManager(): FragmentManager
}