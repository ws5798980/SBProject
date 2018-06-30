package com.rs.mobile.wportal.adapter.xsgr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.biz.xsgr.CommodityDetail;

import java.util.List;


public class FlavorGridViewAdapter extends BaseAdapter {

    private List<CommodityDetail.DataBean.FlavorBean> mItemText = null;
    private Context context = null;
    private LayoutInflater inflater = null;

    public FlavorGridViewAdapter(Context context, List<CommodityDetail.DataBean.FlavorBean> text) {
        super();
        this.mItemText = text;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public final class ViewHolder {

        public TextView mFlacorName;
        public ImageView mImageView;
        private TextView mSpecPrice;
        private LinearLayout mFlavorLayout;

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
            convertView=inflater.inflate(R.layout.flavor_grid_item, null, false);
            holder.mImageView=(ImageView) convertView.findViewById(R.id.add_flavor);
            holder.mFlacorName=(TextView) convertView.findViewById(R.id.flavor_name);
            holder.mFlavorLayout = (LinearLayout) convertView.findViewById(R.id.flavor_layout);
            if (position!=mItemText.size()){
                holder.mFlacorName.setText(mItemText.get(position).getFlavorName());
            }
            convertView.setTag(holder);
//        }
        if (position == mItemText.size()){
            holder.mFlavorLayout.setVisibility(View.GONE);
            holder.mImageView.setVisibility(View.VISIBLE);

        }

//        holder.mTextView.setText(context.getResources().getString(mItemText.get(position)));
        return convertView;
    }
}
