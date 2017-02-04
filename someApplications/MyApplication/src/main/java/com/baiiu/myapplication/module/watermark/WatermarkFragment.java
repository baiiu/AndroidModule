package com.baiiu.myapplication.module.watermark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baiiu.myapplication.R;
import com.baiiu.myapplication.module.fastscrooll.FastScrollLinearLayoutManager;
import com.baiiu.myapplication.module.fastscrooll.SimpleTextAdapter;
import com.baiiu.myapplication.module.ultraPtr.base.LoadingMoreScrollListener;

/**
 * author: baiiu
 * date: on 16/7/6 14:49
 * description:
 */
public class WatermarkFragment extends Fragment implements LoadingMoreScrollListener.OnLoadingMoreListener {

    private RecyclerView recyclerView;
    private SimpleTextAdapter mAdapter;
    private MyDecoration myDecoration;
    private LoadingMoreScrollListener loadingMoreScrollListener;
    private int totallyY = 0;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new FastScrollLinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        myDecoration = new MyDecoration();
        recyclerView.addItemDecoration(myDecoration);

        mAdapter = new SimpleTextAdapter(getContext(), 500);
        recyclerView.setAdapter(mAdapter);

        loadingMoreScrollListener = new LoadingMoreScrollListener();
        loadingMoreScrollListener.setOnLoadingMoreListener(this);
        recyclerView.addOnScrollListener(loadingMoreScrollListener);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totallyY += dy;
                myDecoration.setScrollY(totallyY);
            }
        });


        return view;
    }

    @Override public void loadingMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override public void run() {
                mAdapter.setCount(mAdapter.getItemCount() + 20);
                loadingMoreScrollListener.setLoading(true);
            }
        }, 1000);

    }
}
