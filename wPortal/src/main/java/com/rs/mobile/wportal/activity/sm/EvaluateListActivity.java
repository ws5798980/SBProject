package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class EvaluateListActivity extends BaseActivity {
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private ListView lv;
	private ArrayList<ShoppingCart> listData;
	private CustomAadpter aadpter;
	private String order_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluatelist);

		order_code = getIntent().getStringExtra("order_code");
		initToolbar();

		lv = (ListView) findViewById(R.id.lv);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		iv_back = (LinearLayout) findViewById(R.id.iv_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_title.setText("评价");
		setSupportActionBar(toolbar);
	}

	private class CustomAadpter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			if (convertView == null) {

				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_evluate_commit,
						parent, false);

			}
			WImageView img_goods = (WImageView) convertView.findViewById(R.id.img_goods);
			TextView good_name = (TextView) convertView.findViewById(R.id.good_name);
			TextView good_num = (TextView) convertView.findViewById(R.id.good_num);
			TextView text_evaluate = (TextView) convertView.findViewById(R.id.text_evaluate);
			text_evaluate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(EvaluateListActivity.this, SmEvaluateActivity.class);
					intent.putExtra("order_code", order_code);
					ArrayList<ShoppingCart> data = new ArrayList<>();
					data.add(listData.get(position));
					intent.putParcelableArrayListExtra("goods", data);
					startActivity(intent);
				}
			});
			ImageUtil.drawImageFromUri(listData.get(position).getimgurl(), img_goods);
			good_name.setText(listData.get(position).getName());
			good_num.setText("x" + listData.get(position).getNum());
			return convertView;
		}

	}

	private void initData() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(EvaluateListActivity.this);
		HashMap<String, String> paramsKeyValue = new HashMap<>();
		paramsKeyValue.put("order_num", order_code);
		okHttpHelper.addSMPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				try {
					if (data.get("status").toString().equals("1")) {
						JSONArray array = data.getJSONArray("data");
						listData = new ArrayList<>();
						for (int i = 0; i < array.length(); i++) {
							JSONObject object = array.getJSONObject(i);
							ShoppingCart shoppingCart = new ShoppingCart(object.getString("item_code"),
									object.getString("item_name"),
									Float.parseFloat(object.get("item_price").toString()),
									object.getString("image_url"), object.getInt("item_quantity"), false,
									object.getString("stock_unit"), object.getString("div_code"), "");
							listData.add(shoppingCart);
						}
						aadpter = new CustomAadpter();
						lv.setAdapter(aadpter);
						if (listData.size() == 0) {
							finish();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {

			}
		}, Constant.SM_BASE_URL + Constant.GetOrderPendingAssessOfList, paramsKeyValue);
		;
	}
}
