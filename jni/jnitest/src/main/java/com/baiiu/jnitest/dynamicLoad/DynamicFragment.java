package com.baiiu.jnitest.dynamicLoad;

import android.view.View;
import android.widget.Button;

import com.baiiu.jnitest.R;
import com.baiiu.jnitest.base.BaseFragment;

/*
    动态注册
 */
public class DynamicFragment extends BaseFragment {
    private int mCount = 0;

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initOnCreateView() {
        final DynamicLoad dynamicLoad = new DynamicLoad();
        final Button button = mView.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mCount & 1) == 1) {
                    button.setText(dynamicLoad.getNativeString());
                } else {
                    button.setText(String.valueOf(dynamicLoad.sum(1, mCount)));
                }

                ++mCount;
            }
        });
    }
}
