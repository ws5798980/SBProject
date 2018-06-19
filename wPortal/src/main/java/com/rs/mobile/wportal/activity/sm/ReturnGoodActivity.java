package com.rs.mobile.wportal.activity.sm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rs.mobile.wportal.biz.ShoppingCart;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.util.StringUtil;
import com.rs.mobile.common.view.BringPhotoView;
import com.rs.mobile.common.view.BringPhotoView.OnItemSeletedListener;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.sm.confirmOrderGoodAdapter;
import com.rs.mobile.wportal.biz.RefundReason;
import com.rs.mobile.wportal.view.PickerView;
import com.rs.mobile.wportal.view.PickerView.onSelectListener;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;

public class ReturnGoodActivity extends BaseActivity {

	private List<String> list;

	private LinearLayout linearLayout_img, btn_back;

	private ImageView imageView;

	private RelativeLayout relativeLayout, RelativeLayout_num_pick;

	private BringPhotoView bringPhotoView;

	private Context context;

	private RadioGroup rdg_return;

	private RadioButton radioButton;

	private ShoppingCart shopCart;

	private ArrayList<ShoppingCart> shopcartList;

	private String order_code;

	private ListView lv_goods;

	private PickerView numberPick;

	private TextView text_confirm, text_reason;

	private String item_code, reason_msg;

	ArrayList<String> datas = new ArrayList<>();

	ArrayList<String> datas1 = new ArrayList<>();

	protected String reason_code;

	private List<RefundReason> reasonList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sm_return_goods);

		context = this;

		initView();

		initData();

		list = new ArrayList<String>();

	}

	private void initView() {

		RelativeLayout_num_pick = (RelativeLayout) findViewById(R.id.RelativeLayout_num_pick);

		RelativeLayout_num_pick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RelativeLayout_num_pick.setVisibility(View.GONE);
			}
		});

		btn_back = (LinearLayout) findViewById(R.id.btn_back);

		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		linearLayout_img = (LinearLayout) findViewById(R.id.linear_img);

		numberPick = (PickerView) findViewById(R.id.num_pick);

		text_reason = (TextView) findViewById(R.id.text_reason);

		numberPick.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text, int d) {
				// TODO Auto-generated method stub

				reason_msg = text;

				text_reason.setText(text);

				reason_code = datas1.get(d);

			}
		});

		rdg_return = (RadioGroup) findViewById(R.id.rdg_return);

		rdg_return.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				radioButton = (RadioButton) findViewById(group.getCheckedRadioButtonId());

				switch (radioButton.getId()) {

				case R.id.rdb_001:
					break;
				case R.id.rdb_002:
					break;
				case R.id.rdb_003:
					break;
				default:
					break;
				}

			}
		});

		lv_goods = (ListView) findViewById(R.id.lv_goods);

		text_confirm = (TextView) findViewById(R.id.text_confirm);

		text_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!"".equals(text_reason.getText().toString())) {
					for (int i = 0; i < reasonList.size(); i++) {

						if (text_reason.getText().equals(reasonList.get(i).getCode_name())) {
							reason_code = reasonList.get(i).getSub_code();
							break;
						}
					}
					submitRefundApply(item_code);
				} else {
					t(getString(R.string.common_text069));
				}

			}
		});
		imageView = (ImageView) findViewById(R.id.imageview_add);

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (list.size() > 1) {

					// t("最多可添加1张图！");

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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		finish();

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		switch (requestCode) {
		case BringPhotoView.PHOTO_REQUEST_TAKEPHOTO:

			File f = new File(Environment.getExternalStorageDirectory() + "/wportal/takephoto.jpg");

			try {

				Uri u = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
						f.getAbsolutePath(), null, null));
				String path = ImageUtil.savePublishPicture(
						ImageUtil.ImageCrop(ImageUtil.getBitmapFromUri(ReturnGoodActivity.this, u)));

				addview(ImageUtil.ImageCrop(ImageUtil.getBitmapFromUri(ReturnGoodActivity.this, u)), path);

				// u就是拍摄获得的原始图片的uri，剩下的你想干神马坏事请便……
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e(e);
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

				} catch (Exception e) {
					e(e);
				}
			}
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void initData() {

		shopcartList = getIntent().getParcelableArrayListExtra("goods");

		item_code = shopcartList.get(0).getId();

		order_code = getIntent().getStringExtra("order_code");

		lv_goods.setAdapter(new confirmOrderGoodAdapter(shopcartList, ReturnGoodActivity.this));

		setListViewHeight(lv_goods);

		OkHttpHelper okHttpHelper = new OkHttpHelper(context);

		HashMap<String, String> paramsKeyValue = new HashMap<>();

		okHttpHelper.addPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub
				try {
					JSONArray array = data.getJSONArray("data");
					for (int i = 0; i < array.length(); i++) {
						JSONObject jObject = array.getJSONObject(i);
						datas.add(jObject.getString("code_name"));
						datas1.add(jObject.getString("sub_code"));
						RefundReason reason = new RefundReason(jObject.getString("code_name"),
								jObject.getString("sub_code"));
						reasonList.add(reason);
					}
					numberPick.setData(datas);

					numberPick.setSelected(0);

					text_reason.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							RelativeLayout_num_pick.setVisibility(View.VISIBLE);

							text_reason.setText(numberPick.getText());

						}
					});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.GET_RUFUND_REASON, paramsKeyValue);

	}

	// 添加图片
	public void addview(Bitmap bitmap, String url) {

		try {

			list.add(url);

			ImageView imageView = new ImageView(context);

			imageView.setImageBitmap(bitmap);

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(get_windows_width(context) / 4,
					get_windows_width(context) / 4);

			lp.setMargins(StringUtil.dip2px(context, 10), StringUtil.dip2px(context, 10), 0, 0);

			imageView.setLayoutParams(lp);

			linearLayout_img.addView(imageView);

		} catch (Exception e) {
			// TODO: handle exception
			L.e(e);
		}

	}

	private void submitRefundApply(String item_code) {

		OkHttpHelper okHttpHelper = new OkHttpHelper(context);

		HashMap<String, String> params = new HashMap<>();

		params.put("order_num", order_code);

		params.put("item_code", item_code);

		params.put("custom_code", S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));

		params.put("reason_msg", text_reason.getText().toString());

		params.put("isCancel", "false");

		params.put("reason_code", reason_code);

		okHttpHelper.addSMPostRequest(new CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

				try {

					if (data.get(C.KEY_JSON_FM_STATUS).toString().equals("1")) {

						finish();

					}

				} catch (Exception e) {
					// TODO: handle exception
					e(e);

				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {
				// TODO Auto-generated method stub

			}
		}, Constant.SM_BASE_URL + Constant.SubmitRefundApply, params);

	}

}