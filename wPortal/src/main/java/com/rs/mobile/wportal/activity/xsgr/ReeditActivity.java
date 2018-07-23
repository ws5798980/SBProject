package com.rs.mobile.wportal.activity.xsgr;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.rs.mobile.common.C;
import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.image.ImageUtil;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.FileUtil;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.OnItemClickLitener;
import com.rs.mobile.wportal.adapter.xsgr.FlavorGridViewAdapter;
import com.rs.mobile.wportal.adapter.xsgr.SpecGridViewAdapter;
import com.rs.mobile.wportal.biz.xsgr.CommodityDetail;
import com.rs.mobile.wportal.biz.xsgr.PushImg;
import com.rs.mobile.wportal.biz.xsgr.QueryCategoryList;
import com.rs.mobile.wportal.biz.xsgr.SaveAndGetShelves;
import com.rs.mobile.wportal.takephoto.cutphoto.PhotoUtils;
import com.rs.mobile.wportal.view.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import listpoplibrary.ListPopWindowManager;
import okhttp3.Request;

public class ReeditActivity extends BaseActivity {

    private TextView cancel, save, textSpec;
    private ImageView back;
    private LinearLayout changeImg;
    private RelativeLayout goodsImg;
    private ImageView ivPhoto;
    private EditText editName;
    private LinearLayout select;
    private GridView specification;
    private GridView flavor;
    private SpecGridViewAdapter specGridViewAdapter;
    private FlavorGridViewAdapter flavorGridViewAdapter;
    private List<CommodityDetail.DataBean.SpecBean> mList = new ArrayList<>();
    private List<CommodityDetail.DataBean.FlavorBean> mFlavorList = new ArrayList<>();
    private View contentView;
    private RecyclerView recyclerView;
    private MyPopupWinAdapter popAdapter;
    CommodityDetail commodityDetail;
    private PopupWindow popupWindow;
    private PopupWindow mPopWindow;
    private PhotoUtils photoUtils;
    public String imageBase64 = "";
    private List<QueryCategoryList.DataBean> mData = new ArrayList<>();
    private String groupId;
    private static AlertDialog editDialog;
    private String imagePath;
    private String item_level1;
    private String uploadTime,imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reedit);
        initView();
        initListener();
        initData();
    }

    /**
     * SHA加密
     *
     * @param strSrc 明文
     * @return 加密之后的密文
     */
    public static String shaEncrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");// 将此换成SHA-1、SHA-512、SHA-384等参数
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts 数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    private void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        groupId = bundle.getString("groupId");
        cancel = (TextView) findViewById(R.id.tv_cancel);
        back = (ImageView) findViewById(R.id.branch_btn);
        changeImg = (LinearLayout) findViewById(R.id.change_img);
        goodsImg = (RelativeLayout) findViewById(R.id.goods_img);
        editName = (EditText) findViewById(R.id.edit_name);
        select = (LinearLayout) findViewById(R.id.layout_select);
        specification = (GridView) findViewById(R.id.specifications);
        flavor = (GridView) findViewById(R.id.flavor);
        ivPhoto = (ImageView) findViewById(R.id.iv_photo);
        save = (TextView) findViewById(R.id.save_up);
        textSpec = (TextView) findViewById(R.id.tv_select);
    }

    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D.showDialog(ReeditActivity.this, -1, getResources().getString(R.string.title_promote), getResources().getString(R.string.cancel_edit), getResources().getString(R.string.button_sure), new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        D.alertDialog.dismiss();
                        finish();

                    }
                }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        D.alertDialog.dismiss();
                    }
                });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D.showDialog(ReeditActivity.this, -1, getResources().getString(R.string.title_promote), getResources().getString(R.string.cancel_edit), getResources().getString(R.string.button_sure), new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        D.alertDialog.dismiss();
                        finish();

                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D.showDialog(ReeditActivity.this, -1, getResources().getString(R.string.title_promote), getResources().getString(R.string.save_edit), getResources().getString(R.string.button_sure), new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        requestUpData(commodityDetail);
                        D.alertDialog.dismiss();

                    }
                }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        D.alertDialog.dismiss();
                    }
                });
            }
        });
        changeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWin();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestProductByGid(groupId);
            }
        });

    }

    private void requestProductByGid(String groupId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("lang_type", "kor");
        params.put("token", S.getShare(ReeditActivity.this, C.KEY_JSON_TOKEN, ""));
        params.put("custom_code", S.getShare(ReeditActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
        params.put("CategoryId", "-1");

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                QueryCategoryList entity = GsonUtils.changeGsonToBean(responseDescription, QueryCategoryList.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(entity.getStatus())) {
                    if (entity.getData() != null && entity.getData().size() > 0) {
                        if (mData.size() > 0) {
                            mData.clear();
                        }
                        popupWindow = ListPopWindowManager.getInstance().showMyPopWindow(contentView, select,
                                ReeditActivity.this, false, ViewGroup.LayoutParams.WRAP_CONTENT);
                        mData.addAll(entity.getData());
                        recyclerView.getAdapter().notifyDataSetChanged();
                    } else {
                    }

                } else {
                    Toast.makeText(ReeditActivity.this, entity.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {

            }

            @Override
            public void onNetworkError(Request request, IOException e) {

            }
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_QUERYCATEGORY, GsonUtils.createGsonString(params));
    }

    static public final int REQUEST_CODE_ASK_PERMISSIONS = 101;

    private void showSelectPopWin() {
        //设置contentView
        uploadTime = "" + System.currentTimeMillis();
        View contentView = LayoutInflater.from(ReeditActivity.this).inflate(R.layout.popup_take_photo_layout, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置各个控件的点击响应
        RelativeLayout tv1 = (RelativeLayout) contentView.findViewById(R.id.take_photo);
        RelativeLayout tv2 = (RelativeLayout) contentView.findViewById(R.id.choose_photo);
        RelativeLayout tv3 = (RelativeLayout) contentView.findViewById(R.id.cancel_pop);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkPermission = checkSelfPermission(Manifest.permission.CAMERA);
                    if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
                        } else {
                            new AlertDialog.Builder(ReeditActivity.this)
                                    .setMessage(getResources().getString(R.string.common_text115))
                                    .setPositiveButton(getResources().getString(R.string.button_sure), new DialogInterface.OnClickListener() {
                                        //                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
                                        }
                                    })
                                    .setNegativeButton(getResources().getString(R.string.button_cancel), null)
                                    .create().show();
                        }
                        return;
                    }
                }
                // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //  startActivityForResult(intent, 1);
                photoUtils.takePicture(ReeditActivity.this);
                mPopWindow.dismiss();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoUtils.selectPicture(ReeditActivity.this);
                mPopWindow.dismiss();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(ReeditActivity.this).inflate(R.layout.activity_reedit, null);
        mPopWindow.setAnimationStyle(R.style.take_photo_anim);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }


    private void initData() {
        requestDetail(groupId);

        specGridViewAdapter = new SpecGridViewAdapter(this, mList);
        specification.setAdapter(specGridViewAdapter);
        specification.setOnItemClickListener(new SpecItemClickListener());
        specification.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (mList.size() != position) {
                    if ("".equals(mList.get(position).getItem_code())) {
                        D.showDialog(ReeditActivity.this, -1, getResources().getString(R.string.title_promote), getResources().getString(R.string.del_spec), getResources().getString(R.string.button_sure), new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                D.alertDialog.dismiss();
                                mList.remove(position);
                                specGridViewAdapter.notifyDataSetChanged();
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                    } else {
                        D.showDialog(ReeditActivity.this, -1, getResources().getString(R.string.title_promote), getResources().getString(R.string.del_spec), getResources().getString(R.string.button_sure), new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                D.alertDialog.dismiss();
                                requestDelSpec(mList.get(position).getItem_code(), position);
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                    }
                }
                return false;
            }
        });
        flavorGridViewAdapter = new FlavorGridViewAdapter(this, mFlavorList);
        flavor.setAdapter(flavorGridViewAdapter);
        flavor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mFlavorList.size() == position) {
                    D.showEditTextDialog(ReeditActivity.this, -1, "", getResources().getString(R.string.flavor_text2), "",
                            getResources().getString(R.string.button_sure), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String flavor = D.editText.getText().toString().trim();
                                    if ("".equals(flavor)) {
                                        Toast.makeText(ReeditActivity.this, getResources().getString(R.string.add_flavor), Toast.LENGTH_SHORT).show();
                                    } else {
                                        CommodityDetail.DataBean.FlavorBean flavorBean = new CommodityDetail.DataBean.FlavorBean();
                                        flavorBean.setFlavorName(flavor);
                                        flavorBean.setAdd("add");
                                        mFlavorList.add(flavorBean);
                                        flavorGridViewAdapter.notifyDataSetChanged();
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
            }
        });
        flavor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (mFlavorList.size() != position) {
                    if (!"".equals(mFlavorList.get(position).getAdd())) {
                        D.showDialog(ReeditActivity.this, -1, getResources().getString(R.string.title_promote), "", getResources().getString(R.string.button_sure), new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                D.alertDialog.dismiss();
                                mFlavorList.remove(position);
                                flavorGridViewAdapter.notifyDataSetChanged();
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                    } else {
                        D.showDialog(ReeditActivity.this, -1, getResources().getString(R.string.title_promote), getResources().getString(R.string.del_flavor), getResources().getString(R.string.button_sure), new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                D.alertDialog.dismiss();
                                requestDelFlavor(mFlavorList.get(position).getFlavorName(), position);
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                    }
                }
                return false;
            }
        });
        contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.commodity_type_all, null, false);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, R.drawable.divide_bg));
        popAdapter = new MyPopupWinAdapter(getApplicationContext(), mData);
        popAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                textSpec.setText(mData.get(position).getLevel_name());
                item_level1 = mData.get(position).getId();
                popupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(popAdapter);

        photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onPhotoResult(Uri uri) {
                if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                    imageBase64 = bitmapToString(uri);
                    imagePath = uri.getPath().toString();
                    Log.e("uri:", uri.getPath().toString());
                    Log.e("uri2:", imageBase64);
//                    String itemId=setUserName.getText().toString();
//                    Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath().toString());
//                    ivPhoto.setImageBitmap(bitmap);
                    new ImageUploadAsyncTask().execute();
                }
            }

            @Override
            public void onPhotoCancel() {

            }
        });
    }

    private class ImageUploadAsyncTask extends AsyncTask<Object, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgressBar();
        }

        @Override
        protected String doInBackground(Object... params) {

            try {

                if (imagePath != null && !imagePath.equals("")) {

                    // S.set(SettingActivity.this, C.KEY_SHARED_ICON_PATH, imagePath);

                    ArrayList<String> filePath = new ArrayList<String>();

                    filePath.add(imagePath);

                    return FileUtil.upload(Constant.XSGR_TEST_URL + Constant.COMMODITY_PUSHIMG, filePath, null, "file");

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
            super.onPostExecute(result);

            try {

                L.d(result);

                // ImageUtil.drawIamge(iconImageView,Uri.parse(C.BASE_URL +
                // C.PERSNAL_IMAGE_DOWNLOAD_PATH + "wportal" +
                // S.getShare(SettingActivity.this, C.KEY_REQUEST_MEMBER_ID, "")
                // + ".jpg"));

//                imageDownloadUrl = C.BASE_URL + C.PERSNAL_IMAGE_DOWNLOAD_PATH + "wportal"
//                        + S.getShare(SettingActivity.this, C.KEY_REQUEST_MEMBER_ID, "") + uploadTime + ".jpg";
                PushImg entity = GsonUtils.changeGsonToBean(result, PushImg.class);
                if ("1".equals(entity.getStatus())) {
                    Toast.makeText(ReeditActivity.this, "success", Toast.LENGTH_SHORT).show();
                    if (entity.getData().size() > 0) {
                        //+ Constant.COMMODITY_PUSHIMG
                        imgUrl = Constant.XSGR_TEST_URL  + entity.getData().get(0);
                        Log.i("url++++", imgUrl);
                        Glide.with(ReeditActivity.this).load(imgUrl).into(ivPhoto);
                    } else {
                        Toast.makeText(ReeditActivity.this, "failed", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ReeditActivity.this, "failed", Toast.LENGTH_LONG).show();

                }


                hideProgressBar();

            } catch (Exception e) {

                L.e(e);

                finish();

            }

        }

    }

    private void requestDelFlavor(String flavorName, final int position) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("lang_type", "kor");
        params.put("token", S.getShare(ReeditActivity.this, C.KEY_JSON_TOKEN, ""));
        params.put("custom_code", S.getShare(ReeditActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
        params.put("groupId", groupId);
        params.put("flavorName", flavorName);

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                CommodityDetail entity = GsonUtils.changeGsonToBean(responseDescription, CommodityDetail.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(entity.getStatus())) {
                    Toast.makeText(ReeditActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                    mFlavorList.remove(position);
                    flavorGridViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ReeditActivity.this, entity.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                Log.e("responseDescription456", responseDescription);
//                Log.e("JSONObject",data.toString());
                Log.e("flag145", flag);

            }

            @Override
            public void onNetworkError(Request request, IOException e) {

            }
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_DELFLAVOR, GsonUtils.createGsonString(params));
    }

    private void requestDelSpec(String item_code, final int position) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("lang_type", "kor");
        params.put("token", S.getShare(ReeditActivity.this, C.KEY_JSON_TOKEN, ""));
        params.put("custom_code", S.getShare(ReeditActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
        params.put("groupId", groupId);
        params.put("item_code", item_code);

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                CommodityDetail entity = GsonUtils.changeGsonToBean(responseDescription, CommodityDetail.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(entity.getStatus())) {
                    Toast.makeText(ReeditActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                    mList.remove(position);
                    specGridViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ReeditActivity.this, entity.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                Log.e("responseDescription456", responseDescription);
//                Log.e("JSONObject",data.toString());
                Log.e("flag145", flag);

            }

            @Override
            public void onNetworkError(Request request, IOException e) {

            }
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_DELSPEC, GsonUtils.createGsonString(params));
    }

    private void requestDetail(String groupId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("lang_type", "kor");
        params.put("token", S.getShare(ReeditActivity.this, C.KEY_JSON_TOKEN, ""));
        params.put("custom_code", S.getShare(ReeditActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
        params.put("groupId", groupId);

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                commodityDetail = GsonUtils.changeGsonToBean(responseDescription, CommodityDetail.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(commodityDetail.getStatus())) {
                    Toast.makeText(ReeditActivity.this, commodityDetail.getMessage(), Toast.LENGTH_SHORT).show();
                    item_level1 = commodityDetail.getData().getItem().getITEM_LEVEL1();
                    mFlavorList.addAll(commodityDetail.getData().getFlavor());
                    flavorGridViewAdapter.notifyDataSetChanged();
                    mList.addAll(commodityDetail.getData().getSpec());
                    specGridViewAdapter.notifyDataSetChanged();
                    editName.setText(commodityDetail.getData().getItem().getITEM_NAME());
                    imgUrl = commodityDetail.getData().getItem().getImage_url();
                    Glide.with(ReeditActivity.this).load(commodityDetail.getData().getItem().getImage_url()).into(ivPhoto);
                } else {
                    Toast.makeText(ReeditActivity.this, commodityDetail.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                Log.e("responseDescription456", responseDescription);
//                Log.e("JSONObject",data.toString());
                Log.e("flag145", flag);

            }

            @Override
            public void onNetworkError(Request request, IOException e) {

            }
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_QUERYDETAIL, GsonUtils.createGsonString(params));
    }

    private void requestUpData(final CommodityDetail commodityDetail) {
        if (commodityDetail == null) {
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("lang_type", "kor");
        params.put("token", S.getShare(ReeditActivity.this, C.KEY_JSON_TOKEN, ""));
        params.put("custom_code", S.getShare(ReeditActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
        params.put("groupId", groupId);
        params.put("Image_url",imgUrl);
        String name = editName.getText().toString().trim();
        if (name.length() == 0) {
            Toast.makeText(ReeditActivity.this, getResources().getString(R.string.add_goods_name), Toast.LENGTH_SHORT).show();
            return;
        }
        params.put("item_name", name);
        params.put("custom_item_code", commodityDetail.getData().getItem().getCUSTOM_ITEM_CODE());
        params.put("custom_item_name", commodityDetail.getData().getItem().getCUSTOM_ITEM_NAME());
        params.put("custom_item_spec", commodityDetail.getData().getItem().getCUSTOM_ITEM_SPEC());
        params.put("dom", commodityDetail.getData().getItem().getDom_forign());
        params.put("item_level1", item_level1);
//        params.put("item_level2", "2");
//        params.put("item_level3", "3");
        params.put("price", commodityDetail.getData().getItem().getITEM_P());
        List<SaveAndGetShelves.SpecBean> specBeans = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            SaveAndGetShelves.SpecBean specBean = new SaveAndGetShelves.SpecBean();
            specBean.setSpecName(mList.get(i).getSpecName());
            specBean.setSpecPrice(mList.get(i).getSpecPrice());
            specBean.setItem_code(mList.get(i).getItem_code()+"");
            specBeans.add(specBean);
        }
        params.put("spec", specBeans);
        List<SaveAndGetShelves.FlavorBean> flavorBeans = new ArrayList<>();
        for (int i = 0; i < mFlavorList.size(); i++) {
            SaveAndGetShelves.FlavorBean flavorBean = new SaveAndGetShelves.FlavorBean();
            flavorBean.setFlavorName(mFlavorList.get(i).getFlavorName());
            flavorBeans.add(flavorBean);
        }
        params.put("Flavor", flavorBeans);
        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                CommodityDetail response = GsonUtils.changeGsonToBean(responseDescription, CommodityDetail.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(response.getStatus())) {
                    Toast.makeText(ReeditActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ReeditActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                Log.e("responseDescription456", responseDescription);
//                Log.e("JSONObject",data.toString());
                Log.e("flag145", flag);

            }

            @Override
            public void onNetworkError(Request request, IOException e) {

            }
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_UPDATA, GsonUtils.createGsonString(params));
    }

    class SpecItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mList.size() == position) {
                showDoubleEditTextDialog(ReeditActivity.this, -1, "", "", "", getResources().getString(R.string.button_sure), getResources().getString(R.string.button_cancel));
            }
        }
    }

    /**
     * showDialog
     *
     * @param context
     * @param img
     * @param title
     * @param msg1
     * @param selectText
     */
    public void showDoubleEditTextDialog(Context context, int img, String title, String msg1, String msg2,
                                         String selectText, String cancelText) {

        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(R.layout.dialog_2edit_text, null);
            ImageView icon = (ImageView) v.findViewById(R.id.icon_view);
            TextView titleTextView = (TextView) v.findViewById(R.id.title_text_view);
            final EditText editText1 = (EditText) v.findViewById(R.id.msg_edit_text1);
            final EditText editText2 = (EditText) v.findViewById(R.id.msg_edit_text2);
            TextView selectTextView = (TextView) v.findViewById(R.id.ok_text_view);
            TextView cancelTextView = (TextView) v.findViewById(R.id.cancel_text_view);
            if (img == -1) icon.setVisibility(View.GONE);
            else icon.setBackgroundResource(img);
            titleTextView.setText(title);
            editText1.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                editText2.requestFocus();
                            }
                            break;
                    }
                    return false;
                }
            });
            if (msg1 != null && !msg1.equals("")) {
                editText1.setText(msg1);
            }
            if (msg2 != null && !msg2.equals("")) {
                editText2.setText(msg2);
            }
            selectTextView.setText(selectText);
            cancelTextView.setText(cancelText);
            selectTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = editText1.getText().toString().trim();
                    String price = editText2.getText().toString().trim();
                    if (name.length() == 0 || price.length() == 0) {
                        Toast.makeText(ReeditActivity.this, getResources().getString(R.string.add_nameandprice), Toast.LENGTH_SHORT).show();
                    } else {
                        if (isNumeric(price)) {
                            CommodityDetail.DataBean.SpecBean specBean = new CommodityDetail.DataBean.SpecBean();
                            specBean.setItem_name(name);
                            specBean.setItem_p(price);
                            specBean.setSpecName(name);
                            specBean.setSpecPrice(price);
                            mList.add(specBean);
                            specGridViewAdapter.notifyDataSetChanged();
                            editDialog.dismiss();
                        } else {
                            Toast.makeText(ReeditActivity.this, getResources().getString(R.string.price_isnum), Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });
            cancelTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editDialog.dismiss();
                }
            });
            builder.setView(v);
            builder.setCancelable(true);

            editDialog = builder.create();
            editDialog.show();

        } catch (Exception e) {

            L.e(e);

        }

    }


    public class MyPopupWinAdapter extends RecyclerView.Adapter<MyPopupWinAdapter.ListViewHolder> {
        private Context context;
        private OnItemClickLitener mOnItemClickLitener;
        private List<QueryCategoryList.DataBean> mdatas;

        public MyPopupWinAdapter(Context context, List<QueryCategoryList.DataBean> datas) {
            this.context = context;
            this.mdatas = datas;
        }

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            View view = mInflater.inflate(R.layout.pop_spec_item, parent,
                    false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ListViewHolder holder, int position) {
            QueryCategoryList.DataBean type = mdatas.get(position);
            holder.setData(type);
            if (mOnItemClickLitener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mdatas != null ? mdatas.size() : 0;
        }

        public class ListViewHolder extends RecyclerView.ViewHolder {
            private TextView name;
            private ImageView img;

            public ListViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.pop_name);
                img = (ImageView) itemView.findViewById(R.id.img_sure);
            }

            public void setData(QueryCategoryList.DataBean type) {
                name.setText(type.getLevel_name());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtils.INTENT_CROP:
            case PhotoUtils.INTENT_TAKE:
            case PhotoUtils.INTENT_SELECT:
                photoUtils.onActivityResult(ReeditActivity.this, requestCode, resultCode, data);
                break;

        }
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //把bitmap转换成String
    public static String bitmapToString(Uri filePath) {
        Bitmap bm = getSmallBitmap(filePath.getPath().toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //1.5M的压缩后在100Kb以内，测试得值,压缩后的大小=94486字节,压缩后的大小=74473字节
        //这里的JPEG 如果换成PNG，那么压缩的就有600kB这样
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * 将图片转换成Base64编码的字符串
     *
     * @param path
     * @return base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            System.out.println(chr);
            if (chr < 48 || chr > 57) {
                if (chr != 46)
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            D.showDialog(ReeditActivity.this, -1, getResources().getString(R.string.title_promote), getResources().getString(R.string.cancel_edit), getResources().getString(R.string.button_sure), new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    D.alertDialog.dismiss();
                    finish();

                }
            }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    D.alertDialog.dismiss();
                }
            });
        }
        return true;
    }
}
