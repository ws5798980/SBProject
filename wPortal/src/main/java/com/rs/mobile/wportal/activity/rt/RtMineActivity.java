package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.rs.mobile.common.S;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.PointsActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

/**
 * 个人中心页
 * 
 * @author ZhaoYun
 * @date 2017-3-11
 */
public class RtMineActivity extends BaseActivity implements OnClickListener{
	
	//title
	private LinearLayout ll_message;
	private WImageView iv_head;
	private TextView tv_username;
	private TextView tv_provideinfo;
	
	//list
	private LinearLayout tv_rt_my_point;
	private LinearLayout tv_rt_mine_comments;
	private LinearLayout tv_rt_mine_coupon;
	private LinearLayout tv_rt_mine_favorates;
	private LinearLayout tv_rt_mine_helpcenter;
	private LinearLayout tv_rt_mine_customerservice;
	
	private TextView tv_rt_mine_point;
	private TextView tv_rt_mine_comments_count;
	private TextView tv_rt_mine_coupon_count;
	private TextView tv_rt_mine_favorates_count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rt_mine);
		initTitle();
		initViews();
		initListeners();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		initDatas();
	}

	private void initTitle() {
		ll_message = (LinearLayout) findViewById(R.id.ll_message);
		iv_head = (WImageView) findViewById(R.id.iv_head);
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_provideinfo = (TextView) findViewById(R.id.tv_provideinfo);
		
		iv_head.setCircle(true);
		
	}

	private void initViews() {
		tv_rt_my_point = (LinearLayout) findViewById(R.id.tv_rt_my_point);
		tv_rt_mine_comments = (LinearLayout) findViewById(R.id.tv_rt_mine_comments);
		tv_rt_mine_coupon = (LinearLayout) findViewById(R.id.tv_rt_mine_coupon);
		tv_rt_mine_favorates = (LinearLayout) findViewById(R.id.tv_rt_mine_favorates);
		tv_rt_mine_helpcenter = (LinearLayout) findViewById(R.id.tv_rt_mine_helpcenter);
		tv_rt_mine_customerservice = (LinearLayout) findViewById(R.id.tv_rt_mine_customerservice);
		tv_rt_mine_point = (TextView) findViewById(R.id.tv_rt_mine_point);
		tv_rt_mine_comments_count = (TextView) findViewById(R.id.tv_rt_mine_comments_count);
		tv_rt_mine_coupon_count = (TextView) findViewById(R.id.tv_rt_mine_coupon_count);
		tv_rt_mine_favorates_count = (TextView) findViewById(R.id.tv_rt_mine_favorates_count);
	}

	/**
	 * 尝试从本地提取用户数据
	 * 
	 * @author ZhaoYun
	 * @date 2017-3-18
	 */
	private void initDatas(){
		//TODO
		
		getPersnalInfo();
		
	}
	
	private void initListeners(){
		
		ll_message.setOnClickListener(this);
		tv_provideinfo.setOnClickListener(this);
		tv_rt_my_point.setOnClickListener(this);
		tv_rt_mine_comments.setOnClickListener(this);
		tv_rt_mine_coupon.setOnClickListener(this);
		tv_rt_mine_favorates.setOnClickListener(this);
		tv_rt_mine_helpcenter.setOnClickListener(this);
		tv_rt_mine_customerservice.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_message:
			
			break;
			
		case R.id.tv_provideinfo:
			
			break;
			
		case R.id.tv_rt_my_point:
			Intent pointIntent = new Intent(this , PointsActivity.class);
			startActivity(pointIntent);
			break;
			
		case R.id.tv_rt_mine_comments:
			Intent commentsIntent = new Intent(this , RtCommentsActivity.class);
			startActivity(commentsIntent);
			break;
		case R.id.tv_rt_mine_coupon:
			
			t(getString(R.string.ready));
			
//			Intent i = new Intent(this, RtCouponActivity.class);
//			startActivity(i);
			
			break;
		case R.id.tv_rt_mine_favorates:
			Intent favoratesIntent = new Intent(this , RtMyFavoriteActivity.class);
			startActivity(favoratesIntent);
			break;
			
		case R.id.tv_rt_mine_helpcenter:
			
			break;
			
		case R.id.tv_rt_mine_customerservice:
			Uri uri = Uri.parse("tel:" + "1234567890");
			Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
			startActivity(callIntent);
			break;

		default:
			break;
		}
	}
	
	public void getPersnalInfo() {

		try {

			showProgressBar();

			// nameTextView.setText(S.get(PersnalCenterActivity.this,
			// C.KEY_SHARED_KNICK_NAME));
			//
			// new GetBitmapAsyncTask().execute();

			OkHttpHelper helper = new OkHttpHelper(RtMineActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription,
						JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						String status = data.getString("status");
						
						if (status != null && status.equals("1")) {
						
							//{"status":1,"msg":"successada15e8c-b2c7-4c8a-b714-634fee9ab9cb|13640928872yc|13640928872","data":{"COUPONCOUNT":"0","POINTS":"1201.9672","COMMENTCOUNT":"0","IMG_PATH":"http://222.240.51.146:8488/wsRestaurant/wsProfile/18874097957.jpg","FAVORATECOUNT":"7","NICK_NAME":"JAKE"}}
							

							data = data.getJSONObject("data");
							
//							tv_username.setText(S.getShare(RtMineActivity.this, C.KEY_SHARED_PHONE_NUMBER, ""));
							

							tv_username.setText(data.getString("CUSTOM_ID"));
							S.setShare(RtMineActivity.this, C.KEY_SHARED_PHONE_NUMBER, data.getString("CUSTOM_ID"));
							
							tv_provideinfo.setText(data.getString("NICK_NAME"));
	
							String imgPath = data.getString("IMG_PATH");
	
							Uri uri = Uri.parse(imgPath);
	
							DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
							
							RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(displayMetrics.widthPixels/4, displayMetrics.widthPixels/4);
							
							rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
							
							rp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
							
							ImageUtil.drawIamge(iv_head, uri, rp);
							
							//FAVORATECOUNT":"7","COUPONCOUNT":"1","COMMENTCOUNT":"0"
	//						tv_rt_mine_comments_count.setText(text);
							
//							tv_rt_mine_point.setText("(" + data.getString("POINTS") + "P)");
							tv_rt_mine_point.setText("(" + data.getString("POINTS") + ")");
							tv_rt_mine_comments_count.setText("(" + data.getString("COMMENTCOUNT") + ")");
							tv_rt_mine_coupon_count.setText("(" + data.getString("COUPONCOUNT") + ")");
							tv_rt_mine_favorates_count.setText("(" + data.getString("FAVORATECOUNT") + ")");
						
						} else {
							
							t(data.getString("msg"));
							
						}

					} catch (Exception e) {

						L.e(e);

					}

					hideProgressBar();

				}

				@Override
				public void onBizFailure(String responseDescription,
						JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_RT  + Constant.RT_GET_MY_INFO + "?userID=" + S.getShare(RtMineActivity.this, C.KEY_REQUEST_MEMBER_ID, "") 
				+ "&token=" + S.getShare(RtMineActivity.this, C.KEY_JSON_TOKEN, ""), params);

		} catch (Exception e) {

			L.e(e);

			hideProgressBar();

		}

	}
	
}
