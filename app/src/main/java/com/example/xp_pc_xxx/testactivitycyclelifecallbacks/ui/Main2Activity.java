package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.R;

public class Main2Activity extends BaseActivity {

    private android.widget.Button button1;
    private android.widget.Button button2;
    private android.widget.Button button3;
    private android.widget.TextView text1;
    private android.widget.TextView text2;
    private android.widget.TextView text3;
    private android.widget.ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.imageview = (ImageView) findViewById(R.id.image_view);
        this.text3 = (TextView) findViewById(R.id.text_3);
        this.text2 = (TextView) findViewById(R.id.text_2);
        this.text1 = (TextView) findViewById(R.id.text_1);
        this.button3 = (Button) findViewById(R.id.button_3);
        this.button2 = (Button) findViewById(R.id.button_2);
        this.button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,v.getTag().toString(),Snackbar.LENGTH_LONG).setAction("action",null).show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,v.getTag().toString(),Snackbar.LENGTH_LONG).setAction("action",null).show();
            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,v.getTag().toString(),Snackbar.LENGTH_LONG).setAction("action",null).show();
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,v.getTag().toString(),Snackbar.LENGTH_LONG).setAction("action",null).show();
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,v.getTag().toString(),Snackbar.LENGTH_LONG).setAction("action",null).show();
            }
        });
    }
}
