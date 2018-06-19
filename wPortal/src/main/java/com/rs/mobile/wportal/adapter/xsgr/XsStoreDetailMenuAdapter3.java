package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.StoreItemDetailEntity;
import com.rs.mobile.wportal.entity.StoreMenuListEntity1;

import java.util.List;

public class XsStoreDetailMenuAdapter3 extends BaseQuickAdapter<StoreMenuListEntity1.plistinfo, BaseViewHolder> {
    private Context mContext;


    public XsStoreDetailMenuAdapter3(Context context, int layoutResId, List data){
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreMenuListEntity1.plistinfo item) {
        helper.setText(R.id.name, item.item_name);
        helper.setText(R.id.price, item.item_p);
        helper.setText(R.id.sale_count, "주문수 "+item.MonthSaleCount);
        helper.setText(R.id.tv_itemconent, item.synopsis);

        Glide.with(mContext).load(item.image_url).into((ImageView) helper.getView(R.id.thumbnail));

        helper.addOnClickListener(R.id.bnt_select2);

    }
}
