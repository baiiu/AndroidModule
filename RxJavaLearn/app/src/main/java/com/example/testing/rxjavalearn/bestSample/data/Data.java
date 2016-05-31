// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.testing.rxjavalearn.bestSample.data;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.example.testing.rxjavalearn.bestSample.GankApi;
import com.example.testing.rxjavalearn.bestSample.GankBeautyResultToItemsMapper;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class Data {
    private static Data instance;
    private static final int DATA_SOURCE_MEMORY = 1;
    private static final int DATA_SOURCE_DISK = 2;
    private static final int DATA_SOURCE_NETWORK = 3;

    @IntDef({DATA_SOURCE_MEMORY, DATA_SOURCE_DISK, DATA_SOURCE_NETWORK})
    @interface DataSource {
    }

    BehaviorSubject<List<Item>> cache;

    private int dataSource;

    private Data() {
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private void setDataSource(@DataSource int dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSourceText() {
        String dataSourceTextRes;
        switch (dataSource) {
            case DATA_SOURCE_MEMORY:
                dataSourceTextRes = "内存";
                break;
            case DATA_SOURCE_DISK:
                dataSourceTextRes = "本地缓存";
                break;
            case DATA_SOURCE_NETWORK:
                dataSourceTextRes = "网络";
                break;
            default:
                dataSourceTextRes = "网络";
        }
        return dataSourceTextRes;
    }

    public void loadFromNetwork() {
        Retrofit retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OkHttpClient())

                //baseUrl
                .baseUrl("http://gank.io/api/")

                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())

                //RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                //创建
                .build();

        GankApi gankApi = retrofit.create(GankApi.class);

        gankApi.getBeauties(100, 1)
                .subscribeOn(Schedulers.io())
                .map(GankBeautyResultToItemsMapper.getInstance())
                .doOnNext(new Action1<List<Item>>() {
                    @Override
                    public void call(List<Item> items) {
                        Database.getInstance().writeItems(items);
                    }
                })
                .subscribe(new Action1<List<Item>>() {
                    @Override
                    public void call(List<Item> items) {
                        cache.onNext(items);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public Subscription subscribeData(@NonNull Observer<List<Item>> observer) {
        if (cache == null) {
            cache = BehaviorSubject.create();
            Observable.create(new Observable.OnSubscribe<List<Item>>() {
                @Override
                public void call(Subscriber<? super List<Item>> subscriber) {
                    //从本地文件
                    List<Item> items = Database.getInstance().readItems();
                    if (items == null) {
                        //从网络
                        setDataSource(DATA_SOURCE_NETWORK);
                        loadFromNetwork();
                    } else {
                        //本地有数据了,直接发送给调用者
                        setDataSource(DATA_SOURCE_DISK);
                        subscriber.onNext(items);
                    }
                }
            })
                    .subscribeOn(Schedulers.io()).subscribe(cache);
        } else {
            //缓存存在,读取本地
            setDataSource(DATA_SOURCE_MEMORY);
        }

        return cache.observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void clearMemoryCache() {
        cache = null;
    }

    public void clearMemoryAndDiskCache() {
        clearMemoryCache();
        Database.getInstance().delete();
    }
}
