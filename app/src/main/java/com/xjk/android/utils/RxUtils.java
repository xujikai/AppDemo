package com.xjk.android.utils;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.xjk.android.bean.ApiBean;
import com.xjk.android.constant.Constants;
import com.xjk.android.data.api.ApiException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class RxUtils {

    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable<T> getObservable(T t) {
        return Observable.just(t).throttleFirst(Constants.DEFAULT_CLICK_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public static Observable<Object> getObservable(View view) {
        return RxView.clicks(view).throttleFirst(Constants.DEFAULT_CLICK_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public static <T> ObservableTransformer<ApiBean<T>, T> handleResult() {
        return new ObservableTransformer<ApiBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ApiBean<T>> baseModelObservable) {
                return baseModelObservable.flatMap(new Function<ApiBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(ApiBean<T> tBaseModel) throws Exception {
                        L.e("RxHelper---status：" + tBaseModel.getResult() + "，msg：" + tBaseModel.getMsg());
                        if (tBaseModel.getResult() == 0) {
                            return createData(tBaseModel.getData());
                        } else {
                            return Observable.error(new ApiException(tBaseModel.getMsg()));
                        }
                    }
                });
            }
        };
    }

    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(data);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    public static void disposable(DisposableObserver observer) {
        if (observer != null && !observer.isDisposed()) {
            observer.dispose();
        }
    }
}
