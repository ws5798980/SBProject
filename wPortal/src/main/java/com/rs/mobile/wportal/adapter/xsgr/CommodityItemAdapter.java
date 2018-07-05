package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.ReeditActivity;
import com.rs.mobile.wportal.biz.xsgr.CommodityList;
import com.rs.mobile.wportal.entity.BaseEntity;

import java.util.List;

public class CommodityItemAdapter extends BaseQuickAdapter<CommodityList.DataBean, BaseViewHolder> {
    private ImageView img;
    private TextView name;
    private TextView num;
    private TextView price;
    private Button shelves;
    private Button edit;
    Context context;
//    List<Fragment> list;

//    private String[] titles;

    public CommodityItemAdapter(Context context, int layoutResId, @Nullable List<CommodityList.DataBean> data) {
        super(layoutResId, data);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityList.DataBean item) {
//        img = helper.getView(R.id.image_title);
//        name = helper.getView(R.id.commodity_name);
//        num = helper.getView(R.id.commodity_num);
//        price = helper.getView(R.id.commodity_price);
//        shelves =helper.getView(R.id.get_shelves);
//        edit = helper.getView(R.id.edit_goods);
        helper.addOnClickListener(R.id.get_shelves);
        helper.addOnClickListener(R.id.edit_goods);
        helper.setText(R.id.commodity_name,item.getItem_name());
        helper.setText(R.id.commodity_num,item.getANum());
        helper.setText(R.id.commodity_price,context.getResources().getString(R.string.selling)+item.getItem_p());
        Glide.with(mContext).load(item.getImage_url()).into((ImageView) helper.getView(R.id.image_title));
    }

}
