package vn.dongtrieu.astrochannels.channels;

import androidx.lifecycle.LiveData;

import java.util.List;

import vn.dongtrieu.astrochannels.base.Lifecycle;
import vn.dongtrieu.astrochannels.models.Channel;

public interface ChannelsContract {
    interface View extends Lifecycle.View {

    }

    interface ViewModel extends Lifecycle.ViewModel {
        LiveData<List<Channel>> getChannels();
    }
}
