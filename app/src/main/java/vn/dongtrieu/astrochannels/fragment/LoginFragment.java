package vn.dongtrieu.astrochannels.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import vn.dongtrieu.astrochannels.R;
import vn.dongtrieu.astrochannels.base.Const;
import vn.dongtrieu.astrochannels.databinding.FragmentLoginBinding;

public class LoginFragment extends DialogFragment implements FacebookCallback<LoginResult>, OnCompleteListener<AuthResult> {

    public static final String TAG = LoginFragment.class.getSimpleName();

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        dismiss();
        if (task.isSuccessful()) {
            if (mOnAuthenticationResponseListener != null) {
                mOnAuthenticationResponseListener.onAuthenticationSuccess();
            }
        } else {
            if (mOnAuthenticationResponseListener != null) {
                mOnAuthenticationResponseListener.onAuthenticationFailure();
            }
        }
    }

    public interface OnAuthenticationResponseListener {
        void onAuthenticationSuccess();

        void onAuthenticationFailure();

        void onAuthenticationCancelled();
    }

    private CallbackManager mCallbackManager;
    private OnAuthenticationResponseListener mOnAuthenticationResponseListener;
    private FirebaseAuth mAuth;

    public static LoginFragment newInstance () {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        mCallbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLoginBinding fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
//        fragmentLoginBinding.loginButton.setReadPermissions(getString(R.string.permission_email), getString(R.string.permission_public_profile));
        fragmentLoginBinding.loginButton.setFragment(LoginFragment.this);
        fragmentLoginBinding.loginButton.registerCallback(mCallbackManager,LoginFragment.this);

        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        handleFacebookAccessToken(loginResult.getAccessToken());
        Log.d(Const.TAG,loginResult.getAccessToken().getToken());
    }

    @Override
    public void onCancel() {
        if (mOnAuthenticationResponseListener != null) {
            mOnAuthenticationResponseListener.onAuthenticationCancelled();
        }
    }

    @Override
    public void onError(FacebookException error) {
        if (mOnAuthenticationResponseListener != null) {
            mOnAuthenticationResponseListener.onAuthenticationFailure();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this);
    }

    public void setOnAuthenticationResponseListener(OnAuthenticationResponseListener mOnAuthenticationResponseListener) {
        this.mOnAuthenticationResponseListener = mOnAuthenticationResponseListener;
    }
}
