package com.chan.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chan on 2017/6/28.
 */

public class SubtitleView extends View {

	private Layout mContentLayout;
	private Layout mStrokeLayout;
	private TextPaint mTextPaint;

	private float mStrokeWidth;
	private int mStrokeColor;

	public SubtitleView(Context context) {
		this(context, null);
	}

	public SubtitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SubtitleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mStrokeWidth = 1f;
		mStrokeColor = Color.BLACK;
		int textColor = Color.WHITE;
		float textSize = 20f;

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SubtitleView, defStyleAttr, 0);

		try {
			if (typedArray.hasValue(R.styleable.SubtitleView_stroke_width)) {
				mStrokeWidth = typedArray.getFloat(R.styleable.SubtitleView_stroke_width, mStrokeWidth);
			}

			if (typedArray.hasValue(R.styleable.SubtitleView_stroke_color)) {
				mStrokeColor = typedArray.getColor(R.styleable.SubtitleView_stroke_color, mStrokeColor);
			}

			if (typedArray.hasValue(R.styleable.SubtitleView_text_color)) {
				textColor = typedArray.getColor(R.styleable.SubtitleView_text_color, textColor);
			}

			if (typedArray.hasValue(R.styleable.SubtitleView_text_size)) {
				textSize = typedArray.getDimension(R.styleable.SubtitleView_text_size, textSize);
			}
		} finally {
			typedArray.recycle();
		}

		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTextSize(textSize);
		mTextPaint.setColor(textColor);
	}

	public void render(CharSequence subtitle) {
		if (getWidth() == 0) {
			return;
		}

		mStrokeLayout = null;
		mContentLayout = null;


		int width = getWidth() - getPaddingLeft() - getPaddingRight();
		Layout.Alignment alignment = Layout.Alignment.ALIGN_CENTER;
		float spacingMulti = 1f;
		float spacingAdd = 0f;
		boolean includePad = false;

		mContentLayout = new StaticLayout(subtitle, mTextPaint, width, alignment, spacingMulti, spacingAdd, includePad);

		SpannableStringBuilder spannableStringBuilder = null;
		if (subtitle instanceof Spanned) {
			Spanned spanned = (Spanned) subtitle;
			spannableStringBuilder = (SpannableStringBuilder) spanned.subSequence(0, spannableStringBuilder.length());
			ForegroundColorSpan[] spans = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ForegroundColorSpan.class);
			for (int i = 0; spans != null && i < spans.length; ++i) {
				ForegroundColorSpan span = spans[i];
				spannableStringBuilder.removeSpan(span);
			}
		}

		if (spannableStringBuilder == null) {
			spannableStringBuilder = new SpannableStringBuilder(subtitle);
		}

		spannableStringBuilder.setSpan(new StrokeSpan(mStrokeColor, mStrokeWidth), 0, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		mStrokeLayout = new StaticLayout(spannableStringBuilder, mTextPaint, width, alignment, spacingMulti, spacingAdd, includePad);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mContentLayout == null && mStrokeLayout == null) {
			return;
		}

		canvas.save();

		if (mContentLayout != null) {
			mContentLayout.draw(canvas);
		}

		if (mStrokeLayout != null) {
			mStrokeLayout.draw(canvas);
		}

		canvas.restore();
	}
}
