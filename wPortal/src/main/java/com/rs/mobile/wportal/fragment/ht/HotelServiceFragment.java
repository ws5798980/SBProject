package com.rs.mobile.wportal.fragment.ht;

import java.util.List;

import com.rs.mobile.wportal.biz.ht.HotelService;
import com.rs.mobile.wportal.view.FlowGroupView;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.ht.HtHotelntroduceActivity;
import com.rs.mobile.wportal.view.MenuButtonView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class HotelServiceFragment extends Fragment{
	   public HotelServiceFragment(List<HotelService> list, String HotelInfoID) {
		super();
		this.list = list;
		this.HotelInfoID=HotelInfoID;
	}
	private List<HotelService> list;
	   private FlowGroupView fv;
	   private String HotelInfoID;
	   private Activity context=getActivity();
           @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	   
        	View view=inflater.inflate(R.layout.fragment_ht_service, container, false);
        	 fv=(FlowGroupView)view.findViewById(R.id.fv);
        	 
            
        	 for (int i = 0; i < list.size(); i++) {
			 MenuButtonView v=new MenuButtonView(getContext())	;
            
		       v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			   ViewGroup.MarginLayoutParams p=new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
			   p.setMargins(StringUtil.dip2px(getActivity(), 5), 0, StringUtil.dip2px(getActivity(), 5), 0);
			   v.setText(list.get(i).getServiceDetailName());
				v.setIcon(Uri.parse(list.get(i).getPicUrl()));
				
				v.setIconParam(BaseActivity.get_windows_width(getActivity())/10, BaseActivity.get_windows_width(getActivity())/10);
				fv.addView(v ,p);
				fv.requestLayout();
		}
        	 view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle bundle=new Bundle();
					bundle.putString("HotelInfoID", HotelInfoID);
					PageUtil.jumpTo(getActivity(), HtHotelntroduceActivity.class,bundle);
				}
			});
        	return view;
        	
        }
        
       @Override
    public void onDestroyView() {
    	// TODO Auto-generated method stub
    	super.onDestroyView();
    }
}
