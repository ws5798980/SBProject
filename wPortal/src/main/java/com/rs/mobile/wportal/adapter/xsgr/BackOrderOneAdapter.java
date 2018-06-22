package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.wportal.R;

import java.util.List;

public class BackOrderOneAdapter extends RecyclerView.Adapter<BackOrderOneAdapter.ViewHolder> {


    Context context;
    List<Fragment> list;

    private String[] titles;

    public BackOrderOneAdapter(Context context) {

        this.context = context;

    }


    @Override
    public BackOrderOneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_backorder_doing, parent, false);
        BackOrderOneAdapter.ViewHolder viewHolder = new BackOrderOneAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BackOrderOneAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View itemView) {
            super(itemView);


        }
    }
}
