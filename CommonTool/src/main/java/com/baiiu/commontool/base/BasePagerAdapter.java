package com.baiiu.commontool.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePagerAdapter<T> extends PagerAdapter {

    public List<T> list;
    public int size;
    public Context mContext;

    public BasePagerAdapter(Context context, List<T> list) {
        super();
        this.mContext = context;
        setList(list);
    }

    public void setList(List<T> list) {
        if (list == null) {
            list = new ArrayList<T>();
        }

        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return size = list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = onCreateView(position);
        container.addView(view);
        return view;
    }

    /**
     * 初始化
     *
     * @param position
     * @return
     */
    public abstract View onCreateView(int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

}
