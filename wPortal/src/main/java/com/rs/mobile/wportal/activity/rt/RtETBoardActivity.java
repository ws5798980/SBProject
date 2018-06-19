package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.activity.WebActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.UiUtil;
import com.rs.mobile.common.util.Util;
import com.rs.mobile.common.util.UtilCheckLogin;
import com.rs.mobile.common.util.UtilClear;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.adapter.rt.RtETBoardListAdapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import okhttp3.Request;

/**
 * 
 * @author ZhaoYun
 * @date 2017-3-13
 */
public class RtETBoardActivity extends BaseActivity implements OnItemClickListener, OnRefreshListener2<ListView> {

	/*
	 * xml
	 */
	private Toolbar toolbar;

	private TextView tv_title;

	private TextView naviGameBtn;

	private LinearLayout iv_back;

	// 참여자 초대하기 버튼 뷰
	private LinearLayout invite_btn_area;

	// 참여자 초대하기 버튼
	private LinearLayout invite_btn;

	// 하단 뷰
	private LinearLayout footer_view;

	// 카트 뷰
	private LinearLayout cart_view;

	// 총 금액
	private TextView total_amount_text_view;

	// 참여자 갯수
	private TextView cart_count_text_view;

	// 계산하기 버튼
	private TextView calculate_btn;

	// 게임버튼
	private TextView game_btn;

	// 결제하기 버튼
	private TextView pay_btn;

	private PullToRefreshListView list_view;

	/*
	 * variable
	 */
	private String groupId;

	private String groupStatus;

	private String goldenBellYN;

	private String groupDesc;

	private String goldenbellType;

	private String address;

	private String restaurantCode;

	private String images;

	private String orderMemberCnt;

	private String gameUrl;

	private String gameStatus;

	private String roomId;

	private boolean isLoading = false;

	private JSONObject mainData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_board_eating_together);

		groupId = getIntent().getStringExtra("groupID");

		restaurantCode = getIntent().getStringExtra("restaurantCode");

		initToolbar();

		initViews();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		loadingCount = 0;

		initDates();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		loadingCount = 2;

		loadingHandler.removeCallbacks(loadingRunnable);

	}

	private void initToolbar() {

		try {

			toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);

			iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);

			tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);

			naviGameBtn = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_right1);

			setSupportActionBar(toolbar);

		} catch (Exception e) {

			L.e(e);

		}
	}

	private void initViews() {

		try {

			tv_title.setText(getString(com.rs.mobile.wportal.R.string.rt_eating_together));

			naviGameBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						if (gameStatus != null
								&& (gameStatus.equals("J") || gameStatus.equals("C") || gameStatus.equals("F"))) {

							// 게임 하기

							Intent i = new Intent(RtETBoardActivity.this, WebActivity.class);

							String postData = "?groupId=" + groupId + "&memberId="
									+ S.getShare(RtETBoardActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&token="
									+ S.getShare(RtETBoardActivity.this, C.KEY_JSON_TOKEN, "");

							if (gameIndex == 0) { // 룰렛게임

								i.putExtra(C.KEY_INTENT_URL,
										Constant.BASE_URL_RT + Constant.RT_GAME_ROULETTE + postData);

							} else if (gameIndex == 1) { // 점핑재익

								i.putExtra(C.KEY_INTENT_URL,
										Constant.BASE_URL_RT + Constant.RT_GAME_JUMPING + postData);

							} else if (gameIndex == 2) { // 슬롯머신

								i.putExtra(C.KEY_INTENT_URL, Constant.BASE_URL_RT + Constant.RT_GAME_SLOT + postData);

							} else { // 랜덤게임

								i.putExtra(C.KEY_INTENT_URL, Constant.BASE_URL_RT + Constant.RT_GAME_RANDUM + postData);

							}

							i.putExtra(C.KEY_INTENT_URL, gameUrl + postData);

							// String postData = "groupId=" +
							// URLEncoder.encode(groupId, "UTF-8")
							// + "&memberId=" +
							// URLEncoder.encode(S.getShare(RtETBoardActivity.this,
							// C.KEY_REQUEST_MEMBER_ID, ""), "UTF-8")
							// + "&token=" +
							// URLEncoder.encode(S.getShare(RtETBoardActivity.this,
							// C.KEY_JSON_TOKEN, ""), "UTF-8");
							//
							// i.putExtra("method", 1);
							//
							// i.putExtra("postData", postData);

							L.d(postData);

							startActivity(i);

						} else {

							naviGameBtn.setVisibility(View.GONE);

						}

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			naviGameBtn.setVisibility(View.GONE);

			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			invite_btn_area = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.invite_btn_area);

			invite_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.invite_btn);

			invite_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					try {
						
						UtilClear.CheckLogin(RtETBoardActivity.this, new UtilCheckLogin.CheckListener() {
							
							@Override
							public void onDoNext() {
								if (UiUtil.isInstallApp(RtETBoardActivity.this, "cn.ycapp.im")) {
									Util.Debug_Toast_Message(getApplicationContext(), "Messager Run!!");
									Intent intent = new Intent();

									Bundle bundle = new Bundle();

									bundle.putString("title", (goldenBellYN != null && goldenBellYN.equals("Y"))
											? getString(com.rs.mobile.wportal.R.string.rt_golden_bell) : getString(com.rs.mobile.wportal.R.string.rt_eating_together));

									bundle.putString("images", images);

									bundle.putString("from", getString(com.rs.mobile.wportal.R.string.app_name));

									bundle.putString("description", address);

									bundle.putString("roomid", groupId);

									bundle.putString("roomname", (goldenBellYN != null && goldenBellYN.equals("Y"))
											? getString(com.rs.mobile.wportal.R.string.rt_golden_bell) : getString(com.rs.mobile.wportal.R.string.rt_eating_together));

									bundle.putString("sharetitle",
											((goldenBellYN != null && goldenBellYN.equals("Y"))
													? (getString(com.rs.mobile.wportal.R.string.rt_golden_bell) + " " + goldenbellType)
													: getString(com.rs.mobile.wportal.R.string.rt_eating_together)) + " " + groupDesc);

									bundle.putString("weburl",
											"slapp://phone" + "$" + C.TYPE_EATING_TOGETHER + "$" + groupId);

									intent.setClassName("cn.ycapp.im", "cn.ycapp.im.ui.activity.SharedReceiverActivity");

									intent.putExtras(bundle);

									startActivity(intent);



								} else {

									Uri uri = Uri.parse("market://details?id=" + "cn.ycapp.im");
									Intent installIntent = new Intent(Intent.ACTION_VIEW, uri);
									if (UiUtil.isInstallApp(RtETBoardActivity.this, "com.tencent.android.qqdownloader")) {
										installIntent.setPackage("com.tencent.android.qqdownloader");
										Util.Debug_Toast_Message(getApplicationContext(), "tencent qqdownloader");
									} else if (UiUtil.isInstallApp(RtETBoardActivity.this, "com.qihoo.appstore")) {
										installIntent.setPackage("com.qihoo.appstore");
										Util.Debug_Toast_Message(getApplicationContext(), "com.qihoo.appstore");
									} else if (UiUtil.isInstallApp(RtETBoardActivity.this, "com.baidu.appsearch")) {
										installIntent.setPackage("com.baidu.appsearch");
										Util.Debug_Toast_Message(getApplicationContext(), "com.baidu.appsearch");
									}
									installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

									startActivity(installIntent);

								}

							}
						});


					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			footer_view = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.footer_view);

			cart_view = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.cart_view);

			total_amount_text_view = (TextView) findViewById(com.rs.mobile.wportal.R.id.total_amount_text_view);

			calculate_btn = (TextView) findViewById(com.rs.mobile.wportal.R.id.calculate_btn);

			calculate_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(RtETBoardActivity.this, RtCalvulaterActivity.class);

					i.putExtra("roomId", roomId);

					i.putExtra("groupID", groupId);

					startActivity(i);

				}
			});

			game_btn = (TextView) findViewById(com.rs.mobile.wportal.R.id.game_btn);

			game_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						/*
						 * 게임 버튼을 누른 사람이 마스터이므로
						 * 
						 * 본인 확인과 주문이 완료된 경우에만 게임을 진행하게 한다
						 */
						JSONArray arr = mainData.getJSONArray("GroupMemberList");

						if (arr != null && arr.length() > 0) {

							boolean isMasterOrder = false;

							for (int i = 0; i < arr.length(); i++) {

								try {

									JSONObject item = arr.getJSONObject(i);

									// 본인 확인
									String myYN = item.getString("myYN");

									// 메뉴 고르기
									String orderYN = item.getString("orderYN");

									// 주문을 완료한 경우
									if (myYN != null && myYN.equals("Y") && orderYN != null && orderYN.equals("Y")) {

										isMasterOrder = true;

										break;

									}

								} catch (Exception e) {

									L.e(e);

								}

							}

							// 방장이 주문을 완료하지 않은 경우
							if (isMasterOrder == false) {

								t(getString(com.rs.mobile.wportal.R.string.rt_msg_precondition_game_02));

								return;

							}

							/*
							 * 2명 이상이 주문을 완료해야 게임을 진행할수 있다
							 */
							if (orderMemberCnt != null && !orderMemberCnt.equals("")) {

								int count = Integer.parseInt(orderMemberCnt);

								if (count < 2) {

									t(getString(com.rs.mobile.wportal.R.string.rt_msg_precondition_game));

								} else {

									showGameDialog();

								}

							}

						}

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			pay_btn = (TextView) findViewById(com.rs.mobile.wportal.R.id.pay_btn);

			pay_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {

						/*
						 * 1명 이상이 주문을 완료해야 진행할수 있다
						 */
						if (orderMemberCnt != null && !orderMemberCnt.equals("")) {

							int count = Integer.parseInt(orderMemberCnt);

							if (count < 1) {

								t(getString(com.rs.mobile.wportal.R.string.rt_msg_precondition_pay));

							} else {

								try {

									paymentDialog();

								} catch (Exception e) {

									L.e(e);

								}

							}

						} else {

							t(getString(com.rs.mobile.wportal.R.string.rt_msg_precondition_pay));

						}

					} catch (Exception e) {

						L.e(e);

					}

				}
			});

			cart_count_text_view = (TextView) findViewById(com.rs.mobile.wportal.R.id.cart_count_text_view);

			list_view = (PullToRefreshListView) findViewById(com.rs.mobile.wportal.R.id.list_view);

			list_view.setOnRefreshListener(this);

			list_view.setOnItemClickListener(this);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void initDates() {

		try {

			if (interruptLoaing == true) {

				return;

			}
			
			UtilClear.CheckLogin(RtETBoardActivity.this, new UtilCheckLogin.CheckError() {
				
				@Override
				public void onError() {
					finish();
				}
			});

			isLoading = true;

			OkHttpHelper helper = new OkHttpHelper(RtETBoardActivity.this, false);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

					new Handler().post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							isLoading = false;

							list_view.onRefreshComplete();

						}

					});

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(all_data);

						if (interruptLoaing == true) {

							return;

						}

						String status = data.getString("status");

						// t(data.getString("msg"));

						if (status != null && status.equals("1")) {

							data = data.getJSONObject("data");

							mainData = data;

							// 그룹 생성 여부 C : 생성 완료
							groupStatus = data.getString("groupStatus");

							// 그룹 비고 (메신져에 요청할 비고 정보)
							groupDesc = data.getString("groupDesc");

							// 골든벨 여부(Y : 골든벨 N : 같이먹기)
							goldenBellYN = data.getString("goldenBellYN");

							// 골든벨 종류
							goldenbellType = data.getString("goldenbellType");

							// 상가 주소(메신져에 요청할 가맹점 주소)
							address = data.getString("address");

							// 상가 이미지
							images = data.getString("imgUrl");

							// 방장 여부
							String groupMasterYN = data.getString("groupMasterYN");

							// 게임결과
							gameStatus = data.getString("gameStatus");

							// 참여자 리스트
							JSONArray arr = data.getJSONArray("GroupMemberList");

							// 오더 생성 여부
							String groupFixYN = data.getString("groupFixYN");

							// 참여 게임 url
							gameUrl = data.getString("gameUrl");

							// 총 합계 금액
							Double totalAmount = data.getDouble("totalAmount");

							// 주문을 완료한 참여자 인원
							orderMemberCnt = data.getString("orderMemberCnt");

							// 메신져 방 id
							roomId = data.getString("roomId");

							RtETBoardListAdapter adapter = new RtETBoardListAdapter(RtETBoardActivity.this, arr,
									groupId, restaurantCode, groupMasterYN, groupFixYN);

							list_view.setAdapter(adapter);

							// 메신져로 그룹 초대장을 전송하면 초대하기 버튼을 삭제 한다
							if (groupStatus != null && groupStatus.equals("C")) {

								invite_btn_area.setVisibility(View.VISIBLE);

							} else {

								invite_btn_area.setVisibility(View.GONE);

							}

							// 총 금액이 0보다 큰 경우에만 하단의 뷰가 나타난다
							if (totalAmount > 0) {

								footer_view.setVisibility(View.VISIBLE);

								cart_view.setVisibility(View.VISIBLE);

								cart_count_text_view.setText(orderMemberCnt);

								total_amount_text_view.setText(getString(com.rs.mobile.wportal.R.string.money) + " " + totalAmount);

							} else {

								footer_view.setVisibility(View.GONE);

								cart_view.setVisibility(View.GONE);

							}

							// 게임 혹은 오더,결제가 완료된 경우
							if (groupFixYN != null && groupFixYN.equals("Y")) {

								game_btn.setVisibility(View.GONE);

								// footer view 영역
								if (groupMasterYN != null && groupMasterYN.equals("Y")) {

									pay_btn.setVisibility(View.VISIBLE);

								} else {

									pay_btn.setVisibility(View.GONE);

								}

								// 게임 이름
								String gameName = data.getString("gameName");

								// 게임 버튼이 게임 중일경우에는 게임의 이름으로 나타나고 게임이 완료되면
								// "결과보기"로 보인다
								if (gameStatus != null && gameStatus.equals("F")) {
									// 게임이 완료된 경우

									naviGameBtn.setVisibility(View.VISIBLE);

									naviGameBtn.setText(getString(com.rs.mobile.wportal.R.string.rt_show_result_page));

								} else if (gameStatus != null && (gameStatus.equals("J") || gameStatus.equals("C"))) {
									// 게임 진행중

									naviGameBtn.setVisibility(View.VISIBLE);

									naviGameBtn.setText(gameName);

								} else {
									// 기타 (버튼을 가린다
									naviGameBtn.setVisibility(View.GONE);

								}

							} else {
								// 게임 혹은 오더,결제가 완료되지 않은 경우

								if (groupMasterYN != null && groupMasterYN.equals("Y")) {

									calculate_btn.setVisibility(View.VISIBLE);

									pay_btn.setVisibility(View.VISIBLE);

									if (goldenBellYN != null && goldenBellYN.equals("Y")) {

										game_btn.setVisibility(View.VISIBLE);

									} else {

										game_btn.setVisibility(View.VISIBLE);

									}

									naviGameBtn.setVisibility(View.GONE);

								} else {

									calculate_btn.setVisibility(View.GONE);

									pay_btn.setVisibility(View.GONE);

									game_btn.setVisibility(View.GONE);

									naviGameBtn.setVisibility(View.GONE);

								}

							}

							// 골든벨인 경우에는 아래의 버튼 모두를 가린다
							if (goldenBellYN != null && goldenBellYN.equals("Y")) {

								calculate_btn.setVisibility(View.GONE);

								game_btn.setVisibility(View.GONE);

								naviGameBtn.setVisibility(View.GONE);

							}

						}

						loadingCount = 0;

						loadingHandler.postDelayed(loadingRunnable, 1000);

					} catch (Exception e) {

						L.e(e);

					}

					isLoading = false;

					new Handler().post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							list_view.onRefreshComplete();

						}
					});

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

					new Handler().post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							isLoading = false;

							list_view.onRefreshComplete();

						}
					});

				}
			}, Constant.BASE_URL_RT + Constant.RT_GET_ARR_GROUP_MEMBER + "?memberId="
					+ S.getShare(RtETBoardActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&groupId=" + groupId
					+ "&token=" + S.getShare(RtETBoardActivity.this, C.KEY_JSON_TOKEN, ""), params);

		} catch (Exception e) {

			L.e(e);

			isLoading = false;

		}

		new Handler().post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				list_view.onRefreshComplete();

			}
		});

	}

	@Override
	public void onBackPressed() {
		onBack();
	}

	public void onBack() {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

		initDates();

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

		initDates();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 게임 다이얼로그 생성
	 */
	private int gameIndex = 0;

	public void showGameDialog() {

		try {

			String[] items = new String[4];

			items[0] = getString(com.rs.mobile.wportal.R.string.rt_game_001);
			items[1] = getString(com.rs.mobile.wportal.R.string.rt_game_002);
			items[2] = getString(com.rs.mobile.wportal.R.string.rt_game_003);
			items[3] = getString(com.rs.mobile.wportal.R.string.rt_game_004);

			showSingleChoiceDialog(-1, -1, -1, com.rs.mobile.wportal.R.string.ok, com.rs.mobile.wportal.R.string.cancel, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					try {

						showDialog(getString(com.rs.mobile.wportal.R.string.rt_start_game), getString(com.rs.mobile.wportal.R.string.rt_msg_game),
								getString(com.rs.mobile.wportal.R.string.rt_start_game), new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub

										try {

											D.alertDialog.dismiss();

											Intent i = new Intent(RtETBoardActivity.this, WebActivity.class);

											String postData = "?groupId=" + groupId + "&memberId="
													+ S.getShare(RtETBoardActivity.this, C.KEY_REQUEST_MEMBER_ID, "")
													+ "&token="
													+ S.getShare(RtETBoardActivity.this, C.KEY_JSON_TOKEN, "");

											if (gameIndex == 0) { // 룰렛게임

												i.putExtra(C.KEY_INTENT_URL,
														Constant.BASE_URL_RT + Constant.RT_GAME_ROULETTE + postData);

											} else if (gameIndex == 1) { // 점핑재익

												i.putExtra(C.KEY_INTENT_URL,
														Constant.BASE_URL_RT + Constant.RT_GAME_JUMPING + postData);

											} else if (gameIndex == 2) { // 슬롯머신

												i.putExtra(C.KEY_INTENT_URL,
														Constant.BASE_URL_RT + Constant.RT_GAME_SLOT + postData);

											} else { // 랜덤게임

												i.putExtra(C.KEY_INTENT_URL,
														Constant.BASE_URL_RT + Constant.RT_GAME_RANDUM + postData);

											}

											// String postData = "groupId=" +
											// URLEncoder.encode(groupId,
											// "UTF-8")
											// + "&memberId=" +
											// URLEncoder.encode(S.getShare(RtETBoardActivity.this,
											// C.KEY_REQUEST_MEMBER_ID, ""),
											// "UTF-8")
											// + "&token=" +
											// URLEncoder.encode(S.getShare(RtETBoardActivity.this,
											// C.KEY_JSON_TOKEN, ""), "UTF-8");
											//
											// i.putExtra("method", 1);
											//
											// i.putExtra("postData", postData);

											L.d(postData);

											startActivity(i);

										} catch (Exception e) {

											L.e(e);

										}

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
			}, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			}, items, 0, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					gameIndex = which;

				}
			}, true);

		} catch (Exception e) {

			e(e);

		}

	}

	/**
	 * check payment
	 */
	public void paymentDialog() {

		try {

			showDialog(getString(com.rs.mobile.wportal.R.string.rt_start_payment), getString(com.rs.mobile.wportal.R.string.rt_msg_payment),
					getString(com.rs.mobile.wportal.R.string.rt_start_payment), new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							try {

								D.alertDialog.dismiss();

								interruptLoaing = true;

								OkHttpHelper helper = new OkHttpHelper(RtETBoardActivity.this);

								HashMap<String, String> params = new HashMap<String, String>();

								params.put("", "");

								helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

									@Override
									public void onNetworkError(Request request, IOException e) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onBizSuccess(String responseDescription, JSONObject data,
											final String all_data) {
										// TODO Auto-generated method stub

										try {

											L.d(all_data);

											String status = data.getString("status");

											t(data.getString("msg"));

											if (status != null && status.equals("1")) {

												// 04-22 19:39:00.030:
												// D/w_log(29413):
												// {"status":1,"msg":"success","data":{"orderNum":"201704220000000002D","reserveId":"R201704221907445905"}}

												data = data.getJSONObject("data");

												Intent i = new Intent(RtETBoardActivity.this,
														OrderDetailActivity.class);

												i.putExtra("reserveID", data.getString("reserveId"));

												i.putExtra("orderNumber", data.getString("orderNum"));

												startActivity(i);

												finish();

											}

										} catch (Exception e) {

											L.e(e);

										}

									}

									@Override
									public void onBizFailure(String responseDescription, JSONObject data,
											String responseCode) {
										// TODO Auto-generated method stub

									}
								}, Constant.BASE_URL_RT + Constant.RT_CREATE_GROUP_ORDER + "?groupMasterId="
										+ S.getShare(RtETBoardActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&groupId="
										+ groupId + "&token="
										+ S.getShare(RtETBoardActivity.this, C.KEY_JSON_TOKEN, ""), params);

							} catch (Exception e) {

								L.e(e);

							}

						}
					}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							D.alertDialog.dismiss();
						}
					});

		} catch (Exception e) {

			e(e);

		}

	}

	private boolean interruptLoaing = false;

	private int loadingCount = 2;

	private Handler loadingHandler = new Handler();

	private Runnable loadingRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			if (interruptLoaing == true) {

				loadingCount = 0;

				loadingHandler.removeCallbacks(loadingRunnable);

			}

			if (loadingCount < 2) {

				loadingCount++;

				loadingHandler.postDelayed(loadingRunnable, 1000);

			} else {

				loadingCount = 0;

				loadingHandler.removeCallbacks(loadingRunnable);

				initDates();

			}

		}
	};

}