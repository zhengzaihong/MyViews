package dz.solc.viewtool.view.navigation.listener;

import dz.solc.viewtool.view.navigation.TabItemView;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/10/21
 * create_time: 10:55
 * describe: 提供一个全局的底部导航栏监听器
 **/
public interface ObserverOnTabItemSelectListener {

    void onSelectedTab(TabItemView tabItemView,int position);

}
