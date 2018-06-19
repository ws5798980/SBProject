package com.rs.mobile.common.util;

import java.io.File;
import java.math.BigDecimal;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.util.UtilCheckLogin.CheckListener;
import com.rs.mobile.common.util.UtilCheckLogin.CheckError;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

public class UtilClear {

	/**
	 * 跳转龙聊
	 * 
	 * @param context
	 * @param pageName
	 */
	public static void IntentToLongLiao(final Context context,
			final String pageName, final String param) {
		try {
			UtilClear.CheckLogin(context, new CheckListener() {

				@Override
				public void onDoNext() {
					if (UiUtil.isInstallApp(context, "cn.sbapp.im")) {

						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						/*bundle.putString("phone", S.getShare(context,
								C.KEY_REQUEST_MEMBER_ID, ""));*/
						bundle.putString("phone", param);
						intent.setClassName("cn.sbapp.im", param);
						intent.putExtras(bundle);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);


//						Intent intent = new Intent();
//						intent.setClassName("cn.sbapp.im", param);
//						Bundle bundle = new Bundle();
//						bundle.putString("phone", param);
//						intent.putExtras(bundle);
//						context.startActivity(intent);


					} else {

//						Uri uri = Uri.parse("market://details?id="
//								+ "cn.sbapp.im");
//						Intent installIntent = new Intent(Intent.ACTION_VIEW,
//								uri);
//						if (UiUtil.isInstallApp(context,
//								"com.tencent.android.qqdownloader")) {
//							installIntent
//									.setPackage("com.tencent.android.qqdownloader");
//						} else if (UiUtil.isInstallApp(context,
//								"com.qihoo.appstore")) {
//							installIntent.setPackage("com.qihoo.appstore");
//						} else if (UiUtil.isInstallApp(context,
//								"com.baidu.appsearch")) {
//							installIntent.setPackage("com.baidu.appsearch");
//						}
//						installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//						context.startActivity(installIntent);

					}
				}
			});

		} catch (Exception e) {

			L.e(e);

		}
	}

	/**
	 * 登陆信息验证
	 * 
	 * @param context
	 * @param checkListener
	 *            后续的操作
	 */
	public static void CheckLogin(Context context, CheckListener checkListener) {
		UtilCheckLogin cl = new UtilCheckLogin();
		cl.checkLogin(context, true);
		cl.setCheckListener(checkListener);
	}

	/**
	 * 登陆信息验证
	 * 
	 * @param context
	 * @param checkError
	 *            后续的操作
	 */
	public static void CheckLogin(Context context, CheckError checkError) {
		UtilCheckLogin cl = new UtilCheckLogin();
		cl.checkLogin(context, true);
		cl.setCheckError(checkError);
	}

	/**
	 * 登陆信息验证
	 * 
	 * @param context
	 * @param checkListener
	 *            跳转登陆界面后的操作
	 */
	public static void CheckLogin(Context context, CheckListener checkListener,
			CheckError checkError, boolean flag) {
		UtilCheckLogin cl = new UtilCheckLogin();
		cl.checkLogin(context, flag);
		cl.setCheckListener(checkListener);
		cl.setCheckError(checkError);
	}

	/**
	 * 登陆信息验证
	 * 
	 * @param context
	 * @param checkListener
	 *            跳转登陆界面后的操作
	 */
	public static void CheckLogin(Context context, CheckListener checkListener,
			CheckError checkError) {
		UtilCheckLogin cl = new UtilCheckLogin();
		cl.checkLogin(context, true);
		cl.setCheckListener(checkListener);
		cl.setCheckError(checkError);
	}

	/**
	 * 获取文件夹大小
	 * 
	 * @param file
	 *            File实例
	 * @return long
	 */
	public static long getFolderSize(File file) {

		long size = 0;
		try {
			java.io.File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);

				} else {
					size = size + fileList[i].length();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 删除指定目录下文件及目录
	 * 
	 * @param deleteThisPath
	 * @param filepath
	 * @return
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(filePath)) {
			try {
				File file = new File(filePath);
				if (file.isDirectory()) {// 处理目录
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {// 如果是文件，删除
						file.delete();
					} else {// 目录
						if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 格式化单位
	 * 
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			// return size + "Byte(s)";
			return "0MB";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}

}
