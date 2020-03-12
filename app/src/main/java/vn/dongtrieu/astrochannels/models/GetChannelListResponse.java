package vn.dongtrieu.astrochannels.models;

import com.google.gson.annotations.SerializedName;

public class GetChannelListResponse {
    @SerializedName("responseMessage")
    private String responseMessage;

    @SerializedName("responseCode")
    private String responseCode;

    @SerializedName("channel")
    private Channel[] channel;

    public Channel[] getChannels() {
        return channel;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        int intResponseCode = -1;
        try {
            intResponseCode = Integer.parseInt(responseCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return intResponseCode;
    }
}
