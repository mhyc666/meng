package com.wangdh.mengm.utils;

import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.wangdh.mengm.MyApplication;
import org.reactivestreams.Publisher;
import java.lang.reflect.Field;
import java.util.List;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * wdh
 */

public class RxUtils {
    public static <T> Flowable rxCreateDiskFlowable(final String key, final Class<T> clazz) {
        return Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
               // Log.i("toast", "get data from disk: key==" + key);
                String json = ACache.get(MyApplication.getsInstance()).getAsString(key);
               // Log.i("toast", "get data from disk finish , json==" + json);
                if (!TextUtils.isEmpty(json)) {
                    e.onNext(json);
                }
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER).map(new Function<String, T>() {
            @Override
            public T apply(String s) throws Exception {
                return new Gson().fromJson(s, clazz);
            }
        }).subscribeOn(Schedulers.io());
    }

    public static <T> FlowableTransformer<T, T> rxCacheListHelper(final String key) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .doOnNext(new Consumer<T>() {
                            @Override
                            public void accept(T t) throws Exception {
                                Schedulers.io().createWorker().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                      //  Log.i("toast", "get data from network finish ,start cache...");
                                        //通过反射获取List,再判空决定是否缓存
                                        if (t == null)
                                            return;
                                        Class clazz = t.getClass();
                                        Field[] fields = clazz.getFields();
                                        // Field[] fields = clazz.getDeclaredFields();
                                        for (Field field : fields) {
                                            String className = field.getType().getSimpleName();
                                          //  Log.i("toast", "className==" + className);
                                             //得到属性值
                                               if (className.equalsIgnoreCase("List")) {  //数据类型list
                                               try {
                                               List list = (List) field.get(t);
                                               Log.i("toast", "list==" + list);
                                              if (list != null && !list.isEmpty()) {
                                            ACache.get(MyApplication.getsInstance())
                                                    .put(key, new Gson().toJson(t, clazz));
                                            Log.i("toast", "cache finish");
                                              }
                                               } catch (IllegalAccessException e) {
                                                   e.printStackTrace();
                                               }
                                               }
                                        }
                                    }
                                });
                            }
                        }).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> rxCacheBeanHelper(final String key) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .doOnNext(new Consumer<T>() {
                            @Override
                            public void accept(T t) throws Exception {
                                Schedulers.io().createWorker().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                    //    Log.i("toast", "get data from network finish ,start cache...");
                                        ACache.get(MyApplication.getsInstance())
                                                .put(key, new Gson().toJson(t, t.getClass()));
                                  //      Log.i("toast", "cache finish");
                                    }

                                });
                            }
                        }).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}

//    RXjava1

//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                LogUtils.d("get data from disk: key==" + key);
//                String json = ACache.get(ReaderApplication.getsInstance()).getAsString(key);
//                LogUtils.d("get data from disk finish , json==" + json);
//                if (!TextUtils.isEmpty(json)) {
//                    subscriber.onNext(json);
//                }
//                subscriber.onCompleted();
//            }
//        })
//                .map(new Func1<String, T>() {
//                    @Override
//                    public T call(String s) {
//                        return new Gson().fromJson(s, clazz);
//                    }
//                })
//                .subscribeOn(Schedulers.io());
//    }

//    public static <T> Observable.Transformer<T, T> rxCacheListHelper(final String key) {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> observable) {
//                return observable
//                        .subscribeOn(Schedulers.io())//指定doOnNext执行线程是新线程
//                        .doOnNext(new Action1<T>() {
//                            @Override
//                            public void call(final T data) {
//                                Schedulers.io().createWorker().schedule(new Action0() {
//                                    @Override
//                                    public void call() {
//                                        LogUtils.d("get data from network finish ,start cache...");
//                                        //通过反射获取List,再判空决定是否缓存
//                                        if (data == null)
//                                            return;
//                                        Class clazz = data.getClass();
//                                        Field[] fields = clazz.getFields();
//                                        for (Field field : fields) {
//                                            String className = field.getType().getSimpleName();
//                                            // 得到属性值
//                                            if (className.equalsIgnoreCase("List")) {
//                                                try {
//                                                    List list = (List) field.get(data);
//                                                    LogUtils.d("list==" + list);
//                                                    if (list != null && !list.isEmpty()) {
//                                                        ACache.get(ReaderApplication.getsInstance())
//                                                                .put(key, new Gson().toJson(data, clazz));
//                                                        LogUtils.d("cache finish");
//                                                    }
//                                                } catch (IllegalAccessException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        }
//                                    }
//                                });
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }

//    public static <T> Observable.Transformer<T, T> rxCacheBeanHelper(final String key) {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> observable) {
//                return observable
//                        .subscribeOn(Schedulers.io())//指定doOnNext执行线程是新线程
//                        .doOnNext(new Action1<T>() {
//                            @Override
//                            public void call(final T data) {
//                                Schedulers.io().createWorker().schedule(new Action0() {
//                                    @Override
//                                    public void call() {
//                                        LogUtils.d("get data from network finish ,start cache...");
//                                        ACache.get(ReaderApplication.getsInstance())
//                                                .put(key, new Gson().toJson(data, data.getClass()));
//                                        LogUtils.d("cache finish");
//                                    }
//                                });
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
