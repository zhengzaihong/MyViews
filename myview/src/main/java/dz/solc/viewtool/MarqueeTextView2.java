package dz.solc.viewtool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class MarqueeTextView2 extends TextView{
	private boolean isScroll;
	public MarqueeTextView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		createView();
	}
	
	public MarqueeTextView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		createView();
	}

	
	public MarqueeTextView2(Context context) {
		super(context);
		createView();
	}

    private void createView() {  
        setEllipsize(TruncateAt.MARQUEE);  
        setMarqueeRepeatLimit(-1);  
        setFocusableInTouchMode(true);  
    }  
  
    @Override  
    protected void onFocusChanged(boolean focused, int direction,  
            Rect previouslyFocusedRect) {  
        if (focused) {  
            super.onFocusChanged(focused, direction, previouslyFocusedRect);  
        }  
    }  
  
    @Override  
    public void onWindowFocusChanged(boolean focused) {  
        if (focused) {  
            super.onWindowFocusChanged(focused);  
        }  
    }  
  
    @Override  
    public boolean isFocused() {  
        return isScroll;  
    }  
    
    public void Scroll(boolean b){
    		isScroll = b;
    		if (isScroll) {
    			super.onWindowFocusChanged(true); 
			}else {
				super.onWindowFocusChanged(false);
			}
    		
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
           final int textWidth = getWidth() - getCompoundPaddingLeft() -
                   getCompoundPaddingRight();
           final float lineWidth = this.getLayout().getLineWidth(0);
           final float gap = textWidth / 3.0f;
          float mGhostStart = lineWidth - textWidth + gap;
          float  mMaxScroll = mGhostStart + textWidth;
         float  mGhostOffset = lineWidth + gap;
         float mFadeStop = lineWidth + textWidth / 6.0f;
         float  mMaxFadeScroll = mGhostStart + lineWidth + lineWidth;
    	Log.e("point_X",mMaxFadeScroll +"");
    	super.onDraw(canvas);
    }
}
