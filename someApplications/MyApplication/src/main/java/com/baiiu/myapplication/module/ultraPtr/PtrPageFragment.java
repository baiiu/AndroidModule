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
import android.widget.Toast;
import com.baiiu.myapplication.R;
import com.baiiu.myapplication.module.ultraPtr.base.FooterViewHolder;
import com.baiiu.myapplication.module.ultraPtr.base.LoadFrameLayout;
import com.baiiu.myapplication.module.ultraPtr.base.LoadingMoreScrollListener;
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

    PtrClassicFrameLayout mPtr;
    RecyclerView mRecyclerView;
    LoadFrameLayout mLoadFrameLayout;

    private LoadingMoreScrollListener mLoadingMoreScrollListener;
    private SimpleTextAdapterM mAdapter;


    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ptr, container, false);

        mLoadFrameLayout = (LoadFrameLayout) view.findViewById(R.id.loadFrameLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mPtr = (PtrClassicFrameLayout) view.findViewById(R.id.ptr);



        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new SimpleTextAdapterM(getContext(), 0);
        mAdapter.bindFooter(FooterViewHolder.HAS_MORE);
        mAdapter.setOnErrorClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mAdapter.bindFooter(FooterViewHolder.HAS_MORE);
                loadingMore();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mLoadFrameLayout.setOnErrorClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mLoadFrameLayout.bind(LoadFrameLayout.CONTENT);

                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT)
                        .show();

                mPtr.showLoadingIndicator();
                mPtr.postDelayed(new Runnable() {
                    @Override public void run() {
                        mPtr.refreshComplete();
                        ok();
                    }
                }, 1000);
            }
        });


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

        //SwipeRefreshLayout使用方式
        //final SwipeRefreshLayout s = new SwipeRefreshLayout(getContext());
        //s.setRefreshing(true);
        //s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        //    @Override public void onRefresh() {
        //        s.setRefreshing(false);//即onRefreshComplete()
        //    }
        //});

        return view;
    }

    @Override public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override public void run() {
                frame.refreshComplete();

                //testEmpty();
                testError();
                //ok();

            }
        }, 1000);
    }

    private void ok() {
        mAdapter.setCount(20);
    }

    private void testEmpty() {
        mLoadFrameLayout.bind(LoadFrameLayout.EMPTY);
    }

    private void testError() {
        mLoadFrameLayout.bind(LoadFrameLayout.ERROR);
    }


    private int time;

    @Override public void loadingMore() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override public void run() {
                mAdapter.setCount(mAdapter.getInnerItemCount() + 20);

                ++time;
                if (time == 2) {
                    mAdapter.bindFooter(FooterViewHolder.ERROR);
                } else if (time == 4) {
                    mAdapter.bindFooter(FooterViewHolder.NO_MORE);
                } else {
                    mLoadingMoreScrollListener.onLoadMoreComplete();
                    mAdapter.bindFooter(FooterViewHolder.HAS_MORE);
                }

            }
        }, 1000);
    }

    @Override public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

}
