package com.baiiu.myapplication.module.fastscrooll;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baiiu.myapplication.R;
import com.baiiu.myapplication.util.LogUtil;
import com.baiiu.myapplication.view.RecyclerViewDivider;

/**
 * author: baiiu
 * date: on 16/7/6 14:49
 * description:
 */
public class FastScrollFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private SimpleTextAdapter mAdapter;
    private int mVisibleCount;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fastscroll, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new FastScrollLinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new SimpleTextAdapter(getContext(), 500);
        recyclerView.setAdapter(mAdapter);

        recyclerView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override public void onGlobalLayout() {
                        recyclerView.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);

                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                        mVisibleCount = linearLayoutManager.findLastVisibleItemPosition()
                                - linearLayoutManager.findFirstVisibleItemPosition() + 1;
                        LogUtil.d("显示这么多个: " + mVisibleCount);
                    }
                });

        recyclerView.addItemDecoration(
                new RecyclerViewDivider(getContext(), LinearLayoutManager.VERTICAL, 20, Color.BLUE));


        view.findViewById(R.id.fast_top)
                .setOnClickListener(this);
        view.findViewById(R.id.fast_top_zhihuway)
                .setOnClickListener(this);
        view.findViewById(R.id.fast_end)
                .setOnClickListener(this);

        return view;
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fast_top_zhihuway:
                /*
                    仿知乎,先直接到一个位置,然后再滑动到顶部
                 */
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (firstVisibleItemPosition > mVisibleCount) {
                    recyclerView.scrollToPosition(mVisibleCount);
                }
                recyclerView.smoothScrollToPosition(0);

                break;

            /*
                这两个都是在LinearSmoothScroller里缩短了单位时间距离以达到减少时间,快速滑动
             */
            case R.id.fast_top:
                recyclerView.smoothScrollToPosition(10000);
                break;
            case R.id.fast_end:
                recyclerView.smoothScrollToPosition(0);
                break;
        }
    }
}
