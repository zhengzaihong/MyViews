package dz.solc.viewtool.view.barview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import dz.solc.viewtool.R;


public class ProgressWheel extends View {

	private int layout_height = 0;
	private int layout_width = 0;
	private int fullRadius = 100;
	private int circleRadius = 80;
	private int circlebarLength = 60;
	private int barWidth = 20;
	private int rimWidth = 20;
	private int textSize = 20;

	private int paddingTop = 5;
	private int paddingBottom = 5;
	private int paddingLeft = 5;
	private int paddingRight = 5;

	private int barColor = 0xAA000000;
	private int circleColor = 0x00000000;
	private int rimColor = 0xAADDDDDD;
	private int textColor = 0xFF000000;

	private Paint barPaint = new Paint();
	private Paint circlePaint = new Paint();
	private Paint rimPaint = new Paint();
	private Paint textPaint = new Paint();

	private RectF rectBounds = new RectF();
	private RectF circleBounds = new RectF();

	private int spinSpeed = 2;
	private int delayMillis = 0;
	private Handler spinHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			invalidate();
			if (isSpinning) {
				progress += spinSpeed;
				if (progress > 360) {
					progress = 0;
				}
				spinHandler.sendEmptyMessageDelayed(0, delayMillis);
			}
			// super.handleMessage(msg);
		}
	};
	int progress = 0;
	boolean isSpinning = false;

	private String text = "";
	private String[] splitText = {};

	/**
	 * @param context
	 * @param attrs
	 */
	public ProgressWheel(Context context, AttributeSet attrs) {
		super(context, attrs);

		parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.ProgressWheel));
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// Share the dimensions
		layout_width = w;
		layout_height = h;

		setupBounds();
		setupPaints();
		invalidate();
	}
	private void setupPaints() {
		barPaint.setColor(barColor);
		barPaint.setAntiAlias(true);
		barPaint.setStyle(Style.STROKE);
		barPaint.setStrokeWidth(barWidth);

		rimPaint.setColor(rimColor);
		rimPaint.setAntiAlias(true);
		rimPaint.setStyle(Style.STROKE);
		rimPaint.setStrokeWidth(rimWidth);

		circlePaint.setColor(circleColor);
		circlePaint.setAntiAlias(true);
		circlePaint.setStyle(Style.FILL);

		textPaint.setColor(textColor);
		textPaint.setStyle(Style.FILL);
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(textSize);
	}

	private void setupBounds() {
		// Width should equal to Height, find the min value to steup the circle
		int minValue = Math.min(layout_width, layout_height);

		// Calc the Offset if needed
		int xOffset = layout_width - minValue;
		int yOffset = layout_height - minValue;

		// Add the offset
		paddingTop = this.getPaddingTop() + (yOffset / 2);
		paddingBottom = this.getPaddingBottom() + (yOffset / 2);
		paddingLeft = this.getPaddingLeft() + (xOffset / 2);
		paddingRight = this.getPaddingRight() + (xOffset / 2);

		rectBounds = new RectF(paddingLeft, paddingTop,
				this.getLayoutParams().width - paddingRight, this.getLayoutParams().height
						- paddingBottom);

		circleBounds = new RectF(paddingLeft + barWidth, paddingTop + barWidth,
				this.getLayoutParams().width - paddingRight - barWidth,
				this.getLayoutParams().height - paddingBottom - barWidth);

		fullRadius = (this.getLayoutParams().width - paddingRight - barWidth) / 2;
		circleRadius = (fullRadius - barWidth) + 1;
	}
	private void parseAttributes(TypedArray a) {
		barWidth = (int) a.getDimension(R.styleable.ProgressWheel_barWidth, barWidth);

		rimWidth = (int) a.getDimension(R.styleable.ProgressWheel_rimWidth, rimWidth);

		spinSpeed = (int) a.getDimension(R.styleable.ProgressWheel_spinSpeed, spinSpeed);

		delayMillis = (int) a.getInteger(R.styleable.ProgressWheel_delayMillis, delayMillis);
		if (delayMillis < 0) {
			delayMillis = 0;
		}

		barColor = a.getColor(R.styleable.ProgressWheel_barColor, barColor);

		circlebarLength = (int) a.getDimension(R.styleable.ProgressWheel_circlebarLength, circlebarLength);

		textSize = (int) a.getDimension(R.styleable.ProgressWheel_textSize, textSize);

		textColor = (int) a.getColor(R.styleable.ProgressWheel_textcolorprogress, textColor);

		// if the text is empty , so ignore it
		if (a.hasValue(R.styleable.ProgressWheel_text)) {
			setText(a.getString(R.styleable.ProgressWheel_text));
		}

		rimColor = (int) a.getColor(R.styleable.ProgressWheel_rimColor, rimColor);

		circleColor = (int) a.getColor(R.styleable.ProgressWheel_circleColor, circleColor);

		// Recycle
		a.recycle();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// Draw the rim
		canvas.drawArc(circleBounds, 360, 360, false, rimPaint);
		// Draw the bar
		if (isSpinning) {
			canvas.drawArc(circleBounds, progress - 90, circlebarLength, false, barPaint);
		} else {
			canvas.drawArc(circleBounds, -90, progress, false, barPaint);
		}
		// Draw the inner circle
		canvas.drawCircle((circleBounds.width() / 2) + rimWidth + paddingLeft,
				(circleBounds.height() / 2) + rimWidth + paddingTop, circleRadius, circlePaint);
		// Draw the text (attempts to center it horizontally and vertically)
		int offsetNum = 0;
		for (String s : splitText) {
			float offset = textPaint.measureText(s) / 2;
			canvas.drawText(s, this.getWidth() / 2 - offset, this.getHeight() / 2
					+ (textSize * (offsetNum)) - ((splitText.length - 1) * (textSize / 2)),
					textPaint);
			offsetNum++;
		}
	}

	public void resetCount() {
		progress = 0;
		setText("0%");
		invalidate();
	}

	public void stopSpinning() {
		isSpinning = false;
		progress = 0;
		spinHandler.removeMessages(0);
	}

	public void spin() {
		isSpinning = true;
		spinHandler.sendEmptyMessage(0);
	}

	public void incrementProgress() {
		isSpinning = false;
		progress++;
		setText(Math.round(((float) progress / 360) * 100) + "%");
		spinHandler.sendEmptyMessage(0);
	}

	public void setProgress(int i) {
		isSpinning = false;
		progress = i;
		spinHandler.sendEmptyMessage(0);
	}


	public void setText(String text) {
		this.text = text;
		splitText = this.text.split("\n");
	}



	public int getCircleRadius() {
		return circleRadius;
	}

	public void setCircleRadius(int circleRadius) {
		this.circleRadius = circleRadius;
	}

	public int getBarLength() {
		return circlebarLength;
	}

	public void setBarLength(int barLength) {
		this.circlebarLength = circlebarLength;
	}

	public int getBarWidth() {
		return barWidth;
	}

	public void setBarWidth(int barWidth) {
		this.barWidth = barWidth;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public int getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}

	public int getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(int paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	public int getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public int getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}

	public int getBarColor() {
		return barColor;
	}

	public void setBarColor(int barColor) {
		this.barColor = barColor;
	}

	public int getCircleColor() {
		return circleColor;
	}

	public void setCircleColor(int circleColor) {
		this.circleColor = circleColor;
	}

	public int getRimColor() {
		return rimColor;
	}

	public void setRimColor(int rimColor) {
		this.rimColor = rimColor;
	}

	public Shader getRimShader() {
		return rimPaint.getShader();
	}

	public void setRimShader(Shader shader) {
		this.rimPaint.setShader(shader);
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public int getSpinSpeed() {
		return spinSpeed;
	}

	public void setSpinSpeed(int spinSpeed) {
		this.spinSpeed = spinSpeed;
	}

	public int getRimWidth() {
		return rimWidth;
	}

	public void setRimWidth(int rimWidth) {
		this.rimWidth = rimWidth;
	}

	public int getDelayMillis() {
		return delayMillis;
	}

	public void setDelayMillis(int delayMillis) {
		this.delayMillis = delayMillis;
	}

	/*
		<dz.solc.viewtool.view.barview.ProgressWheel
	android:layout_width="70dp"
	app:text="双圆环"
	app:textSize="6sp"
	app:barColor="@android:color/darker_gray"
	app:rimColor="@android:color/holo_red_dark"
	app:textcolorprogress="@color/colorPrimary"
	app:rimWidth="10dp"
	app:spinSpeed="10dp"
	app:delayMillis="20"
	app:circleColor="@android:color/holo_orange_dark"
	app:barWidth="10dp"
	app:radius="15dp"
	app:circlebarLength="20dp"
	android:layout_height="70dp" />*/

	/*

    <declare-styleable name="ProgressWheel">
        <attr name="text" format="string" />
        <attr name="textcolorprogress" format="color" />
        <attr name="textSize" format="dimension" />
        <attr name="barColor" format="color" />
        <attr name="rimColor" format="color" />
        <attr name="rimWidth" format="dimension" />
        <attr name="spinSpeed" format="dimension" />
        <attr name="delayMillis" format="integer" />
        <attr name="circleColor" format="color" />
        <attr name="radius" format="dimension" />
        <attr name="barWidth" format="dimension" />
        <attr name="circlebarLength" format="dimension" />
    </declare-styleable>
    <declare-styleable name="PieProgress">
        <attr name="foregroundColor" format="color" />
        <attr name="backgroundColor" format="color" />
    </declare-styleable>
*/

}
