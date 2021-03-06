package com.pcp.myapp.net;

import com.pcp.myapp.bean.ChatListBo;
import com.pcp.myapp.bean.ChatMsgBo;
import com.pcp.myapp.bean.LoginBo;
import com.pcp.myapp.bean.MessageListBo;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.bean.SearchTestBo;
import com.pcp.myapp.net.contract.MainContract;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainContract.View> {

    private Disposable disposable;
    private DataManager dataManager;

    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void login(String username, String password, NetCallBack<LoginBo> callBack) {
        Observable<BaseResponse<LoginBo>> observable = dataManager.login(username, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<LoginBo> response) {
                        if (response.getErrorCode() != 0) {
                            callBack.onLoadFailed(response.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(response.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //getView().showError(e.getMessage());
                        callBack.onLoadFailed("服务异常");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void register(String username, String password, String role, NetCallBack<LoginBo> callBack) {
        Observable<BaseResponse<LoginBo>> observable = dataManager.register(username, password, role);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<LoginBo> response) {
                        if (response.getErrorCode() != 0) {
                            callBack.onLoadFailed(response.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(response.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //getView().showError(e.getMessage());
                        callBack.onLoadFailed("服务异常");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getCategoryList(NetCallBack<List<String>> callBack) {
        Observable<BaseResponse<List<String>>> observable = dataManager.getCategoryList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<String>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void search(String category, String keyword, NetCallBack<List<SearchBo>> callBack) {
        Observable<BaseResponse<List<SearchBo>>> observable = dataManager.search(category, keyword);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<SearchBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<SearchBo>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loadNewsDetail(String id, NetCallBack<SearchBo> callBack) {
        Observable<BaseResponse<SearchBo>> observable = dataManager.loadNewsDetail(id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<SearchBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<SearchBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getTestList(NetCallBack<List<String>> callBack) {
        Observable<BaseResponse<List<String>>> observable = dataManager.getTestList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<String>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void searchTest(String category, String keyword, NetCallBack<List<SearchTestBo>> callBack) {
        Observable<BaseResponse<List<SearchTestBo>>> observable = dataManager.searchTest(category, keyword);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<SearchTestBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<SearchTestBo>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loadChatList(String role, NetCallBack<List<ChatListBo>> callBack) {
        Observable<BaseResponse<List<ChatListBo>>> observable = dataManager.loadChatList(role);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<ChatListBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<ChatListBo>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void loadMessageList(String teacher, NetCallBack<List<MessageListBo>> callBack) {
        Observable<BaseResponse<List<MessageListBo>>> observable = dataManager.loadMessageList(teacher);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<MessageListBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<MessageListBo>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loadAnswer(String psyId, NetCallBack<ChatListBo> callBack) {
        Observable<BaseResponse<ChatListBo>> observable = dataManager.loadAnswer(psyId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ChatListBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<ChatListBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void commitAnswer(String psyId, String answer, NetCallBack<String> callBack) {
        Observable<BaseResponse<String>> observable = dataManager.commitAnswer(psyId, answer);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<String> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getMsgList(String student, String teacher, String id, NetCallBack<List<ChatMsgBo>> callBack) {
        Observable<BaseResponse<List<ChatMsgBo>>> observable = dataManager.getMsgList(student, teacher, id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<ChatMsgBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<ChatMsgBo>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void sendMessage(String send, String receive, String content, NetCallBack<ChatMsgBo> callBack) {
        Observable<BaseResponse<ChatMsgBo>> observable = dataManager.sendMessage(send, receive, content);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ChatMsgBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<ChatMsgBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void replyMsg(String id, String reply, NetCallBack<ChatMsgBo> callBack) {
        Observable<BaseResponse<ChatMsgBo>> observable = dataManager.replyMsg(id, reply);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ChatMsgBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<ChatMsgBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void replyTeacherMsg(String content, String student, String teacher, NetCallBack<ChatMsgBo> callBack) {
        Observable<BaseResponse<ChatMsgBo>> observable = dataManager.replyTeacherMsg(content, student, teacher);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ChatMsgBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<ChatMsgBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void addNews(String name, String category, String title, String content, NetCallBack<SearchBo> callBack) {
        Observable<BaseResponse<SearchBo>> observable = dataManager.addNews(name, category, title, content);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<SearchBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<SearchBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteNews(String id, NetCallBack<SearchBo> callBack) {
        Observable<BaseResponse<SearchBo>> observable = dataManager.deleteNews(id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<SearchBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<SearchBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getCommentsList(String id, NetCallBack<List<SearchBo>> callBack) {
        Observable<BaseResponse<List<SearchBo>>> observable = dataManager.getCommentsList(id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<SearchBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<SearchBo>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void addComments(String id, String content, String username, NetCallBack<SearchBo> callBack) {
        Observable<BaseResponse<SearchBo>> observable = dataManager.addComments(id, content, username);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<SearchBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<SearchBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteComments(String id, NetCallBack<SearchBo> callBack) {
        Observable<BaseResponse<SearchBo>> observable = dataManager.deleteComments(id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<SearchBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<SearchBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
