package com.rs.mobile.wportal.network;

/**
 * Created by jackkim on 2018. 3. 6..
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HttpConnection {

    private OkHttpClient client;

    private static HttpConnection instance = new HttpConnection();

    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection() { this.client = new OkHttpClient();}

    public void requestPost(String Url, HashMap<String, String> params, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(Url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void requestGet(String Url_params, Callback callback) {
        Request request = new Request.Builder()
                .addHeader("X-Naver-Client_Id", "DDs0cS8ZfU_oVMqjWJd6")
                .addHeader("X-Naver-Client-Secret", "nfGhGby9T4")
                .url(Url_params)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public String NaverAPIAddressToLatlng(String urls, String txtAddr, String encoding, String clientId, String clientSecret)
    {
        try {
            String addr = "?query=" + URLEncoder.encode(txtAddr, "UTF-8");
            //String addr = "?query=" + txtAddr;
            URL url = new URL(urls + addr);
            Log.d("gigakfme","addr_search : " + url.toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception e) {
            System.out.println(e);
            return "-1";
        }
    }

    public String NaverAPILatingToAddress(String urls, String latitude, String longitude, String encoding, String clientId, String clientSecret)
    {
        try {
            String addr = "?query=" + latitude + "," + longitude;
            URL url = new URL(urls + addr);
            Log.d("gigakfme","addr_search : " + url.toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception e) {
            System.out.println(e);
            return "-1";
        }
    }

    public String PostORGToAddress(String urls, String txtAddr, String encoding, String authkey)
    {
        try {
            String addr = "regkey=" + authkey + "&target=postNew&query=" + URLEncoder.encode(txtAddr, encoding) + "&countPerPage=20&currentPage=1";
            URL url = new URL(urls + addr);
            Log.d("gigakfme","post_addr_search : " + url.toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception e) {
            System.out.println(e);
            return "-1";
        }
    }
}
