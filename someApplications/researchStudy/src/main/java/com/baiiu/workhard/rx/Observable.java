package com.baiiu.workhard.rx;


/**
 * author: zhuzhe
 * time: 2018/12/17
 * description:
 */
class Observable<T> {

    private ObservableOnSubscribe<T> mObservableOnSubscribe;

    private Observable(ObservableOnSubscribe<T> observableOnSubscribe) {
        this.mObservableOnSubscribe = observableOnSubscribe;
    }

    // 创建型操符
    static <T> Observable<T> create(ObservableOnSubscribe<T> observableOnSubscribe) {
        return new Observable<>(observableOnSubscribe);
    }

    void subscribe(final Observer<T> observer) {
        observer.onSubscription();

        mObservableOnSubscribe.subscribe(observer);

        // 装饰者模式
//        mObservableOnSubscribe.subscribe(new Emitter<T>() {
//            @Override
//            public void onNext(T s) {
//                observer.onNext(s);
//            }
//
//            @Override
//            public void onComplete() {
//                observer.onComplete();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                observer.onError(e);
//            }
//        });
    }


    <R> Observable<R> map(final Function<T, R> function) {
        // 会返回一个新的Observable，持有新的mObservableOnSubscribe，可以被下游调用
        return new Observable<>(new ObservableOnSubscribe<R>() {
            @Override
            public void subscribe(final Emitter<R> emitter) {
                // 观察者会调用这块，新包装的ObservableMap会调用上游的subscribe
                // 传入新的emitter被上游调用，自己处理
                mObservableOnSubscribe.subscribe(new Emitter<T>() {
                    @Override
                    public void onNext(T t) {
                        emitter.onNext(function.apply(t));
                    }

                    @Override
                    public void onComplete() {
                        emitter.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        emitter.onError(e);
                    }
                });
            }
        });
    }

    Observable<T> filter(final Prediction<T> prediction) {
        return new Observable<>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(final Emitter<T> emitter) {
                mObservableOnSubscribe.subscribe(new Emitter<T>() {
                    @Override
                    public void onNext(T t) {
                        if (prediction.filter(t)) {
                            emitter.onNext(t);
                        }
                    }

                    @Override
                    public void onComplete() {
                        emitter.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        emitter.onError(e);
                    }
                });
            }
        });
    }


    Observable<T> subscribeOn(final Scheduler scheduler) {
        return new Observable<>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(final Emitter<T> emitter) {
                scheduler.schedule(new Runnable() {
                    @Override
                    public void run() {
                        mObservableOnSubscribe.subscribe(emitter);
                    }
                });
            }
        });
    }

    Observable<T> observeOn(final Scheduler scheduler) {
        return new Observable<>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(final Emitter<T> emitter) {
                mObservableOnSubscribe.subscribe(new Emitter<T>() {
                    @Override
                    public void onNext(final T t) {
                        scheduler.schedule(new Runnable() {
                            @Override
                            public void run() {
                                emitter.onNext(t);
                            }
                        });
                    }

                    @Override
                    public void onComplete() {
                        scheduler.schedule(new Runnable() {
                            @Override
                            public void run() {
                                emitter.onComplete();
                            }
                        });

                    }

                    @Override
                    public void onError(final Throwable e) {
                        scheduler.schedule(new Runnable() {
                            @Override
                            public void run() {
                                emitter.onError(e);
                            }
                        });

                    }
                });
            }
        });
    }

}
