package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.S;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.UtilCheckLogin.CheckError;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

/**
 * 
 * @author ZhaoYun
 * @date 2017-3-13
 */
public class RtCalvulaterActivity extends BaseActivity {

	/*
	 * xml
	 */
	private Toolbar toolbar;

	private TextView tv_title;

	private LinearLayout iv_back;

	private ListView list_view;

	private TextView total_amout_text_view;

	private TextView share_btn;

	private TextView close_btn;

	private String groupId;

	private String type;

	private String msg;

	private RtETBoardListAdapter adapter;

	private int calculteCount = 0;

	private double totalAmount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_rt_calculater);

		groupId = getIntent().getStringExtra("groupID");

		String roomId = getIntent().getStringExtra("roomId");

		if (roomId != null && !roomId.equals("") && !roomId.equals("null")) {

			type = "2";

		} else {

			type = "1";

		}

		initToolbar();

		initViews();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		initDates();

	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(R.id.toolbar);

			iv_back = (LinearLayout) findViewById(R.id.iv_back);

			tv_title = (TextView) findViewById(R.id.tv_title);

			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initViews() {

		try {

			tv_title.setText(getString(R.string.rt_1n_title));

			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			list_view = (ListView) findViewById(R.id.list_view);

			total_amout_text_view = (TextView) findViewById(R.id.total_amout_text_view);

			share_btn = (TextView) findViewById(R.id.share_btn);

			share_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						ArrayList<CalculaterModel> items = adapter.getItem();

						for (int i = 0; i < items.size(); i++) {

							if (items.get(i).isChecked == true) {

								if (i == 0) {

									msg = getString(R.string.rt_1n_title)
											+ "\n\n" + items.get(i).getName()
											+ " : " + items.get(i).getAmount();

								} else {

									msg = msg + "\n\n" + items.get(i).getName()
											+ " : " + items.get(i).getAmount();

								}

							}

						}

						// "https://api.shelongwang.com/appapi/pushapi" +
						// "?action=pushusermsg&senderphone=" +
						// S.getShare(RtCalvulaterActivity.this,
						// C.KEY_REQUEST_MEMBER_ID, "")
						// + "&targetid=" + groupId +"&type=" + type + "&msg=" +
						// msg, params);
						//
						OkHttpHelper helper = new OkHttpHelper(
								RtCalvulaterActivity.this);

						HashMap<String, String> params = new HashMap<String, String>();

						params.put("action", "pushusermsg");

						params.put("senderphone", S.getShare(
								RtCalvulaterActivity.this,
								C.KEY_REQUEST_MEMBER_ID, ""));

						params.put("targetid", groupId);

						params.put("type", type);

						params.put("msg", msg);

						helper.addPostRequest(new CallbackLogic() {

							@Override
							public void onNetworkError(Request request,
									IOException e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onBizSuccess(
									String responseDescription,
									JSONObject data, final String all_data) {
								// TODO Auto-generated method stub

								try {

									L.d(all_data);

									finish();

								} catch (Exception e) {

									L.e(e);

								}

							}

							@Override
							public void onBizFailure(
									String responseDescription,
									JSONObject data, String responseCode) {
								// TODO Auto-generated method stub

							}
						}, "https://api.dxbhtm.com/appapi/pushapi", params);

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			close_btn = (TextView) findViewById(R.id.close_btn);

			close_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					finish();

				}
			});

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void initDates() {

		try {
			UtilClear.CheckLogin(RtCalvulaterActivity.this, new CheckError() {

				@Override
				public void onError() {
					RtCalvulaterActivity.this.finish();
				}

			});

			OkHttpHelper helper = new OkHttpHelper(RtCalvulaterActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(
					new CallbackLogic() {

						@Override
						public void onNetworkError(Request request,
								IOException e) {

						}

						@Override
						public void onBizSuccess(String responseDescription,
								JSONObject data, final String all_data) {

							try {

								L.d(all_data);

								String status = data.getString("status");

								t(data.getString("msg"));

								if (status != null && status.equals("1")) {

									data = data.getJSONObject("data");

									initListView(data);

								}

							} catch (Exception e) {

								L.e(e);

							}

						}

						@Override
						public void onBizFailure(String responseDescription,
								JSONObject data, String responseCode) {
							// TODO Auto-generated method stub

						}
					},
					Constant.BASE_URL_RT
							+ Constant.RT_GET_ARR_GROUP_MEMBER
							+ "?memberId="
							+ S.getShare(RtCalvulaterActivity.this,
									C.KEY_REQUEST_MEMBER_ID, "")
							+ "&groupId="
							+ groupId
							+ "&token="
							+ S.getShare(RtCalvulaterActivity.this,
									C.KEY_JSON_TOKEN, ""), params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void initListView(JSONObject data) {

		try {

			JSONArray arr = data.getJSONArray("GroupMemberList");

			totalAmount = data.getDouble("totalAmount");

			calculteCount = arr.length();

			String persnalAmount = String.format("%.2f", totalAmount
					/ calculteCount);

			total_amout_text_view
					.setText(getString(R.string.rmb) + totalAmount);

			ArrayList<CalculaterModel> items = new ArrayList<CalculaterModel>();

			for (int i = 0; i < calculteCount; i++) {

				JSONObject obj = arr.getJSONObject(i);

				CalculaterModel model = new CalculaterModel("" + i,
						obj.getString("nickName"), obj.getString("iconImg"),
						getString(R.string.rmb) + persnalAmount, true);

				items.add(model);

			}

			if (type.equals("1")) {

				groupId = arr.getJSONObject(1).getString("memberID");

			}

			adapter = new RtETBoardListAdapter(RtCalvulaterActivity.this, items);

			list_view.setAdapter(adapter);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void redrawListView() {

		try {

			calculteCount = 0;

			ArrayList<CalculaterModel> items = adapter.getItem();

			for (int i = 0; i < items.size(); i++) {

				if (items.get(i).isChecked == true) {

					calculteCount++;

				}

			}

			String persnalAmount = String.format("%.2f", totalAmount
					/ calculteCount);

			for (int i = 0; i < items.size(); i++) {

				if (items.get(i).isChecked == true) {

					items.get(i).setAmount(
							getString(R.string.rmb) + persnalAmount);

				} else {

					items.get(i).setAmount("");

				}

			}

			adapter.notifyDataSetChanged();

		} catch (Exception e) {

			L.e(e);

		}

	}

	@Override
	public void onBackPressed() {
		onBack();
	}

	public void onBack() {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public class RtETBoardListAdapter extends BaseAdapter {

		private Context mContext;

		private LayoutInflater mLayoutInflater;

		private ArrayList<CalculaterModel> items;

		public RtETBoardListAdapter(Context context,
				ArrayList<CalculaterModel> items) {

			this.mLayoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			this.mContext = context;

			this.items = items;

		}

		public ArrayList<CalculaterModel> getItem() {

			return items;

		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {

			try {

				return items.get(position);

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
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			final ViewHolder viewHolder;

			if (convertView == null) {

				viewHolder = new ViewHolder();

				convertView = mLayoutInflater.inflate(
						R.layout.list_item_rt_calculater, null);

				viewHolder.icon_view = (WImageView) convertView
						.findViewById(R.id.icon_view);

				viewHolder.nick_name_text_view = (TextView) convertView
						.findViewById(R.id.nick_name_text_view);

				viewHolder.amount_text_view = (TextView) convertView
						.findViewById(R.id.amount_text_view);

				viewHolder.choice_check_box = (CheckBox) convertView
						.findViewById(R.id.choice_check_box);

				convertView.setTag(viewHolder);

			} else {

				viewHolder = (ViewHolder) convertView.getTag();

			}

			// Setting values
			final CalculaterModel item = (CalculaterModel) getItem(position);

			try {

				ImageUtil.drawImageFromUri(item.getImgUrl(),
						viewHolder.icon_view);

				viewHolder.nick_name_text_view.setText(item.getName());

				viewHolder.amount_text_view.setText(item.getAmount());

				viewHolder.choice_check_box.setChecked(item.isChecked);

				viewHolder.choice_check_box
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								items.get(position)
										.setChecked(
												viewHolder.choice_check_box
														.isChecked());

								redrawListView();

							}
						});

			} catch (Exception e) {

				L.e(e);

			}

			return convertView;
		}

		private class ViewHolder {

			public WImageView icon_view;

			public TextView nick_name_text_view;

			public TextView amount_text_view;

			public CheckBox choice_check_box;

		}

	}

	public class CalculaterModel {

		String id;

		String name;

		String imgUrl;

		String amount;

		boolean isChecked;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public boolean isChecked() {
			return isChecked;
		}

		public void setChecked(boolean isChecked) {
			this.isChecked = isChecked;
		}

		public CalculaterModel(String id, String name, String imgUrl,
				String amount, boolean isChecked) {
			super();
			this.id = id;
			this.name = name;
			this.imgUrl = imgUrl;
			this.amount = amount;
			this.isChecked = isChecked;
		}

	}

}