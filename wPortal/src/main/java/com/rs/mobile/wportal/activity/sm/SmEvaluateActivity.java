
package com.rs.mobile.wportal.activity.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.FileUtil;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.view.BringPhotoView;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.wportal.takephoto.PickOrTakeImageActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class SmEvaluateActivity extends BaseActivity {

	// private LinearLayout linearLayout_camera, linearLayout_close_btn;
	private static LinearLayout linearLayout_img;
	// private LinearLayout linearLayout_local;

	private BringPhotoView bringPhotoView;

	private LinearLayout linearLayout_camera_show_cancel;

	private WImageView img_goods;

	private RatingBar ratingBar;

	private RadioGroup radiogroup;

	private EditText editText;

	private ImageView imageView;

	private RelativeLayout relativeLayout;

	private TextView text_save, title_text_view;

	private ArrayList<String> list = new ArrayList<String>();// 接收选择的图片的路劲

	private ArrayList<String> list2 = new ArrayList<>();

	private ArrayList<String> picklist = new ArrayList<>();// 图片路径

	private static List<String> list_imageurl = new ArrayList<String>();// 接收服务器返回的路径

	private static Context context;

	private String item_code, score, level, content;

	private LinearLayout linearLayout_close_btn;

	private List<ShoppingCart> listdata;

	private String order_code;

	private String string;

	private RadioButton radio001, radio002, radio003;

	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_sm_evalute);
		context = this;
		listdata = getIntent().getParcelableArrayListExtra("goods");
		order_code = getIntent().getStringExtra("order_code");
		position = getIntent().getIntExtra("position", 0);
		initView();

	}

	private void initView() {

		radio001 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.radio001);
		radio002 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.radio002);
		radio003 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.radio003);
		linearLayout_close_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);
		linearLayout_close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				finish();
			}
		});
		title_text_view = (TextView) findViewById(com.rs.mobile.wportal.R.id.title_text_view);
		img_goods = (WImageView) findViewById(com.rs.mobile.wportal.R.id.img_goods);
		ImageUtil.drawImageFromUri(listdata.get(0).getimgurl(), img_goods);
		item_code = listdata.get(0).getId();
		ratingBar = (RatingBar) findViewById(com.rs.mobile.wportal.R.id.ratingbar);
		ratingBar.setNumStars(5);
		ratingBar.setStepSize(1);
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

				// TODO Auto-generated method stub
				if (rating >= 0 && rating <= 2) {
					radio001.setChecked(true);
					level = "3";
				} else if (rating == 3) {

					radio002.setChecked(true);
					level = "2";

				} else {
					radio003.setChecked(true);
					level = "1";
				}
			}
		});
		radiogroup = (RadioGroup) findViewById(com.rs.mobile.wportal.R.id.radiogroup);
		radio001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ratingBar.setRating(1);
			}
		});
		radio002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ratingBar.setRating(3);
			}
		});
		radio003.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ratingBar.setRating(5);
			}
		});
		editText = (EditText) findViewById(com.rs.mobile.wportal.R.id.edittext);

		linearLayout_img = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.linear_img);

		text_save = (TextView) findViewById(com.rs.mobile.wportal.R.id.text_save);
		text_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				content = editText.getText().toString();
				float score1 = ratingBar.getRating();
				score = (int) score1 + "";
				if (!"".equals(content) && content != null && !"".equals(level) && level != null && !"".equals(score)
						&& score != null) {

					handler.sendEmptyMessage(0);
				} else {
					t(getResources().getString(com.rs.mobile.wportal.R.string.plz_input_more));

				}
			}
		});

		imageView = (ImageView) findViewById(com.rs.mobile.wportal.R.id.imageview_add);
		// imageView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// // TODO Auto-generated method stub
		// if (list.size() > 0) {
		// t("最多可添加1张图！");
		// // Toast.makeText(context, "最多可添加4张图！",
		// // Toast.LENGTH_SHORT).show();
		// } else {
		// relativeLayout.setVisibility(View.VISIBLE);
		// }
		// }
		// });
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list.size() > 5) {
					t(getString(com.rs.mobile.wportal.R.string.common_text070));
				} else {
					Intent i = new Intent(SmEvaluateActivity.this, PickOrTakeImageActivity.class);
					i.putExtra(PickOrTakeImageActivity.EXTRA_NUMS, 6 - list.size());
					startActivityForResult(i, 1024);
				}

			}
		});

		relativeLayout = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.RelativeLayout_camera_show001);

		relativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				relativeLayout.setVisibility(View.GONE);
			}
		});

		bringPhotoView = (BringPhotoView) findViewById(com.rs.mobile.wportal.R.id.bring_photo_view);
		bringPhotoView.setOnItemSeletedListener(new BringPhotoView.OnItemSeletedListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub

				relativeLayout.setVisibility(View.GONE);

			}

		});

	}

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
				// for (int i = 0; i < picklist.size(); i++) {
				// if (picklist.get(i) != "" && picklist.get(i) != null) {
				// addview(picklist.get(i), i);
				// }
				//
				// }
				break;

			default:
				break;
			}
		}
		// super.onActivityResult(requestCode, resultCode, data);

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				D.showProgressDialog(SmEvaluateActivity.this, "", true);
				commitEvalute();
			}
		};
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

	private void commitEvalute() {

		Map<String, String> params = new HashMap<String, String>();
		params.put(C.KEY_JSON_FM_CONTENT, content);
		params.put(C.KEY_JSON_FM_LEVEL, level);
		params.put("score", score);
		params.put("div_code", C.DIV_CODE);
		params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
		params.put(C.KEY_JSON_FM_ORDERCODE, order_code);
		params.put("token", S.get(SmEvaluateActivity.this, C.KEY_JSON_TOKEN));
		d(params.toString());
		FileUtil.uploadsm(Constant.SM_BASE_URL + Constant.ADD_SENDCOMMENT, list, params, SmEvaluateActivity.this,
				new FileUtil.CallbackLogic1() {

					@Override
					public void onNetworkError(Request request, IOException e) {

					}

					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
						try {
							t(data.getString("message"));
							if (data.get("status").toString().equals("1")) {
								finish();

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onBizFailure(String responseDescription, JSONObject data, String flag) {

					}
				});

	}

}
