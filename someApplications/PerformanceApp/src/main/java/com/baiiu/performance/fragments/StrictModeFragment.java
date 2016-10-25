package com.baiiu.performance.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.baiiu.performance.BaseFragment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author: baiiu
 * date: on 16/10/25 14:26
 * description:
 */

public class StrictModeFragment extends BaseFragment {
    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LogUtil.d("哈哈执行了么StrictModeFragment");

        File filesDir = mContext.getFilesDir();
        File file = new File(filesDir, "a.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (int i = 0; i < 20; i++) {
                fileOutputStream.write("啊啊啊".getBytes());
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
