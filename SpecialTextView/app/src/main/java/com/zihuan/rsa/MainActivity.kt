package com.zihuan.rsa

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.zihuan.specialtext.SpecialTextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SpecialTextView.SpecialTextClick {
    internal var a = "天地玄黄，宇宙洪荒,日月盈昃，辰宿列张。"
    internal var a1 = "天对地，雨对风，大陆对长空，山花对海树，赤日对苍穹。"
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
    var test2 = "\"阿黛侬国家美术馆由建筑大师Theodor Höijer(1843–1910)设计，该建筑在1887年春天竣工，并于1887年11月18日正式揭牌投入使用。阿黛侬是赫尔辛基最受欢迎的博物馆之一，它和另两个艺术馆一起组成了芬兰国家艺术画廊。该博物馆坐落于中央火车站广场南侧。博物馆收藏了从18世纪以来的各种芬兰艺术品，其中包含650件国际艺术品，其中之一便是著名的梵高1890年作品《瓦兹河畔欧韦的街道》。除此之外许多艺术家的作品都在该博物馆中展出，该建筑由政府房地产提供商Senate Properties拥有。\\\""
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_text3.setEnabledLog(true)
        setOneSpecialText()
        setManySpecialText()
        setEndText()
        special()
        connectionMode()
        setSpecialBackGround()
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
                .specialTextAppend(f, R.color.colorPrimary, true)//设置一个不包含的字符
                .setImage(R.mipmap.ic_launcher, enabledClick = true)
                .specialTextComplete()
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun setEndText() {
//        并不是特别优雅
        tv_text3.setWhole(test)
                .setEndText("more", R.color.color_fe68, R.mipmap.ic_bottom_arrow, true, true)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun specialClick(tag: String) {
        when (tag) {
            "more" -> {
            }
        }

        Toast.makeText(this, tag, Toast.LENGTH_SHORT).show()
    }


    fun connectionMode() {
        tv_text4.setConnectionMode()
                .specialConnectionAppend(b, R.color.colorPrimary)
                .specialConnectionAppend(c, R.color.colorAccent, true)
                .specialConnectionAppend(d, R.color.colorPrimaryDark, true)
                .specialConnectionAppend(e, R.color.color_fe68, true)
                .specialConnectionAppend("$f ", R.color.colorPrimary, true)
                .setImage(R.mipmap.ic_launcher, enabledClick = true)
                .specialTextComplete()
    }

    fun setSpecialBackGround() {
        tv_text5.setConnectionMode()
                .specialConnectionAppend(b, R.color.colorPrimary)
                .specialConnectionAppend(c, R.color.colorAccent, true)
                .specialConnectionAppend(d, R.color.colorPrimaryDark, true)
                .specialConnectionAppend(e, R.color.color_fe68, true)
                .specialConnectionAppend(a1, R.color.colorPrimary)
                .setSpecialBackGround(R.drawable.bg_date_true, a1)
//                .setSpecialBackGround(R.drawable.bg_date_true, a1, dip2px(14f))
                .specialTextComplete()
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
        tv_text6.text = spannableString
        tv_text6.movementMethod = LinkMovementMethod.getInstance()
    }

}
