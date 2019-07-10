package com.zihuan.specialtext

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

/***
 *将图片居中
 */
class SpecialImageSpan(arg0: Context, arg1: Int) : ImageSpan(arg0, arg1) {

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val d = drawable
        val rect = d.bounds
        if (fm != null) {
            val fmPaint = paint.fontMetricsInt
            val fontHeight = fmPaint.bottom - fmPaint.top
            val drHeight = rect.bottom - rect.top

            val top = drHeight / 2 - fontHeight / 4
            val bottom = drHeight / 2 + fontHeight / 4

            fm.ascent = -bottom
            fm.top = -bottom
            fm.bottom = top
            fm.descent = top
        }
        return rect.right
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int,
                      x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val b = drawable
        canvas.save()
        var transY = 0
        transY = (bottom - top - b.bounds.bottom) / 2 + top
        canvas.translate(x, transY.toFloat())
        b.draw(canvas)
        canvas.restore()
    }
}
