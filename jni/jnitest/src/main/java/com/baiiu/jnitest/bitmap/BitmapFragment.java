package com.baiiu.jnitest.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.baiiu.jnitest.R;
import com.baiiu.jnitest.base.BaseFragment;

public class BitmapFragment extends BaseFragment {
    static {
        System.loadLibrary("bitmap-lib");
    }


    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_bitmap;
    }

    @Override
    protected void initOnCreateView() {
        final ImageView imageView = mView.findViewById(R.id.imageView);
        mView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                bitmap = callNativeMirrorBitmap(bitmap);
                imageView.setImageBitmap(bitmap);
            }
        });
    }


    private native Bitmap callNativeMirrorBitmap(Bitmap bitmap);


}
