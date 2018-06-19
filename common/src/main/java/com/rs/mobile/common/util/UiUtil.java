package com.rs.mobile.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.SensorWebActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class UiUtil {

	public static final int TYPE_BUTTON = 0;

	public static final int TYPE_LIST = 1;

	private static boolean flag = false;

	public static void doButtonClickAnimation(final Context context,
			final View view) {

		try {

			new Handler().post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					Animation inAnimation = AnimationUtils.loadAnimation(
							context, com.rs.mobile.common.R.anim.button_click_anim_in);
					final Animation outAnimation = AnimationUtils
							.loadAnimation(context,
									com.rs.mobile.common.R.anim.button_click_anim_out);
					inAnimation.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub

							view.startAnimation(outAnimation);

						}
					});
					view.startAnimation(inAnimation);

				}
			});

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static void doListClickAnimation(Context context, final View view) {

		try {

			Animation inAnimation = AnimationUtils.loadAnimation(context,
					com.rs.mobile.common.R.anim.list_click_anim_in);
			final Animation outAnimation = AnimationUtils.loadAnimation(
					context, com.rs.mobile.common.R.anim.list_click_anim_out);
			inAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub

					view.startAnimation(outAnimation);

				}
			});
			view.startAnimation(inAnimation);

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * startActivity
	 * 
	 * @param context
	 * @param cls
	 * @param extra
	 */
	public static void startActivity(final Context context, final Class<?> cls,
			final HashMap<String, String> extra) {

		try {

			Intent i = new Intent(context, cls);

			if (extra != null) {

				Iterator<String> keys = extra.keySet().iterator();
				while (keys.hasNext()) {

					String key = keys.next();

					i.putExtra(key, extra.get(key));

				}

			}

			context.startActivity(i);

		} catch (Exception e) {

			L.e(e);

		}

	}

	//HashMap <String, Integer>
	public static void startActivityStrtoInt(final Context context, final Class<?> cls,
									 final HashMap<String, Integer> extra) {

		try {

			Intent i = new Intent(context, cls);

			if (extra != null) {

				Iterator<String> keys = extra.keySet().iterator();
				while (keys.hasNext()) {

					String key = keys.next();

					i.putExtra(key, extra.get(key).intValue());
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				}

			}

			context.startActivity(i);

		} catch (Exception e) {

			L.e(e);

		}

	}

	/**
	 * startActivity
	 * 
	 * @param cls
	 * @param extra
	 * @param type
	 *            0 : button, 1 : list
	 */
	public static void startActivity(final Context context, final Class<?> cls,
			final HashMap<String, String> extra, int type, View view) {

		try {

			if (type == TYPE_BUTTON) {

				doButtonClickAnimation(context, view);

			} else if (type == TYPE_LIST) {

				doListClickAnimation(context, view);

			}

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					try {

						startActivity(context, cls, extra);

					} catch (Exception e) {

						L.e(e);

					}
				}
			}, 100);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static void startActivity(final Context context,
			final Intent intent, int type, View view) {

		try {

			if (type == TYPE_BUTTON) {

				doButtonClickAnimation(context, view);

			} else if (type == TYPE_LIST) {

				doListClickAnimation(context, view);

			}

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					try {

						context.startActivity(intent);

					} catch (Exception e) {

						L.e(e);

					}
				}
			}, 100);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static void startActivityForResult(final Context context,
			final int requestCode, final Intent intent, int type, View view) {

		try {

			if (type == TYPE_BUTTON) {

				doButtonClickAnimation(context, view);

			} else if (type == TYPE_LIST) {

				doListClickAnimation(context, view);

			}

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					try {

						Activity activity = (Activity) context;

						activity.startActivityForResult(intent, requestCode);

					} catch (Exception e) {

						L.e(e);

					}
				}
			}, 100);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static void setUnderLineText(TextView textView, String text) {

		try {

			Paint p = new Paint();
			p.setColor(Color.RED);
			textView.setPaintFlags(p.getColor());
			textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			textView.setText(text);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static void setRotateImage(Context context, ImageView imageView,
			int image, float degree) {

		try {

			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), image);

			// Matrix 객체 생성
			Matrix matrix = new Matrix();
			// 회전 각도 셋팅
			matrix.postRotate(degree);
			// 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성

			imageView.setBackgroundDrawable(new BitmapDrawable(Bitmap
					.createBitmap(bitmap, 0, 0, imageView.getWidth(),
							imageView.getHeight(), matrix, true)));

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static boolean checkLogin(Context context, int checkedId) {

		UtilClear.CheckLogin(context, new UtilCheckLogin.CheckListener() {

			@Override
			public void onDoNext() {
				flag = true;
			}
		}, new UtilCheckLogin.CheckError() {

			@Override
			public void onError() {
				flag = false;
			}
		});
		return flag;

	}

	public static boolean checkLogin(Context context) {

		UtilClear.CheckLogin(context, new UtilCheckLogin.CheckListener() {

			@Override
			public void onDoNext() {
				flag = true;
			}
		}, new UtilCheckLogin.CheckError() {

			@Override
			public void onError() {
				flag = false;
			}
		});
		if(flag) {
			Log.d("rsapp", "checkLogin == true");
		}
		else {
			Log.d("rsapp", "checkLogin == false");
		}

		return flag;

	}

	public static boolean checkIsLogin(Context context) {

		UtilClear.CheckLogin(context, new UtilCheckLogin.CheckListener() {

			@Override
			public void onDoNext() {
				flag = true;
			}
		}, new UtilCheckLogin.CheckError() {

			@Override
			public void onError() {
				flag = false;
			}
		}, false);
		return flag;

	}

	// 检测app是否安装
	public static boolean isInstallApp(Context context, String packageName) {

		PackageManager pm = context.getPackageManager();
		boolean installed = false;
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {

			L.e(e);

			installed = false;
		}
		return installed;
	}

	public static void installAPK(Context context, String apkName) {

		try {

			if (copyApkFromAssets(context, apkName, Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/"
					+ apkName)) {

				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(
						Uri.parse("file://"
								+ Environment.getExternalStorageDirectory()
										.getAbsolutePath() + "/" + apkName),
						"application/vnd.android.package-archive");
				context.startActivity(intent);

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static boolean copyApkFromAssets(Context context, String fileName,
			String path) {

		boolean copyIsFinish = false;

		try {

			InputStream is = context.getAssets().open(fileName);
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			copyIsFinish = true;

		} catch (IOException e) {
			L.e(e);
		}
		return copyIsFinish;
	}

	/**
	 * @param context
	 * @param type
	 *            0부터 순서대로 포탈은 8번
	 * @param title
	 * @param content
	 * @param imgUrl
	 * @param itemCode
	 */
	public static void share(Context context, String type, String itemCode,
			String title, String content, String imgUrl) {

		try {

			if (isInstallApp(context, "cn.ycapp.im")) {

				// action_type: main , wallet , share
				// type : 0부터 순서대로
				String shareUrl = "slapp://phone" + "$" + type + "$" + itemCode;

				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("loginphone",
						S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));
				// bundle.putString("loginpassword", S.get(context, "pw"));
				bundle.putString("title", title);
				bundle.putString("description", content);
				bundle.putString("images", imgUrl);
				bundle.putString("weburl", shareUrl);
				intent.setClassName("cn.ycapp.im",
						"cn.ycapp.im.ui.activity.SharedReceiverActivity");
				intent.putExtras(bundle);
				context.startActivity(intent);

			} else {

				Uri uri = Uri.parse("market://details?id=" + "cn.ycapp.im");
				Intent installIntent = new Intent(Intent.ACTION_VIEW, uri);
				installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(installIntent);

			}

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static void showVideoPlayer(Context context, String id, String kind) {

		try {

			Intent i = new Intent(context, SensorWebActivity.class);
			i.putExtra(
					C.KEY_INTENT_URL,
					C.BASE_PLAYER_URL_KR + "/WAP/videoplay?videoid=" + id
							+ "&kind=" + kind + "&userId="
							+ S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));
			context.startActivity(i);

		} catch (Exception e) {

			L.e(e);

		}

	}

	public static void setListViewHeight1(ListView listView) {

		int totalHeight = 0;
		ListAdapter listAdapter = listView.getAdapter();
		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItemView = listAdapter.getView(i, null, listView);
			int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
					MeasureSpec.EXACTLY);
			listItemView.measure(desiredWidth, 0);
			totalHeight += listItemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);

	}

	public static void setGridViewHeight(GridView gv, int num) {
		// listView.getNumColumns();
		int totalHeight = 0;

		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度
		for (int i = 0; i < gv.getAdapter().getCount(); i += num) {
			// 获取listview的每一个item
			View listItem = gv.getAdapter().getView(i, null, gv);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}
		// 获取listview的布局参数
		ViewGroup.LayoutParams params = gv.getLayoutParams();
		// 设置高度
		int dh;
		if (gv.getAdapter().getCount() % num == 0) {
			dh = gv.getAdapter().getCount() / num;
		} else {
			dh = gv.getAdapter().getCount() / num + 1;
		}
		params.height = totalHeight + gv.getVerticalSpacing() * (dh - 1);

		// 设置参数
		gv.setLayoutParams(params);

	}

	/**
	 * 获取屏幕宽高
	 * 
	 * @param context
	 * @return int screen width by px
	 */
	public static int get_windows_width(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕宽高
	 * 
	 * @param context
	 * @return int screen height by px
	 */
	public static int get_windows_height(Context context) {

		return context.getResources().getDisplayMetrics().heightPixels;
	}
}
