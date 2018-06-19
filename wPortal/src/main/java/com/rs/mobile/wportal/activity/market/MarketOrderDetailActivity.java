package com.rs.mobile.wportal.activity.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.zxing.WriterException;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.EncodingHandler;
import com.rs.mobile.wportal.activity.market.util.CommonAdapter;
import com.rs.mobile.wportal.activity.market.util.ListGood;
import com.rs.mobile.wportal.activity.market.util.ViewHolder;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Request;

public class MarketOrderDetailActivity extends BaseActivity {
	private ListView lv;
	private TextView order_num;
	private TextView order_amout;
	private TextView order_date;
	private String baseUrl;
	private List<ListGood> listData = new ArrayList<>();
	private CommonAdapter<ListGood> adapter;
	private LinearLayout qr_line;
	private ImageView qr_img;
	private boolean isMarket;
	private String order_numS;
	private String order_amoutS;
	private String order_dateS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_mk_order_detail);
		baseUrl = Constant.BASE_URL_ORDER + Constant.MK_ORDERDETAIL;
		isMarket = getIntent().getBooleanExtra("isMarket", false);
		order_amoutS = getIntent().getStringExtra("order_amout");
		order_dateS = getIntent().getStringExtra("order_date");
		order_numS = getIntent().getStringExtra("order_num");
		initView();
		initData();
	}

	private void initView() {
		findViewById(com.rs.mobile.wportal.R.id.mk_car_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		lv = (ListView) findViewById(com.rs.mobile.wportal.R.id.lv);
		order_num = (TextView) findViewById(com.rs.mobile.wportal.R.id.order_num);
		order_num.setText(order_numS);
		order_date = (TextView) findViewById(com.rs.mobile.wportal.R.id.order_date);
		order_date.setText(order_dateS);
		order_amout = (TextView) findViewById(com.rs.mobile.wportal.R.id.order_amout);
		order_amout.setText(order_amoutS);
		qr_line = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.qr_line);
		qr_img = (ImageView) findViewById(com.rs.mobile.wportal.R.id.qr_img);

		if (isMarket) {
			qr_line.setVisibility(View.GONE);
		} else {
			try {
				qr_img.setImageBitmap(
						EncodingHandler.createQRCode(order_numS, get_windows_width(getApplicationContext()) / 2));
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		adapter = new CommonAdapter<ListGood>(this, listData, com.rs.mobile.wportal.R.layout.list_item_mk_order_detail) {

			@Override
			public void convert(ViewHolder holder, ListGood t, int position, View convertView) {
				// TODO Auto-generated method stub
				WImageView imageView = holder.getView(com.rs.mobile.wportal.R.id.mk_goods_img);
				ImageUtil.drawImageFromUri(t.getItem_image(), imageView);
				holder.setText(com.rs.mobile.wportal.R.id.item_name, t.getItem_name());
				holder.setText(com.rs.mobile.wportal.R.id.unit_price, getString(com.rs.mobile.wportal.R.string.rmb) + t.getOrder_p());
				holder.setText(com.rs.mobile.wportal.R.id.goods_num, "x" + t.getOrder_q());
				holder.setText(com.rs.mobile.wportal.R.id.toatle_money, getString(com.rs.mobile.wportal.R.string.rmb) + t.getOrder_o());
			}
		};
		lv.setAdapter(adapter);
	}

	private void initData() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(this);
		HashMap<String, String> params = new HashMap<>();
		params.put("lang_type", "2");
		params.put("order_num", order_numS);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					if (data.getString("status").equals("1")) {
						JSONArray array = data.getJSONArray("orderDetail");
						for (int i = 0; i < array.length(); i++) {
							listData.add(ListGood.setGood(array.getJSONObject(i)));
						}
						adapter.setDatas(listData);
						setListViewHeight(lv);
					} else {
						t(data.getString("msg"));
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, baseUrl, params);

	}
}
