package dz.solc.viewtool.view.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import dz.solc.viewtool.view.navigation.listener.ObserverOnTabItemSelectListener;
import dz.solc.viewtool.view.navigation.listener.OnSecondSelectListener;
import dz.solc.viewtool.view.navigation.listener.OnTabItemSelectListener;
import dz.solc.viewtool.view.mayview.msgview.listener.UpdateTabItemMsgListener;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2018/6/22
 * create_time: 11:09
 * describe 底部菜单view
 **/
public class BottomTabView extends LinearLayout {

    private ViewPager viewPager;

    /**
     * 记录最新的选择位置
     */
    private int lastPosition = -1;

    /**
     * 所有 TabItem 的集合
     */
    private List<TabItemView> tabItemViews;
    private OnTabItemSelectListener onTabItemSelectListener;
    private OnSecondSelectListener onSecondSelectListener;

    private List<ObserverOnTabItemSelectListener> onTabItemSelectListeners = new ArrayList<>();

    public BottomTabView(Context context) {
        super(context);
    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public BottomTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 连接 Viewpager
     *
     * @param viewPager
     */
    public void setUpWithViewPager(final ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updatePosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setOnTabItemSelectListener(new OnTabItemSelectListener() {
            @Override
            public void onTabItemSelect(int position) {
                viewPager.setCurrentItem(position);
            }
        });
    }


    /**
     * 通过 tag 获取单个菜单view
     *
     * @param msg
     * @param tabTag
     * @return TabItemView 或者 null
     */
    public <T> TabItemView getItemView(T msg, Object tabTag, UpdateTabItemMsgListener listener) {
        if (null != tabItemViews) {
            for (TabItemView itemView : tabItemViews) {
                Object object = itemView.getTag();
                if (null != listener) {
                    boolean flag = listener.equalsTag(object, tabTag);
                    if (flag) {
                        itemView.setUpdateTabItemMsgListener(listener);
                        itemView.updateMsg(msg);
                        return itemView;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 设置 Tab Item View
     */
    public void setTabItemViews(List<TabItemView> tabItemViews) {
        setTabItemViews(tabItemViews, null);
    }

    /**
     * 设置 Tab Item View
     */
    public void setTabItemViews(List<TabItemView> tabItemViews, View centerView) {

        if (this.tabItemViews != null) {
            throw new RuntimeException("不能重复设置！");
        }

        if (tabItemViews == null || tabItemViews.size() < 2) {
            throw new RuntimeException("TabItemView 的数量必须大于2！");
        }

        this.tabItemViews = tabItemViews;
        for (int i = 0; i < tabItemViews.size(); i++) {

            if (centerView != null && i == tabItemViews.size() / 2) {
                this.addView(centerView);
            }

            final TabItemView tabItemView = tabItemViews.get(i);

            this.addView(tabItemView);

            final int finalI = i;

            tabItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (finalI == lastPosition) {
                        // 第二次点击
                        if (onSecondSelectListener != null) {
                            onSecondSelectListener.onSecondSelect(finalI);
                        }
                        return;
                    }

                    updatePosition(finalI);

                    if (onTabItemSelectListener != null) {
                        onTabItemSelectListener.onTabItemSelect(finalI);
                    }
                }
            });
        }

        /**
         * 将所有的 TabItem 设置为 初始化状态
         */
        for (TabItemView tab : tabItemViews) {
            tab.setStatus(TabItemView.DEFAULT);
        }

        /**
         * 默认状态选择第一个
         */
        updatePosition(0);
    }

    /**
     * 更新被选中 Tab Item 的状态
     * 恢复上一个 Tab Item 的状态
     */
    public void updatePosition(int position) {
        if (lastPosition != position) {
            if (tabItemViews != null && tabItemViews.size() != 0) {
                tabItemViews.get(position).setStatus(TabItemView.PRESS);
                if (lastPosition != -1) {
                    tabItemViews.get(lastPosition).setStatus(TabItemView.DEFAULT);
                }
                lastPosition = position;
                for (ObserverOnTabItemSelectListener lister : onTabItemSelectListeners) {
                    if (null != lister) {
                        lister.onSelectedTab(tabItemViews.get(position), position);
                    }
                }
            } else {
                throw new RuntimeException("please setTabItemViews !");
            }
        }
    }

    public BottomTabView addObserverOnTabItemSelectListener(ObserverOnTabItemSelectListener observer) {
        this.onTabItemSelectListeners.add(observer);
        return this;
    }

    public BottomTabView removeObserverOnTabItemSelectListener(ObserverOnTabItemSelectListener observer) {
        this.onTabItemSelectListeners.remove(observer);
        return this;
    }

    private BottomTabView setOnTabItemSelectListener(OnTabItemSelectListener onTabItemSelectListener) {
        this.onTabItemSelectListener = onTabItemSelectListener;
        return this;
    }

    public BottomTabView setOnSecondSelectListener(OnSecondSelectListener onSecondSelectListener) {
        this.onSecondSelectListener = onSecondSelectListener;
        return this;
    }

}
