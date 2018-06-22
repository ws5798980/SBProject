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


    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    Context context;
    List<Fragment> list;
    private View mHeaderView;


    public DateDayAdapter(Context context) {

        this.context = context;

    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public DateDayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == TYPE_HEADER) return new ViewHolder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_today, parent, false);
        return new ViewHolder(layout);


//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_today, parent, false);
//        DateDayAdapter.ViewHolder viewHolder = new DateDayAdapter.ViewHolder(view);
//        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final DateDayAdapter.ViewHolder holder, int position) {


    }


    @Override
    public int getItemCount() {
        return mHeaderView == null ? 10 : 11;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout includelayout;

        View openLayout;
        TextView openTv;
        ImageView img_order_iocn;


        ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;

        }
    }
}
