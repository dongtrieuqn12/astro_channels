package com.huynd.astrochannels.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.huynd.astrochannels.models.Channel;

import java.util.List;

import static com.huynd.astrochannels.utils.ArrayUtils.arrayToList;

/**
 * Created by HuyND on 9/19/2017.
 */

public class PreferencesUtils {
    private static final String KEY_FAVOURITE_CHANNEL = "astroFavouriteChannel";

    private static PreferencesUtils mInstance;
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    private PreferencesUtils(Context context) {
        mContext = context;
        mSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mContext);
    }

    public static void initInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PreferencesUtils(context);
        }
    }

    public static PreferencesUtils getInstance() {
        return mInstance;
    }

    private void saveFavouriteChannels(Channel[] channels) {
        Gson gson = new Gson();
        String jsonString = (channels != null && channels.length > 0) ?
                gson.toJson(arrayToList(channels)) : null;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_FAVOURITE_CHANNEL, jsonString);
        editor.apply();
    }

    public void saveFavouriteChannel(Channel channel) {
        Channel[] channels = loadFavouriteChannels();
        if (channels != null) {
            for (Channel savedChannel : channels) {
                if (savedChannel.getChannelId() == channel.getChannelId()) {
                    return;
                }
            }
        }

        List<Channel> listChannel = arrayToList(channels);
        listChannel.add(channel);
        saveFavouriteChannels(listChannel.toArray(new Channel[0]));
    }

    public void removeFavouriteChannel(Channel channel) {
        Channel[] channels = loadFavouriteChannels();
        Channel savedChannel = null;
        if (channels != null) {
            for (Channel favouriteChannel : channels) {
                if (favouriteChannel.getChannelId() == channel.getChannelId()) {
                    savedChannel = favouriteChannel;
                    break;
                }
            }
        }

        if (savedChannel == null) {
            return;
        }

        List<Channel> listChannel = arrayToList(channels);
        if (listChannel.remove(savedChannel)) {
            saveFavouriteChannels(listChannel.toArray(new Channel[0]));
        }
    }

    public Channel[] loadFavouriteChannels() {
        String jsonString = mSharedPreferences.getString(KEY_FAVOURITE_CHANNEL, null);
        if (jsonString == null) {
            return null;
        }
        return new Gson().fromJson(jsonString, Channel[].class);
    }

    public boolean hasFavouriteChannels() {
        String jsonString = mSharedPreferences.getString(KEY_FAVOURITE_CHANNEL, null);
        return jsonString != null;
    }
}
