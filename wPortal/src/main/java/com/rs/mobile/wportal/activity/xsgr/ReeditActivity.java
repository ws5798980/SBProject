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
import android.os.Build;
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

import com.rs.mobile.common.D;
import com.rs.mobile.common.L;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.FlavorGridViewAdapter;
import com.rs.mobile.wportal.adapter.xsgr.SpecGridViewAdapter;
import com.rs.mobile.wportal.biz.xsgr.CommodityDetail;
import com.rs.mobile.wportal.biz.xsgr.QueryCategoryList;
import com.rs.mobile.wportal.takephoto.cutphoto.PhotoUtils;
import com.rs.mobile.wportal.view.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

    private TextView cancel, save;
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
    private PopupWindow popupWindow;
    private PopupWindow mPopWindow;
    private PhotoUtils photoUtils;
    public String imageBase64 = "";
    private List<QueryCategoryList.DataBean> mData = new ArrayList<>();
    private String groupId;
    private static AlertDialog editDialog;

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
    }

    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D.showDialog(ReeditActivity.this, -1, "提示", "저장하기？", "确定", new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
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
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
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
                                    .setMessage("您需要在设置里打开相机权限。")
                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        //                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .create().show();
                        }
                        return;
                    }
                }
                // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //  startActivityForResult(intent, 1);
                photoUtils.takePicture(ReeditActivity.this);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoUtils.selectPicture(ReeditActivity.this);
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
                        D.showDialog(ReeditActivity.this, -1, "提示", "確定删除此规格？", "确定", new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                D.alertDialog.dismiss();
                                mList.remove(position);
                                specGridViewAdapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        D.showDialog(ReeditActivity.this, -1, "提示", "確定删除此规格？", "确定", new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                D.alertDialog.dismiss();
                                requestDelSpec(mList.get(position).getItem_code(), position);
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
                    D.showEditTextDialog(ReeditActivity.this, -1, "", "구미", "",
                            "确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String flavor = D.editText.getText().toString().trim();
                                    if ("".equals(flavor)) {
                                        Toast.makeText(ReeditActivity.this, "써 주세요 맛", Toast.LENGTH_SHORT).show();
                                    } else {
                                        CommodityDetail.DataBean.FlavorBean flavorBean = new CommodityDetail.DataBean.FlavorBean();
                                        flavorBean.setFlavorName(flavor);
                                        flavorBean.setAdd("add");
                                        mFlavorList.add(flavorBean);
                                        flavorGridViewAdapter.notifyDataSetChanged();
                                    }
                                }
                            }, "取消", new View.OnClickListener() {
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
                        D.showDialog(ReeditActivity.this, -1, "提示", "確定删除此口味？", "确定", new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                D.alertDialog.dismiss();
                                mFlavorList.remove(position);
                                flavorGridViewAdapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        D.showDialog(ReeditActivity.this, -1, "提示", "確定删除此口味？", "确定", new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                D.alertDialog.dismiss();
                                requestDelFlavor(mFlavorList.get(position).getFlavorName(), position);
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
        recyclerView.setAdapter(popAdapter);

        photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onPhotoResult(Uri uri) {
                if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                    imageBase64 = bitmapToString(uri);
                    Log.e("uri:", uri.getPath().toString());
                    Glide.with(ReeditActivity.this).load(uri.getPath()).into(ivPhoto);
//                    String itemId=setUserName.getText().toString();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");// HH:mm:ss
//获取当前时间
                    Date date2 = new Date(System.currentTimeMillis());
                    String date = simpleDateFormat.format(date2);
//
//                    String image=imageBase64;
//                    String type="ImageTarget";
//                    String name="ar_shop_"+itemId;
//                    String size="10";
//                    String meta=itemId;


//                    String signstr="appKey"+KEY_APP_KEY+"date"+date+"image"+imageBase64+"meta"+itemId+"name"+name+"size"+size+"type"+type+KEY_SIGNATURE;
//                    String sign=shaEncrypt(signstr);
//
//
//                    AsyncHttpClient client = new AsyncHttpClient();
//                    client.setTimeout(120000);
//
//                    JSONObject params = new JSONObject();
//
//
//                    try {
//                        params.put("appKey",KEY_APP_KEY);
//                        params.put("date",date);
//                        params.put("image",imageBase64);
//                        params.put("meta",itemId);
//                        params.put("name",name);
//                        params.put("size",size);
//                        params.put("type",type);
//                        params.put("signature",sign);
//                    } catch (JSONException e) {
//
//                    }

//                    ByteArrayEntity entity = null;
//                    try {
//                        entity = new ByteArrayEntity(params.toString().getBytes("UTF-8"));
//                        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }

//                    client.post(LoginActivity.this,"http://487ef0c87805d5e4a4b4f56a23db629d.cn1.crs.easyar.com:8888/targets/",entity,"application/json",new JsonHttpResponseHandler(){
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                            super.onSuccess(statusCode, headers, response);
//
//                            AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this)
//                                    .setTitle("提示")//设置对话框的标题
//                                    .setMessage("成功将图片上传到云端")//设置对话框的内容
//                                    //设置对话框的按钮
//                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    }).create();
//                            dialog.show();
//
//                        }
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                            super.onFailure(statusCode, headers, throwable, errorResponse);
//                        }
//                    });


                }
            }

            @Override
            public void onPhotoCancel() {

            }
        });
    }

    private void requestDelFlavor(String flavorName, final int position) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
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
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
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
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
        params.put("groupId", groupId);

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                CommodityDetail entity = GsonUtils.changeGsonToBean(responseDescription, CommodityDetail.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(entity.getStatus())) {
                    Toast.makeText(ReeditActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                    mFlavorList = entity.getData().getFlavor();
                    flavorGridViewAdapter.notifyDataSetChanged();
                    mList.addAll(entity.getData().getSpec());
                    specGridViewAdapter.notifyDataSetChanged();
                    editName.setText(entity.getData().getItem().getITEM_NAME());
                    Glide.with(ReeditActivity.this).load(entity.getData().getItem().getImage_url()).into(ivPhoto);
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
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_QUERYDETAIL, GsonUtils.createGsonString(params));
    }

    class SpecItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mList.size() == position) {
                showDoubleEditTextDialog(ReeditActivity.this, -1, "", "", "", "确定", "取消");
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
                        Toast.makeText(ReeditActivity.this, "명칭 과 가격 을 기입해 주십시오", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isNumeric(price)) {
                            CommodityDetail.DataBean.SpecBean specBean = new CommodityDetail.DataBean.SpecBean();
                            specBean.setItem_name(name);
                            specBean.setItem_p(price);
                            mList.add(specBean);
                            Log.e("mList.size()", mList.size() + "");
                            specGridViewAdapter.notifyDataSetChanged();
                            editDialog.dismiss();
                        } else {
                            Toast.makeText(ReeditActivity.this, "가격은 반드시 숫자입니다", Toast.LENGTH_SHORT).show();
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
        private List<QueryCategoryList.DataBean> mdatas;

        public MyPopupWinAdapter(Context context, List<QueryCategoryList.DataBean> datas) {
            this.context = context;
            this.mdatas = datas;
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            View view = mInflater.inflate(R.layout.pop_spec_item, parent,
                    false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            QueryCategoryList.DataBean type = mdatas.get(position);
            holder.setData(type);

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

}
