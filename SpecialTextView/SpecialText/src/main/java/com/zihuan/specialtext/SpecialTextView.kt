package com.zihuan.specialtext

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View

class SpecialTextView : AppCompatTextView {
    private var TAG = "SpecialTextView"
    private lateinit var mWholeText: String//完整字符串
    private var isNeedMovementMethod = false//是否需要设置分段点击的方法
    private var mSpannableString: SpannableString? = null

    private var mSpecialTextClick: SpecialTextClick? = null

    constructor(context: Context) : super(context) {
        highlightColor = Color.TRANSPARENT
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        highlightColor = Color.TRANSPARENT
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //        设置单段点击后背景色透明
        //        https://www.jianshu.com/p/690ef18bfb25
        highlightColor = Color.TRANSPARENT
    }

    /***
     * 设置完整的字符
     * @param wholeString
     * @return
     */
    fun setWhole(wholeString: String): SpecialTextView {
        mWholeText = wholeString
        mSpannableString = SpannableString(mWholeText)
        return this
    }

    /***
     * 设置包含单个特殊字符的方法
     * @param wholeString 完整的字符
     * @param special 特殊字符
     * @param color 特殊字符颜色
     */
    fun setSpecialText(wholeString: String, special: String, color: Int) {
        setWhole(wholeString)
        setSpecialText(special, color)
        specialTextComplete()
    }

    /***
     * 连续设置多个特殊字符串
     * @param special
     * @param color
     * @return
     */
    fun specialTextAppend(special: String, color: Int): SpecialTextView {
        setSpecialText(special, color)
        return this
    }

    /**
     * 设置监听事件
     *
     * @param specialListener
     * @return
     */
    fun setSpecialListener(specialListener: SpecialTextClick): SpecialTextView {
        mSpecialTextClick = specialListener
        return this
    }

    /***
     * 设置分段色值和点击事件
     * @param special 特殊的字符
     * @param color 特殊字符的色值
     * @param underline 当前字段是否需要下划线 默认不需要
     * @return
     */
    fun setSpecialTextAndClick(special: String, color: Int, underline: Boolean = false): SpecialTextView {
        if (TextUtils.isEmpty(mWholeText)) {
            Log.e(TAG, "完整字符串为空>>>> 请先调用")
            return this
        }
        setSpecialText(special, color)
        val start = mWholeText.indexOf(special)
        val end = start + special.length
        mSpannableString!!.setSpan(object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = underline//是否设置下划线
            }

            override fun onClick(widget: View) {
                mSpecialTextClick!!.specialClick(special)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        isNeedMovementMethod = true
        return this
    }

    private fun setSpecialText(special: String, color: Int) {
        val start = mWholeText.indexOf(special)
        val end = start + special.length
        try {
            mSpannableString!!.setSpan(ForegroundColorSpan(resources.getColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: Exception) {
            Log.e(TAG, "没有发现当前字符>>>> " + special
                    + "\n暂时不能用于RecycleView等列表中(或者自己手动处理下,滚动到当前view时重新设置setSpecialText()) Exception>>>> " + e.toString())
        }

    }

    //设置字符串完成
    fun specialTextComplete() {
        if (isNeedMovementMethod) {
            isNeedMovementMethod = false
            movementMethod = LinkMovementMethod.getInstance()
        }
        text = mSpannableString
    }

    interface SpecialTextClick {
        fun specialClick(tag: String)
    }
}
