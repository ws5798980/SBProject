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

public class DateDayAdapter extends RecyclerView.Adapter<DateDayAdapter.ViewHolder> {


    Context context;
    List<Fragment> list;

    private String[] titles;

    public DateDayAdapter(Context context) {

        this.context = context;

    }


    @Override
    public DateDayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_today, parent, false);
        DateDayAdapter.ViewHolder viewHolder = new DateDayAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DateDayAdapter.ViewHolder holder, int position) {


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


        }
    }
}
