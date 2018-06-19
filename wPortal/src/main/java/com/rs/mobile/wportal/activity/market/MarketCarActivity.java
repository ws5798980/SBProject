package com.rs.mobile.wportal.activity.market;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.activity.market.util.CommonAdapter;
import com.rs.mobile.wportal.activity.market.util.ListBeen;
import com.rs.mobile.wportal.activity.market.util.ViewHolder;
import com.rs.mobile.wportal.activity.market.view.SwipeMenuLayout;
import com.rs.mobile.common.activity.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MarketCarActivity extends BaseActivity {

	private ListView listView;
	private List<ListBeen> lists;
	private CommonAdapter<ListBeen> adapt ;
	private ImageView mk_topay ;
	private int btn_ifnull = 0 ;
	private TextView mk_bottomtime ;
	private long time = 0 ;
	private int first = 0 ;
	private CountDownTimer timer  ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.rs.mobile.wportal.R.layout.activity_mk_car);
		listView = (ListView) findViewById(com.rs.mobile.wportal.R.id.listView_car);
		mk_bottomtime = (TextView) findViewById(com.rs.mobile.wportal.R.id.mk_bottomtime);
		mk_topay = (ImageView) findViewById(com.rs.mobile.wportal.R.id.mk_topay);
		lists = new ArrayList<ListBeen>();
			View view = new View(MarketCarActivity.this);
			AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams( LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(com.rs.mobile.wportal.R.dimen.view_height), 0);
			view.setLayoutParams(layoutParams);  
			view.setBackgroundColor(Color.parseColor("#f2f4f6"));
			listView.addFooterView(view);
			
			//返回按钮
			findViewById(com.rs.mobile.wportal.R.id.mk_car_back).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					MarketCarActivity.this.finish();
				}
			});
			
			//扫码
			findViewById(com.rs.mobile.wportal.R.id.mk_car_qrcode).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					MarketCarActivity.this.startActivityForResult(new Intent(MarketCarActivity.this, CaptureActivity.class), 2000);
				}
			});
			
			
			//跳转支付
			findViewById(com.rs.mobile.wportal.R.id.mk_topay).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(btn_ifnull == 1){//前往支付
					setOrder();
				}else{//扫描
					MarketCarActivity.this.startActivityForResult(new Intent(MarketCarActivity.this, CaptureActivity.class), 2000);
				}
			}
		});
		
		adapt = new CommonAdapter<ListBeen>(this, lists,
				com.rs.mobile.wportal.R.layout.mk_item) {

			@Override
			public void convert(final ViewHolder holder, final ListBeen listBeen,
                                final int position, View convertView) {
				 holder.setText(com.rs.mobile.wportal.R.id.mk_goods_name,listBeen.getName());
				 holder.setText(com.rs.mobile.wportal.R.id.mk_goods_price,"¥" + listBeen.getPrice());
				 holder.setText(com.rs.mobile.wportal.R.id.mk_goods_num,listBeen.getQuantity() + "");
//				 if(!listBeen.getUnitPrice().equals("")  && !listBeen.getUnitQuantity().equals("") )
				 holder.setText(com.rs.mobile.wportal.R.id.mk_goods_desc,listBeen.getUnitPrice() + " " + listBeen.getUnitQuantity());
				 holder.setText(com.rs.mobile.wportal.R.id.mk_goods_stock,"库存：" + listBeen.getStockQuantity());
				 if(listBeen.getLastStatus() == 1){
					 holder.setVisible(com.rs.mobile.wportal.R.id.mk_goods_enough, true);
//					 holder.setVisible(R.id.mk_bg_enough, true);
				 } else{
					 holder.setVisible(com.rs.mobile.wportal.R.id.mk_goods_enough, false);
//					 holder.setVisible(R.id.mk_bg_enough, false);
				 }
					 
				 WImageView img = holder.getView(com.rs.mobile.wportal.R.id.mk_goods_img);
				 ImageUtil.drawImageFromUri(listBeen.getImgUrl(), img);
				 //减少量
				 holder.getView(com.rs.mobile.wportal.R.id.mk_btn_min).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						if(listBeen.getQuantity() <= 1){
							T.showToast(MarketCarActivity.this, "无法再减少商品数量");
						}else{
							modifyNum(listBeen.getCartOfDetailId(),(listBeen.getQuantity() - 1) + "");
						}
					}
				});
				 //增加量
				 holder.getView(com.rs.mobile.wportal.R.id.mk_btn_add).setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View view) {
							modifyNum(listBeen.getCartOfDetailId(),(listBeen.getQuantity() + 1) + "");
						}
					});
				 
				final SwipeMenuLayout swipeMenuLayout = holder
						.getView(com.rs.mobile.wportal.R.id.swipeMenuLayout);
				// 可以根据自己需求设置一些选项(这里设置了IOS阻塞效果以及item的依次左滑、右滑菜单)
				swipeMenuLayout.setIos(true).setLeftSwipe(true);
				// 监听事件
//				holder.setOnClickListener(R.id.ll_content,
//						new View.OnClickListener() {
//							@Override
//							public void onClick(View v) {
//							}
//						});
				holder.setOnClickListener(com.rs.mobile.wportal.R.id.btn_delete,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								deleteGoods(listBeen.getCartOfDetailId(), position);
								// 在ListView里，点击侧滑菜单上的选项时，如果想让侧滑菜单同时关闭，调用这句话
								swipeMenuLayout.quickClose();
							}
						});
//				// 长按监听
//				holder.setOnLongClickListener(R.id.ll_content,
//						new View.OnLongClickListener() {
//							@Override
//							public boolean onLongClick(View v) {
//								Toast.makeText(MarketCarActivity.this,
//										"正在进行长按操作", Toast.LENGTH_SHORT).show();
//								return true;
//							}
//						});
			}
		};
 		listView.setAdapter(adapt);
		getcarList();
	}
	
	
	
	//删除商品
	private void deleteGoods(String cartOfDetailId,final int position){
	 	OkHttpHelper okHttpHelper = new OkHttpHelper(MarketCarActivity.this);
		HashMap<String, String> params = new HashMap<>(); 
		params.put("tickets", S.getShare(MarketCarActivity.this, "tickets", ""));
		params.put("cartOfDetailId", cartOfDetailId);
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				try {

					if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
						// 删除操作
						lists.remove(position);
						adapt.notifyDataSetChanged();
						if(lists.size() < 1){
							btn_ifnull = 0 ;
							mk_topay.setImageResource(com.rs.mobile.wportal.R.drawable.btn_addgoods);
							findViewById(com.rs.mobile.wportal.R.id.mk_car_nothing).setVisibility(View.VISIBLE);
							findViewById(com.rs.mobile.wportal.R.id.listView_car).setVisibility(View.GONE);
						}
						
						
					}else if(data.getInt(C.KEY_JSON_FM_STATUS) == -9004){
						D.showAlertDialog(MarketCarActivity.this, -1, "提示", data.optString("message"), "重新扫码", new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								startActivity(new Intent(MarketCarActivity.this,MarketMainActivity.class));
							}
						});
					}else{
						T.showToast(MarketCarActivity.this, data.optString("message"));
					}

				} catch (Exception e) {

				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {

			}
		}, Constant.SM_BASE_URL + Constant.MK_DeleteOfflineCarts, params);
	
	
}

	
	//加减商品
	private void modifyNum(String cartOfDetailId, String quantity){
		 	OkHttpHelper okHttpHelper = new OkHttpHelper(MarketCarActivity.this);
			HashMap<String, String> params = new HashMap<>(); 
			params.put("tickets", S.getShare(MarketCarActivity.this, "tickets", ""));
			params.put("cartOfDetailId", cartOfDetailId);
			params.put("quantity", quantity);
			okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

					try {

						if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
							getcarList();
						}else if(data.getInt(C.KEY_JSON_FM_STATUS) == -9004){
							D.showAlertDialog(MarketCarActivity.this, -1, "提示", data.optString("message"), "重新扫码", new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									startActivity(new Intent(MarketCarActivity.this,MarketMainActivity.class));
								}
							});
						}else{
							T.showToast(MarketCarActivity.this, data.optString("message"));
						}

					} catch (Exception e) {

					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {

				}
			}, Constant.SM_BASE_URL + Constant.MK_ModifyOfflineCarts, params);
		
		
	}
	
	
	
	//回调扫描
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

			if (resultCode == Activity.RESULT_OK) {

				 if (requestCode == 2000) { 
					String barCodeStr =  data.getStringExtra("result");
					 
					 OkHttpHelper okHttpHelper = new OkHttpHelper(MarketCarActivity.this);
						HashMap<String, String> params = new HashMap<>(); 
						params.put("tickets", S.getShare(MarketCarActivity.this, "tickets", ""));
						params.put("barCodeStr", barCodeStr);
						okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

							@Override
							public void onNetworkError(Request request, IOException e) {

							}

							@Override
							public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

								try {

									if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
										getcarList();
									}else if(data.getInt(C.KEY_JSON_FM_STATUS) == -9004){
										D.showAlertDialog(MarketCarActivity.this, -1, "提示", data.optString("message"), "重新扫码", new OnClickListener() {
											
											@Override
											public void onClick(View arg0) {
												startActivity(new Intent(MarketCarActivity.this,MarketMainActivity.class));
											}
										});
									}else if(data.getInt(C.KEY_JSON_FM_STATUS) == -36865){
										T.showToast(MarketCarActivity.this, data.optString("message"));
									}

								} catch (Exception e) {

								}
							}

							@Override
							public void onBizFailure(String responseDescription, JSONObject data, String flag) {

							}
						}, Constant.SM_BASE_URL + Constant.MK_AddOfflineCarts, params);
					 
				}

			}

	}
    
    private  String formatDuring(long mss) {
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        
        return  (hours > 9?hours:("0" + hours))+ ":" + (minutes > 9?minutes:("0" + minutes)) + ":"
        + (seconds > 9 ? seconds:("0" + seconds)) ;
    }
	
	
	private long getlongTime(String serviceTime,String backTime){
		 try {
			    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    return (sf.parse(backTime).getTime() - sf.parse(serviceTime).getTime());
			   } catch (ParseException e) {
			   e.printStackTrace();
			  }
			  return 0;
			 }
	
	 private void startCountDownTime(long longtime) {
	        /**
	         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
	         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
	         * 有onTick，onFinsh、cancel和start方法
	         */
	         timer = new CountDownTimer(longtime * 1000, 1000) {
	            @Override
	            public void onTick(long millisUntilFinished) {
	                //每隔countDownInterval秒会回调一次onTick()方法
//	                Log.d(TAG, "onTick  " + millisUntilFinished / 1000);
	                    mk_bottomtime.setText(formatDuring(millisUntilFinished)); 
	                    time =  millisUntilFinished / 1000 ;
//	                    Log.d("TAG", "millisUntilFinished / 1000===" + millisUntilFinished / 1000);
	            }

	            @Override
	            public void onFinish() {
	            	 mk_bottomtime.setText("超时无效"); 
//	                Log.d(TAG, "onFinish -- 倒计时结束");
	            }
	        };
	        timer.start();// 开始计时
	        //timer.cancel(); // 取消
	    }

	 @Override
		public void finish() {
		 if(timer != null)
			timer.cancel();
			super.finish();
		}
	 
	
	//获取购物车列表
	private  void getcarList(){
		lists.clear();
		OkHttpHelper okHttpHelper = new OkHttpHelper(MarketCarActivity.this);
		HashMap<String, String> params = new HashMap<>(); 
		params.put("tickets", S.getShare(MarketCarActivity.this, "tickets", ""));
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {

			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

				try {

					if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
						String invalidDate = data.optString("invalidDate");
						String serverTime = data.optString("serverTime");
						if(first == 0){
						time = getlongTime(serverTime, invalidDate);
						if(time > 0){
						startCountDownTime(time/1000);
						}else{
						mk_bottomtime.setText("超时失效"); 
						}
						first = 1 ;
						}
//						Log.d("TAG", getlongTime(serverTime, invalidDate) + "");
						
						
							JSONArray list = data.getJSONArray("data");
								if(list != null && list.length() > 0){
									for (int i = 0; i < list.length(); i++) {
										lists.add(ListBeen.getCarList(list.optJSONObject(i)));
									}
									btn_ifnull = 1 ;
									mk_topay.setImageResource(com.rs.mobile.wportal.R.drawable.btn_checkout);
									findViewById(com.rs.mobile.wportal.R.id.mk_car_nothing).setVisibility(View.GONE);
									findViewById(com.rs.mobile.wportal.R.id.listView_car).setVisibility(View.VISIBLE);
									adapt.setDatas(lists);
								}else{//购物车为空
									btn_ifnull = 0 ;
									mk_topay.setImageResource(com.rs.mobile.wportal.R.drawable.btn_addgoods);
									findViewById(com.rs.mobile.wportal.R.id.mk_car_nothing).setVisibility(View.VISIBLE);
									findViewById(com.rs.mobile.wportal.R.id.listView_car).setVisibility(View.GONE);
								}
					}else if(data.getInt(C.KEY_JSON_FM_STATUS) == -9004){
						D.showAlertDialog(MarketCarActivity.this, -1, "提示", data.optString("message"), "重新扫码", new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								startActivity(new Intent(MarketCarActivity.this,MarketMainActivity.class));
							}
						});
					}else{
						T.showToast(MarketCarActivity.this, data.optString("message"));
					}

				} catch (Exception e) {

				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String flag) {

			}
		}, Constant.SM_BASE_URL + Constant.MK_CARTSLIST, params);
	}
	
	//下单
	private void setOrder() {
		OkHttpHelper okHttpHelper = new OkHttpHelper(this);
		HashMap<String, String> params = new HashMap<>();
		params.put("tickets", S.getShare(this, "tickets", ""));
		params.put("token", S.getShare(this, C.KEY_JSON_TOKEN, ""));
		okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

			@Override
			public void onNetworkError(Request request, IOException e) {

			}

			@Override
			public void onBizSuccess(String responseDescription,
					JSONObject data, String flag) {

				try {

					if (data.getInt(C.KEY_JSON_FM_STATUS) == 1) {
						final String amount  = data.optString("amount");
						String order_code = data.optString("order_code");
						
						 	Intent intent = new Intent(MarketCarActivity.this,MarketPayActivity.class); 
						    intent.putExtra("amount",amount);
						    intent.putExtra("order_code",order_code);
						    MarketCarActivity.this.startActivity(intent);  
						    MarketCarActivity.this.overridePendingTransition(com.rs.mobile.wportal.R.anim.activity_open,0);
						
					}else if(data.getInt(C.KEY_JSON_FM_STATUS) == -9004){ 
						D.showAlertDialog(MarketCarActivity.this, -1, "提示", data.optString("message"), "重新扫码", new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								startActivity(new Intent(MarketCarActivity.this,MarketMainActivity.class));
							}
						});
					}else if(data.getInt(C.KEY_JSON_FM_STATUS) == -16387){//库存不足
					T.showToast(MarketCarActivity.this, data.optString("message"));	
					JSONObject msPar = 	data.optJSONObject("messageParam");
					JSONArray errorData = msPar.getJSONArray("errorData");
						if(errorData != null && errorData.length() > 0){
							for (int i = 0; i < errorData.length(); i++) {
								String itemCode = errorData.optJSONObject(i).optString("itemCode");
								String stockQuantity = errorData.optJSONObject(i).optString("stockQuantity");
								for (int j = 0; j < lists.size(); j++) {
									if(itemCode.equals(lists.get(j).getCode())){
										lists.get(j).setLastStatus(1);
										lists.get(j).setStockQuantity(stockQuantity);
										break;
									}
								}
							}
							adapt.setDatas(lists);
						}
						
					}else{
						T.showToast(MarketCarActivity.this, data.optString("message"));
					}

				} catch (Exception e) {

				}
			}

			@Override
			public void onBizFailure(String responseDescription,
					JSONObject data, String flag) {

			}
		}, Constant.SM_BASE_URL + Constant.MK_CreateOfflineCartsOrder, params);

	}

}
