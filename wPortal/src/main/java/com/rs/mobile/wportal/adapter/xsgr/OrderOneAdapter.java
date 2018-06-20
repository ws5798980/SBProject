package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.wportal.R;

import java.util.List;

public class OrderOneAdapter extends RecyclerView.Adapter<OrderOneAdapter.ViewHolder> {


    Context context;
    List<Fragment> list;

    private String[] titles;

    public OrderOneAdapter(Context context) {

        this.context = context;

    }


    @Override
    public OrderOneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_new, parent, false);
        OrderOneAdapter.ViewHolder viewHolder = new OrderOneAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderOneAdapter.ViewHolder holder, int position) {

        holder.includelayout.removeAllViews();
        holder.includelayout.setVisibility(View.GONE);
        holder.openTv.setText(context.getResources().getString(R.string.orderopen));
        holder.img_order_iocn.setImageResource(R.drawable.icon_open_goods);

        holder.openLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (View.VISIBLE==holder.includelayout.getVisibility()){
                    holder.includelayout.setVisibility(View.GONE);
                    holder.openTv.setText(context.getResources().getString(R.string.orderopen));
                    holder.img_order_iocn.setImageResource(R.drawable.icon_open_goods);
                }else {
                    holder.includelayout.setVisibility(View.VISIBLE);
                   holder.openTv.setText(context.getResources().getString(R.string.orderclose));
                    holder.img_order_iocn.setImageResource(R.drawable.icon_close_goods);
                }
            }
        });


        for (int i = 0; i < 5; i++) {
            View childView = LayoutInflater.from(context).inflate(R.layout.item_order_open, null);
            holder.includelayout.addView(childView);
        }


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout includelayout;

        View openLayout;
        TextView openTv;
        ImageView img_order_iocn;


        ViewHolder(View itemView) {
            super(itemView);

            includelayout = (LinearLayout) itemView.findViewById(R.id.layout_include);
            openLayout=itemView.findViewById(R.id.layout_open);
            openTv= (TextView) itemView.findViewById(R.id.tv_order_new_pull);
            img_order_iocn= (ImageView) itemView.findViewById(R.id.img_order_iocn);
        }
    }
}
