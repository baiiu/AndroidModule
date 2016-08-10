package com.example.testing.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.testing.myapplication.R;
import com.example.testing.myapplication.bean.Daily;
import com.example.testing.myapplication.bean.Story;
import com.example.testing.myapplication.retrofit.ApiFactory;
import com.example.testing.myapplication.util.LogUtil;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * auther: baiiu
 * time: 16/5/17 17 21:53
 * description:
 */
public class NewsListFragment extends ListFragment {

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String s = "http://news-at.zhihu.com/api/4/news/before/20160517";
        ApiFactory.getAnotherAPI()
                .getNewsList(s)
                .enqueue(new Callback<Daily>() {
                    @Override public void onResponse(Call<Daily> call, Response<Daily> response) {
                        List<Story> stories = response.body().stories;

                        List<String> list = new ArrayList<String>();
                        for (Story story : stories) {
                            list.add(story.title);
                        }

                        LogUtil.d(list.toString());

                        setListAdapter(
                                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list));
                    }

                    @Override public void onFailure(Call<Daily> call, Throwable t) {

                    }
                });
    }
}
