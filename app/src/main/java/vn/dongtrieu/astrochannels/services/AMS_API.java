package vn.dongtrieu.astrochannels.services;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.dongtrieu.astrochannels.models.GetChannelListResponse;
import vn.dongtrieu.astrochannels.models.GetEventsResponse;

import static vn.dongtrieu.astrochannels.base.Const.API_REQUEST_PARAMS_CHANNEL_ID;
import static vn.dongtrieu.astrochannels.base.Const.API_REQUEST_PARAMS_PERIOD_END;
import static vn.dongtrieu.astrochannels.base.Const.API_REQUEST_PARAMS_PERIOD_START;

public interface AMS_API {
    @GET("ams/v3/getChannels")
    Observable<GetChannelListResponse> getChannelList();

    @GET("ams/v3/getEvents")
    Observable<GetEventsResponse> getEvents(
            @Query(API_REQUEST_PARAMS_CHANNEL_ID) int[] channelIds,
            @Query(API_REQUEST_PARAMS_PERIOD_START) String periodStart,
            @Query(API_REQUEST_PARAMS_PERIOD_END) String periodEnd);
}
