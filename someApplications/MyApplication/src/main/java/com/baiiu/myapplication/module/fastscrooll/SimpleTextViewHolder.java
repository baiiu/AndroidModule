package com.baiiu.myapplication.module.fastscrooll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.baiiu.myapplication.module.ultraPtr.base.BaseViewHolder;

/**
 * author: baiiu
 * date: on 16/6/27 17:38
 * description:
 */
public class SimpleTextViewHolder extends BaseViewHolder<Integer> {

    public SimpleTextViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context)
                      .inflate(android.R.layout.simple_list_item_1, parent, false));

        itemView.setPadding(0, 100, 0, 100);

    }

    @Override
    public void bind(Integer position) {

        ((TextView) itemView).setText("position: " + String.valueOf(position));


        //if (position % 2 == 0) {
        //    itemView.setBackgroundResource(android.R.color.darker_gray);
        //} else {
        //    itemView.setBackgroundResource(android.R.color.background_light);
        //}
    }

    public void bind(String text) {
        ((TextView) itemView).setText(text);
    }

}
