package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.view.rt.autolabel.AutoLabelUI;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.biz.rt.SellerTypeCondition;
import com.rs.mobile.wportal.view.rt.autolabel.Label;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import okhttp3.Request;

public class RtSearchSellerActivity extends BaseActivity implements OnClickListener , OnQueryTextListener , AutoLabelUI.OnLabelClickListener {
	
	private Toolbar toolbar;
	private SearchView sv_search;
	private TextView tv_cancel;
	
	private ImageView iv_gc;
	
	private AutoLabelUI al_lables;
	
	private int divCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_searchseller);
		divCode = getIntent().getIntExtra(C.EXTRA_KEY_DIVCODE, 1);
		initToolbar();
		initViews();
		initListeners();
		initDatas();
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		sv_search = (SearchView) findViewById(com.rs.mobile.wportal.R.id.sv_search);
		tv_cancel = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_cancel);
		setSupportActionBar(toolbar);
	}
	
	private void initViews() {
		iv_gc = (ImageView) findViewById(com.rs.mobile.wportal.R.id.iv_gc);
		al_lables = (AutoLabelUI) findViewById(com.rs.mobile.wportal.R.id.al_lables);
	}
	
	private void initListeners(){
		tv_cancel.setOnClickListener(this);
		iv_gc.setOnClickListener(this);
		sv_search.setOnQueryTextListener(this);
		al_lables.setOnLabelClickListener(this);
	}	
	
	private void initDatas(){
		getRecommandKeyword();
	}
	
	private void getRecommandKeyword(){
		OkHttpHelper helper = new OkHttpHelper(RtSearchSellerActivity.this);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("", "");
		helper.addPostRequest(new OkHttpHelper.CallbackLogic() {
			
			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data,
					String flag) {
//				{"status":1,"msg":"success","data":[{"Keyword":"历史搜索—火锅"},{"Keyword":"泰国椰子"},{"Keyword":"羊排"},{"Keyword":"乌鸡腿 "},{"Keyword":"澳洲车厘子"},{"Keyword":"土鸡腿"}]}
				// error
				try {
					if (data.has("Message")
							|| (data.has("status") && data.getInt("status") != 1)) {

					}
					// success
					else {
						al_lables.removeAllViews();
						JSONArray keys = data.getJSONArray("data");
						for (int i = 0; i < keys.length(); i++) {
							final String key = keys.getJSONObject(i).getString("Keyword");
							al_lables.addLabel(key);
						}
					}
				} catch (JSONException e) {
					// TODO: handle exception
					e(e);
					
				}
			}
			
			@Override
			public void onBizFailure(String responseDescription, JSONObject data,
					String flag) {
				// TODO Auto-generated method stub
				
			}
		}, Constant.BASE_URL_RT + Constant.RT_RECOMMANDKEYWORD , params);
	}
	
//	private void sendToSearch(int divCode , String searchValue){
//		OkHttpHelper helper = new OkHttpHelper(RtSearchSellerActivity.this);
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("divCode" , divCode + "");
//		params.put("searchValue", searchValue);
//		params.put("currentPage", 1+"");
//		helper.addPostRequest(new CallbackLogic() {
//			
//			@Override
//			public void onNetworkError(Request request, IOException e) {
//				T.showToast(RtSearchSellerActivity.this, e.getMessage());
//			}
//			
//			@Override
//			public void onBizSuccess(String responseDescription, JSONObject data,
//					String flag) {
//				try {
//					//error
//					if(data.has("Message") || (data.has("status") && data.getInt("status") != 1)){
//						
//					}
//					//success
//					else{
//						JSONObject jsonData = data.getJSONObject("data");
//						
//						
//						
//					}
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//			@Override
//			public void onBizFailure(String responseDescription, JSONObject data,
//					String flag) {
//				// TODO Auto-generated method stub
//				
//			}
//		}, C.BASE_URL_RT + C.RT_RESTAURANT_LIST, params);
//
//	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case com.rs.mobile.wportal.R.id.tv_cancel:
			onBack();
			break;
			
		case com.rs.mobile.wportal.R.id.iv_gc:
			
			break;
			
		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		onBack();
	}
	
	public void onBack(){
		finish();
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		if(!TextUtils.isEmpty(arg0)){
			jumpToSellerList(divCode, arg0);
		}
		return true;
	}
	
	@Override
	public void onClickLabel(View v) {
		Label label = (Label) v;
		jumpToSellerList(divCode , label.getText());
	}
	
	private void jumpToSellerList(int divCode , String searchValue){
		Intent searchSellerInListIntent = new Intent(this , RtSellerListActivity.class);
		SellerTypeCondition sellerTypeCondition = new SellerTypeCondition();
		sellerTypeCondition.setDivCode(divCode);
		sellerTypeCondition.setSearchValue(searchValue);
		searchSellerInListIntent.putExtra(C.EXTRA_KEY_SELLERTYPE_CONDITION, sellerTypeCondition);
		startActivity(searchSellerInListIntent);
		finish();
	}

}
