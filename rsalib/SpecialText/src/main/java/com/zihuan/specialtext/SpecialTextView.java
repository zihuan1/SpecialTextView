package com.zihuan.specialtext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;

public class SpecialTextView extends AppCompatTextView {
    String TAG="SpecialTextView";
    public SpecialTextView(Context context) {
        super(context);
    }

    public SpecialTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpecialTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setSpecialText(@NonNull String wholetext, int color, @NonNull String special) {
        int start = wholetext.indexOf(special);
        int end = start + special.length();
        setSpecialText(wholetext, color, start, end);
    }

    public void setSpecialText(@NonNull String wholetext, int color, int start, int end) {
        SpannableString spannableString = getSpannable(wholetext);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannableString);
    }

    private String mWholeText;

    public SpecialTextView setWhole(String wholetext) {
        mWholeText = wholetext;
        return this;
    }

//    Map<String, Integer> manySpecial = new HashMap<>();

    //设置多字符串完成
    public void specialTextComplete() {
        setText(getSpannable(mWholeText));
    }

    //设置多个特殊字符
    public SpecialTextView specialTextAppend(String special, int color) {
//        manySpecial.put(special, color);
        int start = mWholeText.indexOf(special);
        if (start > 0) {
            int end = start + special.length();
            getSpannable(mWholeText).setSpan(new ForegroundColorSpan(getResources().getColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else {
            Log.e(TAG,"没有发现当前字符>>>> "+special);
//            throw new RuntimeException("没有发现当前字符>>>> "+special);
        }
        return this;
    }

    SpannableString mSpannableString;

    private SpannableString getSpannable(String wholetext) {
        if (mSpannableString == null)
            return mSpannableString = new SpannableString(wholetext);
        else return mSpannableString;
    }
}
