package com.rs.mobile.wportal.view;

import com.rs.mobile.common.L;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.view.WImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuButtonView extends LinearLayout {

	private Context context;

	private WImageView icon;

	private TextView text;

	public MenuButtonView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		initView(context);

	}

	public MenuButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		initView(context);

	}

	public MenuButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub

		initView(context);

	}

	/**
	 * 초기화
	 * 
	 * @param context
	 */
	public void initView(final Context context) {

		try {

			this.context = context;

			LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View v = inflator.inflate(com.rs.mobile.wportal.R.layout.layout_main_menu_btn, null);

			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			icon = (WImageView) v.findViewById(com.rs.mobile.wportal.R.id.icon);

			text = (TextView) v.findViewById(com.rs.mobile.wportal.R.id.text);

			addView(v);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void setIcon(Drawable background) {

		try {

			icon.setBackgroundDrawable(background);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void setTextSize(int a, float b) {
		text.setTextSize(a, b);
	}

	public void setIcon(Uri uri) {

		try {

			ImageUtil.drawIamge(icon, uri);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void setText(int text) {

		try {

			this.text.setText(text);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void set_lines(int a) {
		try {

			this.text.setLines(a);

		} catch (Exception e) {

			L.e(e);

		}
	}

	public void setIconParam(int width, int height) {

		try {

			LinearLayout.LayoutParams parms = (LinearLayout.LayoutParams) this.icon.getLayoutParams();
			parms.width = width;
			parms.height = height;
			this.icon.setLayoutParams(parms);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public void setText(String text) {

		try {

			this.text.setText(text);

		} catch (Exception e) {

			L.e(e);

		}

	}

}
