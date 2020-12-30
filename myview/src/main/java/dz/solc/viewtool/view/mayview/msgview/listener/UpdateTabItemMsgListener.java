package dz.solc.viewtool.view.mayview.msgview.listener;

import dz.solc.viewtool.view.mayview.msgview.MsgTextView;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/10/14
 * create_time: 10:03
 * describe: 新增消息更新功能
 **/
public interface UpdateTabItemMsgListener {

    /**
     * 比较tag 值是否相等
     *
     * @param src 指外部传入的初始tag 默认是菜单的title
     * @param ta  需要判断是否相等的 标志
     * @return
     */
    boolean equalsTag(Object src, Object ta);

    /**
     * @param view 消息的显示控件
     * @param msg  消息文本等
     */
    <T> void onUpdateMsg(MsgTextView view, T msg);
}
