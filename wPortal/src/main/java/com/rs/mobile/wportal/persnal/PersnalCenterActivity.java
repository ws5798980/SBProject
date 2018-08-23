package com.rs.mobile.wportal.persnal;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.sm.MyOrderActivity;
import com.rs.mobile.wportal.activity.sm.SmMyActivity;
import com.rs.mobile.wportal.activity.sm.SmReturnGoodsListActivity;
import com.rs.mobile.wportal.adapter.PersnalCenterListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

import static com.rs.mobile.wportal.R.id.name_text_view;

public class PersnalCenterActivity extends BaseActivity {

	private LinearLayout closeBtn, shareAppBtn;

	private TextView titleTextView;

	private WImageView iconImageView;

	private TextView nameTextView;

	private LinearLayout bookmarkBtn;

	private LinearLayout shareBtn;

	private LinearLayout replyBtn;

	private LinearLayout settingBtn, ll_nbtn_reference;

	private ViewFlipper viewFlipper;

	private LinearLayout tab01UnderLine, tab02UnderLine;

	private RelativeLayout tab01Btn, tab02Btn;

	private LinearLayout sweetLayout, sweetBtn;

	private ImageView sweetIcon, sweetBtnIcon;

	private TextView sweetText;

	private PersnalCenterListAdapter listAdapter;

	private ListView msgListView;

	private ListView listView;

	private LinearLayout ll_noMoney, ll_noSend, ll_noGet, ll_noActive, ll_reMoney;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {

			setContentView(com.rs.mobile.wportal.R.layout.activity_persnal_center_rs);

			titleTextView = (TextView) findViewById(com.rs.mobile.wportal.R.id.title_text_view);

			viewFlipper = (ViewFlipper) findViewById(com.rs.mobile.wportal.R.id.view_flipper);

			iconImageView = (WImageView) findViewById(com.rs.mobile.wportal.R.id.icon_image_view);
			
			iconImageView.setCircle(true);
			
			// iconImageView.setWithoutDrawableView(this);
			//
			// iconImageView.destroyDrawingCache();

			rsMyOrderCheck();

			closeBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.close_btn);
			closeBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						if (titleTextView.getText().toString()
								.equals(getString(com.rs.mobile.wportal.R.string.msg_have_sweet))) {

							titleTextView.setText(com.rs.mobile.wportal.R.string.reply);

							showPrevious(1);

							showSweetView();

						} else if (viewFlipper.getDisplayedChild() > 0) {

							showPrevious();

						} else {

							finish();

						}

					} catch (Exception e) {

						L.e(e);

					}

				}
			});
			
			shareAppBtn = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.share_app_btn);
			shareAppBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
						
						Intent sendIntent = new Intent();
						sendIntent.setAction(Intent.ACTION_SEND);
						sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(com.rs.mobile.wportal.R.string.share));
						sendIntent.putExtra(Intent.EXTRA_TEXT, "http://portal.gigawon.cn:8488/AppDownload/index");
						sendIntent.setType("text/plain");
						startActivity(sendIntent);
						
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			shareAppBtn.setVisibility(View.GONE);

			bookmarkBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.bookmark_btn);
			bookmarkBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					showNewsListView(com.rs.mobile.wportal.R.string.bookmark, C.BASE_URL
							+ Constant.PERSNAL_MY_SCRAB, C.KEY_JSON_SCRAB_DATA,
							PersnalCenterListAdapter.LIST_TYPE_BOOKMARK);

				}
			});

			shareBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.share_btn);
			shareBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					showNewsListView(com.rs.mobile.wportal.R.string.share, C.BASE_URL
							+ Constant.PERSNAL_MY_SHARE, C.KEY_JSON_SHARE_DATA,
							PersnalCenterListAdapter.LIST_TYPE_SHARE);

				}
			});

			replyBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.reply_btn);
			replyBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					showNext(1);

					showReplyView();

				}
			});

			ll_nbtn_reference = (LinearLayout)findViewById(R.id.btn_reference);
			ll_nbtn_reference.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(PersnalCenterActivity.this,
							PersnalReference.class);
					startActivityForResult(i, 1000);
				}
			});

			settingBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.setting_btn);
			settingBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(PersnalCenterActivity.this,
							SettingActivity.class);
					startActivityForResult(i, 1000);

				}
			});

			nameTextView = (TextView) findViewById(name_text_view);

			tab01Btn = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.tab_01_btn);
			tab01Btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						showReplyView();

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			tab02Btn = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.tab_02_btn);
			tab02Btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						showSweetView();

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			tab01UnderLine = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.tab_01_under_line);

			tab02UnderLine = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.tab_02_under_line);

			sweetLayout = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.sweet_layout);
			sweetLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						showMySweetView();

					} catch (Exception e) {

						L.e(e);

					}
				}
			});

			sweetIcon = (ImageView) findViewById(com.rs.mobile.wportal.R.id.sweet_icon);

			sweetText = (TextView) findViewById(com.rs.mobile.wportal.R.id.sweet_text);

			sweetBtn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.my_sweet_btn);

			sweetBtnIcon = (ImageView) findViewById(com.rs.mobile.wportal.R.id.sweet_btn_icon);

			msgListView = (ListView) findViewById(com.rs.mobile.wportal.R.id.msg_list_view);
			msgListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view,
						int position, long id) {
					// TODO Auto-generated method stub

					showDetail(position);

				}
			});

			msgListView
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> adapter,
								View view, int position, long id) {
							// TODO Auto-generated method stub

							showDeleteDialog(position);

							return true;
						}
					});

			listView = (ListView) findViewById(com.rs.mobile.wportal.R.id.list_view);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view,
						int position, long id) {
					// TODO Auto-generated method stub

					showDetail(position);

				}
			});

			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> adapter,
						View view, int position, long id) {
					// TODO Auto-generated method stub

					showDeleteDialog(position);

					return true;
				}
			});

			String token = S.getShare(PersnalCenterActivity.this, C.KEY_JSON_TOKEN, "");

			if (token != null && !token.equals("")) {
			
				getPersnalInfo(token);
			
			}

		} catch (Exception e) {

			e(e);

		}
		
		//버전 가져오기
		try {
			
			PackageManager pm = getPackageManager();
			
			PackageInfo info = null;
		
			info = pm.getPackageInfo(getApplicationContext().getPackageName(), 0);
			
			if (info != null) {
				
				((TextView)findViewById(com.rs.mobile.wportal.R.id.app_ver_text_view)).setText(getResources().getString(com.rs.mobile.wportal.R.string.app_name) +
						"Version : v" + info.versionName + "(" + (C.BASE_URL.contains("portal.osunggiga.cn")? "P":"D") + ")");
				
			}
			
		} catch (Exception e) {

			e(e);

		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		String token = S.getShare(PersnalCenterActivity.this, C.KEY_JSON_TOKEN, "");

		if (token == null || token.equals("")) {
			finish();
		}

	}

	private void rsMyOrderCheck() {
		ll_noMoney = (LinearLayout)findViewById(R.id.noMoney);
		ll_noMoney.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String, Integer> bundle = new HashMap();
				bundle.put("position",1);
				UiUtil.startActivityStrtoInt(getApplicationContext(), MyOrderActivity.class,bundle);

			}
		});

		ll_noSend = (LinearLayout)findViewById(R.id.noSend);
		ll_noSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String, Integer> bundle = new HashMap();
				bundle.put("position",2);
				UiUtil.startActivityStrtoInt(getApplicationContext(), MyOrderActivity.class,bundle);
			}
		});

		ll_noGet = (LinearLayout)findViewById(R.id.noGet);
		ll_noGet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String, Integer> bundle = new HashMap();
				bundle.put("position",3);
				UiUtil.startActivityStrtoInt(getApplicationContext(), MyOrderActivity.class,bundle);
			}
		});

		ll_noActive = (LinearLayout)findViewById(R.id.noActive);
		ll_noActive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String, Integer> bundle = new HashMap();
				bundle.put("position",5);
				UiUtil.startActivityStrtoInt(getApplicationContext(), MyOrderActivity.class,bundle);
			}
		});

		ll_reMoney = (LinearLayout)findViewById(R.id.reMoney);
		ll_reMoney.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PageUtil.jumpToWithFlag(PersnalCenterActivity.this, SmReturnGoodsListActivity.class);
			}
		});

	}

	private void showNext(int which) {

		try {

			// viewFlipper.setInAnimation(AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.push_left_in));
			// viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.push_left_out));
			viewFlipper.setDisplayedChild(which);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void showPrevious() {

		try {
			hideNoData();
			titleTextView.setText(com.rs.mobile.wportal.R.string.persnal_center);

			// viewFlipper.setInAnimation(AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.push_right_in));
			// viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.push_right_out));
			viewFlipper.setDisplayedChild(0);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void showPrevious(int which) {

		try {
			hideNoData();
			titleTextView.setText(com.rs.mobile.wportal.R.string.persnal_center);

			// viewFlipper.setInAnimation(AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.push_right_in));
			// viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.push_right_out));
			viewFlipper.setDisplayedChild(which);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void showReplyView() {

		try {
			hideNoData();
			titleTextView.setText(com.rs.mobile.wportal.R.string.reply);

			// Animation inUnderAnimation =
			// AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.scale_left_in);
			// Animation outUnderAnimation =
			// AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.scale_left_out);
			//
			// tab01UnderLine.startAnimation(inUnderAnimation);
			// tab02UnderLine.startAnimation(outUnderAnimation);

			tab01UnderLine.setVisibility(View.VISIBLE);
			tab02UnderLine.setVisibility(View.INVISIBLE);
			sweetLayout.setVisibility(View.GONE);
			sweetIcon.setVisibility(View.GONE);
			sweetText.setVisibility(View.GONE);
			sweetBtn.setVisibility(View.GONE);
			sweetBtnIcon.setVisibility(View.GONE);

			if (listAdapter != null && listAdapter.getListItems() != null) {

				listAdapter.getListItems().clear();

				listAdapter.notifyDataSetChanged();

			}

			OkHttpHelper helper = new OkHttpHelper(PersnalCenterActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_REQUEST_MEMBER_ID, S.getShare(
					PersnalCenterActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription,
						JSONObject data, String flag) {
					try {
						ArrayList<JSONObject> listItems = new ArrayList<JSONObject>();
						JSONArray arr = data
								.getJSONArray(C.KEY_JSON_REPLY_DATA);
						if (arr != null) {
							for (int i = 0; i < arr.length(); i++) {
								listItems.add(arr.getJSONObject(i));
							}
							listAdapter = new PersnalCenterListAdapter(
									PersnalCenterActivity.this,
									PersnalCenterListAdapter.LIST_TYPE_REPLY,
									listItems);
							msgListView.setAdapter(listAdapter);
						}else{
							showNoData();
						}
					} catch (Exception e) {
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription,
						JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, C.BASE_URL + Constant.PERSNAL_REPLY, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void showMySweetView() {

		try {
			titleTextView.setText(com.rs.mobile.wportal.R.string.msg_have_sweet);
			findViewById(com.rs.mobile.wportal.R.id.no_data_list).setVisibility(View.GONE);
			showNext(2);

			if (listAdapter != null && listAdapter.getListItems() != null) {

				listAdapter.getListItems().clear();

				listAdapter.notifyDataSetChanged();

			}

			OkHttpHelper helper = new OkHttpHelper(PersnalCenterActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_REQUEST_MEMBER_ID, S.getShare(
					PersnalCenterActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription,
						final JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						ArrayList<JSONObject> listItems = new ArrayList<JSONObject>();

						JSONArray arr = data
								.getJSONArray(C.KEY_JSON_RECOMMAND_DATA);

						if (arr != null && arr.length() > 0) {
								hideNoData();
							for (int i = 0; i < arr.length(); i++) {

								listItems.add(arr.getJSONObject(i));

							}

							listAdapter = new PersnalCenterListAdapter(
									PersnalCenterActivity.this,
									PersnalCenterListAdapter.LIST_TYPE_SWEET,
									listItems);

							listView.setAdapter(listAdapter);

						}else{
							findViewById(com.rs.mobile.wportal.R.id.no_data_list).setVisibility(View.VISIBLE);
						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription,
						JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, C.BASE_URL + Constant.PERSNAL_RECOMMAND, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void getPersnalInfo(String token) {

		try {
			
			showProgressBar();
			findViewById(com.rs.mobile.wportal.R.id.no_data_list).setVisibility(View.GONE);
			hideNoData();
			// nameTextView.setText(S.get(PersnalCenterActivity.this,
			// C.KEY_SHARED_KNICK_NAME));
			//
			// new GetBitmapAsyncTask().execute();

			OkHttpHelper helper = new OkHttpHelper(PersnalCenterActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_REQUEST_MEMBER_ID, S.getShare(
					PersnalCenterActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));
			
			params.put("token", token);

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

						//nameTextView.setText(data.getString(C.KEY_JSON_NICK_NAME));
						nameTextView.setText(S.getShare(getApplicationContext(), C.KEY_REQUEST_MEMBER_ID, "").substring(0,10));
						S.set(PersnalCenterActivity.this, C.KEY_SHARED_KNICK_NAME, data.getString(C.KEY_JSON_NICK_NAME));

						// String sex = data.getString("gender").trim();
						// sexTextView.setText(getString(sex.equals("m")?R.string.man:R.string.woman));

						String imgPath = data.getString("imagePath");

						Uri uri = Uri.parse(imgPath);

						ImageUtil.drawImageFromUri(imgPath,iconImageView);

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
			}, C.BASE_URL + Constant.PERSNAL_GET_INFO, params);

			//
			// final File f = new File(S.get(PersnalCenterActivity.this,
			// C.KEY_SHARED_ICON_PATH));
			//
			// if (f.exists() == true) {
			//
			// new Handler().post(new Runnable() {
			//
			// @Override
			// public void run() {
			// // TODO Auto-generated method stub
			//
			// try {
			//
			// Uri uri =
			// Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
			// f.getAbsolutePath(), null, null));
			//
			// Bitmap bm =
			// ImageUtil.getBitmapFromUri(PersnalCenterActivity.this, uri);
			//
			// drawIamge(iconImageView, bm);
			//
			// } catch (Exception e) {
			//
			// L.e(e);
			//
			// }
			//
			// hideProgressBar();
			//
			// }
			// });
			//
			// }

			// OkHttpHelper helper = new
			// OkHttpHelper(PersnalCenterActivity.this);
			//
			// HashMap<String, String> params = new HashMap<String, String>();
			//
			// params.put(C.KEY_REQUEST_MEMBER_ID,
			// S.get(PersnalCenterActivity.this, C.KEY_REQUEST_MEMBER_ID));
			//
			// helper.addPostRequest(new CallbackLogic() {
			//
			// @Override
			// public void onNetworkError(Request request, IOException e) {
			// // TODO Auto-generated method stub
			//
			// }
			//
			// @Override
			// public void onBizSuccess(String responseDescription, final
			// JSONObject data, final String all_data) {
			// // TODO Auto-generated method stub
			//
			// try {
			//
			// nameTextView.setText(data.getString(C.KEY_JSON_NICK_NAME));
			//
			// String imgPath = data.getString("imagePath");
			//
			// Uri uri = Uri.parse(imgPath);
			//
			// CacheKey cacheKey =
			// DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(uri));
			// ImagePipelineFactory.getInstance().getMainDiskStorageCache().remove(cacheKey);
			//
			// drawIamge(iconImageView, uri);
			//
			// } catch (Exception e) {
			//
			// L.e(e);
			//
			// }
			//
			// }
			//
			// @Override
			// public void onBizFailure(String responseDescription, JSONObject
			// data, String responseCode) {
			// // TODO Auto-generated method stub
			//
			// }
			// }, C.BASE_URL + C.PERSNAL_GET_INFO, params);

		} catch (Exception e) {

			L.e(e);

			hideProgressBar();

		}

	}

	public void showSweetView() {

		try {

			titleTextView.setText(com.rs.mobile.wportal.R.string.reply);

			// Animation inUnderAnimation =
			// AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.scale_right_in);
			// Animation outUnderAnimation =
			// AnimationUtils.loadAnimation(PersnalCenterActivity.this,
			// R.anim.scale_right_out);
			//
			// tab02UnderLine.startAnimation(inUnderAnimation);
			// tab01UnderLine.startAnimation(outUnderAnimation);
			hideNoData();
			tab02UnderLine.setVisibility(View.VISIBLE);
			tab01UnderLine.setVisibility(View.INVISIBLE);
			sweetLayout.setVisibility(View.VISIBLE);
			sweetIcon.setVisibility(View.VISIBLE);
			sweetText.setVisibility(View.VISIBLE);
			sweetBtn.setVisibility(View.VISIBLE);
			sweetBtnIcon.setVisibility(View.VISIBLE);

			if (listAdapter != null && listAdapter.getListItems() != null) {

				listAdapter.getListItems().clear();

				listAdapter.notifyDataSetChanged();

			}

			OkHttpHelper helper = new OkHttpHelper(PersnalCenterActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_REQUEST_MEMBER_ID, S.getShare(
					PersnalCenterActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));

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

						ArrayList<JSONObject> listItems = new ArrayList<JSONObject>();

						JSONArray arr = data
								.getJSONArray(C.KEY_JSON_REPLY_RECOMMAND_DATA);

						if (arr != null && arr.length() > 0) {

							for (int i = 0; i < arr.length(); i++) {

								listItems.add(arr.getJSONObject(i));

							}

							listAdapter = new PersnalCenterListAdapter(
									PersnalCenterActivity.this,
									PersnalCenterListAdapter.LIST_TYPE_MY_SWEET,
									listItems);

							msgListView.setAdapter(listAdapter);

						}else{
							showNoData();
						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription,
						JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, C.BASE_URL + Constant.PERSNAL_MY_RECOMMAND, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void showNewsListView(int title, final String url,
			final String dataKey, final int listType) {

		try {

			titleTextView.setText(title);
			findViewById(com.rs.mobile.wportal.R.id.no_data_list).setVisibility(View.GONE);
			showNext(2);

			if (listAdapter != null && listAdapter.getListItems() != null) {

				listAdapter.getListItems().clear();

				listAdapter.notifyDataSetChanged();

			}

			OkHttpHelper helper = new OkHttpHelper(PersnalCenterActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_REQUEST_MEMBER_ID, S.getShare(
					PersnalCenterActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));

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

						ArrayList<JSONObject> listItems = new ArrayList<JSONObject>();

						JSONArray arr = data.getJSONArray(dataKey);

						if (arr != null && arr.length() > 0) {

							for (int i = 0; i < arr.length(); i++) {

								listItems.add(arr.getJSONObject(i));

							}

							listAdapter = new PersnalCenterListAdapter(
									PersnalCenterActivity.this, listType,
									listItems);

							listView.setAdapter(listAdapter);

						}else{
							findViewById(com.rs.mobile.wportal.R.id.no_data_list).setVisibility(View.VISIBLE);
						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription,
						JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, url, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void showDetail(int position) {

		try {

			JSONObject item = (JSONObject) listAdapter.getItem(position);
			Intent i = new Intent(PersnalCenterActivity.this, WebActivity.class);
			i.putExtra(C.KEY_INTENT_URL, item.getString(C.KEY_JSON_URL));
			startActivity(i);

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 
	 * @param params
	 * @param type
	 */
	public void showDeleteDialog(final int position) {

		try {

			int type = listAdapter.getType();

			String subUrl = "";

			JSONObject itme = (JSONObject) listAdapter.getItem(position);

			final HashMap<String, String> params = new HashMap<String, String>();

			params.put(C.KEY_REQUEST_MEMBER_ID, S.getShare(
					PersnalCenterActivity.this, C.KEY_REQUEST_MEMBER_ID, ""));

			if (type == PersnalCenterListAdapter.LIST_TYPE_BOOKMARK) {

				params.put(C.KEY_REQUEST_SEQ_NEWS,
						itme.getString(C.KEY_JSON_SEQ_BANNER));

				subUrl = Constant.PERSNAL_DEL_BOOKMAEK;

			} else if (type == PersnalCenterListAdapter.LIST_TYPE_SHARE) {

				params.put(C.KEY_REQUEST_SEQ_SHARE,
						itme.getString(C.KEY_JSON_SEQ_SHARE));

				subUrl = Constant.PERSNAL_DEL_SHARE;

			} else if (type == PersnalCenterListAdapter.LIST_TYPE_REPLY) {

				params.put(C.KEY_REQUEST_SEQ_NEWS_REPLY,
						itme.getString("replySeq"));

				subUrl = Constant.PERSNAL_DEL_NEWS_REPLY;

			} else if (type == PersnalCenterListAdapter.LIST_TYPE_SWEET) {

				params.put(C.KEY_REQUEST_SEQ_NEWS,
						itme.getString(C.KEY_JSON_SEQ_BANNER));

				params.put(C.KEY_REQUEST_SEQ_REPLY, itme.getString("replySeq"));

				subUrl = Constant.PERSNAL_DEL_RECOMMAND;

			} else {

				return;

			}

			final String url = C.BASE_URL + subUrl;

			showDialog(getString(com.rs.mobile.wportal.R.string.delete),
					getString(com.rs.mobile.wportal.R.string.msg_delete), getString(com.rs.mobile.wportal.R.string.sure),
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							D.alertDialog.dismiss();

							OkHttpHelper helper = new OkHttpHelper(
									PersnalCenterActivity.this);

							helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

								@Override
								public void onNetworkError(Request request,
										IOException e) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onBizSuccess(
										String responseDescription,
										final JSONObject data,
										final String all_data) {
									// TODO Auto-generated method stub

									try {

										listAdapter.getListItems().remove(
												position);

										listAdapter.notifyDataSetChanged();

									} catch (Exception e) {

										L.e(e);

									}

								}

								@Override
								public void onBizFailure(
										String responseDescription,
										JSONObject data, String responseCode) {
									// TODO Auto-generated method stub

								}
							}, url, params);

						}
					}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							D.alertDialog.dismiss();
						}
					});

		} catch (Exception e) {

			L.e(e);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			if (requestCode == 1000) {

				String token = S.getShare(PersnalCenterActivity.this, C.KEY_JSON_TOKEN, "");

				if (token != null && !token.equals("")) {
				
					getPersnalInfo(token);
				
				}

			}

		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if (titleTextView.getText().toString()
				.equals(getString(com.rs.mobile.wportal.R.string.msg_have_sweet))) {

			titleTextView.setText(com.rs.mobile.wportal.R.string.reply);

			showPrevious(1);

			showSweetView();

		} else if (viewFlipper.getDisplayedChild() > 0) {

			showPrevious();

		} else {

			super.onBackPressed();

		}

	}

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
	// File f = new File(S.get(PersnalCenterActivity.this,
	// C.KEY_SHARED_ICON_PATH));
	//
	// Uri uri =
	// Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
	// f.getAbsolutePath(), null, null));
	//
	// Bitmap bm = ImageUtil.getBitmapFromUri(PersnalCenterActivity.this, uri);
	//
	// return new BitmapDrawable(bm);
	//
	// } catch (Exception e) {
	//
	// L.e(e);
	//
	// return null;
	//
	// }
	//
	//
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
	// }
	//
	// }
	//
	// }

}
