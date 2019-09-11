package com.baiiu.workhard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import com.baiiu.library.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new BaseAdapter() {
            @Override public int getCount() {
                return 20;
            }

            @Override public Object getItem(int position) {
                return position;
            }

            @Override public long getItemId(int position) {
                return position;
            }

            @Override public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText(String.valueOf(position));
                textView.setPadding(0, 50, 0, 50);
                textView.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#22000000"));
                return textView;
            }
        });
    }

    public void onClick(View view) {
        Toast.makeText(this, "哈哈", Toast.LENGTH_SHORT)
                .show();
    }

    int currentX;
    int currentY;

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_DOWN) {
            LogUtil.d(currentX + ", " + currentY);
            currentX = (int) ev.getX();
            currentY = (int) ev.getY();

            getTouchedView(findViewById(android.R.id.content), currentX, currentY);
        }

        return super.dispatchTouchEvent(ev);
    }


    /*
        遍历，如果当前xy还在view内，并且view还有子view，继续向下遍历

        打印出一条事件分发链条
     */
    private void getTouchedView(View view, int x, int y) {
        if (view == null) return;
        LogUtil.d(view.toString());

        if (isViewUnder(view, x, y)) {
            LogUtil.e(view);
        }

        if (view instanceof ViewGroup) {

            ViewGroup viewGroup = (ViewGroup) view;

            for (int i = 0, childCount = viewGroup.getChildCount(); i < childCount; ++i) {
                getTouchedView(viewGroup.getChildAt(i), x, y);
            }
        }
    }


    public static boolean isViewUnder(View view, int x, int y) {
        if (view == null) {
            return false;
        }

        int[] arr = new int[2];
        view.getLocationInWindow(arr);

        return x >= arr[0]
                && x < arr[0] + view.getMeasuredWidth()
                && y >= arr[1]
                && y < arr[1] + view.getMeasuredHeight();
    }

}

