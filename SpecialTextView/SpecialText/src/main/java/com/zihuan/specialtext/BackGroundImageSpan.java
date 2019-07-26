package com.zihuan.specialtext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;
import android.util.Log;

public class BackGroundImageSpan extends ReplacementSpan implements ParcelableSpan {
    private static final String TAG = "BackGroundImageSpan";
    private Drawable mDrawable;
    private int mImageId;
    private int mWidth = -1;
//https://android.googlesource.com/platform/packages/apps/Mms/+/9901031/src/com/android/mms/ui/BackgroundImageSpan.java

    /**
     * new BackGroundImageSpan use resource id and Drawable
     *
     * @param id       the drawable resource id
     * @param drawable Drawable related to the id
     * @internal
     * @hide
     */
    public BackGroundImageSpan(int id, Drawable drawable) {
        mImageId = id;
        mDrawable = drawable;
    }

    /**
     * @hide
     * @internal
     */
    public BackGroundImageSpan(Parcel src) {
        mImageId = src.readInt();
    }

    public void drawBackGround(Canvas canvas, int width, float x, int top, int y, int bottom, Paint paint) {
        if (mDrawable == null) {//if no backgroundImage just don't do any drawBackGround
            Log.e(TAG, "mDrawable is null drawBackGround()");
            return;
        }
        Drawable drawable = mDrawable;
        canvas.save();
        canvas.translate(x, top); // translate to the left top point
        int height = bottom - top;//背景的原始高度
        int unknownX = mHeight == 0 ? topMargin : height - mHeight;
        mDrawable.setBounds(0, unknownX, width, height - bottomMargin);
        drawable.draw(canvas);
        canvas.restore();
    }

    //文字和背景的上下边距，由于tv有默认的边距所以可以根据实际情况调整 （目测上边距+10，下边距-5正好包裹内容）
    int topMargin = 0;
    int bottomMargin = 0;
    //    int topMargin = 10;
//    int bottomMargin = 5;
    private int mHeight = 0;

    public void setHeight(int pxHeight) {
        mHeight = pxHeight;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
    }

    /**
     * return a special type identifier for this span class
     *
     * @hide
     * @internal
     * @Override
     */
    public int getSpanTypeId() {
        return 0;
    }

    /**
     * describe the kinds of special objects contained in this Parcelable's marshalled representation
     *
     * @hide
     * @internal
     * @Override
     */
    public int describeContents() {
        return 0;
    }

    /**
     * flatten this object in to a Parcel
     *
     * @hide
     * @internal
     * @Override
     */
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mImageId);
    }

    /**
     * @hide
     * @internal
     */
    public void convertToDrawable(Context context) {
        if (mDrawable == null) {
            mDrawable = context.getResources().getDrawable(mImageId);
        }
    }

    /**
     * convert a style text that contain BackGroundImageSpan, Parcek only pass resource id,
     * after Parcel, we need to convert resource id to Drawable.
     *
     * @hide
     * @internal
     */
    public static void convert(CharSequence text, Context context) {
        if (!(text instanceof SpannableStringBuilder)) {
            return;
        }
        SpannableStringBuilder builder = (SpannableStringBuilder) text;
        BackGroundImageSpan[] spans = builder.getSpans(0, text.length(), BackGroundImageSpan.class);
        if (spans == null || spans.length == 0) {
            return;
        }
        for (int i = 0; i < spans.length; i++) {
            spans[i].convertToDrawable(context);
        }
    }

    /**
     * drawBackGround the span
     *
     * @hide
     * @internal
     * @Override
     */
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        drawBackGround(canvas, mWidth, x, top, y, bottom, paint);
        canvas.drawText(text, start, end, x, y, paint);
    }

    /**
     * get size of the span
     *
     * @hide
     * @internal
     * @Override
     */
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        float size = paint.measureText(text, start, end);
        if (fm != null && paint != null) {
            paint.getFontMetricsInt(fm);
        }
        mWidth = (int) size;
        return mWidth;
    }
}