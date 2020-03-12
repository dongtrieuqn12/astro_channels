package vn.dongtrieu.astrochannels.models;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import vn.dongtrieu.astrochannels.exception.ChannelsTechFailureException;
import vn.dongtrieu.astrochannels.services.AMS_API;

public class ChannelsAPIService {
    private AMS_API mApi;
    private boolean isRequestingChannels;

    public ChannelsAPIService (AMS_API api) {
        mApi = api;
    }

    public boolean isRequestingChannels() {
        return isRequestingChannels;
    }

    public Observable<GetChannelListResponse> getChannels () {
        return mApi.getChannelList()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        isRequestingChannels = true;
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() {
                        isRequestingChannels = false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        handleChannelsError(throwable);
                    }
                });
    }

    private void handleChannelsError (Throwable throwable) {
        throw new ChannelsTechFailureException();
    }
}
