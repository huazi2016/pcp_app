package com.huazi.jdemo.model;

import com.huazi.jdemo.base.model.BaseModel;
import com.huazi.jdemo.bean.square.TreeData;
import com.huazi.jdemo.contract.square.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/08
 * Time: 11:11
 */
public class TreeModel extends BaseModel implements Contract.ITreeModel {
    public TreeModel() {
        setCookies(false);
    }
    @Override
    public Observable<TreeData> loadTreeData() {
        return mApiServer.loadTreeData();
    }
}
