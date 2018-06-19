package com.rs.mobile.wportal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.rs.mobile.common.D;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 后台下载
 */
@SuppressLint("HandlerLeak")
public class UpdateManager {
	private Activity mContext;
	// 返回的安装包url
	private String apkUrl;
	/* 下载包安装路径 */
	private static final String savePath = Environment.getExternalStorageDirectory().getPath() + "/YUchengguoji/APK" ;
	private String saveFileName;
	private boolean interceptFlag = false;
	private Boolean forceUpdate = false;

	public UpdateManager(Activity context,String url , boolean flag) {
		this.mContext = context;
		this.forceUpdate = flag;
		this.apkUrl = url;
	}

	// 外部接口让主Activity调用
	public void checkUpdateInfo() {
		this.saveFileName = savePath + "/" + "shelong.apk";
//		this.apkUrl = "http://www.hn122122.com/apk/chejiao.apk";
//		this.saveFileName = savePath + "/" + "2.0.1" + ".apk";
		showDownloadDialog();
	}

	private void stop() {
		if (forceUpdate) {
			mContext.finish();
		} 
	}

	private Dialog dialog;

	private void showDownloadDialog() {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.log11_progress, null);
		dialog = new Dialog(mContext, R.style.CommDialogStyle);
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		TextView dialogTitle = (TextView) view.findViewById(R.id.dialog_title);
		Button dialogBtn = (Button) view.findViewById(R.id.dialog_but);
		dialogTitle.setText("正在更新");
		dialogBtn.setText(forceUpdate ? "退出程序"
				: "取消下载");
		dialogBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				interceptFlag = true;
				stop();
			}
		});
		dialog.show();
		downloadApk(view);
	}

	/**
	 * 下载apk
	 * 
	 */
	@SuppressLint("ShowToast")
	private void downloadApk(View view) {
		final ProgressBar mProgress = (ProgressBar) view
				.findViewById(R.id.item_progressbar);
		final TextView tv = (TextView) view.findViewById(R.id.textview_speed);
		final Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					mProgress.setProgress(msg.arg1);
					if (msg.arg1 > 100) {
						tv.setText(100 + "%");
					} else {
						tv.setText(msg.arg1 + "%");
					}
					break;
				case 2:
					installApk(mContext, saveFileName);
					dialog.dismiss();
					break;
				case 3:
					dialog.dismiss();
					D.showDialog(mContext, -1, "通知", (String) msg.obj,
							"确定", new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									D.alertDialog.dismiss();
									interceptFlag = true;
									stop();
								}
							});
					break;
				}
			};
		};
		new Thread() {
			@Override
			public void run() {
				try {
					URL url = new URL(apkUrl);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();
//					File filea = new File(new MethodsUtil().getSDCardPath()
//							+ Folder.UMQ);
//					if (!filea.exists()) {
//						filea.mkdir();
//					}
					File file = new File(savePath);
					if (!file.exists()) {
						file.mkdir();
					}
					String apkFile = saveFileName;
					File ApkFile = new File(apkFile);
					FileOutputStream fos = new FileOutputStream(ApkFile);
					int count = 0;
					byte buf[] = new byte[1024];
					do {
						int numread = is.read(buf);
						count += numread;
						// 更新进度
						if (numread <= 0) {
							// 下载完成通知安装
							mHandler.sendEmptyMessage(2);
							break;
						} else {
							Message message = new Message();
							message.what = 1;
							message.arg1 = (int) (((float) count / length) * 100);
							mHandler.sendMessage(message);
						}
						fos.write(buf, 0, numread);
					} while (!interceptFlag);// 点击取消就停止下载.
					fos.close();
					is.close();
				} catch (MalformedURLException e) {
					Message message = new Message();
					message.what = 3;
					message.obj = e.toString();
					mHandler.sendMessage(message);
				} catch (IOException e) {
					Message message = new Message();
					message.what = 3;
					message.obj = e.toString();
					mHandler.sendMessage(message);
				}
			}
		}.start();
	}

	/**
	 * 安装apk
	 * 
	 */
	private void installApk(Context context, String saveFileName) {
		try {
			File apkfile = new File(saveFileName);
			if (!apkfile.exists()) {
				return;
			}
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
					"application/vnd.android.package-archive");
			context.startActivity(i);
			mContext.finish();
		} catch (Exception e) {
			
			D.showDialog(mContext, -1, "通知", "安装失败",
					"确定", new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							D.alertDialog.dismiss();
						}
					});
		}
	}
}