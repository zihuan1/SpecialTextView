package com.zihuan.rsa

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
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

/***
 * 分段点击
 */
class TextClickActivity : AppCompatActivity() {

    private val clickableSpan1 = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = resources.getColor(R.color.colorAccent)
            //设置是否有下划线
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            Toast.makeText(this@TextClickActivity, "这是测试点击1", Toast.LENGTH_SHORT).show()

        }
    }

    //    https://www.jianshu.com/p/f004300c6920
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<SpecialTextView>(R.id.tv_text)

        val str = "天地玄黄，宇宙洪荒,日月盈昃，辰宿列张。"
        val stringBuilder = SpannableStringBuilder(str)
        stringBuilder.setSpan(clickableSpan1, 3, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val span2 = TextViewSpan2()
        stringBuilder.setSpan(span2, 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = stringBuilder
        //一定要记得设置这个方法  不设置不起作用
        textView.movementMethod = LinkMovementMethod.getInstance()
    }


    private inner class TextViewSpan2 : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = resources.getColor(R.color.colorPrimary)
            //设置是否有下划线
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            //点击事件
            Toast.makeText(this@TextClickActivity, "这是测试点击2", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * 使用SpannableStringBuilder事件组合使用
     */
    private fun mode10() {
        val spannableString = SpannableStringBuilder()
        spannableString.append("暗影IV已经开始暴走了")
        //图片
        val imageSpan = ImageSpan(this, R.mipmap.ic_launcher)
        spannableString.setSpan(imageSpan, 2, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //点击事件
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(this@TextClickActivity, "请不要点我", Toast.LENGTH_SHORT).show()
            }
        }
        spannableString.setSpan(clickableSpan, 2, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //文字颜色
        val colorSpan = ForegroundColorSpan(Color.parseColor("#FFFFFF"))
        spannableString.setSpan(colorSpan, 5, 8, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //文字背景颜色
        val bgColorSpan = BackgroundColorSpan(Color.parseColor("#009ad6"))
        spannableString.setSpan(bgColorSpan, 5, 8, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        val textView = findViewById<View>(R.id.tv_text) as TextView
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}
