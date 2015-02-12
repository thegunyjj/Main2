package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.HttpContext;

import java.util.List;

/**
 * Created by roya on 14/11/18.
 */
public class FinishedClassGeter {

    Context context;
    Gson gson;

    public class Result {
        public String errorCode;
        public String errorMsg;
        public List<Data> data;
    }

    public class Data{
        public boolean canCom;
        public String id;
        public String begin_time;
        public String pay;
        public String textbook;
        public int comment;
        public Teacher teacher;
    }

    public class Teacher{
        public String tid;
        public String nickname;
        public String pic;
    }

    public interface OnloadedListener{
        void onSuccess(List<Data> data);
        void onFailure(int errorCode);
    }
    public FinishedClassGeter(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void getFinishedClass(String sid, int page, final OnloadedListener listener){
        RequestParams params = new RequestParams();
        params.put("sid",sid);
        params.put("p",page);


        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"getFinishedClass",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json",json);
                Result result = gson.fromJson(json, Result.class);
                if (result.errorCode.equals("0")) {
                    listener.onSuccess(result.data);
                }else {
                    listener.onFailure(1);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0);
            }
        });
    }

    public void getFinishedClass(String sid, String date_yyyyMMdd, final OnloadedListener listener){

        RequestParams params = new RequestParams();
        params.put("sid",sid);
        params.put("date",date_yyyyMMdd);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"getFinishedClass",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("c",json);
                Result result = gson.fromJson(json, Result.class);
                if (result.errorCode.equals("0")) {
                    listener.onSuccess(result.data);
                }else {
                    listener.onFailure(1);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0);
            }
        });
    }

}
