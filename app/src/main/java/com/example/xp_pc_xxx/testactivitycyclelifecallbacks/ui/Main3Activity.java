package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.ui;

import android.os.Bundle;

import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.R;

public class Main3Activity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, new BaseFragment());
        transaction.commit();

    }
}
