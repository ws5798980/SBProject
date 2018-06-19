package com.rs.mobile.wportal.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.view.WImageView;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {

	public SparseArray< View > views = new SparseArray< View >();
	
	private Context context;
	
	private int count;
	
	private LayoutInflater inflater;
	
//	private JSONArray arr;
	
	public ArrayList<JSONObject> items = new ArrayList<JSONObject>();
	
	private int type = 0; // 0 : 메인, 1, 광락 공연
	
	public void setData(JSONObject data) {
		
		try {
			
			JSONArray arr = new JSONArray(data.get(C.KEY_JSON_BANNER).toString());
			
			this.count = arr.length();
			
			for (int i = 0; i < count; i++) {
				
				items.add( new JSONObject(arr.get(i).toString()));
				
			}
			
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	private ItemClickListener itemClickListener;
	
	public void setOnItemClickListener(ItemClickListener itemClickListener) {
		
		this.itemClickListener = itemClickListener;
		
	}
	
	public interface ItemClickListener {
		
		void onClick(Object obj);
		
	}
	
	/**
	 * ViewPagerAdapter 생성자
	 * @param inflater
	 * @param data
	 * @param type 0 : 메인, 1, 광락 공연, 2 : 외식 메인
	 */
	public ViewPagerAdapter(LayoutInflater inflater, JSONObject data, String key, int type) {

		// TODO Auto-generated constructor stub

		// 전달 받은 LayoutInflater를 멤버변수로 전달
		
		try {
			
			this.type = type;
		
			this.items.clear();
			
			this.context = inflater.getContext();
			
			this.inflater = inflater;
			
			JSONArray arr = data.getJSONArray(key);
			
			this.count = arr.length();
			
			for (int i = 0; i < count; i++) {
				
				items.add( new JSONObject(arr.get(i).toString()));
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}

	public ViewPagerAdapter(LayoutInflater inflater, JSONObject data) {

		// TODO Auto-generated constructor stub

		// 전달 받은 LayoutInflater를 멤버변수로 전달
		
		try {
			
			type = 0;
		
			this.items.clear();
			
			this.context = inflater.getContext();
			
			this.inflater = inflater;
			
			JSONArray arr = new JSONArray(data.get(C.KEY_JSON_BANNER).toString());
			
			this.count = arr.length();
			
			for (int i = 0; i < count; i++) {
				
				items.add( new JSONObject(arr.get(i).toString()));
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}
	
	public ViewPagerAdapter(LayoutInflater inflater, JSONObject data, String key) {

		// TODO Auto-generated constructor stub

		// 전달 받은 LayoutInflater를 멤버변수로 전달
		
		try {
			
			type = 1;
		
			this.items.clear();
			
			this.context = inflater.getContext();
			
			this.inflater = inflater;
			
			JSONArray arr = data.getJSONArray(key);
			
			this.count = arr.length();
			
			for (int i = 0; i < count; i++) {
				
				items.add( new JSONObject(arr.get(i).toString()));
				
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

	}

	// PagerAdapter가 가지고 잇는 View의 개수를 리턴
	// 보통 보여줘야하는 이미지 배열 데이터의 길이를 리턴
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size(); // 이미지 개수 리턴(그림이 10개라서 10을 리턴)
	}

	// ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
	// 쉽게 말해, 스크롤을 통해 현재 보여져야 하는 View를 만들어냄.
	// 첫번째 파라미터 : ViewPager
	// 두번째 파라미터 : ViewPager가 보여줄 View의 위치(가장 처음부터 0,1,2,3...)
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub

		View view = null;

		try {
		
			// 새로운 View 객체를 Layoutinflater를 이용해서 생성
			// 만들어질 View의 설계는 res폴더>>layout폴더>>viewpater_childview.xml 레이아웃 파일 사용
			view = inflater.inflate(com.rs.mobile.wportal.R.layout.layout_intro_image_view, null);
	
			// 만들어진 View안에 있는 ImageView 객체 참조
			// 위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.
//			final WImageView imageView = (WImageView) view.findViewById(R.id.image_view);
			
			final WImageView imageView = (WImageView) view.findViewById(com.rs.mobile.wportal.R.id.image_view);
	
			// ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업
			// 현재 position에 해당하는 이미지를 setting
			
			final JSONObject jsonObject = items.get(position);
			
			if (type == 0 || type == 1) {
				
				ImageUtil.drawImageViewBuFullUrl(context, imageView, jsonObject, C.KEY_JSON_IMAGE_URL, C.KEY_JSON_VERSION);
				
			} else if (type == 2) {
				
				Uri uri = Uri.parse(jsonObject.getString("adImage"));
				
				ImageUtil.drawIamge(imageView, uri);
				
			}

			// ViewPager에 만들어 낸 View 추가
			
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (itemClickListener != null) {
						
						itemClickListener.onClick(jsonObject);
						
					}
					
				}
			});
			
			views.put(position, imageView);
			
			container.addView(view);
		
		} catch (Exception e) {
			
			L.e(e);
			
		}

		// Image가 세팅된 View를 리턴
		return view;
	}

	// 화면에 보이지 않은 View는파쾨를 해서 메모리를 관리함.
	// 첫번째 파라미터 : ViewPager
	// 두번째 파라미터 : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)
	// 세번째 파라미터 : 파괴될 객체(더 이상 보이지 않은 View 객체)
//	@Override
//	public void destroyItem(ViewGroup container, int position, Object object) {
//		// TODO Auto-generated method stub
//
//		// ViewPager에서 보이지 않는 View는 제거
//		// 세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시
//		container.removeView((View) object);
//
//	}
	
	@Override
	public void destroyItem(View collection, int position, Object o) {
	    View view = (View)o;
	    ((ViewPager) collection).removeView(view);
	    views.remove(position);
	    view = null;
	}

	// instantiateItem() 메소드에서 리턴된 Ojbect가 View가 맞는지 확인하는 메소드
	@Override
	public boolean isViewFromObject(View v, Object obj) {
		// TODO Auto-generated method stub
		return v == obj;
	}
	
	@Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

}
