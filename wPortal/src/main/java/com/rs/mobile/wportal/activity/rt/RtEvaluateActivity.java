package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.FileUtil;
import com.rs.mobile.common.util.FileUtil.CallbackLogic1;
import com.rs.mobile.common.view.BringPhotoView;
import com.rs.mobile.common.view.BringPhotoView.OnItemSeletedListener;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
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

public class RtEvaluateActivity extends BaseActivity {

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
	private float score, score1, score2, score3;
	private Context context;
	private LinearLayout linearLayout_img;
	private List<String> list = new ArrayList<String>();// 接收选择的图片的路劲

	private ArrayList<String> picklist = new ArrayList<>();// 图片路径
	private ImageView imageView;
	private RelativeLayout relativeLayout;
	private BringPhotoView bringPhotoView;
	private JSONArray arr_upload = new JSONArray();
	private String comment;
	private String orderNumber;
	private String saleCustomCode;
	private String saleName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rt_evaluate);
		context = this;
		orderNumber = getIntent().getStringExtra("orderNumber");
		saleCustomCode = getIntent().getStringExtra("saleCustomCode");
		saleName = getIntent().getStringExtra("saleName");
		initToolbar();
		initView();
		initData();
	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(R.id.toolbar);
			iv_back = (LinearLayout) findViewById(R.id.iv_back);
			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					RtEvaluateActivity.this.finish();
				}
			});
			tv_title = (TextView) findViewById(R.id.tv_title);
			tv_title.setText(saleName);
			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initData() {
	};

	private void initView() {
		score = 0;
		score1 = 0;
		score2 = 0;
		score3 = 0;
		rating_score = (RatingBar) findViewById(R.id.rating_score);
		linearLayout_img = (LinearLayout) findViewById(R.id.linear_img);
		text_score = (TextView) findViewById(R.id.text_score);
		rating_001 = (RatingBar) findViewById(R.id.rating_001);
		rating_001.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				// TODO Auto-generated method stub
				score1 = rating;

				score = Math.round((score1 + score2 + score3) / 3);
				rating_score.setRating(score);
				text_score.setText(score + getString(R.string.common_text034));
				if (score1 <= 1) {
					text_001.setText(getString(R.string.ht_text_078));
				} else if (1 < score1 && score1 <= 3) {
					text_001.setText(getString(R.string.ht_text_079));
				} else if (score1 > 3) {
					text_001.setText(getString(R.string.ht_text_080));
				}
			}
		});
		text_001 = (TextView) findViewById(R.id.text_001);
		rating_002 = (RatingBar) findViewById(R.id.rating_002);
		rating_002.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				// TODO Auto-generated method stub
				score2 = rating;
				score = Math.round((score1 + score2 + score3) / 3);
				rating_score.setRating(score);
				text_score.setText(score + getString(R.string.common_text034));
				if (score2 <= 1) {
					text_002.setText(getString(R.string.ht_text_078));
				} else if (1 < score2 && score2 <= 3) {
					text_002.setText(getString(R.string.ht_text_079));
				} else if (score2 > 3) {
					text_002.setText(getString(R.string.ht_text_080));
				}
			}
		});
		text_002 = (TextView) findViewById(R.id.text_002);
		rating_003 = (RatingBar) findViewById(R.id.rating_003);
		rating_003.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				// TODO Auto-generated method stub
				score3 = rating;
				score = Math.round((score1 + score2 + score3) / 3);
				rating_score.setRating(score);
				text_score.setText(score + getString(R.string.common_text034));
				if (score3 <= 1) {
					text_003.setText(getString(R.string.ht_text_078));
				} else if (1 < score3 && score3 <= 3) {
					text_003.setText(getString(R.string.ht_text_079));
				} else if (score1 > 3) {
					text_003.setText(getString(R.string.ht_text_080));
				}
			}
		});
		text_003 = (TextView) findViewById(R.id.text_003);
		editText = (EditText) findViewById(R.id.editText);
		linear_img = (LinearLayout) findViewById(R.id.linear_img);
		text_commit = (TextView) findViewById(R.id.text_commit);
		text_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				L.d(list.toString());
				if (list.size() == 0) {
					addComment(arr_upload);
					return;
				}
				FileUtil.uploadrt(C.BASE_URL + "/Common/MultiFileUploader.ashx", list, null, (Activity) context,
						new CallbackLogic1() {

							@Override
							public void onNetworkError(Request request, IOException e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
								// TODO Auto-generated method stub
								try {
									JSONArray arr = data.getJSONArray("UploadImageList");
									arr_upload = new JSONArray("[]");
									for (int i = 0; i < arr.length(); i++) {
										JSONObject js = arr.getJSONObject(i);
										String imgpath = js.getString("imageFileName");
										JSONObject js_upload = new JSONObject();
										js_upload.put("fileName", imgpath);

										arr_upload.put(js_upload);
									}

									addComment(arr_upload);
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
		imageView = (ImageView) findViewById(R.id.imageview_add);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list.size() > 5) {
					t(getString(R.string.common_text070));
				} else {
					Intent i = new Intent(RtEvaluateActivity.this, PickOrTakeImageActivity.class);
					i.putExtra(PickOrTakeImageActivity.EXTRA_NUMS, 6 - list.size());
					startActivityForResult(i, 1024);
				}

			}
		});

		relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_camera_show001);

		relativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				relativeLayout.setVisibility(View.GONE);
			}
		});

		bringPhotoView = (BringPhotoView) findViewById(R.id.bring_photo_view);
		bringPhotoView.setOnItemSeletedListener(new OnItemSeletedListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub

				relativeLayout.setVisibility(View.GONE);

			}

		});

	};

	// // 添加图片
	// public void addview(Bitmap bitmap, String url) {
	// try {
	//
	// list.add(url);
	// ImageView imageView = new ImageView(context);
	// imageView.setImageBitmap(bitmap);
	//
	// LinearLayout.LayoutParams lp = new
	// LinearLayout.LayoutParams(get_windows_width(context) / 5,
	// get_windows_width(context) / 5);
	// lp.setMargins(StringUtil.dip2px(context, 10), StringUtil.dip2px(context,
	// 10), 0, 0);
	// imageView.setLayoutParams(lp);
	//
	// linearLayout_img.addView(imageView);
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// L.e(e);
	// }
	//
	// }

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		if (data == null) {

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

	private void setView(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != "" && list.get(i) != null) {
				addview(list.get(i), i);
			}

		}
	}

	public void addComment(JSONArray arr) {

		try {

			OkHttpHelper helper = new OkHttpHelper(RtEvaluateActivity.this);

			HashMap<String, String> headers = new HashMap<String, String>();

			headers.put("content-type", "application/json;Charset=UTF-8");

			helper.addPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {
						t(data.getString("msg"));
						String status = data.getString("status");
						if (status.equals("1")) {
							finish();
						}
					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_RT + Constant.RT_ADD_COMMENT + "?divCode=" + C.DIV_CODE + "&orderNumber=" + orderNumber
					+ "&saleCustomCode=" + saleCustomCode + "&customCode="
					+ S.getShare(RtEvaluateActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&content="
					+ editText.getText() + "&score1=" + (int) (score1 * 2) + "&score2=" + (int) (score2 * 2)
					+ "&score3=" + (int) (score3 * 2) + "&token="
					+ S.getShare(RtEvaluateActivity.this, C.KEY_JSON_TOKEN, ""), headers, arr.toString());

		} catch (Exception e) {

			L.e(e);

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

					D.showDialog(context, -1, context.getResources().getString(R.string.sm_text_tips), "删除？", "确定",
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									listRemove(url);
									linearLayout_img.removeView(linearLayout_img.findViewById(position));
									D.alertDialog.dismiss();
								}
							}, context.getResources().getString(R.string.cancel), new OnClickListener() {

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

			lp.setMargins(0, getResources().getDimensionPixelSize(R.dimen.marginx2),
					getResources().getDimensionPixelSize(R.dimen.marginx2),
					getResources().getDimensionPixelSize(R.dimen.marginx2));

			imageView.setLayoutParams(lp);

			linearLayout_img.addView(imageView);

		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}

	}
}
