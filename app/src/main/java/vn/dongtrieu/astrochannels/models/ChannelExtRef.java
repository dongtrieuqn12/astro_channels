package vn.dongtrieu.astrochannels.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HuyND on 9/20/2017.
 */
public class ChannelExtRef {
    @SerializedName("system")
    private String system;

    @SerializedName("subSystem")
    private String subSystem;

    @SerializedName("value")
    private String value;

    public String getSystem() {
        return system;
    }

    public String getValue() {
        return value;
    }
}
