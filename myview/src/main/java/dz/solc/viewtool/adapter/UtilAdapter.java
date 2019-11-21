package dz.solc.viewtool.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.haarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;

import java.lang.reflect.Field;

/**
* creat_user: zhengzaihong
* Email:1096877329@qq.com
* creat_date: 2017/12/6
* creat_time: 10:30
* describe 为listview,gridView,为ExpandableListView设置高度
**/
public class UtilAdapter {


	// 为listview设置高度
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()-1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	/**
	 　　* 计算gridview高度
	 　　* @param gridView
	 　　*/
	public static void setGridViewHeightBasedOnChildren(GridView gridView) {
      // 获取GridView对应的Adapter
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int rows;
		int columns = 0;
		int horizontalBorderHeight = 0;
		Class<?> clazz = gridView.getClass();
		try {
        // 利用反射，取得每行显示的个数
			Field column = clazz.getDeclaredField("mRequestedNumColumns");
			column.setAccessible(true);
			columns = (Integer) column.get(gridView);
          // 利用反射，取得横向分割线高度
			Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
			horizontalSpacing.setAccessible(true);
			horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
		} catch (Exception e) {
			e.printStackTrace();
		}
      // 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
		if (listAdapter.getCount() % columns > 0) {
			rows = listAdapter.getCount() / columns + 1;
		} else {
			rows = listAdapter.getCount() / columns;
		}
		int totalHeight = 0;
		int addOneHeight=0;
		for (int i = 0; i < rows; i++) { // 只计算每项高度*行数
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
			addOneHeight= listItem.getMeasuredHeight();
		}
		totalHeight=totalHeight+addOneHeight;
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight + horizontalBorderHeight * (rows - 1);// 最后加上分割线总高度
		gridView.setLayoutParams(params);
	}


	// 为ExpandableListView设置高度
	public static void setExpandListViewHeightBasedOnChildren(ExpandableListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		int totalHeight = 0;
		int count = listAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();

	}


	/**
	 * 设置 listView 显示动画从左渐进
	 * @param listView
	 * @param adapter
	 */
	public static void setSwingLeftInAnimationAdapter(ListView listView,BaseAdapter adapter){
		SwingLeftInAnimationAdapter animationAdapter =	new SwingLeftInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);

	}

	/**
	 * 设置 listView 显示动画从右渐进
	 * @param listView
	 * @param adapter
	 */
	public static void setSwingRightInAnimationAdapter(ListView listView,BaseAdapter adapter){
		SwingRightInAnimationAdapter animationAdapter =	new SwingRightInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);

	}


	/**
	 * 设置 listView 显示动画淡入
	 * @param listView
	 * @param adapter
	 */
	public static void setAlphaInAnimationAdapter(ListView listView,BaseAdapter adapter){
		AlphaInAnimationAdapter animationAdapter =	new AlphaInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);

	}


	/**
	 * 设置 listView 显示动画缩放式进入
	 * @param listView
	 * @param adapter
	 */
	public static void setScaleInAnimationAdapter(ListView listView,BaseAdapter adapter){
		ScaleInAnimationAdapter animationAdapter =	new ScaleInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);
	}


	/**
	 * 设置 listView 显示动画 底部渐进
	 * @param listView
	 * @param adapter
	 */
	public static void setSwingBottomInAnimationAdapter(ListView listView,BaseAdapter adapter){
		SwingBottomInAnimationAdapter animationAdapter =	new SwingBottomInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);

	}






	/**
	 * 设置 listView 显示动画从左渐进
	 * @param listView
	 * @param adapter
	 */
	public static void setSwingLeftInAnimationAdapter(GridView listView,BaseAdapter adapter){
		SwingLeftInAnimationAdapter animationAdapter =	new SwingLeftInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);

	}

	/**
	 * 设置 listView 显示动画从右渐进
	 * @param gridView
	 * @param adapter
	 */
	public static void setSwingRightInAnimationAdapter(GridView gridView,BaseAdapter adapter){
		SwingRightInAnimationAdapter animationAdapter =	new SwingRightInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(gridView);
		gridView.setAdapter(animationAdapter);

	}


	/**
	 * 设置 listView 显示动画淡入
	 * @param gridView
	 * @param adapter
	 */
	public static void setAlphaInAnimationAdapter(GridView gridView,BaseAdapter adapter){
		AlphaInAnimationAdapter animationAdapter =	new AlphaInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(gridView);
		gridView.setAdapter(animationAdapter);

	}


	/**
	 * 设置 gridView 显示动画缩放式进入
	 * @param gridView
	 * @param adapter
	 */
	public static void setScaleInAnimationAdapter(GridView gridView,BaseAdapter adapter){
		ScaleInAnimationAdapter animationAdapter =	new ScaleInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(gridView);
		gridView.setAdapter(animationAdapter);
	}


	/**
	 * 设置 GridView 显示动画 底部渐进
	 * @param gridView
	 * @param adapter
	 */
	public static void setSwingBottomInAnimationAdapter(GridView gridView,BaseAdapter adapter){
		SwingBottomInAnimationAdapter animationAdapter =	new SwingBottomInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(gridView);
		gridView.setAdapter(animationAdapter);

	}

}
