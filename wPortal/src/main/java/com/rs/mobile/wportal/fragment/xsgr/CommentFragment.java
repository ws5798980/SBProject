package com.rs.mobile.wportal.fragment.xsgr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.CommentAdapter;
import com.rs.mobile.wportal.fragment.BaseFragment;

public class CommentFragment extends BaseFragment {

    private View rootView;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comment, container, false);
        initView(rootView);


        return rootView;
    }

    private void initView(View rootView) {

        commentAdapter = new CommentAdapter(getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listview_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));



        recyclerView.setAdapter(commentAdapter);

    }

    @Override
    protected void lazyLoad() {

    }
}
