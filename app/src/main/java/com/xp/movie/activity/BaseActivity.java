package com.xp.movie.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.xp.movie.utils.ActivityCollector;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by XP on 2015/4/12.
 */
public class BaseActivity extends ActionBarActivity implements SwipeBackActivityBase {
    private final String TAG = "BaseActivity";
    private SwipeBackActivityHelper mHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }
    @Override
    public void scrollToFinishActivity() {
       Utils.convertActivityToTranslucent(this);
       getSwipeBackLayout().scrollToFinishActivity();
    }

}
