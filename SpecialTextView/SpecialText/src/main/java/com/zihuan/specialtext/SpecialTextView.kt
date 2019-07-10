package com.zihuan.specialtext

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.AppCompatTextView
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.util.DisplayMetrics
import android.view.WindowManager


class SpecialTextView : AppCompatTextView {
    private var TAG = "SpecialTextView"
    private lateinit var mWholeText: String//完整字符串
    private lateinit var mCopyWholeText: String//完整字符串
    private var isNeedMovementMethod = false//是否需要设置分段点击的方法
    private var mSpannableString: SpannableStringBuilder? = null
    private var mSpecialTextClick: SpecialTextClick? = null

    constructor(context: Context) : super(context) {
        highlightColor = Color.TRANSPARENT
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        highlightColor = Color.TRANSPARENT
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //        设置单段点击后背景色透明
        highlightColor = Color.TRANSPARENT
    }

    /***
     * 设置完整的字符
     * @param wholeString
     * @return
     */
    fun setWhole(wholeString: String): SpecialTextView {
        mWholeText = wholeString
        mCopyWholeText = wholeString
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
     * 设置包含单个特殊字符的方法
     * @param wholeString 完整的字符
     * @param special 特殊字符
     * @param color 特殊字符颜色
     * @param enabledClick 是否设置点击事件
     * @param underline 当前字段是否需要下划线 默认不需要
     */
    fun setSpecialText(wholeString: String, special: String, color: Int, enabledClick: Boolean = false, underline: Boolean = false) {
        setWhole(wholeString)
        setSpecialText(special, color)
        setSpecialClick(enabledClick, special, underline = underline)
        specialTextComplete()
    }

    /***
     * 连续设置多个特殊字符串
     * @param special 特殊的字符
     * @param color 特殊字符的色值
     * @param enabledClick 是否设置点击事件
     * @param underline 当前字段是否需要下划线 默认不需要
     * @return this
     */
    fun specialTextAppend(special: String, color: Int, enabledClick: Boolean = false, underline: Boolean = false): SpecialTextView {
        setSpecialText(special, color)
        setSpecialClick(enabledClick, special, underline = underline)
        return this
    }


    /****
     * 这个方法暂时不能和其他设置特殊字符串的方法一起使用，只能单独使用
     * @param targetLine 目标行 默认获取最后一行
     * @param imgRes 追加在最后的图片
     * @param enabledClick 是否需要点击事件
     * @param underline 是否需要下划线
     * @param extra 额外追加的截取长度，比如用两个逗号替换成两个汉字
     *  在此处追加图片耦合度比较高应该拆分出来
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun setEndText(text: String, color: Int, imgRes: Int = -1, enabledClick: Boolean = false, underline: Boolean = false, targetLine: Int = maxLines - 1, extra: Int = 1): SpecialTextView {
        //先设置文本否则拿不到宽度
        this.text = mWholeText
        //如果图片的
        var imgWidth = if (imgRes != -1) BitmapFactory.decodeResource(resources, imgRes).width else 0
        post {
            if (targetLine == Int.MAX_VALUE) {
                maxLines = lineCount
            }
            Logger("行数和当前字符实际宽度" + lineCount.toString() + " " + paint.measureText(mWholeText))
            var targetLineText = mWholeText.substring(layout.getLineStart(targetLine), layout.getLineEnd(targetLine))
            //如果目标行大于当前屏幕宽度就减去当前行的N个字符+extra N是最后要显示的特殊字符+extra是给图片预留的空间,可以自定义占几个字符宽度，如果图片大可以多占，反之少占
            //获取当前屏幕的宽度
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            val width = dm.widthPixels
            //获取当前行的实际宽度（能看到的内容的宽度，不算没有显示出来的）
            val endLineWidth = layout.getLineWidth(targetLine)
            Logger("当前行数所占宽度$endLineWidth")
            //测量追加字符在当前view的配置下的实际宽度加上末尾图片的宽度
            var textLineWidth = paint.measureText(text)
            //图片的宽度是追加文字的几倍
            var textTimes = when {
                imgRes == -1 -> 1//没有图片的情况
                imgWidth > textLineWidth -> {//图片的宽度大于追加文本的宽度
                    var imgDivText = imgWidth.div(textLineWidth).toInt()
                    if (imgDivText <= 0) 2 else imgDivText
                }
                else -> 1//图片的宽度小于文本
            }
            Logger("原始字符串$mWholeText")
            Logger("目标字符串$targetLineText")
            //如果当前行所剩宽度小于textLineWidth实际宽度 切割掉text.length个字符
            if (Math.abs(width.minus(endLineWidth)) < textLineWidth.plus(imgWidth)) {
                //追加文字和图片所占长度
                var textPlusImgLen = text.length.times(textTimes).plus(extra)
                var cutStart = targetLineText.length.minus(textPlusImgLen)
                if (cutStart > 0) {
                    mWholeText = mWholeText.substring(0, cutStart)
                }
                Logger("目标行切割后$mWholeText")
            } else {
                Logger("目标行满足当前字符需要宽度")
                mWholeText = mWholeText.substring(0, layout.getLineEnd(targetLine))
            }
            var jiance = mWholeText.substring(mWholeText.length - 2, mWholeText.length)
            if (jiance == "\n\n") {
                Logger("包含两个回车，去除一个")
                mWholeText = mWholeText.substring(0, mWholeText.length - 1)
            }
            mWholeText += text.plus(if (imgRes != -1) "  " else "")//如果末尾有图片的话，为图片预留一个空格占位
            getSpannableString()
            Logger("目标行拼接后$mWholeText")
            if (imgRes != -1) {
                setImage(imgRes)
            }
            setSpecialText(text, color)
            setSpecialClick(enabledClick, text, end = mWholeText.length, underline = underline)
            specialTextComplete()
        }
        return this
    }

    /***
     * 将某个位置的文字替换成图片
     * 默认将最后一个字替换成图片
     * 这个方法可以与其他方法共用
     * 优化一下以上一步的最后位置为起始位置，调用了此方法 默认在最后追加两个空格
     * @param res 图片资源
     * @param start 图片所占开始位置
     * @param end 图片所占结束位置
     * @param enabledClick 是否设置点击事件
     * 点击后返回 图片的资源id 以此判断点击的位置
     */
    fun setImage(res: Int, start: Int = -1, end: Int = -1, enabledClick: Boolean = false): SpecialTextView {
        var start = start
        var end = end
        if (start == -1) {
            start = mWholeText.length.minus(1)
            end = start.plus(1)
        }
//        如果不想要居中对齐 可以用系统的ImageSpan 提供了两种对齐方式，ImageSpan.ALIGN_BASELINE、ALIGN_BOTTOM
        val imageSpan = SpecialImageSpan(context, res)
        mSpannableString?.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        setSpecialClick(enabledClick, res.toString(), start, end)
        return this
    }


    private fun setSpecialText(special: String, color: Int) {
        if (TextUtils.isEmpty(mWholeText)) {//要设置最后出现的位置
            Log.e(TAG, "mWholeText为空>>>> 请先调用setWhole函数")
            return
        }
        getSpannableString()
        val start = mWholeText.indexOf(special)
        val end = start + special.length
        try {
            mSpannableString?.setSpan(ForegroundColorSpan(resources.getColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: Exception) {
            Log.e(TAG, "没有发现当前字符>>>> " + special
                    + "\n暂时不能用于RecycleView等列表中(或者自己手动处理下,滚动到当前view时重新设置setSpecialText()) Exception>>>> " + e.toString())
        }
    }

    /***
     * 点击事件方法
     */
    fun setSpecialClick(enabledClick: Boolean = false, special: String, start: Int = -1, end: Int = -1, underline: Boolean = false): SpecialTextView {
        if (!enabledClick) return this
        isNeedMovementMethod = true
        if (mSpecialTextClick == null) {
            if (context is SpecialTextClick) {
                mSpecialTextClick = context as SpecialTextClick
            } else {
                Log.e(TAG, "没有实现监听事件 SpecialTextClick")
            }
        }
//      计算特殊字符的起始位置
        var start = start
        var end = end
        if (start == -1) {
            start = mWholeText.indexOf(special)
        }
        if (end == -1) {
            end = start + special.length
        }
        if (start == -1) return this//未找到字符位置
        mSpannableString?.setSpan(object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = underline//是否设置下划线
            }

            override fun onClick(widget: View) {
                mSpecialTextClick?.specialClick(special)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    //设置字符串完成
    fun specialTextComplete() {
        if (isNeedMovementMethod) {
            isNeedMovementMethod = false
            movementMethod = LinkMovementMethod.getInstance()
        }
        text = mSpannableString
    }

    private fun getSpannableString() {
        if (mSpannableString == null) mSpannableString = SpannableStringBuilder(mWholeText)

    }

    private var enabledLog = false
    fun setEnabledLog(enabled: Boolean = false) {
        enabledLog = enabled
    }

    private fun Logger(msg: String) {
        if (enabledLog) {
            Log.e(TAG, msg)
        }
    }

    /***
     * 监听事件
     */
    interface SpecialTextClick {
        fun specialClick(tag: String)
    }
}
