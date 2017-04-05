package com.xilingyuli.wordstext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity {

    private TextView wordOutput;  //正确单词输出框
    private TextView wordInf;  //单词词性释义显示
    private TextView tipView;  //信息提示框
    private EditText wordInput;  //用户输入框
    private WordSwitcher wordSwitcher;  //下一单词选择器
    private Button sureButton,nextButton;  //确定按钮、下一个按钮
    private int num,right,totalNum;  //已测试单词数、正确数量、总数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        wordOutput = (TextView)findViewById(R.id.textView);
        wordInf = (TextView)findViewById(R.id.textView3);
        tipView = (TextView)findViewById(R.id.textView2);
        wordInput = (EditText)findViewById(R.id.editText);
        nextButton = (Button)findViewById(R.id.button);
        sureButton = (Button)findViewById(R.id.button2);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordInput.setText("");
                if(num>0)  //有剩余单词，则显示下一个单词
                {
                    wordSwitcher.nextWord();
                    setText();
                }
                else  //显示测试结果
                {
                    showMark();
                }
            }
        });


        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(compareWord())  //输入正确
                {
                    right++;
                    tipView.setText("恭喜你，答对了~~~");
                }
                else  //输入错误
                {
                    tipView.setText("对不起，答错了QAQ");
                    wordOutput.setTextSize(30f);
                }
                sureButton.setEnabled(false);
                nextButton.setEnabled(true);
            }
        });

        wordSwitcher = new WordSwitcher(this);

        num = getSharedPreferences("WordSettings", Activity.MODE_PRIVATE).getInt("repeatTimes",15);
        totalNum = num;
        right = 0;

        setText();
    }

    @Override
    public void finish() {
        wordSwitcher.save();
        super.finish();
    }

    public boolean compareWord()
    {
        return (wordOutput.getText()+"").equals(wordInput.getText()+"");
    }

    public void setText()  //显示下一个单词，做出相应的界面更新
    {
        wordOutput.setTextSize(0f);
        tipView.setText("");
        String s = wordSwitcher.nextWord();
        if (s.equals("")||!s.contains(" "))
        {
            tipView.setText("无法从单词文件中读取单词\n请重新编辑单词文件");
            showMark();
            return;
        }
        wordOutput.setText(s.substring(0, s.indexOf(" ")).replace("_"," "));
        s = s.substring(s.indexOf(" ") + 1).replace(";", ";\n");
        wordInf.setText(s);
        num--;
        nextButton.setEnabled(false);
        sureButton.setEnabled(true);
    }

    private void showMark()
    {
        if(totalNum!=num)
            Toast.makeText(TestActivity.this,"本次测试"+(totalNum-num)+"个单词，\n正确"+right+"个，错误"+ (totalNum-num-right) +"个，正确率"+(((float)right)/(totalNum-num)),Toast.LENGTH_LONG).show();
        else
            Toast.makeText(TestActivity.this,"未完成测试",Toast.LENGTH_LONG).show();
        finish();  //结束程序
    }
}
