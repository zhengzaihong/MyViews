package dz.solc.viewtool.view.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import dz.solc.viewtool.R;
import dz.solc.viewtool.view.mayview.msgview.MsgTextView;
import dz.solc.viewtool.view.mayview.msgview.listener.UpdateTabItemMsgListener;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/10/14
 * create_time: 9:59
 * describe: 每个导航菜单
 **/
public class TabItemView extends LinearLayout {

    /**
     * 两个状态 选中、未选中
     */
    public final static int PRESS = 1;
    public final static int DEFAULT = 2;

    /**
     * Item 的标题
     */
    private String title;

    /**
     * 标题的两个状态的颜色 选中、未选中
     */
    private int colorDef;
    private int colorPress;

    /**
     * 两个图标的 资源 id ，选中、未选中
     */
    private int iconResDef;
    private int iconResPress;

    private TextView tvTitle;
    private ImageView ivIcon;
    private MsgTextView msgTextView;

    private Context context;
    /**
     * 外部的配置
     */
    private BottomViewConfig config;

    private UpdateTabItemMsgListener updateTabItemMsgListener;

    public TabItemView(Context context, String title, int colorDef, int colorPress,
                       int iconResDef, int iconResPress) {
        super(context);
        this.context = context;
        this.title = title;
        this.colorDef = colorDef;
        this.colorPress = colorPress;
        this.iconResDef = iconResDef;
        this.iconResPress = iconResPress;
        //设置一个标记,用于后面更新消息用
        this.setTag(title);
        init();
    }

    public TabItemView(Context context, String title, int colorDef, int colorPress,
                       int iconResDef, int iconResPress, Object tag) {
        super(context);
        this.context = context;
        this.title = title;
        this.colorDef = colorDef;
        this.colorPress = colorPress;
        this.iconResDef = iconResDef;
        this.iconResPress = iconResPress;
        //设置一个标记,用于后面更新消息用
        this.setTag(tag);
        init();
    }

    public void setConfig(BottomViewConfig config) {
        this.config = config;
        init();
    }


    /**
     * 初始化
     */
    public void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tab_item, this);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        view.setLayoutParams(layoutParams);
        tvTitle.setText(title);
        /**********************2020-10-11 新增消息数量扩展************************/
        if (null != config) {
            layoutParams.bottomMargin = config.getMenueMarginBottom();
            layoutParams.topMargin = config.getMenueIconMarginTop();
            tvTitle.setTextSize(config.getMenueTextSize());
            if (config.isEnableMsgShow()) {
                msgTextView = new MsgTextView(context, ivIcon);
                msgTextView.setConfig(config);
            }
        }
    }

    /**
     * 设置状态
     */
    public void setStatus(int status) {
        tvTitle.setTextColor(ContextCompat.getColor(getContext(), status == PRESS ? colorPress : colorDef));
        ivIcon.setImageResource(status == PRESS ? iconResPress : iconResDef);

    }

    public <T> void updateMsg(T msg) {
        if(null!=updateTabItemMsgListener){
            updateTabItemMsgListener.onUpdateMsg(msgTextView,msg);
        }
    }

    /**
     * 该监听请通过 bottomTabView.getItemView方式配置 切勿单独 TabItemView 对象调用
     * 否则不会被调用
     *
     * @param listener
     */
    public void setUpdateTabItemMsgListener(UpdateTabItemMsgListener listener) {
        this.updateTabItemMsgListener = listener;
    }
}

