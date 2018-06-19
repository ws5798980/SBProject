package com.rs.mobile.wportal.activity.NaverMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rs.mobile.common.S;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.network.HttpConnection;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Activity_AddressSerche extends Activity {

    public ImageView iv_magnifier;
    public TextView tv_serche_text;
    public ListView lv_addr_list;

    private static final int  LOGIN_TASK = 1;

    private static final HashMap<String, String> postValue = new HashMap<>();

    private static String addr = "";
    private static String ret_value = "";

    public static Gson_Parent gp_json;

    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
            {

                Gson gson = new Gson();
                gp_json = gson.fromJson(msg.obj.toString(), Gson_Parent.class);
                if(gp_json.result != null) {
                    List<String> listContents = new ArrayList<String>(gp_json.result.total);
                    for (int i = 0; i < gp_json.result.total; i++) {
                        String addr_prt = gp_json.result.items.get(i).addrdetail.sido + " " +
                                gp_json.result.items.get(i).addrdetail.sigugun + " " +
                                gp_json.result.items.get(i).addrdetail.dongmyun + " " +
                                gp_json.result.items.get(i).addrdetail.ri + " " +
                                gp_json.result.items.get(i).addrdetail.rest;
                        listContents.add(addr_prt);
                        lv_addr_list.setAdapter(new ArrayAdapter<String>(Activity_AddressSerche.this, android.R.layout.simple_expandable_list_item_1, listContents));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "주소를 찾을 수 없습니다", Toast.LENGTH_LONG).show();
                    S.setShare(getApplicationContext(), "address_naver", "주소를 찾을 수 없습니다");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__address_serche);

        tv_serche_text = (TextView)findViewById(R.id.serche_text);
        lv_addr_list = (ListView)findViewById(R.id.addr_list);

        iv_magnifier = (ImageView)findViewById(R.id.res_magnifier);
        try
        {
            addr = URLEncoder.encode(tv_serche_text.toString(), "UTF-8");
        } catch (Exception e) {

        }

        lv_addr_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();

                extra.putString("pointx", gp_json.result.items.get(position).point.x);
                extra.putString("pointy", gp_json.result.items.get(position).point.y);
                extra.putString("address", gp_json.result.items.get(position).addrdetail.sido + " " + gp_json.result.items.get(position).addrdetail.sigugun + " " + gp_json.result.items.get(position).addrdetail.dongmyun);
                String addr_txt = gp_json.result.items.get(position).addrdetail.sido + " " + gp_json.result.items.get(position).addrdetail.sigugun + " " + gp_json.result.items.get(position).addrdetail.dongmyun;
                S.setShare(getApplicationContext(), "address_naver", addr_txt);
                S.setShare(getApplicationContext(), "pointX", gp_json.result.items.get(position).point.x);
                S.setShare(getApplicationContext(), "pointY", gp_json.result.items.get(position).point.y);
                intent.putExtra("result", addr_txt);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private HttpConnection httpConn = HttpConnection.getInstance();

    public void onClick(View view) {
        /*if(findViewById(R.id.res_magnifier).callOnClick())
        {*/
        new Thread() {
            public void run() {
                //httpConn.requestGet("https://openapi.naver.com/v1/map/geocode?query=" + addr, callback);
                ret_value = httpConn.NaverAPIAddressToLatlng("https://openapi.naver.com/v1/map/geocode", tv_serche_text.getText().toString(),"UTF-8,","1l1JIC1pT26uwTBM565k","6NQScKKyA7");
                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = ret_value;
                mHandler.sendMessage(msg);


                //Log.d("gigakfme", gp_json.results.items.get(0).address.toString());
                    /*for(Gson_Child gc_json : gp_json.result)
                    {
                        Log.d("gigakfme", gc_json.userquery);
                        Log.d("gigakfme", gc_json.total);
                        Log.d("gigakfme", gc_json.items.toString());
                    }*/
                //ret_value = httpConn.PostORGToAddress(KfmeURLINFO.API_POST_ORG,tv_serche_text.getText().toString(),"UTF-8", "4e161da3b4b3c7ff81520154121695");
                    /*postValue.put("regkey", "4e161da3b4b3c7ff81520154121695");
                    postValue.put("target", "postNew");
                    postValue.put("query", tv_serche_text.getText().toString());
                    httpConn.requestPost(KfmeURLINFO.API_POST_ORG,postValue, callback );*/
            }
        }.start();
        // }
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

            Log.d("okhttp","call error");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("okhttp", "response : " + body);
        }
    };

    public class Model1 {
        String name;
        String age;
    }

    public class Gson_Parent {
        Gson_Child result;
    }

    public class Gson_Child {
        int total = 0;
        String userquery = "";
        ArrayList<Gson_Item> items;
    }

    public class Gson_Item {
        String address;
        Gson_addrdetail addrdetail;
        String isRoadAddress;
        Gson_point point;
    }

    public class Gson_addrdetail {
        String country;
        String sido;
        String sigugun;
        String dongmyun;
        String ri;
        String rest;
    }

    public class Gson_point {
        String x;
        String y;
    }

}
