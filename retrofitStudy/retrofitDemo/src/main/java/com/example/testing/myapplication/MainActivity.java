package com.example.testing.myapplication;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.testing.myapplication.fragments.OkHttpFragment;
import com.example.testing.myapplication.util.NetWorkReceiver;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBroadCast();

        //请求网络
        getSupportFragmentManager().beginTransaction()
                .add(getFragment(), "retrofit")
                .commit();

        //设置List
        //getSupportFragmentManager().beginTransaction()
        //        .add(android.R.id.content, new NewsListFragment(), "newsList")
        //        //.addToBackStack(null)
        //        .commit();

        //String url = "https://api.github.com/users/baiiu";
        //OkHttpUtils.get().url(url).build().execute(new StringCallback() {
        //  @Override public void onError(Call call, Exception e) {
        //    LogUtil.d(e.toString());
        //  }
        //
        //  @Override public void onResponse(String response) {
        //    LogUtil.d(response);
        //  }
        //});
    }

    public Fragment getFragment() {
        return

                new OkHttpFragment()

                //new RetrofitFragment()


                //new ConverterTestFragment()


                //new ErrorTestFragment()


                ;
    }


    private NetWorkReceiver netWorkReceiver;

    private void initBroadCast() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        netWorkReceiver = new NetWorkReceiver();
        registerReceiver(netWorkReceiver, filter);
    }

    @Override protected void onDestroy() {
        unregisterReceiver(netWorkReceiver);
        super.onDestroy();
    }

}
