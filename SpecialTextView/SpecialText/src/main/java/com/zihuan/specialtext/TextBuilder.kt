package com.zihuan.specialtext

class TextBuilder(private val textView: SpecialTextView) {


    /**
     * 连续设置多个特殊字符串
     * @param special 特殊的字符
     * @param color 特殊字符的色值
     * @param enabledClick 是否设置点击事件
     * @param underline 当前字段是否需要下划线 默认不需要
     */
    fun addText(special: String, color: Int, textSize: Int = 0, enabledClick: Boolean = false, underline: Boolean = false,bold: Boolean=false): TextBuilder {
        textView.addText(special, color, textSize, enabledClick, underline,bold)
        return this
    }

    /**
     * 将某个位置的文字替换成图片,默认将最后一个字替换成图片
     * 优化一下以上一步的最后位置为起始位置，调用了此方法 默认在最后追加两个空格
     * @param res 图片资源
     * @param start 图片所占开始位置
     * @param end 图片所占结束位置
     * @param enabledClick 是否设置点击事件
     * 点击后返回 图片的资源id 以此判断点击的位置
     */
    fun addImage(res: Int, start: Int = -1, end: Int = -1, enabledClick: Boolean = false): TextBuilder {
        textView.addImage(res, start, end, enabledClick)
        return this
    }

    /**
     * 为指定的文字设置特殊的背景色
     * 注意：如果设置的图片的高度大于文字的高度，背景的高度会以图片的高度为准
     * 如有需要可以重写drawBackGround方法，手动计算 top和bottom
     * @param resId 图片资源或者是颜色id
     * @param special 目标字符串
     * @param height 背景高度 默认使用view高度，如果设置了（如文字中追加了一张大图）等特殊部分，将以当前行最大高度为准
     * @param width 背景宽度
     * @param textColor 字符色值
     */
    fun addBackground(resId: Int, special: String, height: Int = 0, width: Int = 0, textColor: Int = 0): TextBuilder {
        textView.addBackground(resId, special, height, width, textColor)
        return this
    }

    fun setExpand(text: String, color: Int = 0): TextBuilder {
        textView.setExpand(text, color)
        return this
    }

    fun setShrink(text: String, color: Int = 0): TextBuilder {
        textView.setShrink(text, color)
        return this
    }

    fun setEllipsize(text: String, color: Int = 0): TextBuilder {
        textView.setEllipsize(text, color)
        return this
    }

    /***
     * 这个方法暂时不能和其他设置特殊字符串的方法一起使用，只能单独使用
     * 系统版本在4.1以上才适用
     * @param imgRes 追加在最后的图片
     * @param enabledClick 是否需要点击事件
     * @param underline 是否需要下划线
     * @param extraLength 额外追加的截取长度，比如用两个逗号替换成两个汉字这种情况就需要多截取几个长度
     *  在此处追加图片耦合度比较高应该拆分出来
     */
    fun createFoldText(enabledClick: Boolean = false, underline: Boolean = false, extraLength: Int = 0): TextBuilder {
        textView.createFoldText(enabledClick, underline, extraLength)
        return this
    }


    /**
     *设置关键字第一次出现的位置
     * 也可以指定关键字的位置
     */
    fun setFirstIndexOf(): TextBuilder {
        textView.setFirstIndexOf()
        return this
    }

    /**
     *设置关键字最后出现的位置
     */
    fun setLastIndexOf(): TextBuilder {
        textView.setLastIndexOf()
        return this
    }

    /**
     * 是否开启伸缩动画
     */
    fun disableAnim() {
        textView.mDisableAnim = false
    }

    fun enableAnim() {
        textView.mDisableAnim = true
    }

    /**
     * 设置完成
     */
    fun complete() {
        textView.complete()
    }
}