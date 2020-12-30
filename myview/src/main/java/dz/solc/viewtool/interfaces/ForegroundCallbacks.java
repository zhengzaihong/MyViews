package dz.solc.viewtool.interfaces;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/12/16
 * create_time: 14:25
 * describe: 监听app 前后台状态接口
 **/
public interface ForegroundCallbacks {

    //前台
     void onForeground();

    //后台
     void onBackground();

}
