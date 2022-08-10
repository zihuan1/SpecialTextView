package com.zihuan.rsa

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.zihuan.specialtext.SpecialTextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SpecialTextView.SpecialTextClick {
    private var a = "天地玄黄,宇宙洪荒,日月盈昃,辰宿列张 —"
    private var a1 = "天对地，雨对风，大陆对长空，山花对海树，赤日对苍穹。"
    private var xh = "玄黄"
    private var hh = "洪荒"
    private var yz = "      盈昃"
    private var cs = "辰宿"
    private var lz = "列张"
    private var f = "蛤蛤"
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

        var a = "你好哈哈ok吗ok"
//        a = StringBuilder(a)
//                .insert(3, "a").toString()
        Log.e("测试", a.indexOf("ok").toString() + "---" + a.lastIndexOf("ok").toString())
//        setOneSpecialText()
//        setManySpecialText()
        funConnectionMode()
//        funSpecialBackGround()
        setEndText()
        special()
    }

    //目前已知的问题1 如果把目标字符设置为可点击，再再后面追加可点击的图片，如果图片大于一个字符的宽度
    // 那么靠近特殊字符一次的图片的一半宽度被视为目标字符的点击区域（如 tv_text2 后面图片点击左半边会响应文字的事件）

    //设置单个
    private fun setOneSpecialText() {
        tv_text1.setSingleText(a, xh, R.color.colorPrimary, 30, enabledClick = true, underline = true)
//        val html = "<html><font color=\"#666666\" size=\"10\">忘记密码?" +
//                "</font><font color=\"#0000ff\">联系客服</font></html>"
//        tv_text1.setText(Html.fromHtml(html))
    }

    //连续设置
    private fun setManySpecialText() {
        tv_text2.setMultipleText(a)
                .addText(xh, R.color.colorPrimary, enabledClick = true, underline = true)
                .addImage(R.mipmap.ic_bottom_arrow, enabledClick = true)
                .addText(hh, R.color.colorAccent, enabledClick = true, underline = true)
                .addImage(R.mipmap.ic_launcher, enabledClick = true)
                .addText(yz, R.color.colorPrimaryDark)
//                .addText(cs, R.color.colorPrimaryDark, enabledClick = true, underline = true)
                .addText(cs + lz, R.color.color_fe68, enabledClick = true, underline = true)
                .addText("—", R.color.colorPrimaryDark, underline = true)
                .addImage(R.mipmap.ic_27, enabledClick = true)
                .addText(f, R.color.colorPrimary)//设置一个不包含的字符 测试
                .complete()
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun setEndText() {
//        val text = "哈哈哈你们的话说"
        val text = "哈哈哈你们的话说，啊哈哈哈尔滨工业大学威海公安局部解剖学教室里面前面前面前面前面前面前面前面前两天时间段视频呢么事了没办法的吗丁啉发给我打电话呢么。哈哈哈你们的话说，啊哈哈哈尔滨工业大学威海公安局部解剖学教室里面前面前面前面前面前面前面前面前两天时间段视频呢么事了没办法的吗丁啉发给我打电话呢么。\\n哈哈哈你们的话说，啊哈哈哈尔滨工业大学威海公安局部解剖学教室里面前面前面前面前面前面前面前面前两天时间段视频呢么事了没办法的吗丁啉发给我打电话呢么。哈哈哈你们的话说，啊哈哈哈尔滨工业大学威海公安局部解剖学教室里面前面前面前面前面前面前面前面前两天时间段视频呢么事了没办法的吗丁啉发给我打电话呢么。"
        tv_text3.setMultipleText(text)
                .setExpand("展开", R.color.color_fe68)
                .setShrink("收起", R.color.color_fe68)
                .setEllipsize("…")
                .createFoldText( true, true)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun specialClick(tag: String) {
        when (tag) {
            "more" -> {
            }
        }

        Toast.makeText(this, tag, Toast.LENGTH_SHORT).show()
    }


    private fun funConnectionMode() {
        tv_text4.setMultipleText()
                .addText(" $xh", R.color.colorPrimary)
                .addText(hh, R.color.colorAccent, enabledClick = true)
                .addText(yz, R.color.colorAccent, 10, enabledClick = true)
                .addText(lz, R.color.color_fe68, enabledClick = true)
//                .addImage(R.mipmap.ic_bottom_arrow)
                .addText("$f ", R.color.colorPrimary, 10, enabledClick = true)
                .addImage(R.mipmap.ic_27, 0, 1)
                .addBackground(R.mipmap.ic_27, yz, 44, 140, R.color.color_fe68)
                .complete()
    }

    private fun funSpecialBackGround() {
        tv_text5.setMultipleText()
                .addText(xh, R.color.colorPrimary)
                .addText(hh, R.color.colorAccent, enabledClick = true)
                .addText(yz, R.color.colorPrimaryDark, enabledClick = true)
                .addText(lz, R.color.color_fe68, enabledClick = true)
                .addText(a1, R.color.colorPrimary)
                .addBackground(R.drawable.bg_date_true, lz)
                .complete()
    }

    /***
     * 原生使用示例
     */
    private fun special() {
        val spannableString = SpannableStringBuilder()
        spannableString.append(a)
        spannableString.append(a1)

        //文字颜色
        val colorSpan = ForegroundColorSpan(Color.parseColor("#FFFFFF"))
        spannableString.setSpan(colorSpan, 5, 9, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        //图片
        val imageSpan = ImageSpan(this, R.mipmap.ic_launcher)
        spannableString.setSpan(imageSpan, 20, 21, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //点击事件
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(this@MainActivity, "点击图片", Toast.LENGTH_SHORT).show()
            }
        }
        spannableString.setSpan(clickableSpan, 20, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan2 = object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(this@MainActivity, "文字", Toast.LENGTH_SHORT).show()
            }
        }
        spannableString.setSpan(clickableSpan2, 10, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        //文字背景颜色
        val bgColorSpan = BackgroundColorSpan(Color.parseColor("#009ad6"))
        spannableString.setSpan(bgColorSpan, 5, 9, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        val colorSpan1 = ForegroundColorSpan(Color.parseColor("#FFFFFF"))
        spannableString.setSpan(colorSpan1, 31, 33, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //文字背景颜色
        val bgColorSpan1 = BackgroundColorSpan(Color.parseColor("#009ad6"))
        spannableString.setSpan(bgColorSpan1, 31, 33, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        tv_text6.text = spannableString
        tv_text6.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun getResources(): Resources? {
        //禁止app字体大小跟随系统字体大小调节
        val resources: Resources? = super.getResources()
        if (resources != null && resources.getConfiguration().fontScale !== 1.0f) {
            val configuration: Configuration = resources.getConfiguration()
            configuration.fontScale = 1.0f
            resources.updateConfiguration(configuration, resources.getDisplayMetrics())
        }
        return resources
    }

}
