package com.abc360.tool.userdeta.APIs;

import android.content.Context;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by roya on 14/11/25.
 */
public class PasswordFinder {

    Context context;
    Gson gson;


    class Result{
        String errorCode;
        String errorMsg;
    }


    public interface OnFinishedListener{
        void onSuccess();
        void onFailure(int errorCode, String msg);
    }

    public PasswordFinder(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void getCode(String id, final OnFinishedListener listener){
        RequestParams params = new RequestParams();
        params.put("mobile",id);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"getPasswordSmsCode",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String json = new String(responseBody);
                    Result result = gson.fromJson(json,Result.class);
                    if (result.errorCode.equals("0")){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(1,result.errorMsg);
                    }
                }catch (Exception e){
                    listener.onFailure(0,"网络连接失败,请稍后再试");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0,"网络连接失败,请稍后再试");
            }
        });

    }


    public void setPasswold(String id, String code, String newPassword, final OnFinishedListener listener){
        RequestParams params = new RequestParams();
        params.put("mobile",id);
        params.put("code",code);
        params.put("newpassword",newPassword);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"resetPassword",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String json = new String(responseBody);
                    Result result = gson.fromJson(json,Result.class);
                    if (result.errorCode.equals("0")){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(1,result.errorMsg);
                    }
                }catch (Exception e){
                    listener.onFailure(0,"网络连接失败,请稍后再试");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0,"网络连接失败,请稍后再试");
            }
        });


    }


}
