package com.zihuan.specialtext

class SpecialTextEntity {
    val TYPE_TEXT = 0
    val TYPE_IMAGE = 1
    val TYPE_BACKGROUND = 2

    constructor(special: String, index: Int) {
        this.special = special
        currentIndex = index
    }

    //类型
    var type = 0

    //当前的位置
    var currentIndex = -1

    //特殊字符
    var special = ""
    var color = 0
    var textSize = 0
    var textColor = 0
    var enabledClick = false
    var underline = false
    var bold = false
    var res = 0

    //是否由系统设置的起始位置
    var startAuto = true
    var start = -1
    var end = -1
    var height = 0
    var width = 0
}