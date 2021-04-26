package com.huazi.jdemo.contract.mesharearticle;


import com.huazi.jdemo.base.interfaces.IBaseView;
import com.huazi.jdemo.bean.collect.Collect;
import com.huazi.jdemo.bean.db.Share;
import com.huazi.jdemo.bean.share.DeleteShare;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/19
 * Time: 22:16
 */
public class Contract {
    public interface IMeShareModel {

        Observable<List<Share>> loadShareArticle(int pageNum);

        Observable<List<Share>> refreshShareArticle(int pageNum);

        Observable<DeleteShare> deleteShareArticle(int articleId);

        /**
         * 收藏文章
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);
    }

    public interface IMeShareView extends IBaseView {

        void onLoadShareArticle(List<Share> shareList);

        void onRefreshShareArticle(List<Share> shareList);

        void onDeleteShareArticle(DeleteShare deleteShare, int articleId);

        /**
         * 收藏文章
         *
         * @param collect
         * @param articleId
         */
        void onCollect(Collect collect, int articleId);

        /**
         * 取消收藏文章
         *
         * @param collect
         * @param articleId
         */
        void onUnCollect(Collect collect, int articleId);
    }

    public interface IMeSharePresenter {
        void loadShareArticle(int pageNum);

        void refreshShareArticle(int pageNum);

        void deleteShareArticle(int articleId);

        /**
         * 收藏文章
         *
         * @param articleId
         */
        void collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         */
        void unCollect(int articleId);
    }
}
