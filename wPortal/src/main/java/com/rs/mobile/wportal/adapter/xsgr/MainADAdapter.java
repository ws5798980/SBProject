package com.rs.mobile.wportal.adapter.xsgr;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.MainADListItem;

import java.util.List;

public class MainADAdapter extends BaseQuickAdapter<MainADListItem, BaseViewHolder> {
    public MainADAdapter(int layoutResId, List data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainADListItem item) {
        helper.setImageResource(R.id.iv_ad, item.imgRes);
    }
}
