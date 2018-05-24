package com.ppjun.android.improject.app.utils;


import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Package :com.depeili.android.ziranren.util
 * Description :
 * Author :Rc3
 * Created at :2017/05/22 15:00.
 */

public class RxUtil {

    private static ObservableTransformer mObservableTransformer;

    static {
        mObservableTransformer = applySchedulers();
    }

    private static <T> ObservableTransformer<T, T> applySchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> applyMainSchedulers() {
        return mObservableTransformer;
    }

}
