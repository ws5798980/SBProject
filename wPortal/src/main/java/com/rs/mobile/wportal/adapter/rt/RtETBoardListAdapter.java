package com.rs.mobile.wportal.adapter.rt;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.rt.RtETBoardActivity;
import com.rs.mobile.wportal.activity.rt.RtMenuListActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Request;

public class RtETBoardListAdapter extends BaseAdapter {
	
	private Context mContext;
	
	private LayoutInflater mLayoutInflater;
	
	private JSONArray arr;
	
	private String groupId;
	
	private String restaurantCode;
	
	private RtETBoardActivity activity;
	
	private String groupMasterYN;
	
	private String groupFixYN;
	
	public RtETBoardListAdapter(Context context, JSONArray arr, String groupId, String restaurantCode, String groupMasterYN, String groupFixYN){
		
		this.mContext = context;
		
		this.activity = (RtETBoardActivity)mContext;
		
		this.arr = arr;
		
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.groupId = groupId;
		
		this.restaurantCode = restaurantCode;
		
		this.groupMasterYN = groupMasterYN;
		
		this.groupFixYN = groupFixYN;
		
	}
	
	public JSONArray getListItems() {
		
		if (arr == null) arr = new JSONArray();
		
		return arr;
		
	}
	
	public void setListItems(JSONArray arr) {
		
		this.arr = arr;
		
	}
	
	public void removeAllCart() {
		
		try {
			
			for (int i = 0; i < arr.length(); i++) {
			
				JSONObject item = arr.getJSONObject(i);
				
				item.put("count", 0);
				
				arr.put(i, item);
				
			}
			
			notifyDataSetChanged();
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	@Override
	public int getCount() {
		return arr == null ? 0 : arr.length();
	}

	@Override
	public Object getItem(int position) {
		
		try {
		
			return arr == null ? null : arr.getJSONObject(position);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		return null;
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final ViewHolder viewHolder;
		
		if(convertView == null){
			
			viewHolder = new ViewHolder();
			
			convertView = mLayoutInflater.inflate(com.rs.mobile.wportal.R.layout.list_item_rt_eating_together, null);

			viewHolder.icon_view = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.icon_view);
			
			viewHolder.nickName = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.nickName);
			
			viewHolder.tv_status_mine = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_status_mine);
			
			viewHolder.tv_choice_menu = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_choice_menu);
			
			viewHolder.tv_reset_menu = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_reset_menu);

			viewHolder.tv_delete_member = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_delete_member);
			
			viewHolder.menu_list_view = (LinearLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.menu_list_view);
			
			viewHolder.tv_empty_menu = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_empty_menu);
			
			viewHolder.tv_total_amount = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_total_amount);
			
			viewHolder.tv_total_amount_title = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.tv_total_amount_title);
			
			convertView.setTag(viewHolder);
			
		} else {
			
			viewHolder = (ViewHolder) convertView.getTag();
			
		}
		
		//Setting values
		final JSONObject item = (JSONObject) getItem(position);
		
		try {
			
			ImageUtil.drawImageFromUri(item.getString("iconImg"), viewHolder.icon_view);
			
			viewHolder.icon_view.setCircle(true);
			
			viewHolder.nickName.setText(item.getString("nickName"));
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		try {
			
			//본인 확인
			String myYN = item.getString("myYN");
			
			//메뉴 고르기
			String orderYN = item.getString("orderYN");
			
			//주문을 완료한 경우
			if (orderYN != null && orderYN.equals("Y")) {
				/*
				 * 주문이 완된 경우에 
				 * 1. 메뉴 리스트를 보여준다
				 * 2. 총금액을 보여준다
				 */
				
				viewHolder.menu_list_view.setVisibility(View.VISIBLE);
				
				viewHolder.tv_total_amount.setVisibility(View.VISIBLE);
				
				viewHolder.tv_total_amount_title.setVisibility(View.VISIBLE);
				
				viewHolder.tv_empty_menu.setVisibility(View.GONE);
				
				viewHolder.menu_list_view.removeAllViews();
				
				JSONArray OrderList = item.getJSONArray("OrderList");
				
				for (int i = 0; i < OrderList.length(); i++) {
					
					JSONObject orderItem = OrderList.getJSONObject(i);
					
					LinearLayout listItem = (LinearLayout) mLayoutInflater.inflate(com.rs.mobile.wportal.R.layout.list_item_rt_participant_oreder, null);
					
					TextView menu_name = (TextView)listItem.findViewById(com.rs.mobile.wportal.R.id.menu_name);
					
					TextView menu_count = (TextView)listItem.findViewById(com.rs.mobile.wportal.R.id.menu_count);
					
					TextView menu_amount = (TextView)listItem.findViewById(com.rs.mobile.wportal.R.id.menu_amount);
					
					menu_name.setText(orderItem.getString("itemName"));
					
					menu_count.setText("X" + orderItem.getString("quantity"));
					
					menu_amount.setText(orderItem.getString("amount") + mContext.getString(com.rs.mobile.wportal.R.string.money));
					
					viewHolder.menu_list_view.addView(listItem);
					
				}
				
				viewHolder.menu_list_view.invalidate();
				
				viewHolder.tv_total_amount.setText(item.getString("amountSum") + mContext.getString(com.rs.mobile.wportal.R.string.money));
							
			} else {
				
				viewHolder.menu_list_view.removeAllViews();
				
				viewHolder.menu_list_view.setVisibility(View.GONE);

				viewHolder.tv_total_amount.setVisibility(View.GONE);
				
				viewHolder.tv_total_amount_title.setVisibility(View.GONE);
				
				viewHolder.tv_empty_menu.setVisibility(View.VISIBLE);

			}
			
			/*
			 * 오더가 생성된 경우
			 * 
			 * 메뉴 추가 주문, 메뉴 초기화, 멤버 삭제가 불가능하다
			 */
			if (groupFixYN != null && groupFixYN.endsWith("Y")) {
				
				viewHolder.tv_choice_menu.setVisibility(View.GONE);
				
				viewHolder.tv_status_mine.setVisibility(View.GONE);
				
				viewHolder.tv_reset_menu.setVisibility(View.GONE);
				
				viewHolder.tv_delete_member.setVisibility(View.GONE);
				
			} else {
			
				//본인인 경우
				if (myYN != null && myYN.equals("Y")) {
					
					viewHolder.tv_choice_menu.setVisibility(View.VISIBLE);
					
					viewHolder.tv_status_mine.setVisibility(View.VISIBLE);
	
					if (orderYN != null && orderYN.equals("Y")) {
						
						viewHolder.tv_reset_menu.setVisibility(View.VISIBLE);
						
					} else {
						
						viewHolder.tv_reset_menu.setVisibility(View.GONE);
						
					}
					
					viewHolder.tv_delete_member.setVisibility(View.GONE);
					
				} else {
					
					viewHolder.tv_choice_menu.setVisibility(View.GONE);
					
					viewHolder.tv_status_mine.setVisibility(View.GONE);
					
					viewHolder.tv_reset_menu.setVisibility(View.GONE);
					
					//마스터인 경우 본인이 아닌경우에 맴버를 삭제 할수 있다
					if (groupMasterYN.endsWith("Y")) {
						
						viewHolder.tv_delete_member.setVisibility(View.VISIBLE);
						
					} else {
						
						viewHolder.tv_delete_member.setVisibility(View.GONE);
						
					}
					
				}
			
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		try {
			
			//메뉴 주문 여부
			
			String orderYN = item.getString("orderYN");
			
			if (orderYN != null && orderYN.equals("N")) {
				
				viewHolder.tv_empty_menu.setVisibility(View.VISIBLE);
				
				viewHolder.tv_total_amount.setVisibility(View.GONE);
				
				viewHolder.tv_total_amount_title.setVisibility(View.GONE);
				
			} else {
				
				viewHolder.tv_empty_menu.setVisibility(View.GONE);
				
				viewHolder.tv_total_amount.setVisibility(View.VISIBLE);
				
				viewHolder.tv_total_amount_title.setVisibility(View.VISIBLE);
				
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
		//멤버삭제
		viewHolder.tv_delete_member.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {

					deleteMember(groupId, item.getString("memberID"));
					
				} catch (Exception e) {
					
					L.e(e);
					
				}
				
			}
		});
		
		//메뉴 초기화
		viewHolder.tv_reset_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {

					resetMenu(item.getString("memberID"), groupId);
					
				} catch (Exception e) {
					
					L.e(e);
					
				}
				
			}
		});
		
		//메뉴 선택
		viewHolder.tv_choice_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {

					Intent i = new Intent(mContext, RtMenuListActivity.class);
					i.putExtra("restaurantCode", restaurantCode);
					i.putExtra("from", "0");
					i.putExtra("groupId", groupId);
					mContext.startActivity(i);
					
				} catch (Exception e) {
					
					L.e(e);
					
				}
				
			}
		});

		return convertView;
	}
	
	private class ViewHolder {

		public WImageView icon_view;
		
		public TextView nickName;
		
		//본인 여부 icon	
		public TextView tv_status_mine;
		
		//메뉴선택 버튼
		public TextView tv_choice_menu;
		
		//메뉴 초기화
		public TextView tv_reset_menu;
		
		//멤버 삭제
		public TextView tv_delete_member;
		
		//주문한 리스트
		public LinearLayout menu_list_view;
		
		public TextView tv_empty_menu;
		
		//총 금액
		public TextView tv_total_amount;
		
		public TextView tv_total_amount_title;
		
	}

	/**
	 * 메뉴 초기화
	 * @param groupMemberId
	 * @param groupId
	 */
	public void resetMenu(String groupMemberId, String groupId) {

		try {
		
			OkHttpHelper helper = new OkHttpHelper(mContext);
	
			HashMap<String, String> params = new HashMap<String, String>();
	
			params.put("", "");
	
			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
	
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
				}
	
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub
	
					try {
	
						L.d(all_data);
	
						String status = data.getString("status");
	
						if (status != null && status.equals("1")) {
							
							activity.initDates();
							
						}
						
						T.showToast(mContext, data.getString("msg"));
	
					} catch (Exception e) {
	
						L.e(e);
	
					}
	
				}
	
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
	
				}
			}, Constant.BASE_URL_RT + Constant.RT_RESET_MENU + "?groupMemberId=" + S.getShare(mContext, C.KEY_REQUEST_MEMBER_ID, "")
					+ "&groupId=" + groupId + "&token=" + S.getShare(mContext, C.KEY_JSON_TOKEN, ""), params);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}
	
	/**
	 * 멤버삭제
	 * @param groupId
	 * @param cancelMemberId
	 */
	public void deleteMember(String groupId, String cancelMemberId) {

		try {
		
			OkHttpHelper helper = new OkHttpHelper(mContext);
	
			HashMap<String, String> params = new HashMap<String, String>();
	
			params.put("", "");
	
			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
	
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
				}
	
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub
	
					try {
	
						L.d(all_data);
	
						String status = data.getString("status");
	
						if (status != null && status.equals("1")) {
							
							activity.initDates();
							
						}
						
						T.showToast(mContext, data.getString("msg"));
	
					} catch (Exception e) {
	
						L.e(e);
	
					}
	
				}
	
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
	
				}
			}, Constant.BASE_URL_RT + Constant.RT_DELETE_MEMBER + "?memberId=" + S.getShare(mContext, C.KEY_REQUEST_MEMBER_ID, "")
					+ "&groupId=" + groupId + "&cancelMemberId=" + cancelMemberId +"&token=" + S.getShare(mContext, C.KEY_JSON_TOKEN, ""), params);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}
	
}
