package vn.dongtrieu.astrochannels.channels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import vn.dongtrieu.astrochannels.base.Const;
import vn.dongtrieu.astrochannels.models.Channel;
import vn.dongtrieu.astrochannels.models.ChannelsAPIService;
import vn.dongtrieu.astrochannels.models.GetChannelListResponse;
import vn.dongtrieu.astrochannels.services.AMS_API;
import vn.dongtrieu.astrochannels.utils.ChannelComparator;
import vn.dongtrieu.astrochannels.utils.FirebaseUtils;
import vn.dongtrieu.astrochannels.utils.PreferencesUtils;

public class ChannelsRepository {
    private static ChannelsRepository instance;
    private ChannelsAPIService channelsAPIService;
    private MutableLiveData<List<Channel>> channelsListMutableLiveData = new MutableLiveData<>();
    private ArrayList<Channel> channels = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ChannelComparator.SortingCondition mSortingCondition;
    private PreferencesUtils privatePreferences;

    private ChannelsRepository (Context context, AMS_API mApi) {
        privatePreferences = PreferencesUtils.getInstance(context);
        channelsAPIService = new ChannelsAPIService(mApi);

        final Channel[] favouriteChannels = loadAllFavouriteChannels();

        compositeDisposable.add(channelsAPIService.getChannels().flatMap(new Function<GetChannelListResponse, Observable<Channel>>() {
            @Override
            public Observable<Channel> apply(GetChannelListResponse getChannelListResponse) throws Exception {
                if (getChannelListResponse.getResponseCode() == Const.HTTP_RESPONSE_CODE_SUCCESS) {
                    return Observable.fromArray(getChannelListResponse.getChannels());
                } else {
                    throw new Exception(getChannelListResponse.getResponseMessage());
                }
            }
        })
        .subscribeWith(new DisposableObserver<Channel>() {
            @Override
            public void onNext(Channel channel) {
                if (favouriteChannels != null) {
                    for (Channel favouriteChannel : favouriteChannels) {
                        if (channel.getChannelId() == favouriteChannel.getChannelId()) {
                            channel.setFavourite(true);
                            break;
                        }
                    }
                }
                channels.add(channel);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Arrays.sort(channels.toArray(new Channel[0]), new ChannelComparator(mSortingCondition));
                channelsListMutableLiveData.postValue(channels);
            }
        }));
    }

    public static ChannelsRepository getInstance(Context context, AMS_API mApi) {
        synchronized (ChannelsRepository.class) {
            if (instance == null) {
                instance = new ChannelsRepository(context,mApi);
            }

            return instance;
        }
    }

    public boolean isRequesting () {
        return channelsAPIService.isRequestingChannels();
    }

    public MutableLiveData<List<Channel>> getChannelsListMutableLiveData() {
        return channelsListMutableLiveData;
    }

    private Channel[] loadAllFavouriteChannels() {
        return FirebaseAuth.getInstance().getCurrentUser() != null ?
                FirebaseUtils.getInstance().loadFavouriteChannels() :
                privatePreferences.loadFavouriteChannels();
    }

    public void setSortingCondition(ChannelComparator.SortingCondition sortingCondition) {
        if (mSortingCondition != sortingCondition) {
            mSortingCondition = sortingCondition;
            if (channelsListMutableLiveData.getValue() != null) {
                Arrays.sort(channelsListMutableLiveData.getValue().toArray(new Channel[0]), new ChannelComparator(mSortingCondition));
            }
        }
    }

    public void clear () {
        compositeDisposable.clear();
    }
}
