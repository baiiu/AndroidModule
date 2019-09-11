package com.baiiu.commontool.base;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.baiiu.commontool.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baiiu on 15/11/20.
 * 用于上拉加载更多
 */
public abstract class BaseListAdapter<E> extends RecyclerView.Adapter<BaseViewHolder> {
    protected static final int TYPE_FOOTER = 99;

    public Context mContext;
    public List<E> mList;
    private FooterViewHolder footerViewHolder;


    public BaseListAdapter(Context context) {
        this(context, null);
    }

    public BaseListAdapter(Context context, List<E> list) {
        this.mContext = context;
        setList(list);
    }

    public void setList(List<E> list) {
        if (list == null) {
            list = new ArrayList<>(0);
        } else if (footerViewHolder != null && list.size() > 0) {
            bindFooter(list);
        }

        this.mList = list;

        notifyDataSetChanged();
    }

    public void addToFirst(E e) {
        if (e != null) {
            mList.add(0, e);
            notifyItemInserted(0);
        }
    }


    public void addToLast(List<E> list) {
        if (CommonUtil.isEmpty(list)) {
            return;
        }

        notifyItemRangeInserted(mList.size(), list.size());
        mList.addAll(list);
    }

    public void deletePosition(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public E getLastOne() {
        if (mList == null || mList.isEmpty()) {
            return null;
        }

        return mList.get(mList.size() - 1);
    }

    public List<E> getList() {
        return mList;
    }


    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return getFooterHolder();
        }

        return onCreateViewHolderInner(parent, viewType);
    }

    public abstract BaseViewHolder onCreateViewHolderInner(ViewGroup parent, int viewType);

    public FooterViewHolder getFooterHolder() {
        if (footerViewHolder == null) {
            footerViewHolder = new FooterViewHolder(mContext);
            footerViewHolder.getRootView().setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            setOnErrorClickListener();
        }

        return footerViewHolder;
    }

    private void setOnErrorClickListener() {
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_FOOTER) {
            if (mList != null && mList.size() > 0) {
                if (getFooterHolder().isHasLoadMore()) {
                    dispatchLoadMore();
                    return;
                }
            }
            return;
        }

        E data = mList.get(position);
        if (data == null) {
            return;
        }


        bindData(holder, data, position);
    }

    public void bindData(BaseViewHolder holder, E data, int position) {
        holder.bind(data);
    }


    public void dispatchLoadMore() {
    }

    public void loadMore(List<E> list) {
        bindFooter(list);
        addToLast(list);
    }

    public void bindFooter(List<E> list) {
        if (list == null) {
            getFooterHolder().bind(FooterViewHolder.ERROR);
        } else if (list.size() < 20) {
            getFooterHolder().bind(FooterViewHolder.NO_MORE);
        } else {
            getFooterHolder().bind(FooterViewHolder.HAS_MORE);
        }
    }


}
