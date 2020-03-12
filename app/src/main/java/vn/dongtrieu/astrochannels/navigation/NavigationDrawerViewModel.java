package vn.dongtrieu.astrochannels.navigation;

import vn.dongtrieu.astrochannels.adapter.NavigationDrawerListAdapter;
import vn.dongtrieu.astrochannels.base.Lifecycle;

public class NavigationDrawerViewModel implements NavigationDrawerContract.ViewModel {

    private NavigationDrawerContract.View viewCallback;
    private NavigationDrawerListAdapter mAdapter;
    public NavigationDrawerViewModel (NavigationDrawerListAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onItemClick(int position) {
        if (viewCallback != null) {
            viewCallback.selectItem(position,mAdapter.getItem(position));
        }
    }

    @Override
    public void updateItems(boolean isUserSignedIn) {
        if (mAdapter != null) {
            mAdapter.updateItem(isUserSignedIn);
        }
    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewAttached(Lifecycle.View viewCallback) {
        this.viewCallback = (NavigationDrawerContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }
}
