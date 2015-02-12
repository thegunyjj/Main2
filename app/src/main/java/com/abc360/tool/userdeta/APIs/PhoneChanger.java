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

/**
 * Created by roya on 14/11/19.
 */
public class PhoneChanger {

    Context context;
    Gson gson;

    class Result{
        String errorCode;
        String errorMsg;
    }

    public interface OnfinishedListener{
        void onSuccess();
        void onFailure(int errorCode,String msg);
    }

    public PhoneChanger(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void sendCode(String sid, String phoneNumber, final OnfinishedListener listener){
        RequestParams params = new RequestParams();
        params.put("sid",sid);
        params.put("mobile",phoneNumber);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"getSmsCode",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                Result result = gson.fromJson(json,Result.class);
                if (result.errorCode.equals("0")){
                    listener.onSuccess();
                }else {
                    listener.onFailure(-1,result.errorMsg);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0,null);
            }
        });
    }

    public void changePhone(String sid, String phoneNumber, String code,final OnfinishedListener listener){
        RequestParams params = new RequestParams();
        params.put("sid",sid);
        params.put("mobile",phoneNumber);
        params.put("code",code);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(context,context.getString(R.string.tool_api_link)+"saveMobile",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                Result result = gson.fromJson(json,Result.class);
                if (result.errorCode.equals("0")){
                    listener.onSuccess();
                }else {
                    listener.onFailure(-1,result.errorMsg);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0,null);
            }
        });

    }

}
