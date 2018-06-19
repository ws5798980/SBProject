package com.rs.mobile.wportal.activity.rt;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.biz.rt.GroupMember;
import com.rs.mobile.wportal.biz.rt.Menu;
import com.rs.mobile.wportal.biz.rt.ReserveDetailModel;
import com.rs.mobile.wportal.network.rt.NetworkAdapter;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.OnPaymentResultListener;
import com.rs.mobile.wportal.biz.rt.Reserve;
import com.rs.mobile.wportal.rt.RTUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Request;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RtReserveDetailActivity extends BaseActivity implements OnClickListener {

	private String memberID;
	private String reserveID;
	private String orderNum;
	private String reserveStatus;

	// toolbar
	private Toolbar toolbar;
	private LinearLayout iv_back;
	private TextView tv_title;
	private ImageView iv_right1;// share

	// content
	private TextView tv_reservestatus;

	private WImageView iv_thumbnail;
	private TextView tv_sellername;
	private RatingBar rb_sellerrating;
	private TextView tv_price;
	private TextView tv_foodType;
	private TextView tv_distance;
	private TextView tv_count;

	private TextView tv_reserveid_title;
	private TextView tv_reserveid;
	private TextView tv_reservedate;
	private TextView tv_personcount;
	private TextView tv_customerinfo;
	private LinearLayout ll_paytype;
	private TextView tv_paytype;

	private TextView tv_hint;

	private TextView tv_address;

	private LinearLayout ll_menu;
	private LinearLayout ll_menucontainer;

	private LinearLayout ll_amount;
	private TextView tv_totalamount;
	private TextView tv_actualtotalamount_title;
	private TextView tv_actualtotalamount;
	private TextView tv_date;

	// 같이 먹기 영역
	private LinearLayout eating_tegether_menu;

	// 같이 먹기 버튼
	private LinearLayout eating_together_btn;

	// 골든벨 버튼
	private LinearLayout golden_bell_btn;

	// 같이 먹기 체크박스
	private CheckBox eating_together_check_bok;

	// 골든벨 체크박스
	private CheckBox golden_bell_check_bok;

	// 골든벨 상태
	private TextView golden_bell_status;
	private int golden_bell_statusIndex = 0;

	// 골든벨이란?
	private TextView help_golden_bell;

	// 메모
	private EditText et_eat_remark;

	// footer
	private LinearLayout ll_footer;

	// 0 : 같이 먹기
	private String from = "";

	private String groupYN = "";

	private String groupId = "";

	private String groupGameYN = "";

	// 골든벨 상태 arr
	private JSONArray goldenBellCodeArr;

	// 참여자 정보 뷰
	private LinearLayout participant_area;

	// 참여자 인원
	private TextView participant_count_text_view;

	// 게임 결과 확인 버튼
	private TextView check_game_result_text_view;

	// 참여자 정보 리스트
	private HorizontalScrollView participant_scroll_view;

	private LinearLayout participant_list_view;

	private List<GroupMember> groupList;

	// network
	private CompositeSubscription compositeSubscription = new CompositeSubscription();
	private Subscription subscription;
	private Observable<ReserveDetailModel> cache;

	// format
	private DecimalFormat format = new DecimalFormat("#.00");

	// 선택된 데이터
	private Reserve reserveData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_rt_reserve_detail);
		initExtra();
		initToolbar();
		initViews();
		initListeners();
		initDatas();
		init();
	}

	private void initExtra() {
		from = getIntent().getStringExtra("from") == null ? "" : getIntent().getStringExtra("from");
		memberID = getIntent().getStringExtra(C.EXTRA_KEY_MEMBERID) == null
				? S.getShare(RtReserveDetailActivity.this, C.KEY_REQUEST_MEMBER_ID, "")
				: getIntent().getStringExtra(C.EXTRA_KEY_MEMBERID);
		reserveID = getIntent().getStringExtra(C.EXTRA_KEY_RESERVEID) == null ? ""
				: getIntent().getStringExtra(C.EXTRA_KEY_RESERVEID);
		orderNum = getIntent().getStringExtra(C.EXTRA_KEY_ORDERNUM) == null ? ""
				: getIntent().getStringExtra(C.EXTRA_KEY_ORDERNUM);
		reserveStatus = getIntent().getStringExtra(C.EXTRA_KEY_RESERVESTATUS) == null ? ""
				: getIntent().getStringExtra(C.EXTRA_KEY_RESERVESTATUS);
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(com.rs.mobile.wportal.R.id.toolbar);
		iv_back = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.iv_back);
		tv_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_title);
		iv_right1 = (ImageView) findViewById(com.rs.mobile.wportal.R.id.iv_right1);
		setSupportActionBar(toolbar);
	}

	private void initViews() {

		iv_right1.setVisibility(View.GONE);

		tv_reservestatus = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_reservestatus);

		iv_thumbnail = (WImageView) findViewById(com.rs.mobile.wportal.R.id.iv_thumbnail);
		tv_sellername = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_sellername);
		rb_sellerrating = (RatingBar) findViewById(com.rs.mobile.wportal.R.id.rb_sellerrating);
		tv_price = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_price);
		tv_foodType = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_foodType);
		tv_distance = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_distance);
		tv_count = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_count);

		tv_reserveid_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_reserveid_title);
		tv_reserveid = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_reserveid);
		tv_reservedate = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_reservedate);
		tv_personcount = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_personcount);
		tv_customerinfo = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_customerinfo);
		ll_paytype = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_paytype);
		tv_paytype = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_paytype);

		tv_hint = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_hint);

		tv_address = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_address);

		tv_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {

					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putDouble("location_lat", Double.parseDouble(reserveData.getLatitude()));
					bundle.putDouble("location_lng", Double.parseDouble(reserveData.getLongitude()));
					bundle.putString("location_name", reserveData.getRestaurantName());
					intent.setClassName("cn.ycapp.im", "cn.ycapp.im.ui.activity.AMAPLocationActivity");
					intent.putExtras(bundle);
					startActivity(intent);

				} catch (Exception e) {

					L.e(e);

				}

			}
		});

		ll_menu = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_menu);
		ll_menucontainer = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_menucontainer);

		ll_amount = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_amount);
		tv_totalamount = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_totalamount);
		tv_actualtotalamount_title = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_actualtotalamount_title);
		tv_actualtotalamount = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_actualtotalamount);
		tv_date = (TextView) findViewById(com.rs.mobile.wportal.R.id.tv_date);

		// 같이 먹기 영역
		eating_tegether_menu = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.eating_tegether_menu);

		// 같이 먹기 버튼
		eating_together_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.eating_together_btn);
		eating_together_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				eating_together_check_bok.setChecked(true);
				golden_bell_check_bok.setChecked(false);
			}
		});

		// 골든벨 버튼
		golden_bell_btn = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.golden_bell_btn);
		golden_bell_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				eating_together_check_bok.setChecked(false);
				golden_bell_check_bok.setChecked(true);

			}
		});

		// 같이 먹기 체크박스
		eating_together_check_bok = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.eating_together_check_bok);
		eating_together_check_bok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				eating_together_check_bok.setChecked(true);
				golden_bell_check_bok.setChecked(false);

			}
		});

		// 골든벨 체크박스
		golden_bell_check_bok = (CheckBox) findViewById(com.rs.mobile.wportal.R.id.golden_bell_check_bok);
		golden_bell_check_bok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				eating_together_check_bok.setChecked(false);
				golden_bell_check_bok.setChecked(true);

			}
		});

		// 골든벨 기분 상태
		golden_bell_status = (TextView) findViewById(com.rs.mobile.wportal.R.id.golden_bell_status);
		golden_bell_status.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (golden_bell_check_bok.isChecked() == true) {

					if (goldenBellCodeArr == null || goldenBellCodeArr.length() == 0) {

						// 골든벨 코드 가져오기
						getGoldenBellCode();

					} else {

						showGoldenBellCodeDialog();

					}

				}

			}
		});

		// 골든벨이란?
		help_golden_bell = (TextView) findViewById(com.rs.mobile.wportal.R.id.help_golden_bell);
		help_golden_bell.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showAlertDialog(getString(com.rs.mobile.wportal.R.string.rt_msg_golden_bell), getString(com.rs.mobile.wportal.R.string.rt_content_golden_bell),
						getString(com.rs.mobile.wportal.R.string.ok), new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								D.alertDialog.dismiss();
							}
						});

			}
		});

		// 메모
		et_eat_remark = (EditText) findViewById(com.rs.mobile.wportal.R.id.et_eat_remark);

		participant_area = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.participant_area);

		participant_count_text_view = (TextView) findViewById(com.rs.mobile.wportal.R.id.participant_count_text_view);

		check_game_result_text_view = (TextView) findViewById(com.rs.mobile.wportal.R.id.check_game_result_text_view);

		check_game_result_text_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// 게임 결과 확인

			}
		});

		participant_scroll_view = (HorizontalScrollView) findViewById(com.rs.mobile.wportal.R.id.participant_scroll_view);

		participant_list_view = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.participant_list_view);

		ll_footer = (LinearLayout) findViewById(com.rs.mobile.wportal.R.id.ll_footer);
	}

	private void initListeners() {
		iv_back.setOnClickListener(this);
		iv_right1.setOnClickListener(this);
	}

	private void initDatas() {
		tv_title.setText(getString(com.rs.mobile.wportal.R.string.common_text027));
		tv_count.setVisibility(View.GONE);
	}

	private void init() {

		if (subscription != null && !subscription.isUnsubscribed()) {
			subscription.unsubscribe();
			subscription = null;
		}
		if (cache == null) {
			cache = NetworkAdapter.httpAPI().GetReserve(memberID, reserveID, orderNum, reserveStatus)
					.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).cache();
		}
		subscription = cache.subscribe(new Action1<ReserveDetailModel>() {

			@Override
			public void call(ReserveDetailModel reserveDetailModel) {
				if (reserveDetailModel == null || reserveDetailModel.getData() == null) {

					Reserve data = new Reserve();
					settingData(data);

				} else if (reserveDetailModel.getStatus() == 0) {

					Reserve data = reserveDetailModel.getData();
					settingData(data);

				} else {

					t(reserveDetailModel.getMsg());

				}
			}

		}, new Action1<Throwable>() {

			@Override
			public void call(Throwable arg0) {
				arg0.printStackTrace();
			}
		});
		compositeSubscription.add(subscription);

	}

	private void settingData(Reserve data) {

		try {

			reserveData = data;

			groupYN = data.getGroupYN();

			groupId = data.getGroupId();

			groupGameYN = data.getGroupGameYN();

			groupList = data.getGroupList();

			if (groupYN != null && groupYN.equals("Y") && groupList != null && groupList.size() > 0) {

				participant_area.setVisibility(View.VISIBLE);

				participant_count_text_view.setText(groupList.size() + getString(com.rs.mobile.wportal.R.string.rt_participant_count));

				if (groupGameYN != null && groupGameYN.equals("Y")) {

					check_game_result_text_view.setVisibility(View.VISIBLE);

				} else {

					check_game_result_text_view.setVisibility(View.GONE);

				}

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				participant_list_view.removeAllViews();

				for (int i = 0; i < groupList.size(); i++) {

					LinearLayout itemView = (LinearLayout) inflater.inflate(com.rs.mobile.wportal.R.layout.list_item_participant, null);

					WImageView icon_image_view = (WImageView) itemView.findViewById(com.rs.mobile.wportal.R.id.icon_image_view);

					TextView name_text_view = (TextView) itemView.findViewById(com.rs.mobile.wportal.R.id.name_text_view);

					GroupMember item = groupList.get(i);

					ImageUtil.drawImageFromUri(item.getIconImg(), icon_image_view);

					icon_image_view.setCircle(true);

					name_text_view.setText(item.getNickName());

					participant_list_view.addView(itemView);

				}

				participant_list_view.invalidate();

			} else {

				participant_area.setVisibility(View.GONE);

			}

			eating_tegether_menu.setVisibility(View.GONE);

			ll_footer.removeAllViews();
			// null
			if (TextUtils.isEmpty(data.getReserveStatus())) {
				tv_reservestatus.setText("");
				tv_reservestatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

				tv_reserveid.setText("");

				ll_paytype.setVisibility(View.GONE);

				tv_hint.setVisibility(View.GONE);

				tv_address.setVisibility(View.GONE);

				ll_menu.setVisibility(View.GONE);

				ll_amount.setVisibility(View.GONE);
			}
			// O 待付款
			else if ("O".equals(data.getReserveStatus())) { // 결제대기
				tv_reservestatus.setText(getString(com.rs.mobile.wportal.R.string.ht_text_097));
				tv_reservestatus.setCompoundDrawablesWithIntrinsicBounds(com.rs.mobile.wportal.R.drawable.icon_rt_reserve_detail_status_02, 0,
						0, 0);

				tv_reserveid_title.setText(getString(com.rs.mobile.wportal.R.string.common_text041));
				tv_reserveid.setText(data.getReserveId() == null ? "" : data.getReserveId());

				ll_paytype.setVisibility(View.GONE);

				tv_hint.setVisibility(View.GONE);

				tv_address.setVisibility(View.GONE);

				resetMenuData(data.getMenu());
				ll_menu.setVisibility(View.VISIBLE);

				tv_totalamount.setText(format.format(data.getTotalAmount()));
				tv_actualtotalamount_title.setText(getString(com.rs.mobile.wportal.R.string.common_text042));
				tv_actualtotalamount.setText(format.format(data.getActualTotalAmount()));
				tv_date.setText(data.getDate() == null ? "" : data.getDate());
				ll_amount.setVisibility(View.VISIBLE);

				RelativeLayout rl_footer = (RelativeLayout) getLayoutInflater()
						.inflate(com.rs.mobile.wportal.R.layout.layout_rt_reserve_detail_status_o_footer, null);
				TextView tv_cancelreserve = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_cancelreserve);
				TextView tv_pay = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_pay);
				tv_cancelreserve.setOnClickListener(this);
				tv_pay.setOnClickListener(this);
				ll_footer.addView(rl_footer);
			}
			// R 已预约
			else if ("R".equals(data.getReserveStatus())) { // 예약된 오더

				try {

					tv_reservestatus.setText(getString(com.rs.mobile.wportal.R.string.common_text043));
					tv_reservestatus.setCompoundDrawablesWithIntrinsicBounds(
							com.rs.mobile.wportal.R.drawable.icon_rt_reserve_detail_status_01, 0, 0, 0);

					tv_reserveid_title.setText(getString(com.rs.mobile.wportal.R.string.common_text044));
					tv_reserveid.setText(data.getReserveId() == null ? "" : data.getReserveId());

					ll_paytype.setVisibility(View.GONE);

					tv_hint.setVisibility(View.VISIBLE);

					tv_address.setVisibility(View.VISIBLE);
					tv_address.setText(data.getAddress() == null ? "" : data.getAddress());

					ll_menu.setVisibility(View.GONE);

					ll_amount.setVisibility(View.GONE);

					if ((from != null && from.equals("0")) // 최초 예약에서 진행
							|| (groupYN.equals("Y") && (groupId == null || groupId.equals("")))) {
						// groupYN == Y && groupId == ""
						// 같이먹기 예약만 한 상태

						eating_tegether_menu.setVisibility(View.VISIBLE);

						// footer
						RelativeLayout rl_footer = (RelativeLayout) getLayoutInflater()
								.inflate(com.rs.mobile.wportal.R.layout.layout_rt_reserve_detail_status_et_tg_footer, null);
						TextView tv_001 = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_001);
						tv_001.setOnClickListener(this);
						ll_footer.addView(rl_footer);

						// 골든벨 코드 가져오기
						getGoldenBellCode();

					} else if (groupYN.equals("Y") && groupId != null && !groupId.equals("")) {
						// groupYN == Y && groupId != ""
						// 같이먹기 그룹을 생성한 상태

						eating_tegether_menu.setVisibility(View.GONE);

						// footer
						RelativeLayout rl_footer = (RelativeLayout) getLayoutInflater()
								.inflate(com.rs.mobile.wportal.R.layout.layout_rt_reserve_detail_status_et_tg_footer, null);
						TextView tv_001 = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_001);
						tv_001.setOnClickListener(this);
						ll_footer.addView(rl_footer);

					} else {

						eating_tegether_menu.setVisibility(View.GONE);

						RelativeLayout rl_footer = (RelativeLayout) getLayoutInflater()
								.inflate(com.rs.mobile.wportal.R.layout.layout_rt_reserve_detail_status_r_footer, null);
						TextView tv_detelereserve = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_detelereserve);
						TextView tv_order = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_order);
						tv_detelereserve.setOnClickListener(this);
						tv_order.setOnClickListener(this);
						ll_footer.addView(rl_footer);

					}

				} catch (Exception e) {

					L.e(e);

				}

			}
			// B 已点菜
			else if ("B".equals(data.getReserveStatus())) { // 주문된 오더
				tv_reservestatus.setText(getString(com.rs.mobile.wportal.R.string.common_text045));
				tv_reservestatus.setCompoundDrawablesWithIntrinsicBounds(com.rs.mobile.wportal.R.drawable.icon_rt_reserve_detail_status_01, 0,
						0, 0);

				tv_reserveid_title.setText(getString(com.rs.mobile.wportal.R.string.common_text041));
				tv_reserveid.setText(data.getReserveId() == null ? "" : data.getReserveId());

				ll_paytype.setVisibility(View.VISIBLE);
				tv_paytype.setText(data.getPayType() + "");

				tv_hint.setVisibility(View.VISIBLE);

				tv_address.setVisibility(View.VISIBLE);
				tv_address.setText(data.getAddress() == null ? "" : data.getAddress());

				resetMenuData(data.getMenu());
				ll_menu.setVisibility(View.VISIBLE);

				tv_totalamount.setText(format.format(data.getTotalAmount()));
				tv_actualtotalamount_title.setText(getString(com.rs.mobile.wportal.R.string.common_text046));
				tv_actualtotalamount.setText(format.format(data.getActualTotalAmount()));
				tv_date.setText(data.getDate() == null ? "" : data.getDate());
				ll_amount.setVisibility(View.VISIBLE);

				RelativeLayout rl_footer = (RelativeLayout) getLayoutInflater()
						.inflate(com.rs.mobile.wportal.R.layout.layout_rt_reserve_detail_status_b_footer, null);
				TextView tv_refund = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_refund);
				tv_refund.setOnClickListener(this);
				ll_footer.addView(rl_footer);
			}
			// F 未评价
			else if ("F".equals(data.getReserveStatus())) { // 평가대기
				tv_reservestatus.setText(getString(com.rs.mobile.wportal.R.string.common_text047));
				tv_reservestatus.setCompoundDrawablesWithIntrinsicBounds(com.rs.mobile.wportal.R.drawable.icon_rt_reserve_detail_status_01, 0,
						0, 0);

				tv_reserveid_title.setText(getString(com.rs.mobile.wportal.R.string.common_text041));
				tv_reserveid.setText(data.getReserveId() == null ? "" : data.getReserveId());

				ll_paytype.setVisibility(View.VISIBLE);
				tv_paytype.setText(data.getPayType() + "");

				tv_hint.setVisibility(View.VISIBLE);

				tv_address.setVisibility(View.VISIBLE);
				tv_address.setText(data.getAddress() == null ? "" : data.getAddress());

				resetMenuData(data.getMenu());
				ll_menu.setVisibility(View.VISIBLE);

				tv_totalamount.setText(format.format(data.getTotalAmount()));
				tv_actualtotalamount_title.setText(getString(com.rs.mobile.wportal.R.string.common_text046));
				tv_actualtotalamount.setText(format.format(data.getActualTotalAmount()));
				tv_date.setText(data.getDate() == null ? "" : data.getDate());
				ll_amount.setVisibility(View.VISIBLE);

				RelativeLayout rl_footer = (RelativeLayout) getLayoutInflater()
						.inflate(com.rs.mobile.wportal.R.layout.layout_rt_reserve_detail_status_f_footer, null);
				TextView tv_deleteorder = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_deleteorder);
				TextView tv_again = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_again);
				TextView tv_tocomment = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_tocomment);
				tv_deleteorder.setOnClickListener(this);
				tv_again.setOnClickListener(this);
				tv_tocomment.setOnClickListener(this);
				ll_footer.addView(rl_footer);
			}
			// S 已评价
			else if ("S".equals(data.getReserveStatus())) { // 평가된 오더
				tv_reservestatus.setText(getString(com.rs.mobile.wportal.R.string.common_text047));
				tv_reservestatus.setCompoundDrawablesWithIntrinsicBounds(com.rs.mobile.wportal.R.drawable.icon_rt_reserve_detail_status_01, 0,
						0, 0);

				tv_reserveid_title.setText(getString(com.rs.mobile.wportal.R.string.common_text041));
				tv_reserveid.setText(data.getReserveId() == null ? "" : data.getReserveId());

				ll_paytype.setVisibility(View.VISIBLE);
				tv_paytype.setText(data.getPayType() + "");

				tv_hint.setVisibility(View.VISIBLE);

				tv_address.setVisibility(View.VISIBLE);
				tv_address.setText(data.getAddress() == null ? "" : data.getAddress());

				resetMenuData(data.getMenu());
				ll_menu.setVisibility(View.VISIBLE);

				tv_totalamount.setText(format.format(data.getTotalAmount()));
				tv_actualtotalamount_title.setText(getString(com.rs.mobile.wportal.R.string.common_text046));
				tv_actualtotalamount.setText(format.format(data.getActualTotalAmount()));
				tv_date.setText(data.getDate() == null ? "" : data.getDate());
				ll_amount.setVisibility(View.VISIBLE);

				RelativeLayout rl_footer = (RelativeLayout) getLayoutInflater()
						.inflate(com.rs.mobile.wportal.R.layout.layout_rt_reserve_detail_status_s_footer, null);
				TextView tv_deleteorder = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_deleteorder);
				TextView tv_again = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_again);
				TextView tv_checkcomment = (TextView) rl_footer.findViewById(com.rs.mobile.wportal.R.id.tv_checkcomment);
				tv_deleteorder.setOnClickListener(this);
				tv_again.setOnClickListener(this);
				tv_checkcomment.setOnClickListener(this);
				ll_footer.addView(rl_footer);
			}

			ImageUtil.drawImageFromUri(data.getShopThumImage() == null ? "" : data.getShopThumImage(), iv_thumbnail);
			tv_sellername.setText(data.getRestaurantName() == null ? "" : data.getRestaurantName());
			rb_sellerrating.setRating(data.getAverageRate());
			tv_price.setText(data.getAveragePay() == null ? "" : data.getAveragePay());
			tv_foodType.setText(data.getFloor() == null ? "" : data.getFloor());
			tv_distance.setText(data.getDistance() == null ? "" : data.getDistance());

			tv_reservedate.setText(data.getReserveDate() == null ? "" : data.getReserveDate());
			tv_personcount.setText(data.getPersonCnt() == null ? "" : data.getPersonCnt());
			String name = ((data.getName() == null) ? "" : (data.getName() + "  "));
			String phone = (data.getPhone() == null ? "" : data.getPhone());
			tv_customerinfo.setText(name + phone);

		} catch (Exception e) {

			L.e(e);

		}

	}

	private void resetMenuData(List<Menu> menuList) {
		ll_menucontainer.removeAllViews();
		if (menuList != null && !menuList.isEmpty()) {
			for (Menu menu : menuList) {
				LinearLayout ll_menu_item = (LinearLayout) getLayoutInflater()
						.inflate(com.rs.mobile.wportal.R.layout.layout_rt_menu_list_item, null);
				TextView tv_itemname = (TextView) ll_menu_item.findViewById(com.rs.mobile.wportal.R.id.tv_itemname);
				TextView tv_quantity = (TextView) ll_menu_item.findViewById(com.rs.mobile.wportal.R.id.tv_quantity);
				TextView tv_amount = (TextView) ll_menu_item.findViewById(com.rs.mobile.wportal.R.id.tv_amount);
				tv_itemname.setText(menu.getItemName() == null ? "" : menu.getItemName());
				tv_quantity.setText(menu.getQuantity() + "份");
				tv_amount.setText("¥" + menu.getAmount());
				ll_menucontainer.addView(ll_menu_item,
						new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
								(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 43.5f,
										getResources().getDisplayMetrics())));
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case com.rs.mobile.wportal.R.id.iv_back:
			onBack();
			break;

		case com.rs.mobile.wportal.R.id.iv_right1:// share

			break;

		case com.rs.mobile.wportal.R.id.tv_cancelreserve:// 取消订单 주문취소

			cancelReserve(getString(com.rs.mobile.wportal.R.string.common_text014), getString(com.rs.mobile.wportal.R.string.common_text048),
					getString(com.rs.mobile.wportal.R.string.common_text014), getString(com.rs.mobile.wportal.R.string.common_text049));

			break;

		case com.rs.mobile.wportal.R.id.tv_pay:// 去付款

			if (groupYN != null && groupYN.equals("Y") && groupId != null && !groupId.equals("")) {

				Intent i = new Intent(RtReserveDetailActivity.this, RtETBoardActivity.class);
				i.putExtra("groupID", groupId);
				i.putExtra("restaurantCode", reserveData.getRestaurantCode());
				startActivity(i);

			} else {

				Intent payIntent = new Intent(RtReserveDetailActivity.this, OrderDetailActivity.class);
				payIntent.putExtra("orderNumber", reserveData.getOrderNum());
				startActivity(payIntent);

			}
			break;

		case com.rs.mobile.wportal.R.id.tv_detelereserve:// 删除预约 예약삭제

			cancelReserve(getString(com.rs.mobile.wportal.R.string.common_text050), getString(com.rs.mobile.wportal.R.string.common_text051),
					getString(com.rs.mobile.wportal.R.string.common_text050), getString(com.rs.mobile.wportal.R.string.common_text052));

			break;

		case com.rs.mobile.wportal.R.id.tv_order:// 去点餐 주문하기

			Intent orderIntent = new Intent(RtReserveDetailActivity.this, RtMenuListActivity.class);
			orderIntent.putExtra("reserveID", reserveData.getReserveId());
			orderIntent.putExtra("restaurantCode", reserveData.getRestaurantCode());
			startActivity(orderIntent);

			break;

		case com.rs.mobile.wportal.R.id.tv_refund:// 我要退款 환불신청

			cancelReserve(getString(com.rs.mobile.wportal.R.string.common_text053), getString(com.rs.mobile.wportal.R.string.common_text054),
					getString(com.rs.mobile.wportal.R.string.common_text053), getString(com.rs.mobile.wportal.R.string.common_text055));

			break;

		case com.rs.mobile.wportal.R.id.tv_deleteorder:// 删除订单 주문삭제

			cancelReserve(getString(com.rs.mobile.wportal.R.string.common_text014), getString(com.rs.mobile.wportal.R.string.common_text048),
					getString(com.rs.mobile.wportal.R.string.common_text014), getString(com.rs.mobile.wportal.R.string.common_text049));

			break;

		case com.rs.mobile.wportal.R.id.tv_again:// 再次点餐 다시주문하기

			Intent againIntent = new Intent(RtReserveDetailActivity.this, RtMenuListActivity.class);
			againIntent.putExtra("reserveID", reserveData.getReserveId());
			againIntent.putExtra("restaurantCode", reserveData.getRestaurantCode());
			startActivity(againIntent);

			break;

		case com.rs.mobile.wportal.R.id.tv_tocomment:// 去评价 평가하기

			Bundle bundle = new Bundle();
			bundle.putString("saleCustomCode", reserveData.getRestaurantCode());
			bundle.putString("orderNumber", reserveData.getOrderNum());
			bundle.putString("saleName", reserveData.getRestaurantName());
			PageUtil.jumpTo(RtReserveDetailActivity.this, RtEvaluateActivity.class, bundle);

			break;

		case com.rs.mobile.wportal.R.id.tv_checkcomment:// 查看评价 평가조회

			Intent sellerDetailIntent = new Intent(this, RtSellerDetailActivity.class);
			sellerDetailIntent.putExtra("restaurantCode", reserveData.getRestaurantCode());
			startActivity(sellerDetailIntent);

			break;

		case com.rs.mobile.wportal.R.id.tv_001:// 같이 먹기

			createGroup();

			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		if (compositeSubscription != null && compositeSubscription.hasSubscriptions()
				&& !compositeSubscription.isUnsubscribed()) {
			compositeSubscription.unsubscribe();
		}
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		onBack();
	}

	private void onBack() {
		finish();
	}

	private void cancelReserve(String title, String msg, String okMsg, final String resultMsg) {

		showDialog(title, msg, okMsg, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				RTUtil.cancelReserver(RtReserveDetailActivity.this, reserveID, "01", "", new OnPaymentResultListener() {

					@Override
					public void onResult(JSONObject data) {
						// TODO Auto-generated method stub

						try {

							String status = data.get("status").toString();

							if (status.equals("1")) {

								t(resultMsg);

								finish();

							}

						} catch (Exception e) {

							L.e(e);

						}
					}

					@Override
					public void onFail(String msg) {
						// TODO Auto-generated method stub

					}
				});

				D.alertDialog.dismiss();

			}
		}, getString(com.rs.mobile.wportal.R.string.cancel), new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				D.alertDialog.dismiss();
			}
		});

	}

	/**
	 * 골든벨 코드 가져오기
	 */
	public void getGoldenBellCode() {

		try {

			OkHttpHelper helper = new OkHttpHelper(RtReserveDetailActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(all_data);

						String status = data.getString("status");

						if (status != null && status.equals("1")) {

							goldenBellCodeArr = data.getJSONArray("data");

							golden_bell_status.setText(goldenBellCodeArr.getJSONObject(0).getString("CODE_NAME"));

						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_RT + Constant.RT_GET_GOLDEN_BELL_CODE, params);

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * 골든벨 코드 다이얼로그 생성
	 */
	public void showGoldenBellCodeDialog() {

		try {

			String[] items = new String[goldenBellCodeArr.length()];

			for (int i = 0; i < goldenBellCodeArr.length(); i++) {

				items[i] = goldenBellCodeArr.getJSONObject(i).getString("CODE_NAME");

			}

			showSingleChoiceDialog(-1, -1, -1, com.rs.mobile.wportal.R.string.ok, com.rs.mobile.wportal.R.string.cancel, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					try {

						golden_bell_status.setText(
								goldenBellCodeArr.getJSONObject(golden_bell_statusIndex).getString("CODE_NAME"));

					} catch (Exception e) {

						L.e(e);

					}

				}
			}, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			}, items, golden_bell_statusIndex, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					golden_bell_statusIndex = which;

				}
			}, true);

		} catch (Exception e) {

			e(e);

		}

	}

	/**
	 * 그룹 생성하기
	 */
	private void createGroup() {

		try {

			if (groupYN != null && groupYN.equals("Y") && groupId != null && !groupId.equals("")) {

				Intent i = new Intent(RtReserveDetailActivity.this, RtETBoardActivity.class);
				i.putExtra("groupID", groupId);
				i.putExtra("restaurantCode", reserveData.getRestaurantCode());
				startActivity(i);

				return;

			}

			OkHttpHelper helper = new OkHttpHelper(RtReserveDetailActivity.this);

			HashMap<String, String> params = new HashMap<String, String>();

			params.put("", "");

			helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub

					try {

						L.d(all_data);

						// {"status":1,"msg":"success","data":{"groupID":"G201704201656022303"}}
						String status = data.getString("status");

						if (status != null && status.equals("1")) {

							t(data.getString("msg"));

							finish();

							data = data.getJSONObject("data");

							Intent i = new Intent(RtReserveDetailActivity.this, RtETBoardActivity.class);
							i.putExtra("groupID", data.getString("groupID"));
							i.putExtra("restaurantCode", reserveData.getRestaurantCode());
							startActivity(i);

						}

					} catch (Exception e) {

						L.e(e);

					}

				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_RT + Constant.RT_CREATE_EATING_GROUP + "?userId="
					+ S.getShare(RtReserveDetailActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + "&reserveId=" + reserveID
					+ "&goldenBellYN=" + (eating_together_check_bok.isChecked() == true ? "N" : "Y")
					+ "&groupDescription=" + et_eat_remark.getText().toString() + "&goldenBellCode="
					+ goldenBellCodeArr.getJSONObject(golden_bell_statusIndex).getString("SUB_CODE") + "&token="
					+ S.getShare(RtReserveDetailActivity.this, C.KEY_JSON_TOKEN, ""), params);

		} catch (Exception e) {

			L.e(e);

		}

	}

}
