package com.rs.mobile.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.rs.mobile.common.R;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class kfme_post_search extends Activity {
    private Button btn_postsearch;
    private EditText et_searchtxt;
    private ListView lv_listView;
    private ArrayList<String> al_list;
    private ArrayAdapter<String> adapter;

    private Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kfme_post_search);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());

        et_searchtxt = (EditText)findViewById(R.id.editText);
        lv_listView = (ListView)findViewById(R.id.postAddList);
        lv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object vo = (Object)a.getAdapter().getItem(position);
                //Toast.makeText(getApplicationContext(), vo.toString(), Toast.LENGTH_LONG).show();
                extra = new Bundle();
                Intent intent = new Intent(); //초기화 깜빡 했다간 NullPointerException이라는 짜증나는 놈이랑 대면하게 된다.
                extra.putString("postaddr", vo.toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        al_list = new ArrayList<String>();

        btn_postsearch = (Button)findViewById(R.id.postBtn);
        btn_postsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://biz.epost.go.kr/KpostPortal/openapi?regkey=test&target=postNew&query=정보화길&countPerPage=20&currentPage=1
                try
                {
                    //java.net.URLEncoder.encode(new String(param.getByte("euc-kr")),"euc-kr")
                    String posttxt = URLEncoder.encode(et_searchtxt.getText().toString(),"EUC-KR");

                    et_searchtxt.getText().toString();

                    String Addr = "http://biz.epost.go.kr/KpostPortal/openapi?regkey=4e161da3b4b3c7ff81520154121695&target=postNew&query=" + posttxt;
                    URL url = new URL(Addr);
                    Log.d("postdata", "addr_search : " + Addr);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("GET");

                    int responseCode = con.getResponseCode();
                    /*BufferedReader br;
                    if(responseCode == 200) {
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    } else {
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }*/
                    //XML 자료 가져오기
                    SAXBuilder builder = new SAXBuilder();
                    Document doc= builder.build(con.getInputStream());

                    //itemlist 하위에 우편번호와 주소값을 가지고 있다.
                    Element itemlist = doc.getRootElement().getChild("itemlist");
                    List list = itemlist.getChildren();

                    for(int i=0; i<list.size();i++){
                        Element item = (Element)list.get(i);
                        String address = item.getChildText("address");
                        String postcd = item.getChildText("postcd");
                        al_list.add(postcd + " | " + address);
                        //address와 postcd 변수를 이용하여 자신에게 알맞는 형태로 사용하기
                        //this.cbAddr.addItem(postcd+" | "+address);
                    }
                    adapter = new ArrayAdapter<String>(kfme_post_search.this, android.R.layout.simple_list_item_1, al_list);
                    lv_listView.setAdapter(adapter);
                    lv_listView.setDivider(new ColorDrawable(Color.BLACK));

                    //경계선의 굵기를 2px
                    lv_listView.setDividerHeight(2);

                    //System.out.println(response.toString());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }
}
