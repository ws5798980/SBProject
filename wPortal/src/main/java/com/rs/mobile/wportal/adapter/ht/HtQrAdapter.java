package com.rs.mobile.wportal.adapter.ht;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.rs.mobile.common.C;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.T;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.network.OkHttpHelper.CallbackLogic;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.biz.ht.HotelQr;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import okhttp3.Request;

public class HtQrAdapter extends BaseAdapter {
	public HtQrAdapter(List<HotelQr> listData, Context context) {
		super();
		this.listData = listData;
		this.context = context;
	}

	private List<HotelQr> listData;
	private Context context;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.ht_list_item_qr, parent, false);
			vh.text_qr = (TextView) convertView.findViewById(R.id.text_qr);
			vh.text_roomNo = (TextView) convertView.findViewById(R.id.text_roomNo);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		final HotelQr h = listData.get(position);
		vh.text_roomNo.setText(h.getRoomno());
		vh.text_qr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showQrDialog(h.getRoomno(), h.getRegisterID());
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView text_roomNo, text_qr;
	}

	public Bitmap makeQRImage(String content, int QR_WIDTH, int QR_HEIGHT) throws WriterException {
		try {
			// 图像数据转换，使用了矩阵转换
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错率
			hints.put(EncodeHintType.MARGIN, 2); // default is 4
			hints.put(EncodeHintType.MAX_SIZE, 350);
			hints.put(EncodeHintType.MIN_SIZE, 100);
			BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				// 下面这里按照二维码的算法，逐个生成二维码的图片，//两个for循环是图片横列扫描的结果
				for (int x = 0; x < QR_WIDTH; x++) {
					// if (bitMatrix.get(x, y))
					// pixels[y * QR_WIDTH + x] = 0xff000000;
					// else
					// pixels[y * QR_WIDTH + x] = 0xffffffff;
					if (bitMatrix.get(x, y)) {
						if (x < QR_WIDTH / 2 && y < QR_HEIGHT / 2) {
							pixels[y * QR_WIDTH + x] = 0xFF000000;// 蓝色
							Integer.toHexString(new Random().nextInt());
						} else if (x < QR_WIDTH / 2 && y > QR_HEIGHT / 2) {
							pixels[y * QR_WIDTH + x] = 0xFF000000;// 黄色
						} else if (x > QR_WIDTH / 2 && y > QR_HEIGHT / 2) {
							pixels[y * QR_WIDTH + x] = 0xFF000000;// 绿色
						} else {
							pixels[y * QR_WIDTH + x] = 0xFF000000;// 黑色
						}
					}

					else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;// 白色
					}
				}
			}
			// ------------------添加图片部分------------------//
			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
			// // 设置像素点
			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			// // 获取图片宽高
			// int logoWidth = logoBmp.getWidth();
			// int logoHeight = logoBmp.getHeight();
			// if (QR_WIDTH == 0 || QR_HEIGHT == 0) {
			// return null;
			// }
			// if (logoWidth == 0 || logoHeight == 0) {
			// return bitmap;
			// }
			// // 图片绘制在二维码中央，合成二维码图片
			// // logo大小为二维码整体大小的1/2
			// float scaleFactor = QR_WIDTH * 1.0f / 4 / logoWidth;
			// try {
			// Canvas canvas = new Canvas(bitmap);
			// canvas.drawBitmap(bitmap, 0, 0, null);
			// canvas.scale(scaleFactor, scaleFactor, QR_WIDTH / 2, QR_HEIGHT /
			// 2);
			// canvas.drawBitmap(logoBmp, (QR_WIDTH - logoWidth) / 2, (QR_HEIGHT
			// - logoHeight) / 2, null);
			// canvas.save(Canvas.ALL_SAVE_FLAG);
			// canvas.restore();
			//
			// return bitmap;
			// } catch (Exception e) {
			// bitmap = null;
			// e.getStackTrace();
			// }
			return bitmap;
		} catch (Exception e) {
			L.e(e);
			e.printStackTrace();
		}
		return null;
	}

	private void showQrDialog(String RoomNo, String RegisterID) {

		try {

			final View dialogView = View.inflate(context, R.layout.ht_qr_dialog, null);
			final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
			final ImageView img_qr = (ImageView) dialogView.findViewById(R.id.img_qr);
			LayoutParams params = img_qr.getLayoutParams();
			params.height = BaseActivity.get_windows_width(context) / 3 * 2;
			params.width = BaseActivity.get_windows_width(context) / 3 * 2;
			img_qr.setLayoutParams(params);

			ImageView cls_img = (ImageView) dialogView.findViewById(R.id.cls_img);
			cls_img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
				}
			});
			OkHttpHelper okHttpHelper = new OkHttpHelper(context);
			HashMap<String, String> paramsKeyValue = new HashMap<>();
			paramsKeyValue.put("memid", S.getShare(context, C.KEY_REQUEST_MEMBER_ID, ""));
			paramsKeyValue.put("token", S.getShare(context, C.KEY_JSON_TOKEN, ""));
			paramsKeyValue.put("HotelID", RegisterID);
			paramsKeyValue.put("RoomNo", RoomNo);
			okHttpHelper.addPostRequest(new CallbackLogic() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub
					try {
						if (data.get("status").toString().equals("1")) {
							JSONObject obj = new JSONObject(data.get("data").toString());
							String QRText = obj.getString("QRText");
							try {
								img_qr.setImageBitmap(
										makeQRImage(QRText, BaseActivity.get_windows_width(context) / 3 * 2,
												BaseActivity.get_windows_width(context) / 3 * 2));
							} catch (WriterException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							T.showToast(context, data.getString("msg"));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						L.e(e);
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String flag) {
					// TODO Auto-generated method stub

				}
			}, Constant.BASE_URL_HT + Constant.HT_GET_QR, paramsKeyValue);

			Window dialogw = alertDialog.getWindow();
			dialogw.setGravity(Gravity.CENTER);
			alertDialog.setView(dialogView);
			alertDialog.show();
		} catch (Exception e) {

			L.e(e);

		}

	}

}
