package com.rs.mobile.wportal;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 带按钮对话框
 * 
 * @author 彭浩 E-mail:307491878@qq.com 电话：15526453981
 * @version 创建时间：2016-6-18 上午9:13:42
 * 
 */
public class UpdateDialog {
	private Activity mContext;
	private Dialog mDialog;
	private String text;

	/**
	 * 更新弹出框
	 * 
	 * @param context
	 * @param text
	 * @param cancle
	 */
	public UpdateDialog(Activity context, String text, boolean flag,
			OnClickListener cancle) {
		this.mContext = context;
		this.text = text;
		initView(text, flag, cancle);
		show();
    }

	@SuppressLint("InflateParams")
	private void initView(String context, boolean flag,
			final OnClickListener onclick) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.log06_updatelayout, null);
		mDialog = new Dialog(mContext, R.style.CommDialogStyle);
		TextView o06_btn_context = (TextView) view
				.findViewById(R.id.o06_btn_context);
		o06_btn_context.setText(context);
		if (flag) {
			view.findViewById(R.id.o06_btn_cacle).setVisibility(View.VISIBLE);
			view.findViewById(R.id.o06_btn_line).setVisibility(View.VISIBLE);
			view.findViewById(R.id.o06_btn_update).setBackgroundResource(
					R.drawable.liner_right_down);
		} else {
			view.findViewById(R.id.o06_btn_cacle).setVisibility(View.GONE);
			view.findViewById(R.id.o06_btn_line).setVisibility(View.GONE);
			view.findViewById(R.id.o06_btn_update).setBackgroundResource(
					R.drawable.liner_blue_down);

		}
		view.findViewById(R.id.o06_btn_cacle).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dismiss();
					}
				});
		view.findViewById(R.id.o06_btn_update).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (onclick != null)
							onclick.onClick(arg0);
						dismiss();
					}
				});
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setCancelable(flag);
	}

	public void show() {
		mDialog.show();
	}

	public void hide() {
		mDialog.hide();
	}

	public void dismiss() {
		try {
			hide();
			mDialog.dismiss();
		} catch (Exception e) {
		}
	}

}
