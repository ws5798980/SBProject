package com.rs.mobile.wportal.adapter.dp;

import org.json.JSONArray;
import org.json.JSONException;

import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.util.PageUtil;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.activity.dp.DpSerchResultActivity;
import com.rs.mobile.wportal.activity.dp.DpFloorDetailActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DpFloorListAdapter extends BaseAdapter {
	public static int TYPE_1 = 1;
	public static int TYPE_2 = 2;

	public DpFloorListAdapter(JSONArray arr, Context context) {
		super();
		this.arr = arr;
		this.context = context;

	}

	private JSONArray arr;
	private Context context;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return arr.getJSONObject(position);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub

		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		int floornum = 0;
		try {
			floornum = Integer.parseInt(arr.getJSONObject(position).get("floor_num").toString());
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (floornum % 2 == 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@SuppressLint("CutPasteId")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int typeselet = getItemViewType(position);
		if (convertView == null) {
			switch (typeselet) {
			case 1:
				convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.dp_list_item_floor2, parent,
						false);
				break;
			case 0:
				convertView = LayoutInflater.from(parent.getContext()).inflate(com.rs.mobile.wportal.R.layout.dp_list_item_floor1, parent,
						false);
				break;

			default:
				break;
			}

		}
		switch (typeselet) {
		case 0:
			TextView floor_num = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.floor_num);
			TextView level_name = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.level_name);
			WImageView banner_001 = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.banner_001);
			WImageView banner_002 = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.banner_002);
			WImageView banner_003 = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.banner_003);
			WImageView banner_004 = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.banner_004);
			RelativeLayout rela_001 = (RelativeLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.rela_001);
			rela_001.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {
						Bundle bundle = new Bundle();
						bundle.putString("floor_num", arr.getJSONObject(position).get("floor_num").toString());
						PageUtil.jumpTo(context, DpFloorDetailActivity.class, bundle);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
			try {
				JSONArray arrimg = arr.getJSONObject(position).getJSONArray("data");
				floor_num.setText(arr.getJSONObject(position).get("floor_num") + "F");
				level_name.setText(arr.getJSONObject(position).getString("level_name"));
				for (int j = 0; j < arrimg.length(); j++) {
					if (arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0).getString("display_num")
							.contains("a")) {
						ImageUtil.drawImageFromUri(arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.getString("image_url"), banner_001);
						final String custom_code = arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.get("custom_code").toString();
						banner_001.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
								bundle.putString("custom_code", custom_code);
								bundle.putString("flag", "custom_code");
								PageUtil.jumpTo(context, DpSerchResultActivity.class, bundle);
							}
						});
					}
					;
				}
				for (int j = 0; j < arrimg.length(); j++) {
					if (arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0).getString("display_num")
							.contains("b")) {
						ImageUtil.drawImageFromUri(arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.getString("image_url"), banner_002);
						final String custom_code = arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.get("custom_code").toString();
						banner_002.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
								bundle.putString("custom_code", custom_code);
								bundle.putString("flag", "custom_code");
								PageUtil.jumpTo(context, DpSerchResultActivity.class, bundle);
							}
						});
					}
					;
				}
				for (int j = 0; j < arrimg.length(); j++) {
					if (arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0).getString("display_num")
							.contains("c")) {
						ImageUtil.drawImageFromUri(arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.getString("image_url"), banner_003);
						final String custom_code = arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.get("custom_code").toString();
						banner_003.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
								bundle.putString("custom_code", custom_code);
								bundle.putString("flag", "custom_code");
								PageUtil.jumpTo(context, DpSerchResultActivity.class, bundle);
							}
						});
					}
					;
				}
				for (int j = 0; j < arrimg.length(); j++) {
					if (arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0).getString("display_num")
							.contains("d")) {
						ImageUtil.drawImageFromUri(arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.getString("image_url"), banner_004);
						final String custom_code = arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.get("custom_code").toString();
						banner_004.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
								bundle.putString("custom_code", custom_code);
								bundle.putString("flag", "custom_code");
								PageUtil.jumpTo(context, DpSerchResultActivity.class, bundle);
							}
						});
					}
					;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			break;
		case 1:
			TextView floor_num1 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.floor_num1);
			TextView level_name1 = (TextView) convertView.findViewById(com.rs.mobile.wportal.R.id.level_name1);
			WImageView banner_005 = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.banner_005);
			WImageView banner_006 = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.banner_006);
			WImageView banner_007 = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.banner_007);
			WImageView banner_008 = (WImageView) convertView.findViewById(com.rs.mobile.wportal.R.id.banner_008);
			RelativeLayout rela_002 = (RelativeLayout) convertView.findViewById(com.rs.mobile.wportal.R.id.rela_002);
			rela_002.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {
						Bundle bundle = new Bundle();
						bundle.putString("floor_num", arr.getJSONObject(position).get("floor_num").toString());
						PageUtil.jumpTo(context, DpFloorDetailActivity.class, bundle);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

			try {
				JSONArray arrimg = arr.getJSONObject(position).getJSONArray("data");
				floor_num1.setText(arr.getJSONObject(position).get("floor_num") + "F");
				level_name1.setText(arr.getJSONObject(position).getString("level_name"));
				for (int j = 0; j < arrimg.length(); j++) {
					if (arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0).getString("display_num")
							.contains("a")) {
						ImageUtil.drawImageFromUri(arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.getString("image_url"), banner_005);
						final String custom_code = arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.get("custom_code").toString();
						banner_005.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
								bundle.putString("custom_code", custom_code);
								bundle.putString("flag", "custom_code");
								PageUtil.jumpTo(context, DpSerchResultActivity.class, bundle);
							}
						});
					}
					;
				}
				for (int j = 0; j < arrimg.length(); j++) {
					if (arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0).getString("display_num")
							.contains("b")) {
						ImageUtil.drawImageFromUri(arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.getString("image_url"), banner_006);
						final String custom_code = arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.get("custom_code").toString();
						banner_006.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
								bundle.putString("custom_code", custom_code);
								bundle.putString("flag", "custom_code");
								PageUtil.jumpTo(context, DpSerchResultActivity.class, bundle);
							}
						});
					}
					;
				}
				for (int j = 0; j < arrimg.length(); j++) {
					if (arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0).getString("display_num")
							.contains("c")) {
						ImageUtil.drawImageFromUri(arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.getString("image_url"), banner_007);
						final String custom_code = arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.get("custom_code").toString();
						banner_007.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
								bundle.putString("custom_code", custom_code);
								bundle.putString("flag", "custom_code");
								PageUtil.jumpTo(context, DpSerchResultActivity.class, bundle);
							}
						});
					}
					;
				}
				for (int j = 0; j < arrimg.length(); j++) {
					if (arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0).getString("display_num")
							.contains("d")) {
						ImageUtil.drawImageFromUri(arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.getString("image_url"), banner_008);
						final String custom_code = arrimg.getJSONObject(j).getJSONArray("eventImage").getJSONObject(0)
								.get("custom_code").toString();
						banner_008.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
								bundle.putString("custom_code", custom_code);
								bundle.putString("flag", "custom_code");
								PageUtil.jumpTo(context, DpSerchResultActivity.class, bundle);
							}
						});
					}
					;
				}
			} catch (Exception e) {
				// TODO: handle exception
				L.e(e);
			}

			break;
		default:
			break;
		}

		return convertView;
	}

}
