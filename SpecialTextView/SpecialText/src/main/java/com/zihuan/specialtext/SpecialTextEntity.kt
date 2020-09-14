package com.zihuan.specialtext

class SpecialTextEntity {
    constructor(special: String, color: Int, textSize: Int, enabledClick: Boolean, underline: Boolean) {
        this.special = special
        this.color = color
        this.textSize = textSize
        this.enabledClick = enabledClick
        this.underline = underline
    }

    /**
     * 图片资源
     */
    constructor(special: String, res: Int, start: Int, end: Int, enabledClick: Boolean) {
        this.special = special
        this.res = res
        this.start = start
        this.end = end
        this.enabledClick = enabledClick
        this.type = 1
    }

    //背景资源
    constructor(special: String, res: Int, start: Int, end: Int, height: Int = 0, width: Int = 0, textColor: Int = 0) {
        this.special = special
        this.res = res
        this.start = start
        this.end = end
        this.height = height
        this.width = width
        this.textColor = textColor
        this.type = 2
    }


    //类型
    var type = 0

    //特殊字符
    var special = ""
    var color = 0
    var textSize = 0
    var textColor = 0
    var enabledClick = false
    var underline = false
    var res = 0
    var start = 0
    var end = 0
    var height = 0
    var width = 0
}