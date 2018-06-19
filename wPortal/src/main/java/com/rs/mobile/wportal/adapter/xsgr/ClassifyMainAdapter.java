package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.Category1And2ListEntity;

import java.util.List;

/**
 * Created by jiangjun on 2015/11/21.
 */
public class ClassifyMainAdapter extends BaseAdapter {

    private Context context;
    private List<Category1And2ListEntity.lev1> list;
    private int position;
    Holder hold;

    public ClassifyMainAdapter(Context context, List<Category1And2ListEntity.lev1> list, int position) {
        this.context = context;
        this.list = list;
        this.position = position;
    }

    public void setData(List<Category1And2ListEntity.lev1> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int arg0, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = View.inflate(context, R.layout.item_classify_mainlist, null);
            hold = new Holder(view);
            view.setTag(hold);
        } else {
            hold = (Holder) view.getTag();
        }
        hold.txt.setText(list.get(arg0).lev_name);
        hold.layout.setBackgroundColor(0xFFEBEBEB);
        if (arg0 == position) {
            hold.layout.setBackgroundColor(0xFFFFFFFF);
        }
        return view;
    }

    public void setSelectItem(int position) {
        this.position = position;
    }

    public int getSelectItem() {
        return position;
    }

    private static class Holder {
        LinearLayout layout;
        TextView txt;

        public Holder(View view) {
            txt = (TextView) view.findViewById(R.id.tv_main);
            layout = (LinearLayout) view.findViewById(R.id.mainitem_layout);
        }
    }
}

