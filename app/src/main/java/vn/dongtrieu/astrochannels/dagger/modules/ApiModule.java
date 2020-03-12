package vn.dongtrieu.astrochannels.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import vn.dongtrieu.astrochannels.services.AMS_API;
import vn.dongtrieu.astrochannels.services.ServiceGenerator;

@Module
public class ApiModule {
    @Singleton
    @Provides
    public Retrofit providesRetrofit () {
        return ServiceGenerator.createService();
    }

    @Singleton
    @Provides
    public AMS_API providesAPI (Retrofit retrofit) {
        return retrofit.create(AMS_API.class);
    }
}
