package com.baiiu.myapplication.module.ultraPtr;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.baiiu.myapplication.R;
import com.baiiu.myapplication.module.fastscrooll.SimpleTextAdapter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * auther: baiiu
 * time: 16/8/1 01 22:13
 * description:
 */
public class PtrPageFragment extends Fragment implements LoadingMoreScrollListener.OnLoadingMoreListener, PtrHandler {

    private PtrClassicFrameLayout mPtr;
    private RecyclerView mRecyclerView;
    private LoadingMoreScrollListener mLoadingMoreScrollListener;
    private SimpleTextAdapter mAdapter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ptr, container, false);

        mPtr = (PtrClassicFrameLayout) view.findViewById(R.id.ptr);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SimpleTextAdapter(getContext(), 0);
        mRecyclerView.setAdapter(mAdapter);

        //下拉刷新
        mPtr.setPtrHandler(this);
        //上啦加载
        mLoadingMoreScrollListener = new LoadingMoreScrollListener();
        mLoadingMoreScrollListener.setOnLoadingMoreListener(this);
        mRecyclerView.addOnScrollListener(mLoadingMoreScrollListener);

        mPtr.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= 16) {
                            mPtr.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            mPtr.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }

                        mPtr.autoRefresh();
                    }
                });


        return view;
    }

    @Override public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override public void run() {
                frame.refreshComplete();
                mAdapter.setCount(20);
            }
        }, 1000);
    }

    @Override public void loadingMore() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override public void run() {
                mLoadingMoreScrollListener.onLoadmoreComplete();
                mAdapter.setCount(mAdapter.getItemCount() + 20);
            }
        }, 1000);

    }

    @Override public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

}
