package com.baiiu.commontool.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.baiiu.commontool.R;
import com.baiiu.commontool.view.SwipeBackLayout;

/**
 * Created by baiiu on 15/11/16.
 * Base
 */
public abstract class BaseActivity extends AppCompatActivity implements SwipeBackLayout.SwipeBackListener {

    public final String volleyTag = getClass().getName();

    @Nullable @BindView(R.id.toolbar) public Toolbar toolbar;

    public TextView toolbar_title;

    protected ActionBar actionBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());

        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            actionBar = getSupportActionBar();
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(canSwipeBack);
                initActionBar(actionBar);
            }
        }

        initOnCreate(savedInstanceState);
    }


    public abstract int provideLayoutId();

    protected void initActionBar(ActionBar actionBar) {
    }

    protected abstract void initOnCreate(Bundle savedInstanceState);

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (toolbar_title != null) {
            toolbar_title.setText(title);
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    //===============================swipeBack,在ProvideLayoutId前调用==============================================
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    private boolean canSwipeBack = false;

    public void setCanSwipeBack(boolean canSwipeBack) {
        this.canSwipeBack = canSwipeBack;
    }

    @Override public void setContentView(int layoutResID) {
        if (canSwipeBack) {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this)
                    .inflate(layoutResID, null);
            swipeBackLayout.addView(view);
        } else {
            super.setContentView(layoutResID);
        }
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setOnSwipeBackListener(this);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.black_p50));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                                                             RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        return container;
    }


    @Override public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
        ivShadow.setAlpha(1 - fractionScreen);
    }

}
