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
 * Created by roya on 14/11/26.
 */
public class GetuiIdSeter {
    Context context;
    Gson gson;

    class Result {
        String errorCode;
        String errorMsg;
    }

    public  GetuiIdSeter(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    public interface OnFinishedListener{
        void onSuccess();
        void onFailure();
    }

    public void setGetuiId(String sId, String getuiId, final OnFinishedListener listener){
        RequestParams params = new RequestParams();
        params.put("sid",sId);
        params.put("gtid",getuiId);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);


        client.post(context,context.getString(R.string.tool_api_link)+"updateGtid",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json",json);
                try {
                    Result result = gson.fromJson(json,Result.class);
                    if (result.errorCode.equals("0")){
                        if (listener != null) listener.onSuccess();
                    }
                }catch (Exception e){
                    if (listener != null) listener.onFailure();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (listener != null) listener.onFailure();
            }
        });
    }


}
