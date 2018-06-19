package com.rs.mobile.wportal.adapter.xsgr;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.Category3ListEntity;

import java.util.List;

public class Lv3MoreAdapter extends BaseQuickAdapter<Category3ListEntity.lev3, BaseViewHolder> {
    public Lv3MoreAdapter(int layoutResId, List data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Category3ListEntity.lev3 item) {
        helper.setText(R.id.tv_lv3, item.lev_name);
    }
}
