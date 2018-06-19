package com.rs.mobile.wportal.fragment.xsgr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.fragment.BaseFragment;

public class MyOrderFragment2 extends BaseFragment {

    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_order_done, container, false);

        return rootView;
    }

    @Override
    protected void lazyLoad() {

    }
}
