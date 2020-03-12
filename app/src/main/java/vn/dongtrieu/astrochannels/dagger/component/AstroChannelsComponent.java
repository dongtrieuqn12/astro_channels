package vn.dongtrieu.astrochannels.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import vn.dongtrieu.astrochannels.dagger.modules.ApiModule;
import vn.dongtrieu.astrochannels.fragment.ChannelsFragment;

@Singleton
@Component(modules = ApiModule.class)
public interface AstroChannelsComponent {
    void inject (ChannelsFragment fragment);
}
