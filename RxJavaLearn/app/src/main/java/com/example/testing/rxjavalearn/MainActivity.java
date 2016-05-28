package com.example.testing.rxjavalearn;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.testing.rxjavalearn.fragments.RetrofitRxFragment;
import com.example.testing.rxjavalearn.util.LogUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //firstLearn();
    //twoSamples();

    //转化符
    //getSupportFragmentManager().beginTransaction()
    //    .add(new ConvertFragment(), "convertFragment")
    //    .commit();

    //retrofit结合
    getSupportFragmentManager().beginTransaction()
        .add(new RetrofitRxFragment(), "convertFragment")
        .commit();
  }

  /**
   * 两个示例
   */
  private void twoSamples() {
    /*
      1. 打印字符串数据
     */
    String[] names = { "name1", "name2", "name2" };

    //创建被观察者
    Observable.from(names)
        //订阅
        .subscribe(
            //创建观察者,不完整定义回调
            new Action1<String>() {
              @Override public void call(String s) {
                LogUtil.d("the String is : " + s);
              }
            });

    /*
      2. 把指定ID的resource图片资源设置到ImageView的图片
     */
    final int id = R.mipmap.ic_launcher;
    final ImageView imageView = (ImageView) findViewById(R.id.imageView);

    Observable.create(new Observable.OnSubscribe<Drawable>() {
      @Override public void call(Subscriber<? super Drawable> subscriber) {
        subscriber.onNext(getResources().getDrawable(id));
      }
    }).subscribe(new Subscriber<Drawable>() {
      @Override public void onCompleted() {
        LogUtil.d("onComplete");
      }

      @Override public void onError(Throwable e) {
        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
      }

      @Override public void onNext(Drawable drawable) {
        imageView.setImageDrawable(drawable);
      }
    });
  }

  /**
   * 基础代码
   */
  private void firstLearn() {
    /*
      1. 创建观察者
     */

    //使用Observer创建
    Observer<String> observer = new Observer<String>() {

      @Override public void onCompleted() {
        LogUtil.d("onComplete");
      }

      @Override public void onError(Throwable e) {
        LogUtil.d("onError() " + e.toString());
      }

      @Override public void onNext(String s) {
        LogUtil.d("onNext() " + s);
      }
    };

    //使用Observer的实现类 Subscriber 创建
    Subscriber<String> subscriber = new Subscriber<String>() {

      @Override public void onStart() {
        LogUtil.d("onStart()");
      }

      @Override public void onCompleted() {
        LogUtil.d("onComplete");
      }

      @Override public void onError(Throwable e) {
        LogUtil.d("onError() " + e.toString());
      }

      @Override public void onNext(String s) {
        LogUtil.d("onNext() " + s);
      }
    };


    /*
      2. 创建被观察者,使用Observable
     */
    //Observable.OnSubscribe 当Observable被订阅时的回调方法,即会调用OnSubscribe.call()方法
    Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

      @Override public void call(Subscriber<? super String> subscriber) {
        //注意call()内的参数,是Subscriber,即是订阅者,所以此处回调订阅者的方法.
        //和普通的setOnClickListener一样的,是吧,满足某个条件,即回调订阅者的某方法

        subscriber.onNext("next 1");
        subscriber.onNext("next 2");
        subscriber.onNext("next 3");
        subscriber.onCompleted();
      }
    });

    /*
      3. 订阅
        Rx中是,被观察者订阅观察者
     */
    //observable.subscribe(observer);//看源码,把observer包装成为Subscriber对象
    observable.subscribe(subscriber);
  }
}
