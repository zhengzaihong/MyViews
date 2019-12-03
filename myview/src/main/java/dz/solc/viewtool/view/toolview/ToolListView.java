package dz.solc.viewtool.view.toolview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class ToolListView extends ListView {


	public ToolListView(Context context) {
		super(context);
	}

	public ToolListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ToolListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

//
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		switch (ev.getAction()) {
//			// 当手指触摸listview时，让父控件交出ontouch权限,不能滚动
//			case MotionEvent.ACTION_DOWN:
//				setParentScrollAble(false);
//			case MotionEvent.ACTION_MOVE:
//				break;
//			case MotionEvent.ACTION_UP:
//			case MotionEvent.ACTION_CANCEL:
//				// 当手指松开时，让父控件重新获取onTouch权限
//				setParentScrollAble(true);
//				break;
//
//		}
//		return super.onInterceptTouchEvent(ev);
//
//	}
//
//	// 设置父控件是否可以获取到触摸处理权限
//	private void setParentScrollAble(boolean flag) {
//		getParent().requestDisallowInterceptTouchEvent(!flag);
//	}

}
