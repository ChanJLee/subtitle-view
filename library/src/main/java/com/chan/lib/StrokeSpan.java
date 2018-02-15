package com.chan.lib;

import android.graphics.Paint;
import android.os.Parcel;
import android.support.annotation.ColorInt;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class StrokeSpan extends CharacterStyle
		implements UpdateAppearance, ParcelableSpan {

	private final int mColor;
	private final float mStrokeWidth;

	public StrokeSpan(@ColorInt int color, float strokeWidth) {
		mColor = color;
		mStrokeWidth = strokeWidth;
	}

	/**
	 * FIXME current id maybe repeat in one day
	 *
	 * @return
	 */
	public int getSpanTypeId() {
		return 100000;
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		ds.setColor(mColor);
		ds.setStyle(Paint.Style.STROKE);
		ds.setStrokeWidth(mStrokeWidth);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.mColor);
		dest.writeFloat(this.mStrokeWidth);
	}

	protected StrokeSpan(Parcel in) {
		this.mColor = in.readInt();
		this.mStrokeWidth = in.readFloat();
	}

	public static final Creator<StrokeSpan> CREATOR = new Creator<StrokeSpan>() {
		@Override
		public StrokeSpan createFromParcel(Parcel source) {
			return new StrokeSpan(source);
		}

		@Override
		public StrokeSpan[] newArray(int size) {
			return new StrokeSpan[size];
		}
	};
}