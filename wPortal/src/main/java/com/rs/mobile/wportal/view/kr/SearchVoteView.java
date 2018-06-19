package com.rs.mobile.wportal.view.kr;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.kr.MainActivity;
import com.rs.mobile.wportal.activity.kr.VoteDetailActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import okhttp3.Request;

public class SearchVoteView extends RelativeLayout {

	public boolean isInit = false;
	
	private Context context;

	public EditText editText;
	
	private PullToRefreshScrollView scrollView;
	
	private LinearLayout horizontalScrollArea;
	
	public LinearLayout listView;
	
	private String currentPage = "0";
	
	private String nextPage = "1";
	
	private String voteTypeId = "0";
	
	private CatogoryButtonView allTypeBtn;
	
	private CatogoryButtonView beforeTypeBtn;
	
	private CatogoryButtonView liveTypeBtn;
	
	private CatogoryButtonView afterTypeBtn;
	
	//상세//
	public LinearLayout detail_area;
	
	private WImageView image_view;
	
	private TextView vote_title_text_view;
	
	private TextView vote_period_text_view;
	
	private LinearLayout ranking_area;
	
	private TextView detail_text_view;

	public SearchVoteView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public SearchVoteView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}

	public SearchVoteView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		initView(context);
		
	}
	
	public void initView(final Context context) {

		try {
		
			this.context = context;
			
			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			final View v = inflator.inflate(R.layout.activity_kr_search_vote, null);
			
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	
			v.findViewById(R.id.close_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					detail_area.setVisibility(View.GONE);
					
				}
			});
			
			editText = (EditText)v.findViewById(R.id.edit_text);
			editText.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					
					if (actionId == EditorInfo.IME_ACTION_SEARCH) {
						
						listView.removeAllViews();
						
						searchVote();
	
						return true;
					}
					
					return false;
				}
			});
	
			scrollView = (PullToRefreshScrollView)v.findViewById(R.id.scroll_view);
			scrollView.setOnRefreshListener(new OnRefreshListener2() {
	
				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub
	
					listView.removeAllViews();
					
					searchVote();
					
				}
	
				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
					// TODO Auto-generated method stub
					
					if (nextPage != null && !nextPage.equals("") && !nextPage.equals("0")) {

						searchVote();
						
					} else {
						
						scrollView.onRefreshComplete();
						
					}
					
				}
			});
			horizontalScrollArea = (LinearLayout)v.findViewById(R.id.horizontal_scroll_area);

			listView = (LinearLayout)v.findViewById(R.id.list_view);

			allTypeBtn = (CatogoryButtonView)v.findViewById(R.id.type_all_btn);
			
			beforeTypeBtn = (CatogoryButtonView)v.findViewById(R.id.type_before_btn);
			
			liveTypeBtn = (CatogoryButtonView)v.findViewById(R.id.type_live_btn);
			
			afterTypeBtn = (CatogoryButtonView)v.findViewById(R.id.type_after_btn);
			
			detail_area = (LinearLayout)v.findViewById(R.id.detail_area);
			detail_area.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			image_view = (WImageView)v.findViewById(R.id.image_view);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, com.rs.mobile.wportal.activity.MainActivity.displayHeight/4);
			
			image_view.setLayoutParams(params);

			vote_title_text_view = (TextView)v.findViewById(R.id.vote_title_text_view);
			
			vote_period_text_view = (TextView)v.findViewById(R.id.vote_period_text_view);
			
			ranking_area = (LinearLayout)v.findViewById(R.id.ranking_area);
			
			detail_text_view = (TextView)v.findViewById(R.id.detail_text_view);
			
			drawMenuList();

			addView(v);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}


	public void searchVote() {
		
		try {
			
			isInit = true;

			HashMap<String, String> params = new HashMap<String, String>();
			
			params.put(C.KEY_REQUEST_SEARCH_KEY, editText.getText().toString());
			
			params.put(C.KEY_JSON_TYPE, voteTypeId);
			
			params.put(C.KEY_JSON_CURRENT_PAGE, currentPage);
			
			params.put(C.KEY_JSON_NEXT_PAGE, nextPage);
			
			OkHttpHelper helper = new OkHttpHelper(context);
			helper.addGetRequest(new CallbackLogic() {
				
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
	
					scrollView.onRefreshComplete();
					
					isInit = false;
					
				}
				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					
					try {
						
						data = data.getJSONObject(C.KEY_JSON_DATA);

						drawVoteList(data);
						
						currentPage = data.getString(C.KEY_JSON_CURRENT_PAGE);
						
						nextPage = data.getString(C.KEY_JSON_NEXT_PAGE);
				
					} catch (Exception e) {
						
						L.e(e);
						
						isInit = false;
						
					}
					
					scrollView.onRefreshComplete();
					
				}
				
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
					
					scrollView.onRefreshComplete();
					
					isInit = false;
					
				}
			}, Constant.BASE_URL_KR + Constant.KR_VOTE_LIST_SEARCH, params);
		
		} catch (Exception e) {
			
			L.e(e);
		
			scrollView.onRefreshComplete();
			
			isInit = false;
			
		}
		
	}
	
	public void drawVoteList(JSONObject data) {
		
		try {
		
			if (data.has(C.KEY_JSON_VOTE)) {
				
				JSONArray arr = data.getJSONArray(C.KEY_JSON_VOTE);
				
				if (arr != null) {
				
					if (currentPage.equals("0")) {
					
						listView.removeAllViews();
					
					}
					
					for (int i = 0; i < arr.length(); i++) {
						
						final VoteView view = new VoteView(context);
						
						RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, com.rs.mobile.wportal.activity.MainActivity.newsHeight);
	
						view.setLayoutParams(layoutParams);
						
						view.invalidate();
						
						final JSONObject json = new JSONObject(arr.get(i).toString());
	
//						view.setUniqueId(json.getString(C.KEY_JSON_UNIQUE_ID));
						
						view.setUniqueId(json.getString("aid"));
						
						view.setTitle(json.getString(C.KEY_JSON_TITLE));
						
						view.setStatus(json.getString(C.KEY_JSON_STATUS));
	
						view.setDate(json.getString(C.KEY_JSON_END_DATE));
						
						ImageUtil.drawImageViewBuFullUrl(context, view.getImageView(), json, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);

						view.invalidate();
						
						view.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								try {
								
									drawVoteDetail(json.getString("aid"));
									
								} catch (Exception e) {
									
									L.e(e);
									
								}
								
							}
						});
						
						if (i > 0) {
						
							RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) view.getLayoutParams();
							p.topMargin = 10;
							view.setLayoutParams(p);
						
						}
						
						listView.addView(view);
						
					}
					
					listView.invalidate();
				
				}
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void drawMenuList() {
		
		try {

			allTypeBtn.setTitle(context.getResources().getString(R.string.kr_type_vote_all));
			allTypeBtn.setUniqueId("" + 0);

			beforeTypeBtn.setTitle(context.getResources().getString(R.string.kr_type_vote_before));
			beforeTypeBtn.setUniqueId("" + 1);

			liveTypeBtn.setTitle(context.getResources().getString(R.string.kr_type_vote_live));
			liveTypeBtn.setUniqueId("" + 2);

			afterTypeBtn.setTitle(context.getResources().getString(R.string.kr_type_vote_after));
			afterTypeBtn.setUniqueId("" + 3);

			allTypeBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					voteTypeId = "0";
					
					allTypeBtn.selectButton();
					beforeTypeBtn.unSelectButton();
					liveTypeBtn.unSelectButton();
					afterTypeBtn.unSelectButton();

					currentPage = "0";
					
					nextPage = "1";

					searchVote();
					
				}
			});
			
			beforeTypeBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					voteTypeId = "1";
					
					allTypeBtn.unSelectButton();
					beforeTypeBtn.selectButton();
					liveTypeBtn.unSelectButton();
					afterTypeBtn.unSelectButton();

					currentPage = "0";
					
					nextPage = "1";

					searchVote();
					
				}
			});
			
			liveTypeBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
			
					voteTypeId = "2";
					
					allTypeBtn.unSelectButton();
					beforeTypeBtn.unSelectButton();
					liveTypeBtn.selectButton();
					afterTypeBtn.unSelectButton();
			
					currentPage = "0";
					
					nextPage = "1";
			
					searchVote();
					
				}
			});
			
			afterTypeBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
			
					voteTypeId = "3";
					
					allTypeBtn.unSelectButton();
					beforeTypeBtn.unSelectButton();
					liveTypeBtn.unSelectButton();
					afterTypeBtn.selectButton();
			
					currentPage = "0";
					
					nextPage = "1";
			
					searchVote();
					
				}
			});
			
			allTypeBtn.selectButton();

		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public String callApi(String id) {
		
		String returnValue = "data:";

		return returnValue;
		
	}
	
	public void drawVoteDetail(final String aid) {
		
		try {
					
			((BaseActivity)context).showProgressBar();
			
			isInit = true;

			HashMap<String, String> params = new HashMap<String, String>();
			
			params.put("aid", aid);
			
			params.put("userId", S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));
			
			OkHttpHelper helper = new OkHttpHelper(context);
			helper.addGetRequest(new CallbackLogic() {
				
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

					((BaseActivity)context).hideProgressBar();
					
				}
				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					
					try {
						
						String status = data.getString("status");
						
						if (status != null && status.equals("1")) {
						
							detail_area.setVisibility(View.VISIBLE);
							
							data = data.getJSONObject(C.KEY_JSON_DATA);
							
							final JSONArray votedIds = data.getJSONArray("votedId");
							
							JSONObject detail = data.getJSONObject("detail");
							
							ImageUtil.drawImageView(context, image_view, detail.getString("imgUrl"));
							
							vote_title_text_view.setText(detail.getString("title"));
							
							vote_period_text_view.setText(context.getResources().getString(R.string.kr_vote_detail_period) + " : " + detail.getString("startDate") + " ~ " + detail.getString("endDate"));
							
							detail_text_view.setText(detail.getString("description"));
							
							ranking_area.removeAllViews();
							
							JSONArray arr = detail.getJSONArray("items");
							
							for (int i = 0; i <arr.length(); i++) {
							
								final JSONObject item = arr.getJSONObject(i);
								
								View itemView = ((MainActivity)context).inflator.inflate(R.layout.list_item_vote_ranking, null);
							
								TextView rank_text_view = (TextView)itemView.findViewById(R.id.rank_text_view);
								
								TextView name_text_view = (TextView)itemView.findViewById(R.id.name_text_view);
								
								TextView count_text_view = (TextView)itemView.findViewById(R.id.count_text_view);
								
								final CheckBox check_box = (CheckBox)itemView.findViewById(R.id.check_box);
								
								if (i == 0) {
									
									rank_text_view.setBackgroundResource(R.drawable.img_gold);
									
								} else if (i == 1) {
									
									rank_text_view.setBackgroundResource(R.drawable.img_silver);
									
								} else if (i == 2) {
									
									rank_text_view.setBackgroundResource(R.drawable.img_copper);
									
								} else {
									
									rank_text_view.setText("" + (i + 1));
									
								}
								
								name_text_view.setText(item.getString("itemTitle"));
								
								count_text_view.setText(item.getString("count"));
								
								String vId = item.getString("count");
								
								if (votedIds.equals(vId)) {
									
									check_box.setChecked(true);
									
								} else {
									
									check_box.setChecked(false);
									
								}
								
								check_box.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										
										if (check_box.isChecked() == false) {
											
											check_box.setChecked(true);
											
											T.showToast(context, context.getResources().getString(R.string.kr_voted_msg));
											
										} else {

											((MainActivity) context).showDialog(
													context.getResources().getString(R.string.kr_vote),
													context.getResources().getString(R.string.kr_vote_msg),
													context.getResources().getString(R.string.kr_vote),
													new OnClickListener() {

														@Override
														public void onClick(View v) {
															// TODO
															// Auto-generated
															// method stub

															D.alertDialog.dismiss();
															
															try {

																doVote(aid, item.getString("itemNo"), check_box);
															
															} catch (Exception e) {
																
																L.e(e);
																
															}

														}
													}, context.getResources().getString(R.string.cancel),
													new OnClickListener() {

														@Override
														public void onClick(View v) {
															// TODO
															// Auto-generated
															// method stub
															D.alertDialog.dismiss();
															
															check_box.setChecked(false);
														}
													});

										}
										
									}
								});
								
								if (i%2 == 1) {
									
									itemView.setBackgroundColor(Color.parseColor("#ffffff"));
									
								}
								
								itemView.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										
										try {
										
											Intent i = new Intent(context, VoteDetailActivity.class);
											i.putExtra("aid", aid);
											i.putExtra("id", item.getString("itemNo"));
											context.startActivity(i);
										
										} catch (Exception e) {
											
											L.e(e);
											
										}
										
									}
								});
								
								ranking_area.addView(itemView);
								
							}
							
							ranking_area.invalidate();
							
						} else {
							
							T.showToast(context, data.getString("msg"));
							
						}

					} catch (Exception e) {
						
						L.e(e);
						
						isInit = false;
						
					}
					
					((BaseActivity)context).hideProgressBar();
					
					scrollView.onRefreshComplete();
					
				}
				
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
					
					((BaseActivity)context).hideProgressBar();
					
					scrollView.onRefreshComplete();
					
					isInit = false;
					
				}
			}, Constant.BASE_URL_KR + Constant.KR_VOTE_LIST_SEARCH, params);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void doVote(String projectId, String itemId, final CheckBox check_box) {
		
		try {

			OkHttpHelper helper = new OkHttpHelper(context);
			
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("projectId", projectId);
			
			params.put("itemId", itemId);
			
			params.put("userId", S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));
			
			helper.addPostRequest(new CallbackLogic() {
				
				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
					
					scrollView.onRefreshComplete();
					
					check_box.setChecked(false);
					
				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, final String all_data) {
					// TODO Auto-generated method stub
					
					try {

						String status = data.getString("status");
						
						if (status != null && status.equals("1")) {
							
							T.showToast(context, R.string.kr_vote_complete);
							
							check_box.setChecked(true);
							
						} else {
							
							T.showToast(context, data.getString("msg"));
							
							check_box.setChecked(false);
							
						}
					
					} catch (Exception e) {
						
						L.e(e);
						
					}

				}
				
				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
					
					scrollView.onRefreshComplete();
					
					check_box.setChecked(false);
					
				}
			}, Constant.BASE_URL_KR + Constant.KR_VOTE_DETAIL, params);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

}