package com.rs.mobile.wportal.activity.xsgr;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
import com.rs.mobile.common.util.Util;
import com.rs.mobile.common.view.BringPhotoView;
import com.rs.mobile.common.view.WImageView;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.market.util.CheckBankCard;
import com.rs.mobile.wportal.biz.xsgr.AddressSearch;
import com.rs.mobile.wportal.entity.Cate1Type;
import com.rs.mobile.wportal.entity.ExpressList;
import com.rs.mobile.wportal.entity.ListPopBean;
import com.rs.mobile.wportal.entity.MyShopInfoBean;
import com.rs.mobile.wportal.entity.OtherInfoNew;
import com.rs.mobile.wportal.entity.ShopMannageBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class StoreManageActivity extends BaseActivity implements View.OnClickListener {
    ShopMannageBean bean;
    TextView tv_name, tv_phone, tv_add, tv_czh_phone, tv_url, tv_type1, tv_type2,
            tv_zhizhao_no, tv_faren_no, tv_jy_type, tv_is_shui, tv_ht_state, tv_people,
            tv_mobile_phone, tv_settlement, tv_logistics, tv_after_sales, tv_refunds;
    WImageView wImageView;
    private PopupWindow popupWindow;
    private RelativeLayout bringPhothLayout;
    private boolean mIsShowing = false;
    private int index = 0;
    private MyPopupEditAdapter popAdapter;
    private String uploadTime;
    private String zipCode, addr, addrDetail, zipCode2, addr2, addrDetail2;
    private BringPhotoView bringPhotoView;
    private PopupWindow mPopWindow;
    private String imageDownloadUrl = "";
    private Uri imageUri;
    private String imagePath = "";
    private RelativeLayout shopName, telPhone, positionInfo, czLayout, webSiteLayout,
            zhizhaoLayout, farenLayout, jyLayout, shuiLayout, hetongLayout, peopleLayout,
            mobileLayout, settlementLayout, logisticsLayout, afterLayout, refundsLayout;
    private LinearLayout layoutType1, layoutType2;
    private List<ListPopBean> list = new ArrayList<>();
    private String code1, code2, code3, regiGubun, key1, key2, key3, contract_date, bankCard, holder,
            bank, from_site, from_site_code, md_id, md_rate, ceoNo2, ceoNo3, supplierCode, makerCode;
//    private List<ListPopBean> list2 = new ArrayList<>();
//    private List<ListPopBean> list3 = new ArrayList<>();

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
                bundle.putString("zipCode", zipCode);
                bundle.putString("addr", addr);
                bundle.putString("addrDetail", addrDetail);
                intent.putExtras(bundle);
                intent.setClass(StoreManageActivity.this, LocationChangeActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        czLayout.setOnClickListener(this);
        webSiteLayout.setOnClickListener(this);
        zhizhaoLayout.setOnClickListener(this);
        farenLayout.setOnClickListener(this);
        jyLayout.setOnClickListener(this);
        shuiLayout.setOnClickListener(this);
        hetongLayout.setOnClickListener(this);
        peopleLayout.setOnClickListener(this);
        mobileLayout.setOnClickListener(this);
        settlementLayout.setOnClickListener(this);
        logisticsLayout.setOnClickListener(this);
        afterLayout.setOnClickListener(this);
        refundsLayout.setOnClickListener(this);
        layoutType1.setOnClickListener(this);
        layoutType2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_chuanzhen_no:
                D.showEditTextDialog(StoreManageActivity.this, -1, "", getResources().getString(R.string.text_chuanzhen), "",
                        getResources().getString(R.string.button_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = D.editText.getText().toString().trim();
                                if ("".equals(name)) {
                                    D.alertDialog.dismiss();
                                } else {
                                    tv_czh_phone.setText(name);
                                    D.alertDialog.dismiss();
                                }
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                break;
            case R.id.company_url:
                D.showEditTextDialog(StoreManageActivity.this, -1, "", getResources().getString(R.string.text_web), "",
                        getResources().getString(R.string.button_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = D.editText.getText().toString().trim();
                                if ("".equals(name)) {
                                    D.alertDialog.dismiss();
                                } else {
                                    tv_url.setText(name);
                                    D.alertDialog.dismiss();
                                }
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                break;
            case R.id.company_zhizhao:
                D.showEditTextDialog(StoreManageActivity.this, -1, "", getResources().getString(R.string.text_zhizhao), "",
                        getResources().getString(R.string.button_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = D.editText.getText().toString().trim();
                                if ("".equals(name)) {
                                    D.alertDialog.dismiss();
                                } else {
                                    tv_zhizhao_no.setText(name);
                                    D.alertDialog.dismiss();
                                }
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                break;
            case R.id.company_faren:
                D.showEditTextDialog(StoreManageActivity.this, -1, "", getResources().getString(R.string.text_faren), "",
                        getResources().getString(R.string.button_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = D.editText.getText().toString().trim();
                                if ("".equals(name)) {
                                    D.alertDialog.dismiss();
                                } else {
                                    tv_faren_no.setText(name);
                                    D.alertDialog.dismiss();
                                }
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                break;
            case R.id.company_jy:
                showSelectPopWin1();
                break;
            case R.id.company_shui:
                showSelectPopWin2();
                break;
            case R.id.hetong_state:
                showSelectPopWin3();
                break;
            case R.id.layout_people:
                D.showEditTextDialog(StoreManageActivity.this, -1, "", getResources().getString(R.string.text_people), "",
                        getResources().getString(R.string.button_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = D.editText.getText().toString().trim();
                                if ("".equals(name)) {
                                    D.alertDialog.dismiss();
                                } else {
                                    tv_people.setText(name);
                                    D.alertDialog.dismiss();
                                }
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                break;
            case R.id.layout_mobile_phone:
                D.showEditTextDialog(StoreManageActivity.this, -1, "", getResources().getString(R.string.text_mobile), "",
                        getResources().getString(R.string.button_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = D.editText.getText().toString().trim();
                                if ("".equals(name)) {
                                    D.alertDialog.dismiss();
                                } else {
                                    tv_mobile_phone.setText(name);
                                    D.alertDialog.dismiss();
                                }
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                break;
            case R.id.layout_settlement:
                D.show3EditTextDialog(StoreManageActivity.this, -1, "", getResources().getString(R.string.card_owen_hint), "", "", "",
                        getResources().getString(R.string.button_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                holder = D.editText.getText().toString().trim();
                                bank = D.editText2.getText().toString().trim();
                                bankCard = D.editText3.getText().toString().trim();
                                String name3 = bankCard;
                                if ("".equals(holder) && "".equals(name3)) {
                                    D.alertDialog.dismiss();
                                    Toast.makeText(StoreManageActivity.this, R.string.cardno_promit, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!CheckBankCard.checkBankCard(name3)) {
                                        Toast.makeText(StoreManageActivity.this, R.string.cardno_promit, Toast.LENGTH_SHORT).show();
                                    } else {
//                                        String startNo = name3.substring(0, 2);
                                        String endNo = name3.substring(12);
                                        name3 = ("****" + endNo);
                                        tv_settlement.setText(holder + " " + bank + " " + name3);
                                    }

                                    D.alertDialog.dismiss();
                                }
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                break;
            case R.id.layout_logistics:
                index = 3;
                initExpressInfoData();
                break;
            case R.id.layout_after_sales:
                D.showEditTextDialog(StoreManageActivity.this, -1, "", getResources().getString(R.string.text_after_sales), "",
                        getResources().getString(R.string.button_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = D.editText.getText().toString().trim();
                                if ("".equals(name)) {
                                    D.alertDialog.dismiss();
                                } else {
                                    tv_after_sales.setText(name);
                                    D.alertDialog.dismiss();
                                }
                            }
                        }, getResources().getString(R.string.button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                D.alertDialog.dismiss();
                            }
                        });
                break;
            case R.id.position_refunds:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("zipCode", zipCode2 + "");
                bundle.putString("addr", addr2 + "");
                bundle.putString("addrDetail", addrDetail2 + "");
                intent.putExtras(bundle);
                intent.setClass(StoreManageActivity.this, LocationChangeActivity2.class);
                startActivityForResult(intent, 2000);
                break;
            case R.id.layout_type1:
                index = 1;
                initTypeInfoData1();
                break;
            case R.id.layout_type2:
                index = 2;
                initTypeInfoData2();
                break;

        }
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
        czLayout = (RelativeLayout) findViewById(R.id.layout_chuanzhen_no);
        webSiteLayout = (RelativeLayout) findViewById(R.id.company_url);
        zhizhaoLayout = (RelativeLayout) findViewById(R.id.company_zhizhao);
        farenLayout = (RelativeLayout) findViewById(R.id.company_faren);
        jyLayout = (RelativeLayout) findViewById(R.id.company_jy);
        shuiLayout = (RelativeLayout) findViewById(R.id.company_shui);
        hetongLayout = (RelativeLayout) findViewById(R.id.hetong_state);
        peopleLayout = (RelativeLayout) findViewById(R.id.layout_people);
        mobileLayout = (RelativeLayout) findViewById(R.id.layout_mobile_phone);
        settlementLayout = (RelativeLayout) findViewById(R.id.layout_settlement);
        logisticsLayout = (RelativeLayout) findViewById(R.id.layout_logistics);
        afterLayout = (RelativeLayout) findViewById(R.id.layout_after_sales);
        refundsLayout = (RelativeLayout) findViewById(R.id.position_refunds);
        layoutType1 = (LinearLayout) findViewById(R.id.layout_type1);
        layoutType2 = (LinearLayout) findViewById(R.id.layout_type2);
        tv_czh_phone = (TextView) findViewById(R.id.tv_czh_phone);
        tv_url = (TextView) findViewById(R.id.tv_url);
        tv_type1 = (TextView) findViewById(R.id.tv_type1);
        tv_type2 = (TextView) findViewById(R.id.tv_type2);
        tv_zhizhao_no = (TextView) findViewById(R.id.tv_zhizhao_no);
        tv_faren_no = (TextView) findViewById(R.id.tv_faren_no);
        tv_jy_type = (TextView) findViewById(R.id.tv_jy_type);
        tv_is_shui = (TextView) findViewById(R.id.tv_is_shui);
        tv_ht_state = (TextView) findViewById(R.id.tv_ht_state);
        tv_people = (TextView) findViewById(R.id.tv_people);
        tv_mobile_phone = (TextView) findViewById(R.id.tv_mobile_phone);
        tv_settlement = (TextView) findViewById(R.id.tv_settlement);
        tv_logistics = (TextView) findViewById(R.id.tv_logistics);
        tv_after_sales = (TextView) findViewById(R.id.tv_after_sales);
        tv_refunds = (TextView) findViewById(R.id.tv_refunds);

        LinearLayout close_btn = (LinearLayout) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSaveNew();
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
            }, "http://mall." + (AppConfig.CHOOSE.equals("CN") ? "gigawon.cn" : "gigawon.co.kr") + ":8800/api/AppSM/requestStoreImageUpdate", j1.toString());
//			}, C.BASE_RS_MEMBER_URL + C.REQUEST_NICK_NAME_CHANGE, j1.toString());

        } catch (Exception e) {

            L.e(e);

            finish();

        }
    }

    private void requestSaveNew() {
        HashMap<String, String> params = new HashMap<>();
        JSONObject allJson = new JSONObject();
//        params.put("lang_type", AppConfig.LANG_TYPE);
//        params.put("token", S.getShare(StoreManageActivity.this, C.KEY_JSON_TOKEN, ""));
        params.put("custom_code", S.getShare(StoreManageActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
//                j1.put("store_image_url", imageDownloadUrl);
        params.put("custom_name", tv_name.getText().toString());
        params.put("telephon", tv_phone.getText().toString());
        params.put("zip_code", zipCode);
        params.put("kor_addr", addr);
        params.put("kor_addr_detail", addrDetail);

        params.put("company_num", tv_zhizhao_no.getText().toString());
        params.put("corp_no", tv_faren_no.getText().toString());
        params.put("comp_type", tv_type1.getText().toString());
        params.put("comp_class", tv_type2.getText().toString());
        params.put("FAX_NUM", tv_czh_phone.getText().toString());
        params.put("regi_gubun", regiGubun);
        params.put("maker_code", makerCode);
        params.put("supplier_code", supplierCode);
        params.put("saup_gubun", key1);
        params.put("tax_gubun", key2);
        params.put("contract_date", contract_date);
        params.put("contract_type", key3);
        params.put("ceo_name", tv_people.getText().toString());
        params.put("ceo_hp_no1", tv_mobile_phone.getText().toString());
        params.put("ceo_hp_no2", ceoNo2);
        params.put("ceo_hp_no3", ceoNo3);
        params.put("jungsan_holder", holder);
        params.put("jungsan_bank", bank);
        params.put("jungsan_bank_code", bankCard);
        params.put("company_homepage", tv_url.getText().toString());
        params.put("from_site", from_site);
        params.put("from_site_code", from_site_code);
        params.put("md_id", md_id);
        params.put("md_commission_rate", md_rate);
        try {
            L.d(allJson.put("SSSS",params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpHelper helper = new OkHttpHelper(StoreManageActivity.this);
        helper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                try {
                    L.d(data.toString());
                    // t(getString(R.string.complete));
                } catch (Exception e) {
                    L.e(e);
                }
                requestSaveNew2();
//                setResult(RESULT_OK);

            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub
            }
        }, Constant.XSGR_TEST_URL + "merchantinfo/updateInfo", params);
    }
    private void requestSaveNew2() {
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("token", S.getShare(StoreManageActivity.this, C.KEY_JSON_TOKEN, ""));
        params.put("custom_code", S.getShare(StoreManageActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
//                j1.put("store_image_url", imageDownloadUrl);
        params.put("return_as_tel", tv_after_sales.getText().toString());
        params.put("return_addr1", tv_refunds.getText().toString());
        params.put("return_post_no", zipCode2);

        OkHttpHelper helper = new OkHttpHelper(StoreManageActivity.this);
        helper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
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
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub
                finish();
            }
        }, Constant.XSGR_TEST_URL + "merchantinfo/updateotherInfo", params);
    }

    public void initShopInfoData() {

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(StoreManageActivity.this, C.KEY_JSON_TOKEN, ""));
        param.put("custom_code", S.getShare(StoreManageActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
        param.put("page", "1");
        param.put("pageSize", "20");
        OkHttpHelper okHttpHelper = new OkHttpHelper(StoreManageActivity.this);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                L.d(responseDescription);
                bean = GsonUtils.changeGsonToBean(responseDescription, ShopMannageBean.class);

                tv_name.setText(bean.getData().getInfo().getCustom_name() + "");
                tv_phone.setText(bean.getData().getInfo().getTelephon() + "");
                zipCode = bean.getData().getInfo().getZip_code() + "";
                addr = bean.getData().getInfo().getKor_addr() + "";
                addrDetail = bean.getData().getInfo().getKor_addr_detail() + "";
                tv_add.setText(addr + " " + addrDetail);
                contract_date = bean.getData().getInfo().getContract_date();
//                  tv_logistics, tv_after_sales, tv_refunds
//                tv_type1.setText(bean.getData().getInfo().getLEVEL1_NAME() + "");
//                tv_type2.setText(bean.getData().getInfo().getLEVEL2_NAME() + "");
                tv_type1.setText(bean.getData().getInfo().getComp_type() + "");
                tv_type2.setText(bean.getData().getInfo().getComp_class() + "");
                tv_zhizhao_no.setText(bean.getData().getInfo().getCompany_num() + "");
                tv_czh_phone.setText(bean.getData().getInfo().getFAX_NUM() + "");
                tv_url.setText(bean.getData().getInfo().getCompany_homepage() + "");
                tv_faren_no.setText(bean.getData().getInfo().getCorp_no() + "");
                key1 = bean.getData().getInfo().getSaup_gubun() + "";
                if ("pers".equals(key1)) {
                    tv_jy_type.setText(R.string.normal_jy);
                } else if ("corp".equals(key1)) {
                    tv_jy_type.setText(R.string.faren_jy);
                } else if ("simp".equals(key1)) {
                    tv_jy_type.setText(R.string.small_jy);
                } else {
                    tv_jy_type.setText(bean.getData().getInfo().getSaup_gubun() + "");
                }
                key2 = bean.getData().getInfo().getTax_gubun() + "";
                if ("tax".equals(key2)) {
                    tv_is_shui.setText(R.string.yingshui_company);
                } else if ("notax".equals(key2)) {
                    tv_is_shui.setText(R.string.mianshui_company);
                } else if ("nosel".equals(key2)) {
                    tv_is_shui.setText(R.string.no_choose_company);
                } else {
                    tv_is_shui.setText(key2);
                }
                key3 = bean.getData().getInfo().getContract_type() + "";
                if ("contract_progress".equals(key3)) {
                    tv_ht_state.setText(R.string.on_going);
                } else if ("contract_end".equals(key3)) {
                    tv_ht_state.setText(R.string.has_ended);
                } else {
                    tv_ht_state.setText(bean.getData().getInfo().getContract_type() + "");
                }


                tv_people.setText(bean.getData().getInfo().getCeo_name() + "");
                tv_mobile_phone.setText(bean.getData().getInfo().getCeo_hp_no1() + "");
                regiGubun = bean.getData().getInfo().getRegi_gubun();
                bankCard = bean.getData().getInfo().getJungsan_bank_code() + "";
                holder = bean.getData().getInfo().getJungsan_holder() + "";
                bank = bean.getData().getInfo().getJungsan_bank() + "";
                String bankCard2 = "";
                if (!TextUtils.isEmpty(bankCard)) {
                    if (!CheckBankCard.checkBankCard(bankCard)) {
                        Toast.makeText(StoreManageActivity.this, R.string.cardno_promit, Toast.LENGTH_SHORT).show();
                    } else {
                        String endNo = bankCard.substring(12);
                        bankCard2 = ("****" + endNo);
                    }
                }
                tv_settlement.setText(holder + " " + bank + " " + bankCard2);
                from_site = bean.getData().getInfo().getFrom_site();
                from_site_code = bean.getData().getInfo().getFrom_site_code();
                md_id = bean.getData().getInfo().getMd_id();
                md_rate = bean.getData().getInfo().getMd_commission_rate();
                ceoNo2 = bean.getData().getInfo().getCeo_hp_no2();
                ceoNo3 = bean.getData().getInfo().getCeo_hp_no3();
                makerCode = bean.getData().getInfo().getMaker_code();
                supplierCode = bean.getData().getInfo().getSupplier_code();
                initOtherInfoData();
//                if (bean.getShop_thumnail_image() != null && !bean.getShop_thumnail_image().isEmpty()) {
//                    ImageUtil.drawImageFromUri(bean.getShop_thumnail_image(), wImageView);
//                    imageDownloadUrl = bean.getShop_thumnail_image();
//                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XSGR_TEST_URL + "merchantinfo/queryInfo", param);

    }

    private void initOtherInfoData() {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(StoreManageActivity.this, C.KEY_JSON_TOKEN, ""));
        param.put("custom_code", S.getShare(StoreManageActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));

        param.put("page", "1");
        param.put("pageSize", "20");
        OkHttpHelper okHttpHelper = new OkHttpHelper(StoreManageActivity.this);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                OtherInfoNew bean = GsonUtils.changeGsonToBean(responseDescription, OtherInfoNew.class);
                if (bean.getData().getOtherInfo() != null) {
                    addrDetail2 = bean.getData().getOtherInfo().getReturn_addr1();
                    tv_refunds.setText(Util.formatStr(addrDetail2));
                    tv_after_sales.setText(Util.formatStr(bean.getData().getOtherInfo().getReturn_as_tel()));
                    zipCode2 = bean.getData().getOtherInfo().getReturn_post_no() + "";

                }


            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XSGR_TEST_URL + "merchantinfo/otherInfo", param);
    }

    private void initExpressInfoData() {
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
                ExpressList bean = GsonUtils.changeGsonToBean(responseDescription, ExpressList.class);
                String name, code;
                if (list.size() > 0) {
                    list.clear();
                    popAdapter.notifyDataSetChanged();
                }
                if (bean.getData().size() > 0) {
                    for (int i = 0; i < bean.getData().size(); i++) {
                        name = bean.getData().get(i).getCDNAME();
                        code = bean.getData().get(i).getCDCODE();
                        ListPopBean popBean = new ListPopBean();
                        popBean.setName(name);
                        popBean.setCode(code);
                        list.add(popBean);
                    }
                    popup();
                }


            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XSGR_TEST_URL + "merchantinfo/QueryExpresslist", param);
    }

    private void initTypeInfoData1() {
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
                Cate1Type bean = GsonUtils.changeGsonToBean(responseDescription, Cate1Type.class);
                String name, code;
                if (list.size() > 0) {
                    list.clear();
                    popAdapter.notifyDataSetChanged();
                }
                if (bean.getData().getCate1Depth().size() > 0) {
                    for (int i = 0; i < bean.getData().getCate1Depth().size(); i++) {
                        name = bean.getData().getCate1Depth().get(i).getLEVEL_NAME();
                        code = bean.getData().getCate1Depth().get(i).getLEVEL1();
                        ListPopBean popBean = new ListPopBean();
                        popBean.setName(name);
                        popBean.setCode(code);
                        list.add(popBean);
                    }
                    popup();
                }


            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XSGR_TEST_URL + "merchantinfo/requestCate1DepthList", param);
    }

    private void initTypeInfoData2() {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("LEVEL1", code1);
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
                Cate1Type bean = GsonUtils.changeGsonToBean(responseDescription, Cate1Type.class);
                String name, code;
                if (list.size() > 0) {
                    list.clear();
                    popAdapter.notifyDataSetChanged();
                }
                if (bean.getData().getCate1Depth().size() > 0) {
                    for (int i = 0; i < bean.getData().getCate1Depth().size(); i++) {
                        name = bean.getData().getCate1Depth().get(i).getLEVEL_NAME();
                        code = bean.getData().getCate1Depth().get(i).getLEVEL1();
                        ListPopBean popBean = new ListPopBean();
                        popBean.setName(name);
                        popBean.setCode(code);
                        list.add(popBean);
                    }
                    popup();
                }


            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                // TODO Auto-generated method stub

            }
        }, Constant.XSGR_TEST_URL + "merchantinfo/requestCate2DepthList", param);
    }
//    public void initShopInfoData() {
//
//        HashMap<String, String> param = new HashMap<String, String>();
//        param.put("lang_type", AppConfig.LANG_TYPE);
//        param.put("token", S.getShare(StoreManageActivity.this, C.KEY_JSON_TOKEN, ""));
//        param.put("custom_code", S.getShare(StoreManageActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
//        OkHttpHelper okHttpHelper = new OkHttpHelper(StoreManageActivity.this);
//        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {
//
//            @Override
//            public void onNetworkError(Request request, IOException e) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
//
//                bean = GsonUtils.changeGsonToBean(responseDescription, MyShopInfoBean.class);
//
//                tv_name.setText(bean.getCustom_name() + "");
//                tv_phone.setText(bean.getMobilepho() + "");
//                zipCode = bean.getZip_code() + "";
//                addr = bean.getKor_addr() + "";
//                addrDetail = bean.getKor_addr_detail() + "";
//                tv_add.setText(addr + " " + addrDetail);
//
////            Glide.with(XsMyShopActivity.this).load(bean.getShop_thumnail_image()).into(img_myshop);
//                if (bean.getShop_thumnail_image() != null && !bean.getShop_thumnail_image().isEmpty()) {
//                    ImageUtil.drawImageFromUri(bean.getShop_thumnail_image(), wImageView);
//                    imageDownloadUrl = bean.getShop_thumnail_image();
//                }
//            }
//
//            @Override
//            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
//                // TODO Auto-generated method stub
//
//            }
//        }, Constant.XS_BASE_URL + "AppSM/requestSaleOrderAmount", param);
//
//    }

    public void showBringPhotoView() {

        try {

            uploadTime = "" + System.currentTimeMillis();
            bringPhothLayout.setVisibility(View.VISIBLE);
            bringPhotoView.setVisibility(View.VISIBLE);

        } catch (Exception e) {

            L.e(e);

        }

    }

    public void hideBringPhotoView() {

        try {
            bringPhothLayout.setVisibility(View.GONE);
            bringPhotoView.setVisibility(View.GONE);

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
                    tv_add.setText(addr + addrDetail);
                    break;
                case 2000:
                    zipCode2 = data.getStringExtra("textNo");
                    addr2 = data.getStringExtra("textLocation");
                    addrDetail2 = data.getStringExtra("detailLocation");
                    tv_refunds.setText(addr2 + addrDetail2);
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
                    return FileUtil.upload("http://portal." + (AppConfig.CHOOSE.equals("CN") ? "gigawon.cn" : "gigawon.co.kr") + ":8488/Common/FileUploader.ashx", filePath, null, "file");

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
                //http://portal."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8488/MediaUploader/wsProfile/
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

    private void showSelectPopWin1() {
        //设置contentView
        uploadTime = "" + System.currentTimeMillis();
        View contentView = LayoutInflater.from(StoreManageActivity.this).inflate(R.layout.popup_layout01, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置各个控件的点击响应
        RelativeLayout tv1 = (RelativeLayout) contentView.findViewById(R.id.normal_person);
        RelativeLayout tv2 = (RelativeLayout) contentView.findViewById(R.id.faren_person);
        RelativeLayout tv3 = (RelativeLayout) contentView.findViewById(R.id.small_person);
        RelativeLayout tv4 = (RelativeLayout) contentView.findViewById(R.id.cancel_pop);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_jy_type.setText(R.string.normal_jy);
                key1 = "pers";
                mPopWindow.dismiss();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_jy_type.setText(R.string.faren_jy);
                key1 = "corp";
                mPopWindow.dismiss();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_jy_type.setText(R.string.small_jy);
                key1 = "simp";
                mPopWindow.dismiss();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(StoreManageActivity.this).inflate(R.layout.activity_xs_my_info, null);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setAnimationStyle(R.style.take_photo_anim);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    private void showSelectPopWin2() {
        //设置contentView
        uploadTime = "" + System.currentTimeMillis();
        View contentView = LayoutInflater.from(StoreManageActivity.this).inflate(R.layout.popup_layout02, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置各个控件的点击响应
        RelativeLayout tv1 = (RelativeLayout) contentView.findViewById(R.id.have_company);
        RelativeLayout tv2 = (RelativeLayout) contentView.findViewById(R.id.no_company);
        RelativeLayout tv3 = (RelativeLayout) contentView.findViewById(R.id.no_choose);
        RelativeLayout tv4 = (RelativeLayout) contentView.findViewById(R.id.cancel_pop);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_is_shui.setText(R.string.yingshui_company);
                key2 = "tax";
                mPopWindow.dismiss();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_is_shui.setText(R.string.mianshui_company);
                key2 = "notax";
                mPopWindow.dismiss();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_is_shui.setText(R.string.no_choose_company);
                key2 = "nosel";
                mPopWindow.dismiss();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(StoreManageActivity.this).inflate(R.layout.activity_xs_my_info, null);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setAnimationStyle(R.style.take_photo_anim);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    private void showSelectPopWin3() {
        //设置contentView
        uploadTime = "" + System.currentTimeMillis();
        View contentView = LayoutInflater.from(StoreManageActivity.this).inflate(R.layout.popup_layout03, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置各个控件的点击响应
        RelativeLayout tv1 = (RelativeLayout) contentView.findViewById(R.id.ongoing);
        RelativeLayout tv2 = (RelativeLayout) contentView.findViewById(R.id.has_ended);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ht_state.setText(R.string.on_going);
                key3 = "contract_progress";
                mPopWindow.dismiss();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ht_state.setText(R.string.has_ended);
                key3 = "contract_end";
                mPopWindow.dismiss();
            }
        });

        //显示PopupWindow
        View rootview = LayoutInflater.from(StoreManageActivity.this).inflate(R.layout.activity_xs_my_info, null);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setAnimationStyle(R.style.take_photo_anim);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        requestSaveNew();
    }

    public void popup() {
        if (popupWindow == null) {
            initPopup();
        }
        if (!mIsShowing) {
            View rootview = LayoutInflater.from(StoreManageActivity.this).inflate(R.layout.activity_xs_my_info, null);
            popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
            mIsShowing = true;
        }
    }

    private void initPopup() {
        View emptyView = LayoutInflater.from(this).inflate(R.layout.pop_layout_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        View pop = View.inflate(this, R.layout.mypop_more3, null);
        popupWindow = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                dismiss();
            }
        });
        popupWindow.setAnimationStyle(R.style.take_photo_anim);

        RecyclerView popRecycler = (RecyclerView) pop.findViewById(R.id.poprecycler_view);
        popRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        popAdapter = new MyPopupEditAdapter(R.layout.mypop_more3_item, list);
        popAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (index == 1) {
                    tv_type1.setText(Util.formatStr(list.get(position).getName()));
                    code1 = list.get(position).getCode();
                    popupWindow.dismiss();
                } else if (index == 2) {
                    tv_type2.setText(Util.formatStr(list.get(position).getName()));
                    code2 = list.get(position).getCode();
                    popupWindow.dismiss();
                } else {
                    tv_logistics.setText(Util.formatStr(list.get(position).getName()));
                    code3 = list.get(position).getCode();
                    popupWindow.dismiss();
                }
            }
        });
        popAdapter.bindToRecyclerView(popRecycler);
        popAdapter.setEmptyView(emptyView);
        mIsShowing = false;
    }

    public void dismiss() {
        if (popupWindow != null && mIsShowing) {
            popupWindow.dismiss();
            mIsShowing = false;
        }
    }

    class MyPopupEditAdapter extends BaseQuickAdapter<ListPopBean, BaseViewHolder> {

        public MyPopupEditAdapter(int layoutResId, @Nullable List<ListPopBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ListPopBean item) {
            helper.setText(R.id.name_txt, item.getName());
        }
    }
}
