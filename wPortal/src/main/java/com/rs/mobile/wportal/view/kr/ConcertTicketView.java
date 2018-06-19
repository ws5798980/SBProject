package com.rs.mobile.wportal.view.kr;

import org.json.JSONObject;

import com.rs.mobile.common.L;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConcertTicketView extends LinearLayout{

	private Context context;
	
	private int count = 1;
	
	private TextView count_edit_text;
	
	private TextView ticket_kind;
	
	private JSONObject item;
	
	private OnCountChangeListener onCountChangeListener;
	
	public void setOnCountChangeListener(OnCountChangeListener onCountChangeListener) {
		
		this.onCountChangeListener = onCountChangeListener;
		
	}
	
	public interface OnCountChangeListener {
		
		void onChanged(int count);
		
	}
	
	public ConcertTicketView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public ConcertTicketView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public ConcertTicketView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}
	
	/**
	 * 초기화
	 * @param context
	 */
	public void initView(final Context context) {
		
		try {
		
			this.context = context;
			
			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View v = inflator.inflate(com.rs.mobile.wportal.R.layout.list_item_kr_concert_ticket, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			ticket_kind = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.ticket_kind);
			
			TextView m_btn = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.m_btn);
			m_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (count > 1) {
						
						if (onCountChangeListener != null)
							onCountChangeListener.onChanged(-1);
						
						count--;
						
						count_edit_text.setText(count + "");
						
					}

				}
			});
			count_edit_text = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.count_edit_text);
			
			TextView p_btn = (TextView)v.findViewById(com.rs.mobile.wportal.R.id.p_btn);
			p_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					count++;
					
					count_edit_text.setText(count + "");
					
					if (onCountChangeListener != null)
						onCountChangeListener.onChanged(1);
				
				}
			});
			
			addView(v);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void draw(JSONObject item) {
		
		try {
			
			this.item = item;
			
			ticket_kind.setText(item.getString("name") + " " + 
					item.getString("price") + 
					context.getResources().getString(com.rs.mobile.wportal.R.string.money) + "/" +
					context.getResources().getString(com.rs.mobile.wportal.R.string.kr_person_count));
			
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public int getTotalPrice() {
		
		try {
			
			return Integer.parseInt(item.getString("price")) * count;
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return 0;
		
	}
	
	public JSONObject getItem() {
		
		return item;
		
	}
	
}
