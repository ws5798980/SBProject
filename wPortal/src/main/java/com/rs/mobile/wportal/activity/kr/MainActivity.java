package com.rs.mobile.wportal.activity.kr;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.view.kr.MainView;
import com.rs.mobile.wportal.view.kr.MovieView;
import com.rs.mobile.wportal.view.kr.PlayView;
import com.rs.mobile.wportal.view.kr.SearchVodView;
import com.rs.mobile.wportal.view.kr.SearchVoteView;
import com.rs.mobile.wportal.view.kr.SettingView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewFlipper;

public class MainActivity extends BaseActivity {

	private ViewFlipper viewFlipper;
	
	public RadioGroup tabRadioGroup;
	
	private RadioButton rButton1, rButton2, rButton3, rButton4, rButton5;
	
	public MainView mainView;
	
	public SearchVodView searchVodView;
	
	public SearchVoteView searchVoteView;
	
	public SettingView settingView;
	
	public PlayView playView;
	
	public MovieView movieView;

	public static int selectColor = Color.parseColor("#fca73d");
	
	public static int unSelectColor = Color.parseColor("#ffffff");
	
	public static int grayColor = Color.parseColor("#cccccc");
	
	private int lateSelectedId = com.rs.mobile.wportal.R.id.common_toolbar_1_home;
	
	private LinearLayout ticketView;
	
	private LinearLayout playBtn, movieBtn;
	
	private LinearLayout ticketCloseBtn;
	
	public LayoutInflater inflator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(com.rs.mobile.wportal.R.layout.activity_crazymedia);
		
		try {
			
			inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			mainView = new MainView(MainActivity.this);
			
			searchVodView = new SearchVodView(MainActivity.this);
			
			searchVoteView = new SearchVoteView(MainActivity.this);
			
			settingView = new SettingView(MainActivity.this);
			
			playView = new PlayView(MainActivity.this);
			
			movieView = new MovieView(MainActivity.this);
			
			viewFlipper = (ViewFlipper)findViewById(com.rs.mobile.wportal.R.id.content_area);
			
			viewFlipper.addView(mainView);
			
			viewFlipper.addView(searchVodView);
			
			viewFlipper.addView(searchVoteView);
			
			viewFlipper.addView(settingView);

			viewFlipper.addView(playView);
			
			viewFlipper.addView(movieView);

			tabRadioGroup = (RadioGroup) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_container);
			
			rButton1 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_1_home);
			rButton1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					movePage(com.rs.mobile.wportal.R.id.common_toolbar_1_home);
				}
			});
			rButton2 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_1_discovery);
			rButton2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					movePage(com.rs.mobile.wportal.R.id.common_toolbar_1_discovery);
				}
			});
			rButton3 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_1_selfticket);
			rButton3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					movePage(com.rs.mobile.wportal.R.id.common_toolbar_1_selfticket);
				}
			});
			rButton4 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_1_vote);
			rButton4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					movePage(com.rs.mobile.wportal.R.id.common_toolbar_1_vote);
				}
			});
			rButton5 = (RadioButton) findViewById(com.rs.mobile.wportal.R.id.common_toolbar_1_mine);
			rButton5.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					movePage(com.rs.mobile.wportal.R.id.common_toolbar_1_mine);
				}
			});
//			tabRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//				
//				@Override
//				public void onCheckedChanged(RadioGroup group, int checkedId) {
//					
//					movePage(checkedId);
//
//				}
//			});
			
			ticketView = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.ticket_view);
			ticketView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					hideTicketView();
				}
			});
			
			playBtn = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.play_btn);
			playBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					hideTicketView(com.rs.mobile.wportal.R.id.common_toolbar_1_selfticket);
					
					changeTextColor(3);
					viewFlipper.setDisplayedChild(4);
					
					if (playView.isInit == false)
						playView.draw();
				}
			});
			
			movieBtn = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.movie_btn);
			movieBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					hideTicketView(com.rs.mobile.wportal.R.id.common_toolbar_1_selfticket);
					
					changeTextColor(3);
					viewFlipper.setDisplayedChild(5);
					
					if (movieView.isInit == false)
						movieView.draw();
				}
			});

			ticketCloseBtn = (LinearLayout)findViewById(com.rs.mobile.wportal.R.id.ticket_close_btn);
			ticketCloseBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					hideTicketView();
				}
			});
			
			if (mainView.isInit == false)
				mainView.drawview();

		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		if (lateSelectedId == com.rs.mobile.wportal.R.id.common_toolbar_1_vote) {
			
			if (searchVoteView.detail_area.getVisibility() == View.VISIBLE) {
				
				searchVoteView.detail_area.setVisibility(View.GONE);
				
				return;
						
			}
			
		}
		
		super.onBackPressed();
	}
	
	public void movePage(int id) {
		
		try {
			
			if (id != com.rs.mobile.wportal.R.id.common_toolbar_1_selfticket) {
				
				lateSelectedId = id;
			
			}
		
			switch (id) {
			case com.rs.mobile.wportal.R.id.common_toolbar_1_home:
				viewFlipper.setDisplayedChild(0);
				changeTextColor(1);
				if (mainView.isInit == false)
					mainView.drawview();
				break;
			case com.rs.mobile.wportal.R.id.common_toolbar_1_discovery:
				viewFlipper.setDisplayedChild(1);
				changeTextColor(2);
				if (searchVodView.isInit == false)
					searchVodView.getVideoList("");
				break;
			case com.rs.mobile.wportal.R.id.common_toolbar_1_selfticket:
				changeTextColor(3);
				showTicketView();
				break;
			case com.rs.mobile.wportal.R.id.common_toolbar_1_vote:
				viewFlipper.setDisplayedChild(2);
				changeTextColor(4);
				if (searchVoteView.isInit == false)
					searchVoteView.searchVote();
				break;
			case com.rs.mobile.wportal.R.id.common_toolbar_1_mine:
				viewFlipper.setDisplayedChild(3);
				changeTextColor(5);
				
				if (settingView.isInit == false) {
				
//					String token = S.get(MainActivity.this, C.KEY_JSON_TOKEN);
					
					String token = S.getShare(MainActivity.this, C.KEY_JSON_TOKEN, "");
					
					if (token != null || !token.equals("")) {
	
						settingView.searchPurchar();
						
					}
				
				}
				
				break;
			default:
				break;
			}
			
		} catch (Exception e) {
			
			e(e);
			
		}
		
	}
	
	public void goVodSearch(String text) {
		
		try {
			
			tabRadioGroup.check(com.rs.mobile.wportal.R.id.common_toolbar_1_discovery);
			movePage(com.rs.mobile.wportal.R.id.common_toolbar_1_discovery);
			searchVodView.listView.removeAllViews();
			searchVodView.searchVod(text);
			
		} catch (Exception e) {
			
			e(e);
			
		}
		
	}
	
	public void showVoteDetail(String id) {
		
		try {
			
			showProgressBar();
			
			tabRadioGroup.check(com.rs.mobile.wportal.R.id.common_toolbar_1_vote);
			
			movePage(com.rs.mobile.wportal.R.id.common_toolbar_1_vote);
			
			searchVoteView.drawVoteDetail(id);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	private void changeTextColor(int index) {
		rButton1.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.inputblack));
		rButton2.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.inputblack));
		rButton3.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.inputblack));
		rButton4.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.inputblack));
		rButton5.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.inputblack));

		switch (index) {
		case 1:
			rButton1.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
			break;
		case 2:
			rButton2.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
			break;
		case 3:
			rButton3.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
			break;
		case 4:
			rButton4.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
			break;
		case 5:
			rButton5.setTextColor(getResources().getColor(com.rs.mobile.wportal.R.color.mainblue));
		default:
			break;
		}
	}
	

	public void showTicketView() {
		
		try {

			Animation scaleAnimation = AnimationUtils.loadAnimation(MainActivity.this, com.rs.mobile.wportal.R.anim.scale_top_in);
			
			ticketView.setVisibility(View.VISIBLE);
			
			ticketView.startAnimation(scaleAnimation);

		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void hideTicketView(int id) {
		
		try {

			Animation scaleAnimation = AnimationUtils.loadAnimation(MainActivity.this, com.rs.mobile.wportal.R.anim.scale_bottom_out);

			ticketView.setVisibility(View.GONE);

			ticketView.startAnimation(scaleAnimation);
			
			tabRadioGroup.check(id);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	public void hideTicketView() {
		
		try {

			Animation scaleAnimation = AnimationUtils.loadAnimation(MainActivity.this, com.rs.mobile.wportal.R.anim.scale_bottom_out);

			ticketView.setVisibility(View.GONE);

			ticketView.startAnimation(scaleAnimation);
			
			tabRadioGroup.check(lateSelectedId);
			movePage(lateSelectedId);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			
			if (lateSelectedId == com.rs.mobile.wportal.R.id.common_toolbar_1_mine) {
				
				settingView.searchPurchar();
				
			}
			
		}
		
	}

}