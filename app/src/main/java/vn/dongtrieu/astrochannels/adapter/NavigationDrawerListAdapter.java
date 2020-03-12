package vn.dongtrieu.astrochannels.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.util.List;

import vn.dongtrieu.astrochannels.R;

public class NavigationDrawerListAdapter extends ArrayAdapter<String> {

    private boolean mIsUserSignedIn;

    public NavigationDrawerListAdapter(@NonNull Context context, int resource,
                                       List<String> objects, ListView listView) {
        super(context, resource,objects);
        listView.setAdapter(this);
    }

    public void updateItem (boolean isUserSignedIn) {
        String[] items = null;

        if (isUserSignedIn && !mIsUserSignedIn) {
            items = getContext().getResources().getStringArray(R.array.array_of_navigation_drawer_item_title_signed_in);
        } else if (!isUserSignedIn && mIsUserSignedIn) {
            items = getContext().getResources().getStringArray(R.array.array_of_navigation_drawer_item_title);
        }

        if (items != null) {
            clear();
            addAll(items);
            notifyDataSetChanged();
        }

        mIsUserSignedIn = isUserSignedIn;
    }
}
