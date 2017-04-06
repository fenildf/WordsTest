package com.xilingyuli.wordstext;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] themes = new int[]{R.style.AppTheme_Blue,R.style.AppTheme_Green};
        int[] res = new int[]{R.drawable.background1,R.drawable.background2};
        int kind = getSharedPreferences("WordSettings", MODE_PRIVATE).getInt("skin",0);
        setTheme(themes[kind]);
        setContentView(R.layout.activity_main);
        ((ImageView)findViewById(R.id.image)).setImageResource(res[kind]);
        Button startButton = (Button)findViewById(R.id.button4);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setClass(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
        Button settingButton = (Button)findViewById(R.id.button3);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setClass(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
