package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.BaseEntity;

import java.util.List;

public class BackOrderTwoAdapter extends BaseQuickAdapter<BaseEntity, BaseViewHolder> {


    Context context;
    List<Fragment> list;


    private String[] titles;

    public BackOrderTwoAdapter(Context context, int layoutResId, @Nullable List<BaseEntity> data) {
        super(layoutResId, data);
        this.context = context;

    }


    @Override
    protected void convert(BaseViewHolder helper, BaseEntity item) {
        helper.addOnClickListener(R.id.bt_check);
    }
}
