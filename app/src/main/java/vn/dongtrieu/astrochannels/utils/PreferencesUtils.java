package vn.dongtrieu.astrochannels.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.List;

import vn.dongtrieu.astrochannels.models.Channel;

import static vn.dongtrieu.astrochannels.utils.ArrayUtils.arrayToList;

public class PreferencesUtils {
    private static final String KEY_FAVOURITE_CHANNEL = "astroFavouriteChannel";

    private static PreferencesUtils mInstance;
    private SharedPreferences mSharedPreferences;

    private PreferencesUtils(Context context) {
        mSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public static PreferencesUtils getInstance(Context context) {
        synchronized (PreferencesUtils.class) {
            if (mInstance == null) {
                mInstance = new PreferencesUtils(context);
            }
        }
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
