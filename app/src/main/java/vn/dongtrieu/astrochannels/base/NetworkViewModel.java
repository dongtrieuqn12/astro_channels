package vn.dongtrieu.astrochannels.base;

import androidx.annotation.CallSuper;

import io.reactivex.observers.DisposableMaybeObserver;

public abstract class NetworkViewModel {
    protected @Constants.RequestState int requestState;
    protected Throwable lastError;

    public abstract boolean isRequestingInformation();

    public NetworkViewModel () {
        this.requestState = Constants.REQUEST_NONE;
    }

    public @Constants.RequestState int getRequestState() {
        if (isRequestingInformation()) {
            return Constants.REQUEST_RUNNING;
        }
        return requestState;
    }

    public Throwable getLastError () {
        return lastError;
    }

    protected class MaybeNetworkObserver<T> extends DisposableMaybeObserver<T> {

        @Override
        @CallSuper
        public void onSuccess(T t) {
            requestState = Constants.REQUEST_SUCCEEDED;
        }

        @Override
        @CallSuper
        public void onError(Throwable e) {
            requestState = Constants.REQUEST_FAILED;
        }

        @Override
        public void onComplete() {

        }
    }
}
