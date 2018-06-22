package com.rs.mobile.wportal.activity.xsgr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.fragment.BaseFragment;

public class CommentFragment extends BaseFragment {

    private View rootView;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comment, container, false);

        initView(rootView);


        return rootView;
    }

    private void initView(View rootView) {

    }

    @Override
    protected void lazyLoad() {

    }
}
