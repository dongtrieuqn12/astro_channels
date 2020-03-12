package vn.dongtrieu.astrochannels.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import javax.inject.Inject;

import vn.dongtrieu.astrochannels.AstroChannelsApp;
import vn.dongtrieu.astrochannels.R;
import vn.dongtrieu.astrochannels.base.Lifecycle;
import vn.dongtrieu.astrochannels.channels.ChannelsContract;
import vn.dongtrieu.astrochannels.databinding.FragmentChannelsBinding;
import vn.dongtrieu.astrochannels.services.AMS_API;

public class ChannelsFragment extends BaseFragment implements ChannelsContract.View {

    private FragmentChannelsBinding fragmentChannelsBinding;

    @Inject
    AMS_API mApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inject
        AstroChannelsApp app = (AstroChannelsApp) Objects.requireNonNull(getActivity()).getApplication();
        app.getComponent().inject(this);

        fragmentChannelsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_channels,container,false);

        return fragmentChannelsBinding.getRoot();
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return null;
    }
}
