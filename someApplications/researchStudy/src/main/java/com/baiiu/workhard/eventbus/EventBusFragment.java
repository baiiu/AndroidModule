package com.baiiu.workhard.eventbus;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * author: zhuzhe
 * time: 2020-04-09
 * description:
 */
public class EventBusFragment extends Fragment {
    private List<Test> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            测试 SynchronousQueue，必现得执行完毕才能执行下一个
         */

        list = new ArrayList<>();
        list.add(new Test1());
        list.add(new Test1());
        list.add(new Test2());
        list.add(new Test3());
        for (Test test : list) {
            test.init();
        }


        EventBus.getDefault()
                .post("1");
    }
}
