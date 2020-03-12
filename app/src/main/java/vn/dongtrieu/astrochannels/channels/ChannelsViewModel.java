package vn.dongtrieu.astrochannels.channels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import vn.dongtrieu.astrochannels.base.Constants;
import vn.dongtrieu.astrochannels.base.Lifecycle;
import vn.dongtrieu.astrochannels.base.NetworkViewModel;
import vn.dongtrieu.astrochannels.models.Channel;

public class ChannelsViewModel extends NetworkViewModel implements ChannelsContract.ViewModel {

    private ChannelsContract.View viewCallback;
    private ChannelsRepository channelsRepository;

    public ChannelsViewModel (ChannelsRepository channelsRepository) {
        this.channelsRepository = channelsRepository;
    }

    @Override
    public boolean isRequestingInformation() {
        return false;
    }

    @Override
    public void onViewResumed() {
        @Constants.RequestState int requestState = getRequestState();
        if (requestState == Constants.REQUEST_SUCCEEDED) {

        } else if (requestState == Constants.REQUEST_FAILED) {

        }
    }

    @Override
    public void onViewAttached(Lifecycle.View viewCallback) {
        this.viewCallback = (ChannelsContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    @Override
    public LiveData<List<Channel>> getChannels() {
        return channelsRepository.getChannelsListMutableLiveData();
    }
}
