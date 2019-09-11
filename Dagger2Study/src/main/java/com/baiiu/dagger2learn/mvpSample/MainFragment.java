package com.baiiu.dagger2learn.mvpSample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baiiu.dagger2learn.MainActivity;
import com.baiiu.dagger2learn.R;

import javax.inject.Inject;

/**
 * author: baiiu
 * date: on 16/6/13 11:33
 * description:
 */
public class MainFragment extends Fragment implements IView {

    private TextView textView;


    @Inject MainPresenter mMainPresenter;
    private MainFragmentComponent mainFragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainFragmentComponent = ((MainActivity) getActivity()).mainComponent.mainFragmentComponent();
        mainFragmentComponent.inject(this);
        mMainPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textView = (TextView) view.findViewById(R.id.textView);

        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMainPresenter.start();
            }
        }, 5000);

        return view;
    }

    @Override
    public void showPerson(String s) {
        textView.setText(s);
    }
}
