package com.rs.mobile.wportal.persnal;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.ChangPwActivity;
import com.rs.mobile.common.activity.LoginActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.FileUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.common.view.BringPhotoView;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.A;
import com.rs.mobile.wportal.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

public class SettingActivity extends BaseActivity {

	private LinearLayout closeBtn;

	private TextView titleTextView;

	private WImageView iconImageView;

	private TextView nameTextView , load_size;

	private LinearLayout photoEditBtn;

	private LinearLayout sexEditBtn;

	private TextView sexTextView;

	private LinearLayout nameEditBtn;/*line_language*/;

	private LinearLayout logoutBtn;

	private RelativeLayout bringPhothLayout;

	private BringPhotoView bringPhotoView;

	private String imagePath = "";

	private Uri imageUri;

	private String uploadTime;

	private String imageDownloadUrl = "";
	
//	private RadioGroup rg_lang;
	
//	private RadioButton set_default ,set_china,set_ko;
	

	private LinearLayout change_pw_btn;
	private static String path = Environment.getExternalStorageDirectory().getPath() + "/YUchengguoji" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {

			setContentView(com.rs.mobile.wportal.R.layout.activity_setting);

			titleTextView = (TextView) findViewById(com.rs.mobile.wportal.R.id.title_text_view);
			load_size = (TextView) findViewById(com.rs.mobile.wportal.R.id.load_size);
			titleTextView.setText(com.rs.mobile.wportal.R.string.setting);
//			rg_lang = (RadioGroup) findViewById(com.rs.mobile.wportal.R.id.rg_lang);
//			set_default =(RadioButton) findViewById(com.rs.mobile.wportal.R.id.set_default);
//			set_china =(RadioButton) findViewById(com.rs.mobile.wportal.R.id.set_china);
//			set_ko =(RadioButton) findViewById(com.rs.mobile.wportal.R.id.set_ko);
			iconImageView = (WImageView) findViewById(com.rs.mobile.wportal.R.id.icon_image_view);
//			line_language =  (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.line_language);
			change_pw_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.change_pw_btn);
			
			//设置默认语言
//			if(S.get(SettingActivity.this, "SET_LANGUAGE", "0").equals("0")){
//				set_default.setChecked(true);
//			}else if(S.get(SettingActivity.this, "SET_LANGUAGE", "0").equals("2")){
//				set_ko.setChecked(true);
//			}else{
//				//set_china.setChecked(true);
//				set_ko.setChecked(true);
//			}
//
//			rg_lang.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//				@Override
//				public void onCheckedChanged(RadioGroup radiogroup, int i) {
//					if(set_ko.getId() == i){
//						S.set(SettingActivity.this, "SET_LANGUAGE", "2");
//					}else if(set_china.getId() == i){
//						S.set(SettingActivity.this, "SET_LANGUAGE", "1");
//					}else{
//						S.set(SettingActivity.this, "SET_LANGUAGE", "0");
//					}
//
//					showAlertDialog("提示", "修改语言设置后需要重启APP才能生效", "立即重启", new OnClickListener() {
//
//						@Override
//						public void onClick(View view) {
//							  //重启app代码
//							S.set(SettingActivity.this, "LANGUEGE_STATUS", "1");
//		                    Intent intent = getBaseContext().getPackageManager()
//		                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
//		                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		                    startActivity(intent);
//						}
//					});
//				}
//			});
			
			change_pw_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PageUtil.jumpTo(SettingActivity.this, ChangPwActivity.class);
				}
			});

			iconImageView.setCircle(true);
			
			
			//清除缓存
			findViewById(com.rs.mobile.wportal.R.id.clear_load).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					UtilClear.deleteFolderFile(path, false);
					load_size.setText("0MB");
				}
			});
			
			//设置语言
//			findViewById(com.rs.mobile.wportal.R.id.set_language).setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					if(line_language.isShown())
//						line_language.setVisibility(View.GONE);
//					else
//						line_language.setVisibility(View.VISIBLE);
//				}
//			});
			
			

			closeBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);
			closeBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					setPersnalInfo();

				}
			});

			photoEditBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.edit_photo_btn);
			photoEditBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					showBringPhotoView();

				}
			});

			bringPhothLayout = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.bring_photo_layout);
			bringPhothLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					hideBringPhotoView();

				}
			});

			bringPhotoView = (BringPhotoView) findViewById(com.rs.mobile.wportal.R.id.bring_photo_view);
			bringPhotoView.setOnItemSeletedListener(new BringPhotoView.OnItemSeletedListener() {

				@Override
				public void onItemClick(int position) {
					// TODO Auto-generated method stub

					hideBringPhotoView();

				}
			});

			sexEditBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.edit_sex_btn);
			sexEditBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					showSexDialog();

				}
			});

			sexTextView = (TextView) findViewById(com.rs.mobile.wportal.R.id.sex_text_view);

			nameEditBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.edit_name_btn);
			nameEditBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					showEditTextDialog(getString(com.rs.mobile.wportal.R.string.name_area), getString(com.rs.mobile.wportal.R.string.name_area), null,
							getString(com.rs.mobile.wportal.R.string.ok), new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									nameTextView.setText(D.editText.getText().toString());

									D.alertDialog.dismiss();
								}
							}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									D.alertDialog.dismiss();
								}
							});

					D.editText.setText(nameTextView.getText().toString());

				}
			});

			nameTextView = (TextView) findViewById(com.rs.mobile.wportal.R.id.name_text_view);

			logoutBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.logout_btn);
			logoutBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					showDialog(getString(com.rs.mobile.wportal.R.string.logout), getString(com.rs.mobile.wportal.R.string.logout_msg), getString(com.rs.mobile.wportal.R.string.logout),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									D.alertDialog.dismiss();
									
									JSONObject obj = new JSONObject();
									try{
										HashMap<String, String> headers = new HashMap<>();
										headers.put("Content-Type", "application/json;Charset=UTF-8");

	//									HashMap<String, String> params = new HashMap<String, String>();
										obj.put("lang_type", AppConfig.LANG_TYPE);
										obj.put("id", S.get(SettingActivity.this, C.KEY_JSON_CUSTOM_CODE));
										obj.put("siteCode","yc");
										obj.put("deviceNo", Util.getDeviceId(SettingActivity.this));
										OkHttpHelper helper = new OkHttpHelper(SettingActivity.this);

										helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

											@Override
											public void onNetworkError(Request request, IOException e) {

											}

											@Override
											public void onBizSuccess(String responseDescription, final JSONObject data,
													final String flag) {
												try {

//													C.INTERFACE_PARAMS.clear();
//
//													A.setToken("");
//
//													S.set(SettingActivity.this, C.KEY_SHARED_KNICK_NAME, "");
//
//													S.set(SettingActivity.this, C.KEY_JSON_TOKEN, "");
//
//													S.set(SettingActivity.this, C.KEY_REQUEST_MEMBER_ID, "");
//
//													T.showToast(SettingActivity.this, data.getString("msg"));

													S.set(SettingActivity.this, C.KEY_JSON_CUSTOM_CODE, "");
													S.set(SettingActivity.this, C.KEY_JSON_CUSTOM_ID, "");
													S.set(SettingActivity.this, C.KEY_JSON_CUSTOM_NAME, "");
													S.set(SettingActivity.this, C.KEY_JSON_NICK_NAME, "");
													S.set(SettingActivity.this, C.KEY_JSON_TOKEN, "");
													S.set(SettingActivity.this, C.KEY_JSON_PROFILE_IMG, "");
													S.set(SettingActivity.this, C.KEY_JSON_DIV_CODE, "");
													S.set(SettingActivity.this, C.KEY_JSON_SSOID, "");
													S.set(SettingActivity.this, C.KEY_JSON_SSO_REGIKEY, "");
													S.set(SettingActivity.this, C.KEY_JSON_MALL_HOME_ID, "");
													S.set(SettingActivity.this, C.KEY_JSON_POINT_CARD_NO, "");
													S.set(SettingActivity.this, C.KEY_JSON_PARENT_ID, "");

													finish();

												} catch (Exception e) {

													L.e(e);

												}

											}

											@Override
											public void onBizFailure(String responseDescription, JSONObject data,
													String responseCode) {

											}
										}, "http://api1.gigawon.co.kr:8088" + Constant.SSO_LOGOUT,headers,obj.toString());
//										}, Constant.BASE_URL_SSO + Constant.SSO_LOGOUT,headers,obj.toString());

										}catch(Exception e){
											e.getMessage();

										}

								}
							}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									D.alertDialog.dismiss();
								}
							}, true);

				}
			});

			getPersnalInfo();
			
			
			//初始化缓存数据获取大小
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					File file = new File(path);
					load_size.setText(UtilClear.getFormatSize(UtilClear.getFolderSize(file)));
				}
			}, 500);
			

		} catch (Exception e) {

			e(e);

		}

	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (resultCode == RESULT_OK) {

			switch (requestCode) {
			case BringPhotoView.PHOTO_REQUEST_TAKEPHOTO:

				try {

					new TakePhotoAsyncTask().execute();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					L.e(e);
				}

				break;

			case BringPhotoView.PHOTO_REQUEST_GALLERY:

				if (data != null) {

					try {

						new GetPhotoFromGallaryAsyncTask().execute(data.getData());

					} catch (Exception e) {
						L.e(e);
					}
				}
				break;

			}

		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	public void showBringPhotoView() {

		try {

			uploadTime = "" + System.currentTimeMillis();

			// Animation fadeAnimation =
			// AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.fade_in);
			// Animation scaleAnimation =
			// AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.scale_top_in);

			bringPhothLayout.setVisibility(View.VISIBLE);
			bringPhotoView.setVisibility(View.VISIBLE);

			// bringPhothLayout.startAnimation(fadeAnimation);
			// bringPhotoView.startAnimation(scaleAnimation);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void hideBringPhotoView() {

		try {

			// Animation fadeAnimation =
			// AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.fade_out);
			// Animation scaleAnimation =
			// AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.scale_bottom_out);

			bringPhothLayout.setVisibility(View.GONE);
			bringPhotoView.setVisibility(View.GONE);

			// bringPhothLayout.startAnimation(fadeAnimation);
			// bringPhotoView.startAnimation(scaleAnimation);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private int selectedSexIndex = 0;

	public void showSexDialog() {

		try {

			String[] items = new String[2];

			items[0] = getString(com.rs.mobile.wportal.R.string.man);

			items[1] = getString(com.rs.mobile.wportal.R.string.woman);

			showSingleChoiceDialog(-1, -1, -1, com.rs.mobile.wportal.R.string.ok, com.rs.mobile.wportal.R.string.cancel, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					sexTextView.setText(getString(selectedSexIndex == 0 ? com.rs.mobile.wportal.R.string.man : com.rs.mobile.wportal.R.string.woman));

				}
			}, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			}, items, sexTextView.getText().toString().equals(getString(com.rs.mobile.wportal.R.string.man)) ? 0 : 1,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							selectedSexIndex = which;

						}
					}, true);

		} catch (Exception e) {

			e(e);

		}
	}

	public void getPersnalInfo() {

		try {

			OkHttpHelper helper = new OkHttpHelper(SettingActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("lang_type", AppConfig.LANG_TYPE);

			params.put("MemberID", S.get(SettingActivity.this, C.KEY_JSON_CUSTOM_CODE));

			params.put(C.KEY_JSON_TOKEN, S.get(SettingActivity.this, C.KEY_JSON_TOKEN));
			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, final JSONObject data, final String all_data) {
					try {
						JSONObject jsonData = data.getJSONObject("data");
						nameTextView.setText(jsonData.getString(C.KEY_JSON_NICK_NAME));

						String sex = jsonData.getString("gender").trim();

						selectedSexIndex = sex.equals("m") ? 0 : 1;

						sexTextView.setText(getString(sex.equals("m") ? com.rs.mobile.wportal.R.string.man : com.rs.mobile.wportal.R.string.woman));

						imageDownloadUrl = jsonData.getString("imagePath");

//						String[] p = imageDownloadUrl.split("/");
//
//						String imgUrl = p[p.length - 1];
//
//						bringPhotoView.setFileName(imgUrl);
//
//						Uri uri = Uri.parse(imageDownloadUrl);

						ImageUtil.drawImageFromUri(imageDownloadUrl,iconImageView);
						S.set(SettingActivity.this, C.KEY_JSON_PROFILE_IMG, imageDownloadUrl);
						S.set(SettingActivity.this, C.KEY_JSON_NICK_NAME, nameTextView.getText().toString());
					} catch (Exception e) {

						L.e(e);

					}

					hideProgressBar();

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {

				}
			}, "http://member.gigawon.co.kr:8808/api/member/requestProfileInfo", params);
//			}, C.BASE_URL + Constant.PERSNAL_GET_INFO, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void setPersnalInfo() {

		try {

//			HashMap<String, String> headers = new HashMap<>();
//			headers.put("Content-Type", "application/json;Charset=UTF-8");

			JSONObject j1=new JSONObject();
			HashMap<String, String> params = new HashMap<>();
			try {

				j1.put(C.KEY_REQUEST_MEMBER_ID_TOW, S.get(SettingActivity.this, C.KEY_JSON_CUSTOM_CODE));
				j1.put(C.KEY_JSON_NICK_NAME, nameTextView.getText().toString());
				j1.put("imageUrl",imageDownloadUrl);
				j1.put("gender", selectedSexIndex == 0 ? "m" : "f");
				j1.put("token", S.get(SettingActivity.this, C.KEY_JSON_TOKEN));
				/*j1.put(C.KEY_REQUEST_MEMBER_ID_TOW, "1862756329077a18"); //memberID
				j1.put(C.KEY_JSON_NICK_NAME, "kimdsttthhjh");  //deviceNo
				j1.put("imagePath","http:\\/\\/imfiles.dxbhtm.com:8640\\/upload\\/image\\/2018121\\/201812110163976300x250.jpg"); //s_id
				j1.put("gender", "f");
				j1.put("toekn", "ad-443d-bef8-bec719f4a77c|00C79862-B755-4903-92E1-20833C8A3429|1862756329077a18yc|1862756329077a18|8111100200012063");  //lang_type
				*/


				//Jason Type : memid,mempwd,deviceNo, s_id, ver, lang_type

			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			OkHttpHelper helper = new OkHttpHelper(SettingActivity.this);
			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					finish();
				}

				@Override
				public void onBizSuccess(String responseDescription, final JSONObject data, final String all_data) {

					try {
						S.set(SettingActivity.this, C.KEY_SHARED_KNICK_NAME, nameTextView.getText().toString());

						L.d(data.toString());

						// t(getString(R.string.complete));

					} catch (Exception e) {

						L.e(e);

					}

					setResult(RESULT_OK);

					finish();

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					String aaa = responseCode.toString();
					finish();

				}
			}, "http://member.gigawon.co.kr:8808/api/member/editProfile", j1.toString());
//			}, C.BASE_RS_MEMBER_URL + C.REQUEST_NICK_NAME_CHANGE, j1.toString());

		} catch (Exception e) {

			L.e(e);

			finish();

		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		setPersnalInfo();

	}

	private class ImageUploadAsyncTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			showProgressBar();
		}

		@Override
		protected String doInBackground(Object... params) {
			// TODO Auto-generated method stub

			try {

				/*
				 * if (status == "-60001") alert("컨텐츠 타입 에러"); else if (status
				 * == "-60002") alert("확장자 타입 에러"); else if (status == "-60003")
				 * alert("스트림 read  에러"); else if (status == "-60004") alert(
				 * "이미지 용량 최저용량 미만"); else if (status == "-60005") alert(
				 * "이미지 용량 최대용량 이상"); else if (status == "-60006") alert(
				 * "파일 내 이상 코드 발견");
				 */

				if (imagePath != null && !imagePath.equals("")) {

					S.set(SettingActivity.this, C.KEY_SHARED_ICON_PATH, imagePath);

					ArrayList<String> filePath = new ArrayList<String>();

					filePath.add(imagePath);

					return FileUtil.upload(C.BASE_URL + C.PERSNAL_IMAGE_UPLOAD_PATH, filePath, null, "file");

				} else {

					return "";

				}

			} catch (Exception e) {

				L.e(e);

				return e.getClass().getName();

			}

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				L.d(result);

				// ImageUtil.drawIamge(iconImageView,Uri.parse(C.BASE_URL +
				// C.PERSNAL_IMAGE_DOWNLOAD_PATH + "wportal" +
				// S.getShare(SettingActivity.this, C.KEY_REQUEST_MEMBER_ID, "")
				// + ".jpg"));

				imageDownloadUrl = C.BASE_URL + C.PERSNAL_IMAGE_DOWNLOAD_PATH + "wportal"
						+ S.getShare(SettingActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + uploadTime + ".jpg";
				ImageUtil.drawImageFromUri(imageDownloadUrl,iconImageView);

				hideProgressBar();

			} catch (Exception e) {

				L.e(e);

				finish();

			}

		}

	}

//	private void setImage(Uri mImageCaptureUri) {
//
//		// 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
//		// 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看
//
//		ContentResolver cr = this.getContentResolver();
//		Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找
//		if (cursor != null) {
//			cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
//			String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路
//			String orientation = cursor.getString(cursor
//					.getColumnIndex("orientation"));// 获取旋转的角度
//			cursor.close();
//			if (filePath != null) {
//				Bitmap bitmap = BitmapFactory.decodeFile(filePath);//根据Path读取资源图片
//				int angle = 0;
//				if (orientation != null && !"".equals(orientation)) {
//					angle = Integer.parseInt(orientation);
//				}
//				if (angle != 0) {
//					// 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
//					Matrix m = new Matrix();
//					int width = bitmap.getWidth();
//					int height = bitmap.getHeight();
//					m.setRotate(angle); // 旋转angle度
//					bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
//							m, true);// 从新生成图片
//
//				}
//				photo.setImageBitmap(bitmap);
//			}
//		}
//	}


	// private class GetBitmapAsyncTask extends AsyncTask<Object, Integer,
	// Drawable> {
	//
	// @Override
	// protected void onPreExecute() {
	// // TODO Auto-generated method stub
	// super.onPreExecute();
	//
	// showProgressBar();
	// }
	//
	// @Override
	// protected Drawable doInBackground(Object... params) {
	// // TODO Auto-generated method stub
	//
	// try {
	//
	// File f = new File(S.get(SettingActivity.this, C.KEY_SHARED_ICON_PATH));
	//
	// Uri uri =
	// Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
	// f.getAbsolutePath(), null, null));
	//
	// Bitmap bm = ImageUtil.getBitmapFromUri(SettingActivity.this, uri);
	//
	// return new BitmapDrawable(bm);
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// }
	//
	// return null;
	//
	// }
	//
	// @Override
	// protected void onPostExecute(Drawable result) {
	// // TODO Auto-generated method stub
	// super.onPostExecute(result);
	//
	// try {
	//
	// hideProgressBar();
	//
	// ImageUtil.drawIamge(iconImageView, result);
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// finish();
	//
	// }
	//
	// }
	//
	// }

	private class TakePhotoAsyncTask extends AsyncTask<Object, Integer, Drawable> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			showProgressBar();
		}

		@Override
		protected Drawable doInBackground(Object... params) {
			// TODO Auto-generated method stub

			try {

				// uploadTime = "" + System.currentTimeMillis();

				File dir = new File(Environment.getExternalStorageDirectory() + "/wportal");

				if (!dir.exists())
					dir.mkdirs();

				File f = new File(dir, bringPhotoView.getFileName() + ".jpg");// localTempImgDir和localTempImageFileName是自己定义的名字

				// File f = new File(Environment.getExternalStorageDirectory() +
				// "/wportal/"
				// + S.getShare(SettingActivity.this, C.KEY_REQUEST_MEMBER_ID,
				// "") + uploadTime + ".jpg");

				imageUri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
						f.getAbsolutePath(), null, null));

				Bitmap bm = ImageUtil.getBitmapFromUri(SettingActivity.this, imageUri);
				L.d(bm.getByteCount() + "frist");

				imagePath = ImageUtil.savePublishPicture(ImageUtil.comp(bm),
						S.getShare(SettingActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + uploadTime);

				return new BitmapDrawable(bm);

			} catch (Exception e) {

				L.e(e);

			}

			return null;

		}

		@Override
		protected void onPostExecute(Drawable result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				hideProgressBar();

				new ImageUploadAsyncTask().execute();

				// ImageUtil.drawIamge(iconImageView, result);

			} catch (Exception e) {

				L.e(e);

			}

		}

	}

	private class GetPhotoFromGallaryAsyncTask extends AsyncTask<Object, Integer, Drawable> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			showProgressBar();
		}

		@Override
		protected Drawable doInBackground(Object... params) {
			// TODO Auto-generated method stub

			try {

				// uploadTime = "" + System.currentTimeMillis();

				imageUri = (Uri) params[0]; // 获得图片的uri

				Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

				imagePath = ImageUtil.savePublishPicture(ImageUtil.comp(bm),
						S.getShare(SettingActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + uploadTime);
				return new BitmapDrawable(bm);

			} catch (Exception e) {

				L.e(e);

			}

			return null;

		}

		@Override
		protected void onPostExecute(Drawable result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				hideProgressBar();

				new ImageUploadAsyncTask().execute();

				// ImageUtil.drawIamge(iconImageView, result);

			} catch (Exception e) {

				L.e(e);

			}

		}

	}
}
