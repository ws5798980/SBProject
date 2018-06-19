package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.Category1And2ListEntity;

import java.util.List;

/**
 * Created by jiangjun on 2015/11/21.
 */
public class ClassifyMoreAdapter extends BaseAdapter {

    private Context context;
    private List<Category1And2ListEntity.lev2> text_list;
    private int position = 0;
    Holder hold;

    public ClassifyMoreAdapter(Context context, List<Category1And2ListEntity.lev2> text_list) {
        this.context = context;
        this.text_list = text_list;
    }

    public void setData(List<Category1And2ListEntity.lev2> text_list){
        this.text_list = text_list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return text_list.size();
    }

    public Object getItem(int position) {
        return text_list.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int arg0, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = View.inflate(context, R.layout.item_classify_morelist, null);
            hold = new Holder(view);
            view.setTag(hold);
        } else {
            hold = (Holder) view.getTag();
        }
        hold.txt.setText(text_list.get(arg0).lev_name);
        hold.txt.setTextColor(0xFF666666);
        if (arg0 == position) {
            hold.txt.setTextColor(0xFFFF8C00);
        }
        return view;
    }

    public void setSelectItem(int position) {
        this.position = position;
    }

    private static class Holder {
        TextView txt;

        public Holder(View view) {
            txt = (TextView) view.findViewById(R.id.moreitem_txt);
        }
    }
}
