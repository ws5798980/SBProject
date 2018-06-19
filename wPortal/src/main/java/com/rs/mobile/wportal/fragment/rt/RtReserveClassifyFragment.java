package com.rs.mobile.wportal.fragment.rt;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.wportal.activity.rt.OrderDetailActivity;
import com.rs.mobile.wportal.activity.rt.RtReserveActivity;
import com.rs.mobile.wportal.biz.rt.ReserveListModel;
import com.rs.mobile.wportal.network.rt.NetworkAdapter;
import com.rs.mobile.wportal.activity.rt.RtETBoardActivity;
import com.rs.mobile.wportal.activity.rt.RtReserveDetailActivity;
import com.rs.mobile.wportal.activity.rt.RtSellerDetailActivity;
import com.rs.mobile.wportal.adapter.rt.RtReserveClassifyAdapter;
import com.rs.mobile.wportal.biz.rt.Reserve;
import com.rs.mobile.wportal.fragment.BaseFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RtReserveClassifyFragment extends BaseFragment implements
		OnItemClickListener, OnRefreshListener2<ListView> {

	private PullToRefreshListView lv_content;
	private RtReserveClassifyAdapter mAdapter;
	private List<Reserve> mReserveList = new ArrayList<>();
	private boolean flag = false;// 判断该Fragment是否第一次加载

	private String mReserveStatus;
	private int currentPage = 1;
	private int nextPage = 0;
	private View parent;

	// private static final String KEY_RESERVE_STATUS = "reserveStatus";
	// private static final String KEY_PAGE_NO = "pageNo";
	// private int pageNo = 1;
	// private static final String KEY_RESERVE_LIST = "reserveList";

	// network
	private Subscription subscription;

	public static RtReserveClassifyFragment newInstance(String reserveStatus) {
		RtReserveClassifyFragment mInstance = new RtReserveClassifyFragment();
		if (reserveStatus == null) {
			reserveStatus = RtReserveActivity.mReserveTags[0];
		}
		mInstance.mReserveStatus = reserveStatus;
		// Bundle argument = new Bundle();
		// argument.putString(KEY_RESERVE_STATUS, reserveStatus);
		// int pageNo = mInstance.pageNo;
		// argument.putInt(KEY_PAGE_NO, pageNo);
		// ArrayList<Reserve> reserveList = (ArrayList<Reserve>)
		// mInstance.mReserveList;
		// argument.putSerializable(KEY_RESERVE_LIST, reserveList);
		// mInstance.setArguments(argument);
		return mInstance;
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// EventBus.getDefault().registerSticky(this);
		parent = inflater.inflate(com.rs.mobile.wportal.R.layout.fragment_rt_reserve, container,
				false);
		initViews(parent);
		flag = true;
		mReserveStatus = getArguments().getString("mReserveStatus");
		initDatas();
		initListener();
		resetCurrentPage();
		resetNetwork();
		lazyLoad();
		return parent;
	}

	@Override
	public void onDestroyView() {
		// EventBus.getDefault().unregister(this);
		resetNetwork();
		super.onDestroyView();
	}

	private void initViews(View parent) {
		lv_content = (PullToRefreshListView) parent
				.findViewById(com.rs.mobile.wportal.R.id.lv_content);
	}

	private void initDatas() {
		mAdapter = new RtReserveClassifyAdapter(getActivity(), mReserveList,
				com.rs.mobile.wportal.R.layout.layout_rt_reserve_list_item);
		lv_content.setAdapter(mAdapter);
		// if(NetworkUtil.getNetworkState() == NetworkUtil.NETWORN_NONE){
		// //如果第一次进入刷新，然而网络不通，也把isJustCreated赋值为false，表示不用进行多余的requestInit
		// if(isJustCreated){
		// isJustCreated = !isJustCreated;
		// }
		// if (mEmptyViewUtil.getShowType() !=
		// EmptyViewUtil.SHOW_RETRYNETWORK_FRAME) {
		// mEmptyViewUtil
		// .showSingleFrame(EmptyViewUtil.SHOW_RETRYNETWORK_FRAME);
		// }
		// return;
		// }
		//
		// if(!isJustCreated && mAdapter.getCount() <= 0){//初始化过后的第二、第三、第N次进入
		// if (mEmptyViewUtil.getShowType() !=
		// EmptyViewUtil.SHOW_EMPTYDATA_FRAME) {
		// mEmptyViewUtil
		// .showSingleFrame(EmptyViewUtil.SHOW_EMPTYDATA_FRAME);
		// }
		// }
	}

	private void initListener() {
		lv_content.setOnRefreshListener(this);
		lv_content.setOnItemClickListener(this);
		// mAdapter.setOrderInterface(this);
		// mEmptyViewUtil.setOnEmptyViewBehaviorListener(this);
	}

	@Override
	protected void lazyLoad() {
		if (isVisible && flag) {// 处于显示状态并且尚未加载成功数据
			Log.d("TAG", "555555");
			flag = false;
			init();
		}
	}

	private void init() {
		// 只有第一次创建时的init()，才需要马上requestInit
		// if(isJustCreated){
		// if (!requestOrderList(TAG_DATA_INIT, GlobalParams.ukey, mOrderStatus,
		// pageNo, pageSize)) {
		// if (mEmptyViewUtil != null
		// && mEmptyViewUtil.isInited()
		// && mEmptyViewUtil.getShowType() !=
		// EmptyViewUtil.SHOW_RETRYNETWORK_FRAME) {
		// mEmptyViewUtil.showSingleFrame(EmptyViewUtil.SHOW_RETRYNETWORK_FRAME);
		// }
		// ToastUtil.displayShortToast("请检查您的网络连接");
		// }
		// }
		requestReserveList(true);
	}

	private void resetNetwork() {
		if (subscription != null) {
			subscription.unsubscribe();
		}
		lv_content.onRefreshComplete();
	}

	public void resetCurrentPage() {
		currentPage = 1;
	}

	private void currentPagePlus() {
		currentPage++;
	}

	
	
	
	
	
	public void requestReserveList(final boolean isRefresh) {
		if (subscription != null) {
			subscription.unsubscribe();
			subscription = null;
		}
		subscription = NetworkAdapter
				.httpAPI()
				.ArrReserveList(
						S.getShare(getContext(), C.KEY_REQUEST_MEMBER_ID, ""),
						mReserveStatus, currentPage)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<ReserveListModel>() {

					@Override
					public void call(ReserveListModel reserveModel) {
						if (reserveModel.getStatus() == 1) {
							hideNoData(parent);
							// if
							// ("success".equalsIgnoreCase(reserveModel.getMsg()))
							// {
							List<Reserve> datas = reserveModel.getData()
									.getList();

							nextPage = reserveModel.getData().getNextPage();

							reserveModel.getData().setNextPage(nextPage);

							System.out.println(datas.toString());

							if (isRefresh) {
								mReserveList.clear();
							}

							if (reserveModel.getData().getCanLoadMore()) {
								// lv_content.setMode(Mode.BOTH);
								currentPagePlus();
							} else {
								// lv_content.setMode(Mode.PULL_FROM_START);
							}

							mReserveList.addAll(datas);

							// if(mReserveList == null || mReserveList.size() <=
							// 0){
							
							// }else{
							// hideNoData(parent);
							// }

							// if (isRefresh) {
							//
							// initDatas();
							//
							// } else {

							mAdapter.notifyDataSetChanged();

							// }

						}else{
							showNoData(parent);
						}

						new Handler().post(new Runnable() {

							@Override
							public void run() {
								lv_content.onRefreshComplete();
							}
						});

					}

				}, new Action1<Throwable>() {

					@Override
					public void call(Throwable arg0) {
						arg0.printStackTrace();
						lv_content.onRefreshComplete();
					}
				}, new Action0() {

					@Override
					public void call() {
						if (lv_content.isRefreshing()) {
							lv_content.onRefreshComplete();
						}
					}
				});
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

		resetCurrentPage();

		requestReserveList(true);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

		if (nextPage == 0) {

			new Handler().post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					lv_content.onRefreshComplete();
				}
			});

			return;
		}

		requestReserveList(false);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (lv_content.getMode().showHeaderLoadingLayout() && position > 0) {
			position--;
		}

		// String groupYN = mReserveList.get(position).getGroupYN();
		//
		// String groupId = mReserveList.get(position).getGroupId();
		//
		// String from = "";
		//
		// if (groupYN != null && groupYN.equals("Y") && groupId != null &&
		// !groupId.equals("")) {
		//
		// from = "0";
		//
		// }

		switch (mAdapter.getItemViewType(position)) {
		case RtReserveClassifyAdapter.TYPE_STAY_PAY:

			Reserve reserve = mReserveList.get(position);

			if (reserve.getGroupYN() != null
					&& reserve.getGroupYN().equals("Y")
					&& reserve.getGroupId() != null
					&& !reserve.getGroupId().equals("")
					&& !reserve.getReserveStatus().equals("C")
					&& !reserve.getReserveStatus().equals("CF")) {

				Intent i = new Intent(getContext(), RtETBoardActivity.class);
				i.putExtra("groupID", reserve.getGroupId());
				i.putExtra("restaurantCode", reserve.getRestaurantCode());
				startActivity(i);

			} else {

				Intent i = new Intent(getContext(), OrderDetailActivity.class);
				i.putExtra("orderNumber", mReserveList.get(position)
						.getOrderNum() == null ? "" : mReserveList
						.get(position).getOrderNum());
				// i.putExtra("from", from);

				i.putExtra("title", getString(com.rs.mobile.wportal.R.string.order_detail));

				i.putExtra("payText", getString(com.rs.mobile.wportal.R.string.order_detail_pay));

				startActivity(i);

			}
			break;
		case RtReserveClassifyAdapter.TYPE_STAY_NO_COMMENT:// 평가중
		case RtReserveClassifyAdapter.TYPE_STAY_COMMENTED:// 평가완료
			Intent sellerIntent = new Intent(getContext(),
					RtSellerDetailActivity.class);
			sellerIntent.putExtra("restaurantCode",
					mReserveList.get(position).getRestaurantCode() == null ? ""
							: mReserveList.get(position).getRestaurantCode());
			// sellerIntent.putExtra("from", from);
			startActivity(sellerIntent);
			break;
		case RtReserveClassifyAdapter.TYPE_STAY_CHOOSEDMENU:
		case RtReserveClassifyAdapter.TYPE_STAY_RESERVED:
			if (getActivity() != null) {
				Intent reserveDetailIntent = new Intent(getActivity(),
						RtReserveDetailActivity.class);
				reserveDetailIntent.putExtra(C.EXTRA_KEY_MEMBERID,
						S.getShare(getActivity(), C.KEY_REQUEST_MEMBER_ID, ""));// TODO
																				// 这里要在流程通畅后填入实时获取的memberId
				reserveDetailIntent.putExtra(C.EXTRA_KEY_RESERVEID,
						mReserveList.get(position).getReserveId() == null ? ""
								: mReserveList.get(position).getReserveId());
				reserveDetailIntent.putExtra(C.EXTRA_KEY_ORDERNUM, mReserveList
						.get(position).getOrderNum() == null ? ""
						: mReserveList.get(position).getOrderNum());
				reserveDetailIntent
						.putExtra(
								C.EXTRA_KEY_RESERVESTATUS,
								mReserveList.get(position).getReserveStatus() == null ? ""
										: mReserveList.get(position)
												.getReserveStatus());
				// reserveDetailIntent.putExtra("from", from);
				getActivity().startActivity(reserveDetailIntent);
			}
			break;

		case RtReserveClassifyAdapter.TYPE_STAY_REFUNDING: // 환불중
		case RtReserveClassifyAdapter.TYPE_STAY_REFUNDCOMPLETED: // 환불완료

			break;

		default:
			break;
		}
	}

}