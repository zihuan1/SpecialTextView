package com.zihuan.rsa;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zihuan.specialtext.SpecialTextView;

/***
 * 分段点击
 */
public class TextClickActivity extends AppCompatActivity {
    //    https://www.jianshu.com/p/f004300c6920
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpecialTextView textView = findViewById(R.id.tv_text);

        String str = "天地玄黄，宇宙洪荒,日月盈昃，辰宿列张。";
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(str);
        stringBuilder.setSpan(clickableSpan1, 3, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextViewSpan2 span2 = new TextViewSpan2();
        stringBuilder.setSpan(span2, 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(stringBuilder);
        //一定要记得设置这个方法  不设置不起作用
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private ClickableSpan clickableSpan1 = new ClickableSpan() {
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.colorAccent));
            //设置是否有下划线
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(@NonNull View widget) {
            Toast.makeText(TextClickActivity.this, "这是测试点击1", Toast.LENGTH_SHORT).show();

        }
    };


    private class TextViewSpan2 extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.colorPrimary));
            //设置是否有下划线
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            //点击事件
            Toast.makeText(TextClickActivity.this, "这是测试点击2", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 使用SpannableStringBuilder事件组合使用
     */
    private void mode10() {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append("暗影IV已经开始暴走了");
        //图片
        ImageSpan imageSpan = new ImageSpan(this, R.mipmap.ic_launcher);
        spannableString.setSpan(imageSpan, 2, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TextClickActivity.this, "请不要点我", Toast.LENGTH_SHORT).show();
            }
        };
        spannableString.setSpan(clickableSpan, 2, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //文字颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FFFFFF"));
        spannableString.setSpan(colorSpan, 5, 8, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //文字背景颜色
        BackgroundColorSpan bgColorSpan = new BackgroundColorSpan(Color.parseColor("#009ad6"));
        spannableString.setSpan(bgColorSpan, 5, 8, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        TextView textView = (TextView) findViewById(R.id.tv_text);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
