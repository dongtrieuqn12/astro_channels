package vn.dongtrieu.astrochannels.fragment;

import android.content.Context;

import androidx.fragment.app.Fragment;

import vn.dongtrieu.astrochannels.R;
import vn.dongtrieu.astrochannels.base.Lifecycle;
import vn.dongtrieu.astrochannels.utils.FlowUtils;

public abstract class BaseFragment extends Fragment implements Lifecycle.View {

    protected abstract Lifecycle.ViewModel getViewModel ();

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().onViewResumed();
    }

    @Override
    public void onStart() {
        super.onStart();
        getViewModel().onViewAttached(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getViewModel().onViewDetached();
    }

    @Override
    public void showLoadingDialog() {
        FlowUtils.getInstance().showLoadingDialog(this.getContext());
    }

    @Override
    public void dismissLoadingDialog() {
        FlowUtils.getInstance().dismissLoadingDialog();
    }

    @Override
    public void showFailedDialog(String errorMessage) {
        Context context = this.getContext();
        if (context == null) {
            return;
        }
        if (errorMessage == null) {
            errorMessage = context.getString(R.string.unexpected_error_message);
        }
        FlowUtils.getInstance().showAlert(context,context.getString(R.string.error),errorMessage);
    }

    @Override
    public void updateViews() {

    }

    protected void updateSpinnerValues () {

    }

    protected void updateFavouriteChannels () {

    }


}
