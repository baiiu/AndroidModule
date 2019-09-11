package com.baiiu.performance.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.baiiu.library.LogUtil;
import com.baiiu.performance.BaseFragment;
import com.baiiu.performance.R;

/**
 * author: baiiu
 * date: on 16/10/25 14:31
 * description:
 */

public class LeakCanaryFragment extends BaseFragment {
    private static MOnClickListener mOnClickListener;

    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                       @Nullable Bundle savedInstanceState) {
        LogUtil.d("哈哈哈哈哈哈");

        View view = inflater.inflate(R.layout.fragment_leak_canary, container, false);
        mOnClickListener = new MOnClickListener();
        view.setOnClickListener(mOnClickListener);

        startAsyncTask();

        return view;
    }

    public static class MOnClickListener implements View.OnClickListener {
        private Context context;

        @Override public void onClick(View v) {
            context = v.getContext();
            Toast.makeText(context, "哈哈哈", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    /**
     * 内存泄露
     */
    void startAsyncTask() {
        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>() {
            @Override protected Void doInBackground(Void... params) {
                // Do some slow work in background
                SystemClock.sleep(20000);
                return null;
            }
        }.execute();
    }
}
