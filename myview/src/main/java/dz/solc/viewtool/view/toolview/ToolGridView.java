package dz.solc.viewtool.view.toolview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ToolGridView extends GridView {
	public ToolGridView(Context context) {
		super(context);
	}

	public ToolGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ToolGridView(Context context, AttributeSet attrs, int defStyle) {
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
