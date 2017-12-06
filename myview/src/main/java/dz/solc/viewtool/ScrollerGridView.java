package dz.solc.viewtool;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ScrollerGridView extends GridView {
	public ScrollerGridView(Context context) {
		super(context);
	}

	public ScrollerGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollerGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	/**
	 * 重写该方法，达到使ListView适应ScrollView的效
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
