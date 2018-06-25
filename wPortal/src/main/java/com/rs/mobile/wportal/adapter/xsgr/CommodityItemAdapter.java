package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.wportal.R;

import java.util.List;

public class CommodityItemAdapter extends RecyclerView.Adapter<CommodityItemAdapter.ViewHolder> {


    Context context;
//    List<Fragment> list;

//    private String[] titles;

    public CommodityItemAdapter(Context context) {

        this.context = context;

    }


    @Override
    public CommodityItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commodity_new, parent, false);
        CommodityItemAdapter.ViewHolder viewHolder = new CommodityItemAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CommodityItemAdapter.ViewHolder holder, int position) {
        holder.setData();

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView num;
        private TextView price;
        private Button shelves;
        private Button edit;


        ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.image_title);
            name = (TextView) itemView.findViewById(R.id.commodity_name);
            num = (TextView) itemView.findViewById(R.id.commodity_num);
            price = (TextView) itemView.findViewById(R.id.commodity_price);
            shelves = (Button) itemView.findViewById(R.id.get_shelves);
            edit = (Button) itemView.findViewById(R.id.edit_goods);
        }
        public void setData() {

        }
    }
}
