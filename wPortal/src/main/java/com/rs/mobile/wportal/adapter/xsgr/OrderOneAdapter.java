package com.rs.mobile.wportal.adapter.xsgr;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rs.mobile.wportal.R;

public class OrderOneAdapter extends RecyclerView.Adapter<OrderOneAdapter.ViewHolder> {

    @Override
    public OrderOneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_new, parent, false);
        OrderOneAdapter.ViewHolder viewHolder = new OrderOneAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderOneAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText;
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
