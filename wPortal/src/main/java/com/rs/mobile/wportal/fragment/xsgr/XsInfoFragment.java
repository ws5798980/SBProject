package com.rs.mobile.wportal.fragment.xsgr;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.activity.xsgr.XsStoreDetailActivity;
import com.rs.mobile.wportal.entity.StoreDetailEntity;
import com.rs.mobile.wportal.entity.StoreItemDetailEntity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;


public class XsInfoFragment extends Fragment{
    private View rootView;
    private TextView tvWorkingHour, tvShopInfo, tvSaleCnt, tvFavCnt, tvTelephon, tvAddr, tvCompanyNum, tvOnlyTakeout, tvShopHoliday;

    private StoreDetailEntity mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_xs_info, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initView(){
        tvWorkingHour = (TextView) rootView.findViewById(R.id.tv_working_hour);
        tvShopInfo = (TextView) rootView.findViewById(R.id.tv_shop_info);
        tvSaleCnt = (TextView) rootView.findViewById(R.id.tv_sale_cnt);
        tvFavCnt = (TextView) rootView.findViewById(R.id.tv_fav_cnt);
        tvTelephon = (TextView) rootView.findViewById(R.id.tv_telephon);
        tvAddr = (TextView) rootView.findViewById(R.id.tv_addr);
        tvCompanyNum = (TextView) rootView.findViewById(R.id.tv_company_num);
        tvOnlyTakeout = (TextView) rootView.findViewById(R.id.tv_only_takeout);
        tvShopHoliday = (TextView) rootView.findViewById(R.id.tv_shop_holiday);
    }

    private void initData(){
        requestStoreDetail(S.get(getActivity(), C.KEY_JSON_CUSTOM_CODE), ((XsStoreDetailActivity)getActivity()).mSaleCustomCode, ""+AppConfig.latitude, ""+AppConfig.longitude);
    }

    private void requestStoreDetail(String customCode, String saleCustom, String latitude, String longitude){
        HashMap<String, String> params = new HashMap<>();
        params.put("lang_type", AppConfig.LANG_TYPE);
        params.put("custom_code", customCode);
        params.put("sale_custom_code", saleCustom);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        OkHttpHelper okHttpHelper = new OkHttpHelper(getActivity());
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {

                StoreDetailEntity entity = GsonUtils.changeGsonToBean(responseDescription, StoreDetailEntity.class);

                tvWorkingHour.setText("working_hour: " + entity.working_hour);
                tvShopInfo.setText("shop_info: " + entity.shop_info);
                tvSaleCnt.setText("sale_cnt: " + entity.sale_cnt);
                tvFavCnt.setText("fav_cnt: " + entity.fav_cnt);
                tvTelephon.setText("telephon: " + entity.telephon);
                tvAddr.setText("addr: " + entity.addr);
                tvCompanyNum.setText("company_num: " + entity.company_num);
                tvOnlyTakeout.setText("only_takeout: " + entity.only_takeout);
                tvShopHoliday.setText("shop_holiday: " + entity.shop_holiday);
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
            }

            @Override
            public void onNetworkError(Request request, IOException e) {
            }
        }, Constant.XS_BASE_URL+"StoreCate/requestStoreDetail", GsonUtils.createGsonString(params));
    }
}
