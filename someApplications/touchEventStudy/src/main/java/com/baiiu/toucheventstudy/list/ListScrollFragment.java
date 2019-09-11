package com.baiiu.toucheventstudy.list;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baiiu.toucheventstudy.R;
import com.baiiu.toucheventstudy.base.BaseFragment;

import butterknife.BindView;

/**
 * author: baiiu
 * date: on 16/7/6 14:49
 * description:
 */
public class ListScrollFragment extends BaseFragment {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @Override protected int provideLayoutId() {
        return R.layout.fragment_fastscroll;
    }

    @Override protected void initOnCreateView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        SimpleTextAdapter mAdapter = new SimpleTextAdapter(getContext(), 500);
        recyclerView.setAdapter(mAdapter);

    }

}
