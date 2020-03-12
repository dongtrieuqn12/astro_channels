package vn.dongtrieu.astrochannels.navigation;

import vn.dongtrieu.astrochannels.base.Lifecycle;

public interface NavigationDrawerContract {
    interface View extends Lifecycle.View {
        void selectItem (int position, String title);
    }

    interface ViewModel extends Lifecycle.ViewModel {
        void onItemClick (int position);
        void updateItems(boolean isUserSignedIn);
    }
}
