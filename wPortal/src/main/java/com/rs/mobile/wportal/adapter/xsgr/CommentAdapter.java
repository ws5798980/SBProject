package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.BaseEntity;

import java.util.List;

public class CommentAdapter extends BaseQuickAdapter<BaseEntity, BaseViewHolder> {


    Context context;
    List<Fragment> list;


    public CommentAdapter(Context context, int layoutResId, @Nullable List<BaseEntity> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, BaseEntity item) {

    }
}
