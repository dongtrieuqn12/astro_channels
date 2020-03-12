package vn.dongtrieu.astrochannels.base;

import vn.dongtrieu.astrochannels.BuildConfig;

public interface Const {
    String TAG = "HDT";
    String API_URL = BuildConfig.API_URL;
    int HTTP_RESPONSE_CODE_SUCCESS = 200;

    String API_REQUEST_PARAMS_CHANNEL_ID = "channelId";
    String API_REQUEST_PARAMS_PERIOD_START = "periodStart";
    String API_REQUEST_PARAMS_PERIOD_END = "periodEnd";

    String API_REQUEST_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    String API_RESPONSE_UTC_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.s";
    String GUIDE_SHORT_TIME_FORMAT = "HH:mm a";

    String FIREBASE_KEY_USERS = "users";
    String FIREBASE_KEY_CHANNELS = "channels";
}
