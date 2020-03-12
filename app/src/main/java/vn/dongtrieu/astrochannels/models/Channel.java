package com.huynd.astrochannels.models;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HuyND on 9/18/2017.
 */

public class Channel {
    @SerializedName("channelId")
    private int channelId;

    @SerializedName("channelTitle")
    private String channelTitle;

    @SerializedName("channelStbNumber")
    private int channelStbNumber;

    @SerializedName("channelExtRef")
    private ChannelExtRef[] channelExtRef;

    private String logoUri;

    private boolean isFavourite;

    public int getChannelId() {
        return channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public int getChannelStbNumber() {
        return channelStbNumber;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getLogoUri() {
        if (channelExtRef != null) {
            for (ChannelExtRef extRef : channelExtRef) {
                if ("Logo".equals(extRef.getSystem())) {
                    return extRef.getValue();
                }
            }
        }

        if (logoUri != null) {
            return logoUri;
        }

        return null;
    }
}
