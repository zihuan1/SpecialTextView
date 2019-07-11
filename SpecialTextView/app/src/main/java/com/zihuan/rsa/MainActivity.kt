package com.zihuan.rsa

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.zihuan.specialtext.SpecialTextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SpecialTextView.SpecialTextClick {
    internal var a = "天地玄黄，宇宙洪荒,日月盈昃，辰宿列张。"
    var a1 = "天对地，雨对风，大陆对长空，山花对海树，赤日对苍穹。"
    internal var b = "玄黄"
    internal var c = "洪荒"
    internal var d = "盈昃"
    internal var e = "列张"
    internal var f = "蛤蛤"
    var g = "更多"
    var test = "\"Clearly you can bow your head, why should I cry?\"\n" +
            "\n" +
            "\"How can the person who disappoints you disappoint you only once?\"\n" +
            "\n" +
            "Last year, the interactive device exhibition of the Gyroscopic History Museum was held in Beijing and Tianjin at the same time. In less than a week, we received nearly 3,000 private letters and hundreds of love relics. Nine days, nearly 20,000 + people entered the pavilion.\n" +
            "\n" +
            "After six months of careful polishing, the gyroscopic History Museum has returned and opened the 100-city tour exhibition. This time, we will bring the \"gyroscopic history museum\" to more cities."

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_text3.setEnabledLog(true)
        setOneSpecialText()
        setManySpecialText()
        setEndText()
//        special()
    }

    //设置单个
    private fun setOneSpecialText() {
        tv_text1.setSpecialText(a, b, R.color.colorPrimary)
    }

    //连续设置
    private fun setManySpecialText() {
        tv_text2.setWhole(a)
                .specialTextAppend(b, R.color.colorPrimary)
                .specialTextAppend(c, R.color.colorAccent, true)
                .specialTextAppend(d, R.color.colorPrimaryDark, true)
                .specialTextAppend(e, R.color.color_fe68, true)
                .specialTextAppend(f, R.color.colorPrimary, true)
                .setImage(R.mipmap.ic_launcher, enabledClick = true)
                .specialTextComplete()
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun setEndText() {
//        缺少点击展开功能和动画
        tv_text3.setWhole(test)
                .setEndText("more", R.color.color_fe68, R.mipmap.ic_bottom_arrow, true, true)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun specialClick(tag: String) {
        when (tag) {
            "more" -> {
//                var line = tv_text3.maxLines
//                if (line == 5) {
//                    tv_text3.text = test
//                    tv_text3.post {
//                        line = tv_text3.lineCount
//                        tv_text3.maxLines = line
//                        tv_text3.setWhole(test)
//                                .setEndText("more", R.color.color_fe68, R.mipmap.ic_bottom_arrow, true, true)
//                    }
//                } else {
//                    line = 5
//                    tv_text3.maxLines = line
//                    tv_text3.setWhole(test)
//                            .setEndText("more", R.color.color_fe68, R.mipmap.ic_bottom_arrow, true, true)
//
//                }
            }
        }

        Toast.makeText(this, tag, Toast.LENGTH_SHORT).show()
    }

    /***
     *使用示例
     */
    fun special() {
        val spannableString = SpannableStringBuilder()
        spannableString.append(a)
        spannableString.append(a1)
        //图片
        val imageSpan = ImageSpan(this, R.mipmap.ic_launcher)
        spannableString.setSpan(imageSpan, 2, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //点击事件
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(this@MainActivity, "点击图片", Toast.LENGTH_SHORT).show()
            }
        }
        spannableString.setSpan(clickableSpan, 2, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //文字颜色
        val colorSpan = ForegroundColorSpan(Color.parseColor("#FFFFFF"))
        spannableString.setSpan(colorSpan, 5, 9, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //文字背景颜色
        val bgColorSpan = BackgroundColorSpan(Color.parseColor("#009ad6"))
        spannableString.setSpan(bgColorSpan, 5, 9, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        tv_text4.text = spannableString
        tv_text4.movementMethod = LinkMovementMethod.getInstance()
    }
}
