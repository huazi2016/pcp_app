package com.huazi.jdemo.presenter.me;

import com.huazi.jdemo.base.presenter.BasePresenter;
import com.huazi.jdemo.bean.me.IntegralData;
import com.huazi.jdemo.contract.me.Contract;
import com.huazi.jdemo.model.MeModel;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2019/12/31
 * Time: 14:19
 */
public class MePresenter extends BasePresenter<Contract.IMeView> implements Contract.IMePresenter {
    Contract.IMeModel iMeModel;
    public MePresenter() {
        iMeModel = new MeModel();
    }

    @Override
    public void loadIntegralData() {
        iMeModel.loadIntegralData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IntegralData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(IntegralData integrals) {
                        if (isViewAttached()) {
                            getView().onLoadIntegralData(integrals);
                            getView().onLoadSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void refreshIntegralData() {
        iMeModel.refreshIntegralData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IntegralData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(IntegralData integrals) {
                        if (isViewAttached()) {
                            getView().onRefreshIntegralData(integrals);
                            getView().onLoadSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
