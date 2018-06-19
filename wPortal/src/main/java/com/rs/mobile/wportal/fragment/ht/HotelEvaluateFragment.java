package com.rs.mobile.wportal.fragment.ht;

import com.rs.mobile.wportal.activity.ht.HtEvaluateListActivity;
import com.rs.mobile.wportal.biz.ht.HotelRate;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.wportal.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class HotelEvaluateFragment extends Fragment{
	 public HotelEvaluateFragment(String HotelInfoID,HotelRate h) {
		super();
		this.h = h;
		this.HotelInfoID=HotelInfoID;
	}
	private TextView text_score;
	private TextView text_evaluate;
	private TextView text_name;
	private TextView text_content;
	private HotelRate h;
	private String HotelInfoID;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.fragment_ht_evaluate, container, false);
		text_score=(TextView)v.findViewById(R.id.text_score); 
		text_evaluate=(TextView)v.findViewById(R.id.text_evaluate);
		text_name=(TextView)v.findViewById(R.id.text_name);
		text_content=(TextView)v.findViewById(R.id.text_content);
		text_score.setText(h.getTotal_score()+"");
		text_evaluate.setText(h.getRatedName());
		text_name.setText(h.getUserName());
		text_content.setText(h.getRatedContext());
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle=new Bundle();
				bundle.putString("HotelInfoID", HotelInfoID);
				PageUtil.jumpTo(getActivity(), HtEvaluateListActivity.class,bundle);
			}
		});
		return v;
	}
    
    @Override
    public void onDestroyView() {
    	// TODO Auto-generated method stub
    	super.onDestroyView();
    }
}
