package vn.dongtrieu.astrochannels;

import android.app.Application;

import vn.dongtrieu.astrochannels.dagger.component.AstroChannelsComponent;
import vn.dongtrieu.astrochannels.dagger.component.DaggerAstroChannelsComponent;
import vn.dongtrieu.astrochannels.dagger.modules.ApiModule;
import vn.dongtrieu.astrochannels.utils.FirebaseUtils;

public class AstroChannelsApp extends Application {

    private AstroChannelsComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseUtils.initInstance();

        mComponent = DaggerAstroChannelsComponent
                .builder()
                .apiModule(new ApiModule())
                .build();
    }

    public AstroChannelsComponent getComponent() {
        return mComponent;
    }
}
