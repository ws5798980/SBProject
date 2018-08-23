package com.rs.mobile.wportal.activity.xsgr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.FileUtil;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.common.view.BringPhotoView;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.entity.MyShopInfoBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

public class StoreManageActivity extends BaseActivity {
    MyShopInfoBean bean;
    TextView tv_name, tv_phone, tv_add;
    WImageView wImageView;
    private RelativeLayout bringPhothLayout;
    private String uploadTime;
    private String zipCode,addr,addrDetail;
    private BringPhotoView bringPhotoView;
    private String imageDownloadUrl = "";
    private Uri imageUri;
    private String imagePath = "";
    private RelativeLayout shopName, telPhone, positionInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_my_info);

        initView();
        initData();
        setListener();

    }

    private void setListener() {
        wImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBringPhotoView();
            }
        });
        bringPhothLayout = (RelativeLayout) findViewById(com.rs.mobile.wportal.R.id.bring_photo_layout);
        bringPhothLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                hideBringPhotoView();

            }
        });

        bringPhotoView = (BringPhotoView) findViewById(com.rs.mobile.wportal.R.id.bring_photo_view);
        bringPhotoView.setOnItemSeletedListener(new BringPhotoView.OnItemSeletedListener() {

            @Override
            public void onItemClick(int position) {
                // TODO Auto-generated method stub

                hideBringPhotoView();

            }
        });
        shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D.showEditTextDialog(StoreManageActivity.this, -1, "", getResources().getString(R.string.shop_name), "",
                        getResources().getString(R.string.button_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = D.editText.getText().toString().trim();
                                if ("".equals(name)) {
                                    D.alertDialog.dismiss();
                                } else {
                                    tv_name.setText(name);
                                    D.alertDialog.dismiss();
                                }
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
            }
        });
        telPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D.showEditTextDialog(StoreManageActivity.this, -1, "", getResources().getString(R.string.mk_reference_03), "",
                        getResources().getString(R.string.button_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = D.editText.getText().toString().trim();
                                if ("".equals(name)) {
                                    D.alertDialog.dismiss();
                                } else {
                                    tv_phone.setText(name);
                                    D.alertDialog.dismiss();
                                }
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
            }
        });
        positionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("zipCode",zipCode);
                bundle.putString("addr",addr);
                bundle.putString("addrDetail",addrDetail);
                intent.putExtras(bundle);
                intent.setClass(StoreManageActivity.this, LocationChangeActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

    }

    private void initData() {
        initShopInfoData();
    }

    private void initView() {
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_name = (TextView) findViewById(R.id.tv_name);
        wImageView = (WImageView) findViewById(R.id.img_head);
        wImageView.setCircle(true);
        shopName = (RelativeLayout) findViewById(R.id.tv_shop_name);
        telPhone = (RelativeLayout) findViewById(R.id.layout_phone);
        positionInfo = (RelativeLayout) findViewById(R.id.position_info);
        LinearLayout close_btn = (LinearLayout) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSave();
            }
        });
    }

    private void requestSave() {
        try {

//			HashMap<String, String> headers = new HashMap<>();
//			headers.put("Content-Type", "application/json;Charset=UTF-8");

            JSONObject j1 = new JSONObject();
            HashMap<String, String> params = new HashMap<>();
            try {

                j1.put("lang_type", AppConfig.LANG_TYPE);
                j1.put("token", S.getShare(StoreManageActivity.this, C.KEY_JSON_TOKEN, ""));
                j1.put("custom_code", S.getShare(StoreManageActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
                j1.put("store_image_url", imageDownloadUrl);
                j1.put("custom_name", tv_name.getText().toString());
                j1.put("telephone", tv_phone.getText().toString());
                j1.put("zip_code", zipCode);
                j1.put("kor_addr", addr);
                j1.put("kor_addr_detail", addrDetail);
                Log.e("j1--", j1.toString());
				/*j1.put(C.KEY_REQUEST_MEMBER_ID_TOW, "1862756329077a18"); //memberID
				j1.put(C.KEY_JSON_NICK_NAME, "kimdsttthhjh");  //deviceNo
				j1.put("imagePath","http:\\/\\/imfiles.dxbhtm.com:8640\\/upload\\/image\\/2018121\\/201812110163976300x250.jpg"); //s_id
				j1.put("gender", "f");
				j1.put("toekn", "ad-443d-bef8-bec719f4a77c|00C79862-B755-4903-92E1-20833C8A3429|1862756329077a18yc|1862756329077a18|8111100200012063");  //lang_type
				*/


                //Jason Type : memid,mempwd,deviceNo, s_id, ver, lang_type

            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            OkHttpHelper helper = new OkHttpHelper(StoreManageActivity.this);
            helper.addPostRequest(new OkHttpHelper.CallbackLogic() {

                @Override
                public void onNetworkError(Request request, IOException e) {
                    finish();
                }

                @Override
                public void onBizSuccess(String responseDescription, final JSONObject data, final String all_data) {

                    try {


                        L.d(data.toString());

                        // t(getString(R.string.complete));

                    } catch (Exception e) {

                        L.e(e);

                    }

                    setResult(RESULT_OK);

                    finish();

                }

                @Override
                public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
                    String aaa = responseCode.toString();
                    finish();

                }
            }, "http://mall.gigawon.cn:8800/api/AppSM/requestStoreImageUpdate", j1.toString());
//			}, C.BASE_RS_MEMBER_URL + C.REQUEST_NICK_NAME_CHANGE, j1.toString());

        } catch (Exception e) {

            L.e(e);

            finish();

        }
    }


    //评论列表
    public void initShopInfoData() {

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(StoreManageActivity.this, C.KEY_JSON_TOKEN, ""));
        param.put("custom_code", S.getShare(StoreManageActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
        OkHttpHelper okHttpHelper = new OkHttpHelper(StoreManageActivity.this);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                bean = GsonUtils.changeGsonToBean(responseDescription, MyShopInfoBean.class);

                tv_name.setText(bean.getCustom_name() + "");
                tv_phone.setText(bean.getMobilepho() + "");
                zipCode = bean.getZip_code()+"";
                addr = bean.getKor_addr()+"";
                addrDetail = bean.getKor_addr_detail()+"";
                tv_add.setText(addr+" "+addrDetail);

//            Glide.with(XsMyShopActivity.this).load(bean.getShop_thumnail_image()).into(img_myshop);
                if (bean.getShop_thumnail_image() != null && !bean.getShop_thumnail_image().isEmpty()) {
                    ImageUtil.drawImageFromUri(bean.getShop_thumnail_image(), wImageView);
                    imageDownloadUrl = bean.getShop_thumnail_image();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XS_BASE_URL + "AppSM/requestSaleOrderAmount", param);

    }

    public void showBringPhotoView() {

        try {

            uploadTime = "" + System.currentTimeMillis();

            // Animation fadeAnimation =
            // AnimationUtils.loadAnimation(PersnalCenterActivity.this,
            // R.anim.fade_in);
            // Animation scaleAnimation =
            // AnimationUtils.loadAnimation(PersnalCenterActivity.this,
            // R.anim.scale_top_in);

            bringPhothLayout.setVisibility(View.VISIBLE);
            bringPhotoView.setVisibility(View.VISIBLE);

            // bringPhothLayout.startAnimation(fadeAnimation);
            // bringPhotoView.startAnimation(scaleAnimation);

        } catch (Exception e) {

            L.e(e);

        }

    }

    public void hideBringPhotoView() {

        try {

            // Animation fadeAnimation =
            // AnimationUtils.loadAnimation(PersnalCenterActivity.this,
            // R.anim.fade_out);
            // Animation scaleAnimation =
            // AnimationUtils.loadAnimation(PersnalCenterActivity.this,
            // R.anim.scale_bottom_out);

            bringPhothLayout.setVisibility(View.GONE);
            bringPhotoView.setVisibility(View.GONE);

            // bringPhothLayout.startAnimation(fadeAnimation);
            // bringPhotoView.startAnimation(scaleAnimation);

        } catch (Exception e) {

            L.e(e);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case BringPhotoView.PHOTO_REQUEST_TAKEPHOTO:

                    try {

                        new TakePhotoAsyncTask().execute();

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        L.e(e);
                    }

                    break;

                case BringPhotoView.PHOTO_REQUEST_GALLERY:

                    if (data != null) {

                        try {

                            new GetPhotoFromGallaryAsyncTask().execute(data.getData());

                        } catch (Exception e) {
                            L.e(e);
                        }
                    }
                    break;
                case 1000:
                    zipCode = data.getStringExtra("textNo");
                    addr = data.getStringExtra("textLocation");
                    addrDetail = data.getStringExtra("detailLocation");
                    tv_add.setText(addr+addrDetail);
                    break;

            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private class TakePhotoAsyncTask extends AsyncTask<Object, Integer, Drawable> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            showProgressBar();
        }

        @Override
        protected Drawable doInBackground(Object... params) {
            // TODO Auto-generated method stub

            try {

                // uploadTime = "" + System.currentTimeMillis();

                File dir = new File(Environment.getExternalStorageDirectory() + "/wportal");

                if (!dir.exists())
                    dir.mkdirs();

                File f = new File(dir, bringPhotoView.getFileName() + ".jpg");// localTempImgDir和localTempImageFileName是自己定义的名字

                // File f = new File(Environment.getExternalStorageDirectory() +
                // "/wportal/"
                // + S.getShare(SettingActivity.this, C.KEY_REQUEST_MEMBER_ID,
                // "") + uploadTime + ".jpg");

                imageUri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
                        f.getAbsolutePath(), null, null));

                Bitmap bm = ImageUtil.getBitmapFromUri(StoreManageActivity.this, imageUri);
                L.d(bm.getByteCount() + "frist");

                imagePath = ImageUtil.savePublishPicture(ImageUtil.comp(bm),
                        S.getShare(StoreManageActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + uploadTime);

                return new BitmapDrawable(bm);

            } catch (Exception e) {

                L.e(e);

            }

            return null;

        }

        @Override
        protected void onPostExecute(Drawable result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {

                hideProgressBar();

                new ImageUploadAsyncTask().execute();

                // ImageUtil.drawIamge(iconImageView, result);

            } catch (Exception e) {

                L.e(e);

            }

        }

    }

    private class GetPhotoFromGallaryAsyncTask extends AsyncTask<Object, Integer, Drawable> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            showProgressBar();
        }

        @Override
        protected Drawable doInBackground(Object... params) {
            // TODO Auto-generated method stub

            try {

                // uploadTime = "" + System.currentTimeMillis();

                imageUri = (Uri) params[0]; // 获得图片的uri

                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                imagePath = ImageUtil.savePublishPicture(ImageUtil.comp(bm),
                        S.getShare(StoreManageActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + uploadTime);
                return new BitmapDrawable(bm);

            } catch (Exception e) {

                L.e(e);

            }

            return null;

        }

        @Override
        protected void onPostExecute(Drawable result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {

                hideProgressBar();

                new ImageUploadAsyncTask().execute();

                // ImageUtil.drawIamge(iconImageView, result);

            } catch (Exception e) {

                L.e(e);

            }

        }

    }

    private class ImageUploadAsyncTask extends AsyncTask<Object, Integer, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            showProgressBar();
        }

        @Override
        protected String doInBackground(Object... params) {
            // TODO Auto-generated method stub

            try {

                /*
                 * if (status == "-60001") alert("컨텐츠 타입 에러"); else if (status
                 * == "-60002") alert("확장자 타입 에러"); else if (status == "-60003")
                 * alert("스트림 read  에러"); else if (status == "-60004") alert(
                 * "이미지 용량 최저용량 미만"); else if (status == "-60005") alert(
                 * "이미지 용량 최대용량 이상"); else if (status == "-60006") alert(
                 * "파일 내 이상 코드 발견");
                 */

                if (imagePath != null && !imagePath.equals("")) {

                    S.set(StoreManageActivity.this, C.KEY_SHARED_ICON_PATH, imagePath);

                    ArrayList<String> filePath = new ArrayList<String>();

                    filePath.add(imagePath);
                    Log.e("tag===", imagePath);
                    //C.BASE_UPLOAD_IMG_URL + C.STORE_IMAGE_UPLOAD_PATH
                    return FileUtil.upload("http://portal.gigawon.cn:8488/Common/FileUploader.ashx", filePath, null, "file");

                } else {

                    return "";

                }

            } catch (Exception e) {

                L.e(e);

                return e.getClass().getName();

            }

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {

                L.d(result);

                // ImageUtil.drawIamge(iconImageView,Uri.parse(C.BASE_URL +
                // C.PERSNAL_IMAGE_DOWNLOAD_PATH + "wportal" +
                // S.getShare(SettingActivity.this, C.KEY_REQUEST_MEMBER_ID, "")
                // + ".jpg"));
//C.BASE_URL + C.PERSNAL_IMAGE_DOWNLOAD_PATH
                //http://portal.gigawon.cn:8488/MediaUploader/wsProfile/
                imageDownloadUrl = C.BASE_URL + C.PERSNAL_IMAGE_DOWNLOAD_PATH + "wportal"
                        + S.getShare(StoreManageActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + uploadTime + ".jpg";
                Log.e("tag_img", imageDownloadUrl);
                ImageUtil.drawImageFromUri(imageDownloadUrl, wImageView);

                hideProgressBar();

            } catch (Exception e) {

                L.e(e);

                finish();

            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        requestSave();
    }
}
