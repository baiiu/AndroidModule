package com.example.testing.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baiiu.library.LogUtil;
import com.example.testing.myapplication.retrofit.http.OKHttpFactory;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: baiiu
 * date: on 17/6/30 11:57
 * description:
 */
public class OkHttpFragment extends Fragment {


    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = "https://api.github.com";

        //try {
        //run(url);
        test(url);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    private void run(String url) throws IOException {
        Request request = new Request.Builder().url(url)
                .build();

        OKHttpFactory.INSTANCE.getOkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override public void onFailure(Call call, IOException e) {

                    }

                    @Override public void onResponse(Call call, Response response) throws IOException {
                        LogUtil.d(response.body()
                                          .string());
                    }
                });
    }

    private void test(String url) {
        HttpUrl httpUrl = HttpUrl.parse(url)
                .newBuilder()
                .addQueryParameter("ak", "a")
                .build();

        Request build = new Request.Builder().url(httpUrl)
                .delete()
                .build();

        OKHttpFactory.INSTANCE.getOkHttpClient()
                .newCall(build)
                .enqueue(new Callback() {
                    @Override public void onFailure(Call call, IOException e) {
                        LogUtil.e(e.toString());
                    }

                    @Override public void onResponse(Call call, Response response) throws IOException {
                        LogUtil.d(response.body()
                                          .string());

                    }
                });

    }

}
