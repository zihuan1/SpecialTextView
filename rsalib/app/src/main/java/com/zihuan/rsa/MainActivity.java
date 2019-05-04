package com.zihuan.rsa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.zihuan.specialtext.SpecialTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String a = "天地玄黄，宇宙洪荒,日月盈昃，辰宿列张。";
        String b = "玄黄";
        String c = "洪荒";
        String d = "日月";
        String e = "列张";
        String f = "蛤蛤";
        SpecialTextView tv_special = findViewById(R.id.tv_text);
//        tv_special.setSpecialText(a, R.color.colorPrimary, b);
        tv_special.setWhole(a)
                .specialTextAppend(b, R.color.colorPrimary)
                .specialTextAppend(c, R.color.colorAccent)
                .specialTextAppend(d, R.color.colorPrimaryDark)
                .specialTextAppend(e, R.color.colorPrimary)
                .specialTextAppend(f, R.color.colorPrimary)
                .specialTextComplete();
//        String str="123456789";
//        tv_special.setSpecialText(str,"000");
//        tv_special.setText(str);
    }
}
