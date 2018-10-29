package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.XsStoreDetailMenuItem;

import java.util.List;

public class XsIndexAdapter2 extends BaseQuickAdapter<XsStoreDetailMenuItem.datafav, BaseViewHolder> {
    private Context mContext;


    public XsIndexAdapter2(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, XsStoreDetailMenuItem.datafav item) {

        helper.setText(R.id.tv, item.level_name);
        if (item.image_url != null && !item.image_url.equals("")) {
            ImageUtil.drawImageFromUri(item.image_url, (WImageView) helper.getView(R.id.iv_itemimage));
        }
        ((WImageView) helper.getView(R.id.iv_itemimage)).setCircle(true);
        helper.addOnClickListener(R.id.iv_itemimage);

    }
}
