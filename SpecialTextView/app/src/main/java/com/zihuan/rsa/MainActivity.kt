package com.zihuan.rsa

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

import com.zihuan.specialtext.SpecialTextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SpecialTextView.SpecialTextClick {
    internal var a = "天地玄黄，宇宙洪荒,日月盈昃，辰宿列张。天对地，雨对风，大陆对长空，山花对海树，赤日对苍穹。"
    internal var b = "玄黄"
    internal var c = "洪荒"
    internal var d = "盈昃"
    internal var e = "列张"
    internal var f = "蛤蛤"

    var g = "更多"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setOneSpecialText()
//        setManySpecialText()
//        setSpecialAndClick()
        setManySpecialText()
        tv_text.post {
            Log.e("行数和当前字符实际宽度", tv_text.lineCount.toString() + " " + tv_text.paint.measureText(a))
            Log.e("当前行数所占宽度", tv_text.layout.getLineWidth(0).toString())
            Log.e("当前行数字符详情", a.substring(tv_text.layout.getLineStart(0),tv_text.layout.getLineEnd(0)))
        }
    }

    //设置单个
    private fun setOneSpecialText() {
        tv_text.setSpecialText(a, b, R.color.colorPrimary)
    }

    //连续设置
    private fun setManySpecialText() {
        tv_text.setWhole(a)
                .specialTextAppend(b, R.color.colorPrimary)
                .specialTextAppend(c, R.color.colorAccent)
                .specialTextAppend(d, R.color.colorPrimaryDark)
                .specialTextAppend(e, R.color.color_fe68)
                .specialTextAppend(f, R.color.colorPrimary)
//                .setImage(R.mipmap.ic_launcher)
                .setEndText(g, R.color.color_fe68)
                .specialTextComplete()
    }

    //分段点击
    private fun setSpecialAndClick() {
        tv_text.setWhole(a)
                .setSpecialListener(this)
                .setSpecialTextAndClick(b, R.color.colorPrimary, false)
                .setSpecialTextAndClick(c, R.color.colorAccent, true)
                .specialTextComplete()
    }

    override fun specialClick(tag: String) {
        Toast.makeText(this, tag, Toast.LENGTH_SHORT).show()

    }
}
