package dz.solc.viewtool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author 
 *
 */
public class ToolScrollerView extends ScrollView {

	public ToolScrollerView(Context context) {
		super(context);
	}

	public ToolScrollerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ToolScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	  
}
