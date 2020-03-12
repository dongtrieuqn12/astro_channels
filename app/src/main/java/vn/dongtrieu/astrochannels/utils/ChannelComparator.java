package com.huynd.astrochannels.utils;

import com.huynd.astrochannels.models.Channel;

import java.util.Comparator;

import static com.huynd.astrochannels.utils.ChannelComparator.SortingCondition.CHANNEL_NAME;
import static com.huynd.astrochannels.utils.ChannelComparator.SortingCondition.CHANNEL_NUMBER;

/**
 * Created by HuyND on 9/18/2017.
 */

public class ChannelComparator implements Comparator<Channel> {
    public enum SortingCondition {
        CHANNEL_NUMBER, CHANNEL_NAME, FAVOURITES
    }

    private SortingCondition mSortingCondition = CHANNEL_NUMBER;

    public ChannelComparator(SortingCondition sortingCondition) {
        mSortingCondition = sortingCondition;
    }

    @Override
    public int compare(Channel channel1, Channel channel2) {
        if (mSortingCondition == CHANNEL_NUMBER) {
            int channelNumber1 = channel1.getChannelStbNumber();
            int channelNumber2 = channel2.getChannelStbNumber();

            if (channelNumber1 == 0) {
                return 1;
            } else if (channelNumber2 == 0) {
                return -1;
            } else {
                return channelNumber1 - channelNumber2;
            }
        } else if (mSortingCondition == CHANNEL_NAME) {
            String channelName1 = channel1.getChannelTitle();
            if (channelName1 == null) {
                return -1;
            }
            return channel1.getChannelTitle().compareTo(channel2.getChannelTitle());
        } else {
            if (channel1.isFavourite() && channel2.isFavourite()) {
                return channel1.getChannelStbNumber() - channel2.getChannelStbNumber();
            } else if (channel1.isFavourite()) {
                return -1;
            } else if (channel2.isFavourite()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
