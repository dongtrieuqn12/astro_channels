package vn.dongtrieu.astrochannels.activities;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vn.dongtrieu.astrochannels.R;
import vn.dongtrieu.astrochannels.base.Lifecycle;

public abstract class BaseActivity extends AppCompatActivity implements Lifecycle.View {
    private static final String TAG = BaseActivity.class.getSimpleName();

    protected abstract Lifecycle.ViewModel getViewModel ();

    private FragmentManager mFragmentManager;
    private String mCurrentFragmentTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
    }

    public void setFragment (Fragment fragment, String tag, boolean clearFragmentBackStack) {
        if (tag.equals(mCurrentFragmentTag)) {
            return;
        }

        if (clearFragmentBackStack) {
            clearFragmentBackStack();
        }

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment existfragment = mFragmentManager.findFragmentByTag(tag);
        if (existfragment != null) {
            fragmentTransaction.show(existfragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container,fragment,tag);
        }
    }

    public void clearFragmentBackStack () {
        mFragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public Fragment getCurrentFragment () {
        return mCurrentFragmentTag != null ? mFragmentManager.findFragmentByTag(mCurrentFragmentTag) : null;
    }

    public Fragment getFragmentWithTag (String tag) {
        return mFragmentManager.findFragmentByTag(tag);
    }

    private String getCurrentFragmentTag() {
        return mCurrentFragmentTag;
    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showFailedDialog(String errorMessage) {

    }

    @Override
    public void updateViews() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewModel().onViewResumed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getViewModel().onViewAttached(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getViewModel().onViewDetached();
    }
}
