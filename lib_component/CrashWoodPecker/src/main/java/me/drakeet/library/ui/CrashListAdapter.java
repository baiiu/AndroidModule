/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 drakeet (drakeet.me@gmail.com)
 * http://drakeet.me
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.drakeet.library.ui;

import android.graphics.Typeface;
import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import me.drakeet.library.R;
import me.drakeet.library.StringStyleUtils;

/**
 * Created by drakeet(http://drakeet.me)
 * Date: 9/2/15 12:42
 */
class CrashListAdapter extends RecyclerView.Adapter<CrashListAdapter.ViewHolder> {

    public static final String TAG = "CrashListAdapter";

    private String[] traces;
    private String[] keys;
    private int selectedPosition = -1;


    CrashListAdapter(String[] traces, String[] keys) {
        this.traces = traces;
        this.keys = keys;
    }


    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_trace, parent, false);
        return new ViewHolder(v);
    }


    @Override public void onBindViewHolder(final ViewHolder holder, final int position) {
        String trace = traces[position];
        holder.log = trace;
        boolean startsWithAt = trace.startsWith("at ");
        if (trace != null) {
            setSpaceState(holder, /*show = */ startsWithAt);

            if (trace.startsWith("Caused by")) {
                holder.title.setTypeface(null, Typeface.BOLD);
                holder.title.setTextColor(0xdeffffff);
            } else {
                holder.title.setTypeface(null, Typeface.NORMAL);
                holder.title.setTextColor(0xffef4545);
            }

            boolean shouldHighlight = false;
            for (String key : keys) {
                if (startsWithAt && trace.contains(key)) {
                    if (selectedPosition == -1) {
                        selectedPosition = position;
                    }
                    int indexOfC = trace.indexOf("(");
                    if (indexOfC >= 0) {
                        String atPackage = trace.substring(0, indexOfC);
                        SpannableStringBuilder builder = new SpannableStringBuilder(
                            atPackage).append(
                            StringStyleUtils.format(holder.title.getContext(),
                                " " + trace.substring(indexOfC), R.style.LineTextAppearance));
                        CharSequence title = builder.subSequence(0, builder.length());
                        holder.title.setText(title);
                    } else {
                        holder.title.setText(trace);
                    }
                    shouldHighlight = true;
                    break;
                }
            }

            if (!shouldHighlight) {
                holder.itemView.setSelected(false);
                holder.title.setText(trace);
            }

            if (selectedPosition == position) {
                holder.itemView.setSelected(true);
            } else {
                holder.itemView.setSelected(false);
            }
        }
    }


    @Override public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override public int getItemCount() {
        return traces == null ? 0 : traces.length;
    }


    private void setSpaceState(ViewHolder holder, boolean show) {
        if (!show) {
            holder.space.setVisibility(View.GONE);
        } else {
            holder.space.setVisibility(View.VISIBLE);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        Space space;
        String log;


        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.trace);
            space = (Space) itemView.findViewById(R.id.space);
            itemView.setOnClickListener(this);
        }


        @Override public void onClick(View v) {
            if (log.endsWith("more")) {
                Toast.makeText(v.getContext(), "It is not supported temporarily.",
                    Toast.LENGTH_SHORT).show();
            }
        }
    }
}
