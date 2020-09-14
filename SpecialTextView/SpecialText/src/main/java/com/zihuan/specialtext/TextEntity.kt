package com.zihuan.specialtext

class TextEntity {
        constructor(special: String, color: Int, textSize: Int, enabledClick: Boolean, underline: Boolean) {
            this.special = special
            this.color = color
            this.textSize = textSize
            this.enabledClick = enabledClick
            this.underline = underline
        }

        var special = ""
        var color = 0
        var textSize = 0
        var enabledClick = false
        var underline = false
    }