package com.rs.mobile.wportal.activity.ht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.FileUtil;
import com.rs.mobile.common.view.BringPhotoView;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.takephoto.PickOrTakeImageActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class HtEvaluateActivity extends BaseActivity {

	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private RatingBar rating_score;
	private TextView text_score;
	private RatingBar rating_001;
	private TextView text_001;
	private RatingBar rating_002;
	private TextView text_002;
	private RatingBar rating_003;
	private TextView text_003;
	private EditText editText;
	private LinearLayout linear_img;
	private TextView text_commit;
	private float score, score1, score2, score3, score4;
	private Context context;
	private LinearLayout linearLayout_img;
	private List<String> list = new ArrayList<String>();// 接收选择的图片的路劲
	private ImageView imageView;
	private RelativeLayout relativeLayout;
	private BringPhotoView bringPhotoView;
	private JSONArray arr_upload = new JSONArray();
	private String comment;
	private String orderId;
	private String imgUrl;
	private String bedType;
	private String arriveTime;
	private String leaveTime;
	private String order_amount;
	private RatingBar rating_004;
	private TextView text_004;
	private TextView text_price;
	private TextView text_type;
	private TextView text_time;
	private TextView text_name;
	private WImageView img_hotel;
	private ArrayList<String> picklist;
	private String HotelInfoID;
	private String hotelName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_ht_evaluate);
		context = this;
		orderId = getIntent().getStringExtra("orderId");
		HotelInfoID = getIntent().getStringExtra("HotelInfoID");
		hotelName = getIntent().getStringExtra("hotelName");
		imgUrl = getIntent().getStringExtra("imgUrl");
		bedType = getIntent().getStringExtra("bedType");
		arriveTime = getIntent().getStringExtra("arriveTime");
		leaveTime = getIntent().getStringExtra("leaveTime");
		order_amount = getIntent().getStringExtra("order_amount");
		initToolbar();
		initView();

	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					HtEvaluateActivity.this.finish();
				}
			});
			tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
			tv_title.setText(getString(com.rs.mobile.wportal.R.string.ht_text_076));
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initView() {
		img_hotel = (WImageView) findViewById(com.rs.mobile.wportal.R.id.img_hotel);
		text_name = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_name);
		text_type = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_type);
		text_time = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_time);
		text_price = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_price);
		ImageUtil.drawImageFromUri(imgUrl, img_hotel);
		text_name.setText(hotelName);
		text_type.setText(bedType);
		text_time.setText(arriveTime + getString(com.rs.mobile.wportal.R.string.ht_text_077) + leaveTime);
		text_price.setText(getString(com.rs.mobile.wportal.R.string.rmb) + order_amount);
		score = 0;
		score1 = 0;
		score2 = 0;
		score3 = 0;
		score4 = 0;
		rating_score = (RatingBar) findViewById(com.rs.mobile.wportal.R.id.rating_score);
		linearLayout_img = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.linear_img);
		text_score = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_score);
		rating_001 = (RatingBar) findViewById(com.rs.mobile.wportal.R.id.rating_001);
		rating_001.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				// TODO Auto-generated method stub
				score1 = rating;

				score = Math.round((score1 + score2 + score3 + score4) / 4);
				rating_score.setRating(score);
				text_score.setText(score + getString(com.rs.mobile.wportal.R.string.ht_text_fen));
				if (score1 <= 1) {
					text_001.setText(getString(com.rs.mobile.wportal.R.string.ht_text_078));
				} else if (1 < score1 && score1 <= 3) {
					text_001.setText(getString(com.rs.mobile.wportal.R.string.ht_text_079));
				} else if (score1 > 3) {
					text_001.setText(getString(com.rs.mobile.wportal.R.string.ht_text_080));
				}
			}
		});
		text_001 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_001);
		rating_002 = (RatingBar) findViewById(com.rs.mobile.wportal.R.id.rating_002);
		rating_002.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				// TODO Auto-generated method stub
				score2 = rating;
				score = Math.round((score1 + score2 + score3 + score4) / 4);
				rating_score.setRating(score);
				text_score.setText(score + getString(com.rs.mobile.wportal.R.string.ht_text_fen));
				if (score2 <= 1) {
					text_002.setText(getString(com.rs.mobile.wportal.R.string.ht_text_078));
				} else if (1 < score2 && score2 <= 3) {
					text_002.setText(getString(com.rs.mobile.wportal.R.string.ht_text_079));
				} else if (score2 > 3) {
					text_002.setText(getString(com.rs.mobile.wportal.R.string.ht_text_080));
				}
			}
		});
		text_002 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_002);
		rating_003 = (RatingBar) findViewById(com.rs.mobile.wportal.R.id.rating_003);
		rating_003.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				// TODO Auto-generated method stub
				score3 = rating;
				score = Math.round((score1 + score2 + score3 + score4) / 4);
				rating_score.setRating(score);
				text_score.setText(score + getString(com.rs.mobile.wportal.R.string.ht_text_fen));
				if (score3 <= 1) {
					text_003.setText(getString(com.rs.mobile.wportal.R.string.ht_text_078));
				} else if (1 < score3 && score3 <= 3) {
					text_003.setText(getString(com.rs.mobile.wportal.R.string.ht_text_079));
				} else if (score3 > 3) {
					text_003.setText(getString(com.rs.mobile.wportal.R.string.ht_text_080));
				}
			}
		});
		text_003 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_003);
		rating_004 = (RatingBar) findViewById(com.rs.mobile.wportal.R.id.rating_004);
		rating_004.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				score4 = rating;
				score = Math.round((score1 + score2 + score3 + score4) / 4);
				rating_score.setRating(score);
				text_score.setText(score + getString(com.rs.mobile.wportal.R.string.ht_text_fen));
				if (score4 <= 1) {
					text_004.setText(getString(com.rs.mobile.wportal.R.string.ht_text_078));
				} else if (1 < score4 && score4 <= 3) {
					text_004.setText(getString(com.rs.mobile.wportal.R.string.ht_text_079));
				} else if (score4 > 3) {
					text_004.setText(getString(com.rs.mobile.wportal.R.string.ht_text_080));
				}

			}
		});
		text_004 = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_004);
		editText = (EditText) findViewById(com.rs.mobile.wportal.R.id.editText);
		linear_img = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.linear_img);
		text_commit = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_commit);
		text_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (editText.getText().toString().equals("") || editText.getText().toString() == null) {
					t(getString(com.rs.mobile.wportal.R.string.ht_text_081));
					return;
				}
				if (list.size() == 0) {
					addcomment("");
					return;
				}
				FileUtil.uploadrt(Constant.BASE_URL_HT + "/07_FileUpload/MultiFileUploader.ashx", list, null,
						(Activity) context, new FileUtil.CallbackLogic1() {

							@Override
							public void onNetworkError(Request request, IOException e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
								// TODO Auto-generated method stub
								try {
									JSONArray arr = data.getJSONArray("UploadImageList");
									String localUrl = "";
									for (int i = 0; i < arr.length(); i++) {
										JSONObject js = arr.getJSONObject(i);
										String imgpath = js.getString("imageFileName");
										if (i == 0) {
											localUrl = localUrl + imgpath;
										} else {
											localUrl = localUrl + "," + imgpath;
										}

									}

									addcomment(localUrl);
								} catch (Exception e) {
									// TODO: handle exception
								}

							}

							@Override
							public void onBizFailure(String responseDescription, JSONObject data, String flag) {
								// TODO Auto-generated method stub

							}
						});
			}
		});
		imageView = (ImageView) findViewById(com.rs.mobile.wportal.R.id.imageview_add);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list.size() > 9) {
					t(getString(com.rs.mobile.wportal.R.string.ht_text_082));
				} else {
					Intent i = new Intent(HtEvaluateActivity.this, PickOrTakeImageActivity.class);
					i.putExtra(PickOrTakeImageActivity.EXTRA_NUMS, 9 - list.size());
					startActivityForResult(i, 1024);
				}

			}
		});

	};

	private void setView(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != "" && list.get(i) != null) {
				addview(list.get(i), i);
			}

		}
	}

	private void listRemove(String url) {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(url))
					list.remove(i);
			}
		}
	}

	// 添加图片
	public void addview(final String url, final int position) {

		try {

			list.add(url);

			WImageView imageView = new WImageView(context);
			imageView.setId(position);
			imageView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub

					// list.remove(position);
					// linearLayout_img.removeViewAt(position + 1);

					D.showDialog(context, -1, context.getResources().getString(com.rs.mobile.wportal.R.string.sm_text_tips), "删除？", "确定",
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									listRemove(url);
									linearLayout_img.removeView(linearLayout_img.findViewById(position));
									D.alertDialog.dismiss();
								}
							}, context.getResources().getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

								@Override
								public void onClick(View v) {

									// TODO Auto-generated method stub
									D.alertDialog.dismiss();

								}
							});

					return true;
				}
			});
			ImageUtil.drawImageFromLocal(url, imageView);

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(this.imageView.getWidth(),
					this.imageView.getHeight());

			lp.setMargins(0, getResources().getDimensionPixelSize(com.rs.mobile.wportal.R.dimen.marginx2),
					getResources().getDimensionPixelSize(com.rs.mobile.wportal.R.dimen.marginx2),
					getResources().getDimensionPixelSize(com.rs.mobile.wportal.R.dimen.marginx2));

			imageView.setLayoutParams(lp);

			linearLayout_img.addView(imageView);

		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		if (data == null) {
			t("null");
			return;
		} else {

			switch (requestCode) {

			case 1024:
				picklist = data.getStringArrayListExtra("data");

				setView(picklist);
				break;

			default:
				break;
			}
		}
		// super.onActivityResult(requestCode, resultCode, data);

	}

	private void addcomment(String localurl) {
		OkHttpHelper okHttpHelper = new OkHttpHelper(HtEvaluateActivity.this);
		HashMap<String, String> params = new HashMap<>();
		params.put("memid", S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, ""));
		params.put("orderId", orderId);
		params.put("RatedContext", editText.getText().toString());
		params.put("score", score + "");
		params.put("HygieneScore", score4 + "");
		params.put("EnvironmentalScore", score3 + "");
		params.put("ServiceScore", score1 + "");
		params.put("FacilitiesProportion", score2 + "");
		params.put("HotelInfoID", HotelInfoID);
		params.put("ImgPath", localurl);
		okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					if (data.get("status").toString().equals("1")) {
						finish();
					}
					t(data.getString("msg"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.BASE_URL_HT + Constant.HT_ADD_EVALUATE, params);
	}
}
