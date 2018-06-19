
package com.rs.mobile.wportal.activity.dp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.C;
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

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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

public class DpEvaluateActivity extends BaseActivity {

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

	private List<String> list = new ArrayList<String>();// 接收选择的图片的路劲

	private static List<String> list_imageurl = new ArrayList<String>();// 接收服务器返回的路径

	private static Context context;

	private String item_code, score, level, content;

	private LinearLayout linearLayout_close_btn;

	private List<ShoppingCart> listdata;

	private String order_code;

	private String string;

	private RadioButton radio001, radio002, radio003;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_evalute);
		context = this;
		listdata = getIntent().getParcelableArrayListExtra("goods");
		order_code = getIntent().getStringExtra("order_code");
		initView();

	}

	private void initView() {

		radio001 = (RadioButton) findViewById(R.id.radio001);
		radio002 = (RadioButton) findViewById(R.id.radio002);
		radio003 = (RadioButton) findViewById(R.id.radio003);
		linearLayout_close_btn = (LinearLayout) findViewById(R.id.close_btn);
		linearLayout_close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				finish();
			}
		});
		title_text_view = (TextView) findViewById(R.id.title_text_view);
		img_goods = (WImageView) findViewById(R.id.img_goods);
		ImageUtil.drawImageFromUri(listdata.get(0).getimgurl(), img_goods);
		item_code = listdata.get(0).getId();
		ratingBar = (RatingBar) findViewById(R.id.ratingbar);
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
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);

		editText = (EditText) findViewById(R.id.edittext);

		linearLayout_img = (LinearLayout) findViewById(R.id.linear_img);

		text_save = (TextView) findViewById(R.id.text_save);
		text_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				content = editText.getText().toString();
				float score1 = ratingBar.getRating();
				score = (int) score1 + "";
				if (content.equals("") || level.equals("") || score.equals("")) {
					t(getResources().getString(R.string.plz_input_more));
				} else {

					try {
						new Thread(new Runnable() {

							public void run() {

								commitEvalute();

							}
						}).start();

					} catch (Exception e) {
						// TODO: handle exception
						L.e(e);
					}
				}
			}
		});

		imageView = (ImageView) findViewById(R.id.imageview_add);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				if (list.size() > 0) {
					t("最多可添加1张图！");
					// Toast.makeText(context, "最多可添加4张图！",
					// Toast.LENGTH_SHORT).show();
				} else {
					relativeLayout.setVisibility(View.VISIBLE);
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

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		switch (requestCode) {
		case BringPhotoView.PHOTO_REQUEST_TAKEPHOTO:
			File f = new File(Environment.getExternalStorageDirectory() + "/wportal/takephoto.jpg");
			try {
				Uri u = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
						f.getAbsolutePath(), null, null));
				String path = ImageUtil.savePublishPicture(
						ImageUtil.ImageCrop(ImageUtil.getBitmapFromUri(DpEvaluateActivity.this, u)));

				addview(ImageUtil.ImageCrop(ImageUtil.getBitmapFromUri(DpEvaluateActivity.this, u)), path);

				// u就是拍摄获得的原始图片的uri，剩下的你想干神马坏事请便……
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case BringPhotoView.PHOTO_REQUEST_GALLERY:

			if (data != null) {
				Bitmap bm = null;
				// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
				ContentResolver resolver = getContentResolver();
				// 此处的用于判断接收的Activity是不是你想要的那个

				try {
					Uri originalUri = data.getData(); // 获得图片的uri
					bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);

					String path001 = ImageUtil.savePublishPicture(ImageUtil.comp(bm));

					addview(ImageUtil.ImageCrop(bm), path001);
				} catch (IOException e) {
					Log.e("TAG-->Error", e.toString());
				}
			}
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	// 添加图片
	public void addview(Bitmap bitmap, String url) {

		try {

			list.add(url);
			ImageView imageView = new ImageView(context);
			imageView.setImageBitmap(bitmap);
			// LinearLayout.LayoutParams lp = new
			// LinearLayout.LayoutParams(get_windows_width(context) / 5,
			// get_windows_width(context) / 5);
			// lp.setMargins(StringUtil.dip2px(context, 10),
			// StringUtil.dip2px(context, 10), 0, 0);

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

	private void commitEvalute() {

		Map<String, String> params = new HashMap<String, String>();
		params.put(C.KEY_JSON_FM_CONTENT, content);
		params.put(C.KEY_JSON_FM_LEVEL, level);
		params.put("score", score);

		params.put(C.KEY_JSON_FM_ITEM_CODE, item_code);
		params.put(C.KEY_JSON_FM_ORDERCODE, order_code);
		params.put("token", S.getShare(DpEvaluateActivity.this, C.KEY_JSON_TOKEN, ""));
		FileUtil.uploadsm(Constant.BASE_URL_DP1 + Constant.ADD_SENDCOMMENT, list, params, DpEvaluateActivity.this,
				new CallbackLogic1() {

					@Override
					public void onNetworkError(Request request, IOException e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
						// TODO Auto-generated method stub

						// TODO Auto-generated method stub
						try {
							t(data.getString("message"));
							d(data.toString());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finish();

					}

					@Override
					public void onBizFailure(String responseDescription, JSONObject data, String flag) {
						// TODO Auto-generated method stub

					}
				});

	}

	// private void changeEvaluate(String order_code){
	// Map<String, String> params =new HashMap<String, String>();
	// Map<String, String> params1=new HashMap<String, String>();
	// params.put(C.KEY_JSON_FM_ORDERCODE, "1111");
	// params.put("gd_comment", "");
	// params.put(C.KEY_JSON_FM_CONTENT, content);
	// params.put(C.KEY_JSON_FM_LEVEL, level);
	// params.put(C.KEY_JSON_FM_SCORE, score);
	// params.put(C.KEY_JSON_FM_ITEM_CODE, "111");
	// FileUtil.upload(C.SM_BASE_URL+C.UPDATE_COMMENT, list, params);
	// }
	private void getEvaluate(String Orderid) {

		Map<String, String> params = new HashMap<String, String>();
		params.put(C.KEY_JSON_FM_ORDERCODE, Orderid);
		OkHttpHelper okHttpHelper = new OkHttpHelper(DpEvaluateActivity.this);
		okHttpHelper.addSMPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				// TODO Auto-generated method stub
				try {
					JSONArray arr = data.getJSONArray(C.KEY_JSON_DATA);
					JSONObject jsonObject = new JSONObject(arr.getJSONObject(0).toString());
					ratingBar.setRating(Float.parseFloat(jsonObject.get("source").toString()));
					editText.setText(jsonObject.getString("text"));
					ImageUtil.drawImageFromUri(jsonObject.getString("goods_url"), img_goods);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.GETCOMMENTODFORDERCODE, params);
	}

}
