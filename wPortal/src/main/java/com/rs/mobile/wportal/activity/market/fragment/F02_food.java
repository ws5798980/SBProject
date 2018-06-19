package com.rs.mobile.wportal.activity.market.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 食品区
 */
public class F02_food extends Fragment
{

    private View parentView = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        parentView = inflater.inflate(com.rs.mobile.wportal.R.layout.activity_f02_food, container, false);
        init();
        return parentView;
    }
    @SuppressLint({ "SetJavaScriptEnabled", "InlinedApi" })
    public void init()
    {
       
       

    }


}
