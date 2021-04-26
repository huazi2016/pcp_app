package com.huazi.jdemo.presenter.rank;

import com.huazi.jdemo.base.presenter.BasePresenter;
import com.huazi.jdemo.bean.db.Rank;
import com.huazi.jdemo.contract.rank.Contract;
import com.huazi.jdemo.model.RankModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/13
 * Time: 10:58
 */
public class RankPresenter extends BasePresenter<Contract.IRankView> implements Contract.IRankPResenter {
    Contract.IRankModel iRankModel;

    public RankPresenter() {
        iRankModel = new RankModel();
    }

    @Override
    public void loadRankData(int pageNum) {
        if (isViewAttached() && pageNum == 1) {
            getView().onLoading();
        }
        iRankModel.loadRankData(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Rank>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Rank> rankList) {
                        if (isViewAttached()) {
                            getView().onLoadRankData(rankList);
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
    public void refreshRankData(int pageNum) {
        iRankModel.refreshRankData(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Rank>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Rank> rankList) {
                        if (isViewAttached()) {
                            getView().onRefreshRankData(rankList);
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
