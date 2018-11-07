package com.rs.mobile.common;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class D {

	public static AlertDialog alertDialog;
	
	public static Dialog progressdialog;
	
	public static EditText editText,editText2,editText3;

	public static boolean isshowing;
	/**
	 * initProgressDialog
	 * 
	 * @param dialog
	 */
	public static void initProgressDialog(Dialog dialog) {
		
		try {
			
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.layout_progress_dialog);
	        
	        LinearLayout v = (LinearLayout)dialog.findViewById(R.id.parent_view);
	        v.getBackground().setAlpha(0);
	        
	        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(color.transparent));
	        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	        
		} catch (Exception e) {
			
			L.e(e);
			
		}
		
	}

	/**
	 * startProgressDialog
	 * @param context
	 * @param msg
	 * @param cancelAble
	 */
	public static void showProgressDialog(Context context, String msg, boolean cancelAble) {
		
		try {

//			if (context instanceof Service){
//			  // handle service case
//				
//				if (progressdialog == null ||progressdialog.isShowing() == false) {
//					
//					progressdialog = new Dialog(context);
//					initProgressDialog(progressdialog);
//					
//					if (msg != null && !msg.equals(""))
//						setProgressDialogMessage(progressdialog, msg);
//					progressdialog.setCancelable(cancelAble);
//	//					progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//					progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(color.transparent));
//					progressdialog.show();
//					
//				}
//				
//			} else if (context instanceof Activity) {
//				// handle activity case
//				
//				if (((Activity)context).isFinishing() == false && progressdialog == null ||progressdialog.isShowing() == false) {
//					
//					progressdialog = new Dialog(context);
//					initProgressDialog(progressdialog);
//					
//					if (msg != null && !msg.equals(""))
//						setProgressDialogMessage(progressdialog, msg);
//					progressdialog.setCancelable(cancelAble);
//	//					progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//					progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(color.transparent));
//					progressdialog.show();
//					
//				}
//				
//			}
			
			if (context instanceof Activity) {
				// handle activity case

				if (((Activity)context).isFinishing() == false && progressdialog == null ||progressdialog.isShowing() == false) {
					
					progressdialog = new Dialog(context);
					initProgressDialog(progressdialog);
					
					if (msg != null && !msg.equals(""))
						setProgressDialogMessage(progressdialog, msg);
					progressdialog.setCancelable(cancelAble);
	//					progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
					progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(color.transparent));
					progressdialog.show();
					isshowing=true;
				}
				
			}
			
        } catch (Exception e) {
        	
        	L.e(e);
        	
        }
		
	}
	
	/**
	 * setProgressDialogMessage
	 * @param dialog
	 * @param msg
	 */
	public static void setProgressDialogMessage(Dialog dialog, String msg) {
		
		try {
		
			TextView v = (TextView)dialog.findViewById(R.id.msg);
			
			if (msg == null || msg.equals("")) {
				
				v.setVisibility(View.GONE);
				
			} else {
			
				v.setVisibility(View.VISIBLE);
				v.setText(msg);
			
			}
		
		} catch (Exception e) {
			
			L.e(e);
			
		}
        
	}
	
	/**
	 * stopProgressDialog
	 */
	public static void hideProgressDialog() {

		try {
			
			if (progressdialog != null && progressdialog.isShowing() == true) {

				progressdialog.dismiss();
				isshowing=false;
			}
			
			progressdialog = null;
			
        } catch (Exception e) {

        	L.e(e);
        	
        }
		
	} 
	
	public static void showAlertDialog(Context context, int img, String title, String msg, String selectText, OnClickListener selectListener) {

		try {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);

	    	LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.dialog_title_layout, null);
			
			ImageView icon = (ImageView)v.findViewById(R.id.icon_view);
	    	TextView titleTextView = (TextView)v.findViewById(R.id.title_text_view);
	    	TextView msgTextView = (TextView)v.findViewById(R.id.msg_text_view);
	    	TextView cancelTextView = (TextView)v.findViewById(R.id.cancel_text_view);
	    	TextView selectTextView = (TextView)v.findViewById(R.id.ok_text_view);
	    	
	    	if (img != -1) {
	    		
	    		icon.setVisibility(View.VISIBLE);
	    		
	    		icon.setBackgroundResource(img);
	    		
	    	} else {
	    		
	    		icon.setVisibility(View.GONE);
	    		
	    	}
	    	titleTextView.setText(title);
	    	msgTextView.setText(msg);
	    	selectTextView.setText(selectText);
	    	cancelTextView.setVisibility(View.GONE);
	    	selectTextView.setOnClickListener(selectListener);
	    	builder.setView(v);
	    	builder.setCancelable(true);
	    	
	    	alertDialog = builder.create();
	    	alertDialog.show();

		} catch (Exception e) {
        	
        	L.e(e);
        	
        }
    	
    }
	
	/**
	 * showDialog
	 * @param context
	 * @param img
	 * @param title
	 * @param msg
	 * @param selectText
	 * @param selectListener
	 */
	public static void showDialog(Context context, int img, String title, String msg, String selectText, OnClickListener selectListener) {

		try {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);

	    	LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.dialog_title_layout, null);
			
			ImageView icon = (ImageView)v.findViewById(R.id.icon_view);
	    	TextView titleTextView = (TextView)v.findViewById(R.id.title_text_view);
	    	TextView msgTextView = (TextView)v.findViewById(R.id.msg_text_view);
	    	TextView cancelTextView = (TextView)v.findViewById(R.id.cancel_text_view);
	    	TextView selectTextView = (TextView)v.findViewById(R.id.ok_text_view);
	    	
	    	if (img != -1) {
	    		
	    		icon.setVisibility(View.VISIBLE);
	    		
	    		icon.setBackgroundResource(img);
	    		
	    	} else {
	    		
	    		icon.setVisibility(View.GONE);
	    		
	    	}
	    	titleTextView.setText(title);
	    	msgTextView.setText(msg);
	    	selectTextView.setText(selectText);
	    	cancelTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (alertDialog != null) 
						alertDialog.dismiss();
				}
			});
	    	selectTextView.setOnClickListener(selectListener);
	    	builder.setView(v);
	    	builder.setCancelable(true);
	    	
	    	alertDialog = builder.create();
	    	alertDialog.show();

		} catch (Exception e) {
        	
        	L.e(e);
        	
        }
    	
    }
	
	/**
	 * showDialog
	 * @param context
	 * @param img
	 * @param title
	 * @param msg
	 * @param selectText
	 * @param selectListener
	 */
	public static void showDialog(Context context, int img, String title, String msg, String selectText, OnClickListener selectListener, boolean cancelable) {

		try {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);

	    	LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.dialog_title_layout, null);
			
			ImageView icon = (ImageView)v.findViewById(R.id.icon_view);
	    	TextView titleTextView = (TextView)v.findViewById(R.id.title_text_view);
	    	TextView msgTextView = (TextView)v.findViewById(R.id.msg_text_view);
	    	TextView cancelTextView = (TextView)v.findViewById(R.id.cancel_text_view);
	    	TextView selectTextView = (TextView)v.findViewById(R.id.ok_text_view);
	    	if (img != -1) {
	    		
	    		icon.setVisibility(View.VISIBLE);
	    		
	    		icon.setBackgroundResource(img);
	    		
	    	} else {
	    		
	    		icon.setVisibility(View.GONE);
	    		
	    	}
	    		
	    	titleTextView.setText(title);
	    	msgTextView.setText(msg);
	    	selectTextView.setText(selectText);
	    	cancelTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (alertDialog != null) 
						alertDialog.dismiss();
				}
			});
	    	selectTextView.setOnClickListener(selectListener);
	    	builder.setView(v);
	    	builder.setCancelable(cancelable);
	    	
	    	alertDialog = builder.create();
	    	alertDialog.show();

		} catch (Exception e) {
        	
        	L.e(e);
        	
        }
    	
    }
	
	/**
	 * showDialog
	 * @param context
	 * @param img
	 * @param title
	 * @param msg
	 * @param selectText
	 * @param selectListener
	 * @param cancelListener
	 */
	public static void showDialog(Context context, int img, String title, String msg, String selectText, OnClickListener selectListener, String cancelText, OnClickListener cancelListener) {

		try {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);

	    	LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.dialog_title_layout, null);
			
			ImageView icon = (ImageView)v.findViewById(R.id.icon_view);
	    	TextView titleTextView = (TextView)v.findViewById(R.id.title_text_view);
	    	TextView msgTextView = (TextView)v.findViewById(R.id.msg_text_view);
	    	TextView selectTextView = (TextView)v.findViewById(R.id.ok_text_view);
	    	TextView cancelTextView = (TextView)v.findViewById(R.id.cancel_text_view);
	    	
	    	if (img == -1) icon.setVisibility(View.GONE);
	    	else icon.setBackgroundResource(img);
	    	titleTextView.setText(title);
	    	msgTextView.setText(msg);
	    	selectTextView.setText(selectText);
	    	cancelTextView.setText(cancelText);
	    	selectTextView.setOnClickListener(selectListener);
	    	cancelTextView.setOnClickListener(cancelListener);
	    	builder.setView(v);
	    	builder.setCancelable(true);
	    	
	    	alertDialog = builder.create();
	    	alertDialog.show();

		} catch (Exception e) {
        	
        	L.e(e);
        	
        }
    	
    }
	
	public static void showDialog(Context context, int img, String title, String msg, String selectText, OnClickListener selectListener, String cancelText, OnClickListener cancelListener, boolean cancelAble) {

		try {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);

	    	LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.dialog_title_layout, null);
			
			ImageView icon = (ImageView)v.findViewById(R.id.icon_view);
	    	TextView titleTextView = (TextView)v.findViewById(R.id.title_text_view);
	    	TextView msgTextView = (TextView)v.findViewById(R.id.msg_text_view);
	    	TextView selectTextView = (TextView)v.findViewById(R.id.ok_text_view);
	    	TextView cancelTextView = (TextView)v.findViewById(R.id.cancel_text_view);
	    	
	    	if (img == -1) icon.setVisibility(View.GONE);
	    	else icon.setBackgroundResource(img);
	    	titleTextView.setText(title);
	    	msgTextView.setText(msg);
	    	selectTextView.setText(selectText);
	    	cancelTextView.setText(cancelText);
	    	selectTextView.setOnClickListener(selectListener);
	    	cancelTextView.setOnClickListener(cancelListener);
	    	builder.setView(v);
	    	builder.setCancelable(cancelAble);
	    	
	    	alertDialog = builder.create();
	    	alertDialog.show();

		} catch (Exception e) {
        	
        	L.e(e);
        	
        }
    	
    }
	
	/**
	 * showDialog
	 * @param context
	 * @param img
	 * @param title
	 * @param msg
	 * @param selectText
	 * @param selectListener
	 * @param cancelListener
	 */
	public static void showEditTextDialog(Context context, int img, String title, String hint, String msg, String selectText, OnClickListener selectListener, String cancelText, OnClickListener cancelListener) {

		try {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);

	    	LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.dialog_edit_text, null);
			
			ImageView icon = (ImageView)v.findViewById(R.id.icon_view);
	    	TextView titleTextView = (TextView)v.findViewById(R.id.title_text_view);
	    	editText = (EditText)v.findViewById(R.id.msg_edit_text);
	    	TextView selectTextView = (TextView)v.findViewById(R.id.ok_text_view);
	    	TextView cancelTextView = (TextView)v.findViewById(R.id.cancel_text_view);
	    	
	    	if (img == -1) icon.setVisibility(View.GONE);
	    	else icon.setBackgroundResource(img);
	    	titleTextView.setText(title);
	    	editText.setHint(hint);
	    	if (msg != null && !msg.equals(""))
	    		editText.setText(msg);
	    	selectTextView.setText(selectText);
	    	cancelTextView.setText(cancelText);
	    	selectTextView.setOnClickListener(selectListener);
	    	cancelTextView.setOnClickListener(cancelListener);
	    	builder.setView(v);
	    	builder.setCancelable(true);
	    	
	    	alertDialog = builder.create();
	    	alertDialog.show();

		} catch (Exception e) {
        	
        	L.e(e);
        	
        }
    	
    }
	public static void show3EditTextDialog(Context context, int img, String title, String hint, String msg1,  String msg2, String msg3,String selectText, OnClickListener selectListener, String cancelText, OnClickListener cancelListener) {

		try {

			AlertDialog.Builder builder = new AlertDialog.Builder(context);

			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.dialog_3edit_text, null);

			ImageView icon = (ImageView)v.findViewById(R.id.icon_view);
			TextView titleTextView = (TextView)v.findViewById(R.id.title_text_view);
			editText = (EditText)v.findViewById(R.id.msg_edit_text);
			 editText2 = (EditText)v.findViewById(R.id.msg_edit_text2);
			 editText3 = (EditText)v.findViewById(R.id.msg_edit_text3);
			TextView selectTextView = (TextView)v.findViewById(R.id.ok_text_view);
			TextView cancelTextView = (TextView)v.findViewById(R.id.cancel_text_view);

			if (img == -1) icon.setVisibility(View.GONE);
			else icon.setBackgroundResource(img);
			titleTextView.setText(title);
			editText.setHint(hint);
			if (msg1 != null && !msg1.equals(""))
				editText.setText(msg1);
			if (msg2 != null && !msg2.equals(""))
				editText2.setText(msg2);
			if (msg3 != null && !msg3.equals(""))
				editText3.setText(msg3);
			selectTextView.setText(selectText);
			cancelTextView.setText(cancelText);
			selectTextView.setOnClickListener(selectListener);
			cancelTextView.setOnClickListener(cancelListener);
			builder.setView(v);
			builder.setCancelable(true);

			alertDialog = builder.create();
			alertDialog.show();

		} catch (Exception e) {

			L.e(e);

		}

	}
	/**
	 * showDialog
	 * @param context
	 * @param img
	 * @param title
	 * @param msg
	 * @param selectText
	 * @param selectListener
	 * @param cancelListener
	 */
	public static void showMyEditTextDialog(Context context, int img, String title, String hint, String msg, String selectText, OnClickListener selectListener, String cancelText, OnClickListener cancelListener) {

		try {

			AlertDialog.Builder builder = new AlertDialog.Builder(context);

			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.dialog_edit_text_new, null);

			ImageView icon = (ImageView)v.findViewById(R.id.icon_view);
			TextView titleTextView = (TextView)v.findViewById(R.id.title_text_view);
			editText = (EditText)v.findViewById(R.id.msg_edit_text);
			TextView selectTextView = (TextView)v.findViewById(R.id.ok_text_view);
			TextView cancelTextView = (TextView)v.findViewById(R.id.cancel_text_view);

			if (img == -1) icon.setVisibility(View.GONE);
			else icon.setBackgroundResource(img);
			titleTextView.setText(title);
			editText.setHint(hint);
			if (msg != null && !msg.equals(""))
				editText.setText(msg);
			selectTextView.setText(selectText);
			cancelTextView.setText(cancelText);
			selectTextView.setOnClickListener(selectListener);
			cancelTextView.setOnClickListener(cancelListener);
			builder.setView(v);
			builder.setCancelable(true);

			alertDialog = builder.create();
			alertDialog.show();

		} catch (Exception e) {

			L.e(e);

		}

	}


	
	public static void showSingleChoiceDialog(Context context, int icon, int title, int msg, int posButton, int negButton, 
			 DialogInterface.OnClickListener posListener, DialogInterface.OnClickListener negListener,
			 CharSequence[] items, int selectedPosition, DialogInterface.OnClickListener selectListener, boolean cancelAble) {
			
			try {

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				if (icon != -1)builder.setIcon(icon);
				if (title != -1)builder.setTitle(title);
				if (msg != -1) builder.setMessage(msg);
				if (items != null)builder.setSingleChoiceItems(items, selectedPosition, selectListener);
				if (posButton != -1)builder.setPositiveButton(posButton, posListener);
				if (negButton != -1)builder.setNegativeButton(negButton, negListener);
				builder.setCancelable(cancelAble);
				builder.create().show();
				
			} catch (Exception e) {
				
				L.e(e);
				
			}
			
		}
	
}
