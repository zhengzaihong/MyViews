package dz.solc.viewtool;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author 
 *
 */
public class ScrollerScrollerView extends ScrollView {

	public ScrollerScrollerView(Context context) {
		super(context);
	}

	public ScrollerScrollerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollerScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	  
}
