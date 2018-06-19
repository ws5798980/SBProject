package com.rs.mobile.wportal.activity.kr;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class SettingActivity extends BaseActivity {

	private LinearLayout closeBtn;
	
	private LinearLayout play_setting_btn;
	
	private LinearLayout download_setting_btn;
	
	private CheckBox play_check_box;
	
	private CheckBox download_check_box;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			
			setContentView(R.layout.activity_kr_setting);

			closeBtn = (LinearLayout)findViewById(R.id.close_btn);
			closeBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					try {
					
						finish();
					
					} catch (Exception e) {
						
						L.e(e);
						
					}
					
				}
			});
			
			play_setting_btn = (LinearLayout)findViewById(R.id.play_setting_btn);
			play_setting_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					play_check_box.setChecked(!play_check_box.isChecked());
					
				}
			});
			
			download_setting_btn = (LinearLayout)findViewById(R.id.download_setting_btn);
			download_setting_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					download_check_box.setChecked(!download_check_box.isChecked());
					
				}
			});
			
			play_check_box = (CheckBox)findViewById(R.id.play_check_box);
			play_check_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					
					S.set(SettingActivity.this, C.KEY_PREFERENCE_PLAY_WIFI_ONLY, isChecked == true?"1":"0");
					
				}
			});
			
			download_check_box = (CheckBox)findViewById(R.id.download_check_box);
			download_check_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					
					S.set(SettingActivity.this, C.KEY_PREFERENCE_DOWNLOAD_WIFI_ONLY, isChecked == true?"1":"0");
					
				}
			});
			
			draw();

		} catch (Exception e) {
			
			e(e);
			
		}

	}
	
	/**
	 * 리스트 그리기
	 */
	public void draw() {
		
		try {
			
			String play = S.get(SettingActivity.this, C.KEY_PREFERENCE_PLAY_WIFI_ONLY);
			
			String download = S.get(SettingActivity.this, C.KEY_PREFERENCE_DOWNLOAD_WIFI_ONLY);

			play_check_box.setChecked(play != null && play.equals("1")?true:false);

			download_check_box.setChecked(download != null && download.equals("1")?true:false);
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

}