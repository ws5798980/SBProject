package com.rs.mobile.wportal.view.kr;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.kr.ListActivity;
import com.rs.mobile.wportal.activity.kr.SettingActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class SettingView extends RelativeLayout {

	public boolean isInit = false;

	private Context context;

	private BaseActivity activity;

	private WImageView iconView;

	private TextView nameTextView;

	private LinearLayout vodPurcharBtn;

	private LinearLayout playPurcharBtn;

	private LinearLayout votePurcharBtn;

	private TextView vodPurcharTextView;

	private TextView playPurcharTextView;

	private TextView votePurcharTextView;

	private LinearLayout setting01Btn;

	private LinearLayout setting02Btn;

	private LinearLayout setting03Btn;

	private LinearLayout setting04Btn;

	private LinearLayout setting05Btn;

	private LinearLayout setting06Btn;

	public SettingView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		initView(context);

	}

	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		initView(context);

	}

	public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub

		initView(context);

	}

	public void initView(final Context context) {

		try {

			this.context = context;

			activity = (BaseActivity) context;

			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			final View v = inflator.inflate(com.rs.mobile.wportal.R.layout.layout_kr_setting, null);

			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));

			iconView = (WImageView) v.findViewById(com.rs.mobile.wportal.R.id.icon_image_view);

			nameTextView = (TextView) v.findViewById(com.rs.mobile.wportal.R.id.name_text_view);

			nameTextView.setText(S.get(context, C.KEY_SHARED_KNICK_NAME));

			nameTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					UtilClear.CheckLogin(context, new UtilCheckLogin.CheckError() {

						@Override
						public void onError() {
						}

					});

				}
			});

			vodPurcharBtn = (LinearLayout) v.findViewById(com.rs.mobile.wportal.R.id.vod_purchar_btn);
			vodPurcharBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(context, ListActivity.class);
					i.putExtra(C.KEY_JSON_TYPE, ListActivity.TYPE_VOD_LIST);
					i.putExtra("title",
							getResources().getString(com.rs.mobile.wportal.R.string.kr_vod_purchar));
					context.startActivity(i);

				}
			});

			playPurcharBtn = (LinearLayout) v
					.findViewById(com.rs.mobile.wportal.R.id.play_purchar_btn);
			playPurcharBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(context, ListActivity.class);
					i.putExtra(C.KEY_JSON_TYPE,
							ListActivity.TYPE_PLAY_TICKET_LIST);
					i.putExtra("title",
							getResources().getString(com.rs.mobile.wportal.R.string.kr_play_purchar));
					context.startActivity(i);

				}
			});

			votePurcharBtn = (LinearLayout) v
					.findViewById(com.rs.mobile.wportal.R.id.vote_purchar_btn);
			votePurcharBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// Intent i = new Intent(context, ListActivity.class);
					// i.putExtra(C.KEY_JSON_TYPE,
					// ListActivity.TYPE_MOVIE_TICKET_LIST);
					// i.putExtra("title",
					// getResources().getString(R.string.kr_vote_purchar));
					// context.startActivity(i);

				}
			});

			vodPurcharTextView = (TextView) v
					.findViewById(com.rs.mobile.wportal.R.id.vod_purchar_text_view);

			playPurcharTextView = (TextView) v
					.findViewById(com.rs.mobile.wportal.R.id.play_purchar_text_view);

			votePurcharTextView = (TextView) v
					.findViewById(com.rs.mobile.wportal.R.id.vote_purchar_text_view);

			setting01Btn = (LinearLayout) v.findViewById(com.rs.mobile.wportal.R.id.setting_01_btn);
			setting01Btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(context, ListActivity.class);
					i.putExtra(C.KEY_JSON_TYPE, ListActivity.TYPE_BOOKMARK_LIST);
					i.putExtra(
							"title",
							getResources().getString(
									com.rs.mobile.wportal.R.string.kr_setting_btn_001));
					context.startActivity(i);

				}
			});

			setting02Btn = (LinearLayout) v.findViewById(com.rs.mobile.wportal.R.id.setting_02_btn);
			setting02Btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(context, ListActivity.class);
					i.putExtra(C.KEY_JSON_TYPE, ListActivity.TYPE_HISTORY_LIST);
					i.putExtra(
							"title",
							getResources().getString(
									com.rs.mobile.wportal.R.string.kr_setting_btn_002));
					context.startActivity(i);

				}
			});

			setting03Btn = (LinearLayout) v.findViewById(com.rs.mobile.wportal.R.id.setting_03_btn);
			setting03Btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(context, ListActivity.class);
					i.putExtra(C.KEY_JSON_TYPE, ListActivity.TYPE_LOCAL_LIST);
					i.putExtra(
							"title",
							getResources().getString(
									com.rs.mobile.wportal.R.string.kr_setting_btn_003));
					context.startActivity(i);

				}
			});

			setting04Btn = (LinearLayout) v.findViewById(com.rs.mobile.wportal.R.id.setting_04_btn);
			setting04Btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(context, ListActivity.class);
					i.putExtra(C.KEY_JSON_TYPE, ListActivity.TYPE_MESSAGE_LIST);
					i.putExtra(
							"title",
							getResources().getString(
									com.rs.mobile.wportal.R.string.kr_setting_btn_004));
					context.startActivity(i);

				}
			});

			setting05Btn = (LinearLayout) v.findViewById(com.rs.mobile.wportal.R.id.setting_05_btn);
			setting05Btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(context, ListActivity.class);
					i.putExtra(C.KEY_JSON_TYPE, ListActivity.TYPE_PURCHAR_LIST);
					i.putExtra(
							"title",
							getResources().getString(
									com.rs.mobile.wportal.R.string.kr_setting_btn_005));
					context.startActivity(i);

				}
			});

			setting06Btn = (LinearLayout) v.findViewById(com.rs.mobile.wportal.R.id.setting_06_btn);
			setting06Btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(context, SettingActivity.class);
					context.startActivity(i);

				}
			});

			addView(v);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void searchPurchar() {

		try {

			isInit = true;

			draw();

		} catch (Exception e) {

			L.e(e);

			isInit = false;

		}

	}

	public void draw() {

		try {

			activity.showProgressBar();

			OkHttpHelper helper = new OkHttpHelper(activity);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_REQUEST_MEMBER_ID,
					S.getShare(activity, C.KEY_REQUEST_MEMBER_ID, ""));

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription,
						final JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						String imgPath = data.getString("imagePath");

						Uri uri = Uri.parse(imgPath);

						RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
								iconView.getWidth(), iconView.getHeight());

						lp.addRule(RelativeLayout.ALIGN_PARENT_TOP,
								RelativeLayout.TRUE);

						lp.addRule(RelativeLayout.CENTER_HORIZONTAL,
								RelativeLayout.TRUE);

						lp.setMargins(0, context.getResources()
								.getDimensionPixelSize(com.rs.mobile.wportal.R.dimen.marginx6), 0, 0);

						ImageUtil.drawIamge(iconView, uri, lp);

						nameTextView.setText(data
								.getString(C.KEY_JSON_NICK_NAME));

						S.set(context, C.KEY_SHARED_KNICK_NAME,
								data.getString(C.KEY_JSON_NICK_NAME));

						HashMap<String, String> params = new HashMap<String, String>();

						params.put("userId", S.getShare(context,
								C.KEY_REQUEST_MEMBER_ID, ""));

						OkHttpHelper helper = new OkHttpHelper(context);
						helper.addGetRequest(new OkHttpHelper.CallbackLogic() {

							@Override
							public void onNetworkError(Request request,
									IOException e) {
								// TODO Auto-generated method stub

								isInit = false;

							}

							@Override
							public void onBizSuccess(
									String responseDescription,
									JSONObject data, String flag) {
								// TODO Auto-generated method stub

								try {

									data = data.getJSONObject(C.KEY_JSON_DATA);

									vodPurcharTextView.setText(data
											.getString(C.KEY_JSON_VIDEOS));

									playPurcharTextView.setText(data
											.getString(C.KEY_JSON_TICKETS));

									votePurcharTextView.setText(data
											.getString(C.KEY_JSON_VOTES));

								} catch (Exception e) {

									L.e(e);

									isInit = false;

								}

							}

							@Override
							public void onBizFailure(
									String responseDescription,
									JSONObject data, String responseCode) {
								// TODO Auto-generated method stub

								isInit = false;

							}
						}, Constant.BASE_URL_KR + Constant.KR_MY_PURCHAR,
								params);

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription,
						JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, C.BASE_URL + Constant.PERSNAL_GET_INFO, params);

		} catch (Exception e) {

			L.e(e);

			activity.hideProgressBar();

		}

	}

}