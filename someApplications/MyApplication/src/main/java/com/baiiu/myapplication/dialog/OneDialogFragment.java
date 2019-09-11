package com.baiiu.myapplication.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.baiiu.myapplication.util.LogUtil;

/**
 * author: baiiu
 * date: on 16/12/5 14:53
 * description:
 */

public class OneDialogFragment extends DialogFragment {

    @NonNull
    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        LogUtil.d("onDismiss");
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(android.R.layout.simple_list_item_1, container, false);
        TextView textView = (TextView) view;
        textView.setText("哈哈哈哈");
        textView.setTextSize(22);
        textView.setBackgroundColor(Color.BLUE);
        return view;
    }
}
