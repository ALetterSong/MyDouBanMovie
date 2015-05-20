package com.xp.movie.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.xp.movie.R;
import com.xp.movie.fragment.SettingFragment;

/**
 * Created by XP on 2015/5/7.
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //加载SettingFragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SettingFragment settingFragment = new SettingFragment();
        transaction.add(R.id.settingFragment, settingFragment);
        transaction.commit();
    }
}
