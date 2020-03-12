package vn.dongtrieu.astrochannels.base;

public interface Lifecycle {
    interface View {
        void showLoadingDialog ();
        void dismissLoadingDialog();
        void showFailedDialog (String errorMessage);
        void updateViews();
    }

    interface ViewModel {
        void onViewResumed();
        void onViewAttached (Lifecycle.View viewCallback);
        void onViewDetached();
    }
}
