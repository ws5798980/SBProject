
package com.rs.mobile.wportal.activity.sm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.CaptureActivity;
import com.rs.mobile.common.activity.PointsActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.CaptureUtil;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.XsStoreDetailActivity;
import com.rs.mobile.wportal.activity.xsgr.XsStoreListActivity;
import com.rs.mobile.wportal.activity.xsgr.kfmemain;
import com.rs.mobile.wportal.adapter.ShopListGridListAdapter;
import com.rs.mobile.wportal.adapter.ShopListGridListAdapter2;
import com.rs.mobile.wportal.adapter.xsgr.XsStoreListAdapter;
import com.rs.mobile.wportal.entity.ListItem;
import com.rs.mobile.wportal.entity.StoreCateListEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class SmNew_shopcatList extends BaseActivity  implements TextView.OnClickListener{

	private LinearLayout btnBack;
	private XsStoreListAdapter mAdapter;
	private int mNextRequestPage=1;
	private String mOrderBy = "1";
	private String mLv1Position = "1";
	StoreCateListEntity entity;
	private TextView tvAddress;
	private LinearLayout btnMap, btnSearch;
	private List<ListItem> allcatlist = new ArrayList<>();
	private ShopListGridListAdapter2 shoplistcatAd;
	private GridView gridview,gridview2,gridview3,gridview4;
	private ImageView btnScan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_shopcatlist1);

		mLv1Position = getIntent().getStringExtra("lv1");

		initView();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initView() {

		btnBack = (LinearLayout) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(this);
		tvAddress = (TextView) findViewById(R.id.tv_address);
		tvAddress.setText(S.getShare(getApplicationContext(), "address_naver", ""));

		btnScan = (ImageView) findViewById(R.id.btnScan);
		btnScan.setOnClickListener(this);
		//btnMap = (LinearLayout) findViewById(R.id.btn_map);
		btnSearch = (LinearLayout) findViewById(R.id.btn_serch);
	//	btnMap.setOnClickListener(this);
		btnSearch.setOnClickListener(this);


		gridview = (GridView) findViewById(R.id.gridview);
		requestStoreCateList(mLv1Position,"","","1",1);

	}

	private void initgridview()
	{

		shoplistcatAd=new ShopListGridListAdapter2(this,allcatlist);
		//配置适配器
		gridview.setAdapter(shoplistcatAd);
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
											public void onItemClick(AdapterView adapterView, View view, int arg2, long arg3)
											{

													    String lv=allcatlist.get(arg2).getHeadline();
														Intent intent = new Intent(SmNew_shopcatList.this, XsStoreListActivity.class);
														intent.putExtra("lv1", mLv1Position);
														intent.putExtra("lv2", lv);
														//intent.putExtra("lv3", "");
														startActivity(intent);

											}
										}
		);
	}

	//获取二给分类数据
	private void requestStoreCateList(String customLv1, final String customLv2, final String customLv3, String orderBy, final int pg) {
		HashMap<String, String> params = new HashMap<>();
		params.put("lang_type", AppConfig.LANG_TYPE);
		params.put("div_code", S.get(SmNew_shopcatList.this, C.KEY_JSON_DIV_CODE));
		params.put("custom_code", S.get(SmNew_shopcatList.this, C.KEY_JSON_CUSTOM_CODE));
		params.put("token", S.get(SmNew_shopcatList.this, C.KEY_JSON_TOKEN));
		params.put("pg", ""+pg);
		params.put("pagesize", "5");
		params.put("custom_lev1", customLv1);
		params.put("custom_lev2", customLv2);
		params.put("custom_lev3", customLv3);
		params.put("latitude", ""+AppConfig.latitude);
		params.put("longitude", ""+AppConfig.longitude);
		params.put("order_by", orderBy);
		Log.i("123123", "pg="+pg);
		OkHttpHelper okHttpHelper = new OkHttpHelper(SmNew_shopcatList.this);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				entity = GsonUtils.changeGsonToBean(responseDescription, StoreCateListEntity.class);
				Log.i("123123", "responseDescription="+responseDescription);

			if(entity.data.size()>0) {
	         for (int i = 0; i < entity.data.size(); i++) {
		       ListItem l = new ListItem();
		       l.custom_name = entity.data.get(i).level_name;
		       l.setUrl(entity.data.get(i).image_url);
		       l.setHeadline(entity.data.get(i).custom_lev2);
		       allcatlist.add(l);
	          }
	          initgridview();
            }

			}
			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				mAdapter.loadMoreComplete();
				mAdapter.loadMoreEnd(true);
			}

			@Override
			public void onNetworkError(Request request, IOException e) {
				mAdapter.loadMoreComplete();
				mAdapter.loadMoreEnd(true);
			}
		}, Constant.XS_BASE_URL+"/StoreCate/requestStoreCate2List", GsonUtils.createGsonString(params));
	}


	@Override
	public void onClick(View v)
	{
		switch (v.getId()) {
			case R.id.btn_back:
				finish();
				break;
			case R.id.btn_serch:
				Intent intentSearch = new Intent(SmNew_shopcatList.this, SmSeachActivity.class);
				startActivity(intentSearch);
				break;
			case R.id.btnScan:
				Intent intentScan = new Intent(SmNew_shopcatList.this, CaptureActivity.class);
				startActivityForResult(intentScan, 0);
				break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		// 수행을 제대로 한 경우-----
		if(requestCode == 0) {
			if (resultCode == RESULT_OK && data != null) {
//                String result = data.getStringExtra("resultSetting");
				CaptureUtil.handleResultScaning(SmNew_shopcatList.this,
						data.getStringExtra("result"), "");


			}
			// 수행을 제대로 하지 못한 경우
			else if (resultCode == RESULT_CANCELED) {

			}
		} else if(requestCode == 1000) {
			if (resultCode == RESULT_OK) {
				String result = data.getStringExtra("result");
				AppConfig.address = result;
				//AddressTitle.setText(result);

			}
		} else if(requestCode == 1001) {
			if (resultCode == RESULT_OK) {
				String result = data.getStringExtra("result");
				//AddressTitle.setText(result);
			}
		}
		//--------
	}

}
