package vn.dongtrieu.astrochannels.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import vn.dongtrieu.astrochannels.R;

public class FlowUtils {
    private static FlowUtils sInstance;
    private ProgressDialog mProgressDialog;

    public static FlowUtils getInstance() {
        if (sInstance == null) {
            sInstance = new FlowUtils();
        }

        return sInstance;
    }

    public void showLoadingDialog (Context context) {
        if (!isLoadingDialogShowing()) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getString(R.string.loading_string));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    public void dismissLoadingDialog () {
        if (isLoadingDialogShowing()) {
            try {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean isLoadingDialogShowing () {
        return mProgressDialog != null && mProgressDialog.isShowing();
    }

    public void showAlert (Context context, String title, String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(context);
        bld.setTitle(title);
        bld.setMessage(message);
        bld.setNeutralButton(context.getString(R.string.ok),null);
        bld.create().show();
    }
}
