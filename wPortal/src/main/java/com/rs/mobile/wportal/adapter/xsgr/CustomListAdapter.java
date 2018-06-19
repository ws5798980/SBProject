package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.ListItem;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<ListItem> listData;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public CustomListAdapter(Context context, ArrayList<ListItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layoutimageurl, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
           /* holder.reportedDateView = (TextView) convertView.findViewById(R.id.date);*/
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListItem newsItem = listData.get(position);
        /*holder.headlineView.setText(newsItem.getHeadline());
        holder.reporterNameView.setText("By, " + newsItem.getReporterName());
        holder.reportedDateView.setText(newsItem.getDate());*/

        if (holder.imageView != null) {
//            new ImageDownloaderTask(holder.imageView).execute(newsItem.getUrl());
            Glide.with(mContext).load(newsItem.getUrl()).into(holder.imageView);
        }
        holder.txtTitle.setText(newsItem.custom_name);
        holder.txtDate.setText(newsItem.date);
        return convertView;
    }

    static class ViewHolder {
        TextView txtDate;
        TextView reporterNameView;
        TextView txtTitle;
        ImageView imageView;
    }
}
