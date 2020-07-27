package com.baiiu.workhard.rx;

/**
 * author: zhuzhe
 * time: 2018/12/18
 * description:
 */
class ZZMainTest {


    public static void main(String[] args) {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(Emitter<String> e) {
                        System.out.println("create: " + Thread.currentThread().getName());

                        e.onNext("1");
                        e.onNext("2");
//                        e.onNext("3");
//                        e.onNext("4");
                        e.onComplete();
                    }
                })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) {
                        System.out.println("apply: " + Thread.currentThread().getName());
                        int i = Integer.parseInt(s);
                        return i * i;
                    }
                })
                .filter(new Prediction<Integer>() {
                    @Override
                    public boolean filter(Integer integer) {
                        System.out.println("filter: " + integer + ", " + Thread.currentThread().getName());
                        return integer % 2 == 0;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscription() {
                        System.out.println("onSubscription: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Integer s) {
                        System.out.println("onNext:" + s + ", " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e.toString());
                    }
                });
    }

}
