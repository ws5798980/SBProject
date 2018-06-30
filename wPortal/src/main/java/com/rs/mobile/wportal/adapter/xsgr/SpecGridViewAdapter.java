package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.biz.xsgr.CommodityDetail;

import java.util.List;


public class SpecGridViewAdapter extends BaseAdapter {

    private List<CommodityDetail.DataBean.SpecBean> mItemText = null;
    private Context context = null;
    private LayoutInflater inflater = null;

    public SpecGridViewAdapter(Context context, List<CommodityDetail.DataBean.SpecBean> text) {
        super();
        this.mItemText = text;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public final class ViewHolder {

        public TextView mSpecName;
        public ImageView mImageView;
        private TextView mSpecPrice;
        private LinearLayout mSpecLayout;

    }
    @Override
    public int getCount() {
        return mItemText.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
//        if(convertView != null){
//            holder=(ViewHolder) convertView.getTag();
//        }else {
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.spec_grid_item, null, false);
            holder.mImageView=(ImageView) convertView.findViewById(R.id.add_spec);
            holder.mSpecName=(TextView) convertView.findViewById(R.id.spec_name);
            holder.mSpecPrice=(TextView) convertView.findViewById(R.id.spec_price);
            holder.mSpecLayout = (LinearLayout) convertView.findViewById(R.id.spec_layout);
            if (position!=mItemText.size()){
                holder.mSpecName.setText(mItemText.get(position).getItem_name());
                holder.mSpecPrice.setText(mItemText.get(position).getItem_p());
            }
            convertView.setTag(holder);
//        }
        if (position == mItemText.size()){
            holder.mSpecLayout.setVisibility(View.GONE);
            holder.mImageView.setVisibility(View.VISIBLE);
//            holder.mImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }

//        holder.mTextView.setText(context.getResources().getString(mItemText.get(position)));
        return convertView;
    }
}
