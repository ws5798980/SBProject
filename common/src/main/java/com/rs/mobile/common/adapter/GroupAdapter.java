package com.rs.mobile.common.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.common.R;
import com.rs.mobile.common.entity.GroupItem;
import com.rs.mobile.common.entity.SelectGroupItem;

import java.util.List;

public class GroupAdapter extends BaseQuickAdapter<SelectGroupItem.data, BaseViewHolder> {
    public GroupAdapter(int layoutResId, List data){
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, SelectGroupItem.data item) {
        helper.setText(R.id.tv_no, item.rum);
        helper.setText(R.id.tv_group_nm, item.custom_code);
        helper.setText(R.id.tv_group_rep, item.custom_name);
    }
}
