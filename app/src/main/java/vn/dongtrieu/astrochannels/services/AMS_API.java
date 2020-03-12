package com.huynd.astrochannels.services;

import com.huynd.astrochannels.models.GetChannelListResponse;
import com.huynd.astrochannels.models.GetEventsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.huynd.astrochannels.utils.Constants.API_REQUEST_PARAMS_CHANNEL_ID;
import static com.huynd.astrochannels.utils.Constants.API_REQUEST_PARAMS_PERIOD_END;
import static com.huynd.astrochannels.utils.Constants.API_REQUEST_PARAMS_PERIOD_START;

/**
 * Created by HuyND on 9/18/2017.
 */

public interface AMS_API {
    @GET("ams/v3/getChannels")
    Call<GetChannelListResponse> getChannelList();

    @GET("ams/v3/getEvents")
    Call<GetEventsResponse> getEvents(
            @Query(API_REQUEST_PARAMS_CHANNEL_ID) int[] channelIds,
            @Query(API_REQUEST_PARAMS_PERIOD_START) String periodStart,
            @Query(API_REQUEST_PARAMS_PERIOD_END) String periodEnd);
}
