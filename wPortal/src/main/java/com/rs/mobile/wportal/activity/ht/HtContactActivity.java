package com.rs.mobile.wportal.activity.ht;

import java.util.ArrayList;

import com.rs.mobile.common.activity.BaseActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HtContactActivity extends BaseActivity{
	   Context mContext = null;  
	  
	    /**获取库Phon表字段**/  
	    private static final String[] PHONES_PROJECTION = new String[] {  
	            Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };  
	     
	    /**联系人显示名称**/  
	    private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
	      
	    /**电话号码**/  
	    private static final int PHONES_NUMBER_INDEX = 1;  
	      
	    /**头像ID**/  
	    private static final int PHONES_PHOTO_ID_INDEX = 2;  
	     
	    /**联系人的ID**/  
	    private static final int PHONES_CONTACT_ID_INDEX = 3;  
	      
	  
	    /**联系人名称**/  
	    private ArrayList<String> mContactsName = new ArrayList<String>();  
	      
	    /**联系人头像**/  
	    private ArrayList<String> mContactsNumber = new ArrayList<String>();  
	  
//	    /**联系人头像**/  
//	    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();  
	      
	    ListView mListView = null;  
	    MyListAdapter myAdapter = null;  
	    
	    private int location;
	  
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        mContext = this; 
	        setContentView(com.rs.mobile.wportal.R.layout.activity_ht_contact);
	        location=getIntent().getIntExtra("position", 0);
	        mListView = (ListView)findViewById(com.rs.mobile.wportal.R.id.lv);
	        /**得到手机通讯录联系人信息**/  
	        getPhoneContacts();  
	        getSIMContacts();
	        myAdapter = new MyListAdapter(this);  
	        mListView.setAdapter(myAdapter);
	  
	  
	        mListView.setOnItemClickListener(new OnItemClickListener() {  
	  
	           
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					Intent i=new Intent();
					i.putExtra("phone", mContactsNumber.get(position));
					i.putExtra("position", location);
					setResult(RESULT_OK, i);
					finish();
				}  
	        });  
	  
	        super.onCreate(savedInstanceState);  
	    }  
	  
	    /**得到手机通讯录联系人信息**/  
	    private void getPhoneContacts() {  
	        ContentResolver resolver = mContext.getContentResolver();  
	  
	        // 获取手机联系人  
	        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  
	  
	  
	        if (phoneCursor != null) {  
	            while (phoneCursor.moveToNext()) {  
	  
	                //得到手机号码  
	                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
	                //当手机号码为空的或者为空字段 跳过当前循环  
	                if (TextUtils.isEmpty(phoneNumber))  
	                    continue;  
	                  
	                //得到联系人名称  
	                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  
	                  
	                 
	                  
	              
	                  
	                mContactsName.add(contactName);  
	                mContactsNumber.add(phoneNumber);  
	                 
	            }  
	  
	            phoneCursor.close();  
	        }  
	    }  
	      
	    /**得到手机SIM卡联系人人信息**/  
	    private void getSIMContacts() {  
	        ContentResolver resolver = mContext.getContentResolver();  
	        // 获取Sims卡联系人  
	        Uri uri = Uri.parse("content://icc/adn");  
	        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,  
	                null);  
	  
	        if (phoneCursor != null) {  
	            while (phoneCursor.moveToNext()) {  
	  
	                // 得到手机号码  
	                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
	                // 当手机号码为空的或者为空字段 跳过当前循环  
	                if (TextUtils.isEmpty(phoneNumber))  
	                    continue;  
	                // 得到联系人名称  
	                String contactName = phoneCursor  
	                        .getString(PHONES_DISPLAY_NAME_INDEX);  
	  
	                //Sim卡中没有联系人头像  
	                  
	                mContactsName.add(contactName);  
	                mContactsNumber.add(phoneNumber);  
	            }  
	  
	            phoneCursor.close();  
	        }  
	    }  
	      
	    class MyListAdapter extends BaseAdapter {  
	        public MyListAdapter(Context context) {  
	            mContext = context;  
	        }  
	  
	        public int getCount() {  
	            //设置绘制数量  
	            return mContactsName.size();  
	        }  
	  
	      
	        public Object getItem(int position) {  
	            return position;  
	        }  
	  
	        public long getItemId(int position) {  
	            return position;  
	        }  
	  
	        public View getView(int position, View convertView, ViewGroup parent) {  
	            ImageView iamge = null;  
	            TextView title = null;  
	            TextView text = null;  
	            if (convertView == null) {  
	                convertView = LayoutInflater.from(mContext).inflate(  
	                        com.rs.mobile.wportal.R.layout.ht_list_item_contact, parent,false);
	                
	            }  
	            iamge = (ImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.color_image);
                title = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.color_title);
                text = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.color_text);
//	            绘制联系人名称  
	            title.setText(mContactsName.get(position));  
//	            绘制联系人号码  
	            text.setText(mContactsNumber.get(position));  
//	            绘制联系人头像  
	            iamge.setBackground(ContextCompat.getDrawable(mContext, com.rs.mobile.wportal.R.drawable.img_headphoto));;
	            return convertView;  
	        }

			
	    }  
	}  

