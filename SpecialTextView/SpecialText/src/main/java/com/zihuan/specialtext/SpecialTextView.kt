package com.zihuan.specialtext

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.util.DisplayMetrics
import android.support.v4.content.ContextCompat.getSystemService
import android.view.WindowManager


class SpecialTextView : AppCompatTextView {
    private var TAG = "SpecialTextView"
    private lateinit var mWholeText: String//完整字符串
    private var isNeedMovementMethod = false//是否需要设置分段点击的方法
    private lateinit var mSpannableString: SpannableString
    private lateinit var mSpecialTextClick: SpecialTextClick

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
        mSpannableString.setSpan(object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = underline//是否设置下划线
            }

            override fun onClick(widget: View) {
                mSpecialTextClick.specialClick(special)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        isNeedMovementMethod = true
        return this
    }

    fun setEndText(text: String, color: Int): SpecialTextView {
        post {
            Log.e("行数和当前字符实际宽度", lineCount.toString() + " " + paint.measureText(mWholeText))
            var curLineText = mWholeText.substring(layout.getLineStart(0), layout.getLineEnd(0))
            Log.e("当前行数字符详情", curLineText)
//如果目标行大于当前屏幕宽度就减去当前行的N个字符+a N是最后要显示的特殊字符+a是给图片预留的,可以自定义占几个字符宽度，如果图片大可以多占，反之少占
//            获取当前屏幕的宽度
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            val width = dm.widthPixels
            //获取当前行的实际宽度（能看到的内容的宽度，不算没有显示出来的）
            val endLineWidth = layout.getLineWidth(0)
            Log.e("当前行数所占宽度", endLineWidth.toString())
            //测量当前字符在当前view的配置下的实际宽度
            val textLineWidth = paint.measureText(text)
            //如果当前行所剩宽度小于textLineWidth实际宽度 切割掉text.length个字符
            if (Math.abs(width.minus(endLineWidth)) < textLineWidth) {
                var textlen = text.length
                curLineText = curLineText.substring(0, curLineText.length - textlen)
                Log.e("当前行剩余空间不足 切割后的字符", curLineText)
            } else {
                Log.e("当前行剩余空间充足", "${Math.abs(width.minus(endLineWidth))}")
                curLineText += text
            }
            Log.e("拼接后的字符", curLineText)

        }
        return this
    }

    /***
     * 如果起始位置是-1的话默认加在最后
     * 可以优化一下以上一步的最后位置为起始位置
     */
    fun setImage(res: Int, start: Int = -1, end: Int = -1): SpecialTextView {
        var start = start
        var end = end
        if (start == -1) {
            start = mWholeText.length.minus(1)
            end = start.plus(1)
        }
        val imageSpan = ImageSpan(context, res)
        mSpannableString.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        return this
    }


    private fun setSpecialText(special: String, color: Int) {
        val start = mWholeText.indexOf(special)
        val end = start + special.length
        try {
            mSpannableString.setSpan(ForegroundColorSpan(resources.getColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
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
