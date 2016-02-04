package com.xilingyuli.wordstext;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

/**
 * Created by xilingyuli on 2015/11/7.
 */
public class WordSwitcher {

    private SharedPreferences settingsPreferences;  //获取设置项
    private String[] words;  //单词列表
    private int index;  //上一单词位置

    WordSwitcher(Context context)
    {
        settingsPreferences = context.getSharedPreferences("WordSettings", Activity.MODE_PRIVATE);
        FileReader f = new FileReader(settingsPreferences.getString("path",null));
        words = f.read();
        f.closeFile();
        index = settingsPreferences.getInt("index",0);
    }
    public String nextWord()
    {
        if(words==null||words.length==0)
            return "";
        if(settingsPreferences.getBoolean("isRandom",false))
        {
            Random r = new Random();
            index = r.nextInt(words.length);
            return words[index];
        }
        else
            return words[(++index)%words.length];
    }
    public void save()
    {
        settingsPreferences.edit().putInt("index",index).apply();
    }
}
