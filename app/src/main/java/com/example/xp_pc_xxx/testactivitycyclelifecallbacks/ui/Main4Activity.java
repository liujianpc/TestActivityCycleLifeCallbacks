package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.R;
import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.utils.DataCollector;

import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private android.support.design.widget.AppBarLayout appbar;
    private android.support.v7.widget.RecyclerView recyclelist;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        this.recyclelist = (RecyclerView) findViewById(R.id.recycle_list);
        this.appbar = (AppBarLayout) findViewById(R.id.appbar);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclelist.setLayoutManager(manager);
        mData = initData();
        MyAdapter adapter = new MyAdapter(mData, getApplicationContext());
        recyclelist.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "this activity is hooked and viewId is:" + String.valueOf(view.getId()));
                Snackbar.make(view, view.getTag().toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                DataCollector.onViewClick(view);
            }
        });
    }

    private List<String> initData() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            mList.add(String.valueOf(i) + "---item");
        }
        return mList;
    }

}
