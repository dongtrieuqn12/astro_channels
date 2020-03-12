package vn.dongtrieu.astrochannels.activities;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.dongtrieu.astrochannels.R;
import vn.dongtrieu.astrochannels.adapter.NavigationDrawerListAdapter;
import vn.dongtrieu.astrochannels.base.Const;
import vn.dongtrieu.astrochannels.base.Lifecycle;
import vn.dongtrieu.astrochannels.databinding.ActivityMainBinding;
import vn.dongtrieu.astrochannels.fragment.BaseFragment;
import vn.dongtrieu.astrochannels.fragment.LoginFragment;
import vn.dongtrieu.astrochannels.navigation.NavigationDrawerContract;
import vn.dongtrieu.astrochannels.navigation.NavigationDrawerViewModel;
import vn.dongtrieu.astrochannels.utils.ArrayUtils;
import vn.dongtrieu.astrochannels.utils.FirebaseUtils;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener, LoginFragment.OnAuthenticationResponseListener, NavigationDrawerContract.View {

    private ActivityMainBinding activityMainBinding;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationDrawerListAdapter mNavigationDrawerAdapter;
    private NavigationDrawerContract.ViewModel navigationViewModel;

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return navigationViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setSupportActionBar(activityMainBinding.toolbar);
        setupNavigationDrawer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawer () {
        activityMainBinding.listViewLeftDrawer.setOnItemClickListener(this);
        mNavigationDrawerAdapter = new NavigationDrawerListAdapter(this, R.layout.drawer_list_item,
                ArrayUtils.arrayToList(getResources().getStringArray(R.array.array_of_navigation_drawer_item_title)),
                activityMainBinding.listViewLeftDrawer);

        navigationViewModel = new NavigationDrawerViewModel(mNavigationDrawerAdapter);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            navigationViewModel.updateItems(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, activityMainBinding.layoutDrawer,
                R.string.drawer_open, R.string.drawer_close);

        activityMainBinding.layoutDrawer.addDrawerListener(mDrawerToggle);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mDrawerToggle.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigationViewModel.onItemClick(position);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                dialog.dismiss();
                LoginFragment loginFragment = LoginFragment.newInstance();
                loginFragment.setOnAuthenticationResponseListener(this);
                loginFragment.show(getSupportFragmentManager(),LoginFragment.TAG);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    @Override
    public void onAuthenticationSuccess() {
        // update UI
        navigationViewModel.updateItems(true);
        callUpdatingSpinnerValues();

        // store info for new user
        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = currentUser.getUid();
                String email = FirebaseUtils.getInstance().getUser(userId);
                Log.d(Const.TAG,"user: " + userId + " email: " + email);

                if (email == null) {
                    email = currentUser.getEmail();
                    FirebaseUtils.getInstance().saveNewUser(userId, email);
                }
            }
        }).start();
    }

    @Override
    public void onAuthenticationFailure() {

    }

    @Override
    public void onAuthenticationCancelled() {

    }

    private void callUpdatingSpinnerValues() {
        BaseFragment currentFragment = (BaseFragment) getCurrentFragment();
        if (currentFragment != null) {
            currentFragment.updateViews();
        }
    }

    @Override
    public void selectItem(int position, String title) {

    }
}
