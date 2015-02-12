package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.abc360.tool.userdeta.SearchResultManager;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by roya on 14/11/13.
 */
public class SavaTextbookSelection {

    Context context;
    Gson gson;

    class Result{
        String errorCode;
        String errorMsg;
    }

    public interface OnfinishListner{
        void onSuccess();
        void onFailure(int errorCode, String msg);
    }

    public SavaTextbookSelection( Context context){
        this.context = context;
        gson = new Gson();
    }

    public void doSaveTextbook(RequestParams params, final OnfinishListner listner){

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"saveTextbook",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("onSuccess",json);
                try {
                    Result result = gson.fromJson(json, Result.class);
                    if (result.errorCode.equals("0")){
                        listner.onSuccess();
                    }else {
                        listner.onFailure(1, result.errorMsg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listner.onFailure(0,"网络连接失败");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listner.onFailure(0,"网络连接失败");
            }
        });
    }
}
