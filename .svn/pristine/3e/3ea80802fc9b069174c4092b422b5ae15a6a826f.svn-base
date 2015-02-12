package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by roya on 14/12/19.
 */
public class LeaveCancel {

    Context context;
    Gson gson;

    class Result{
        String errorCode;
        String errorMsg;
    }

    public interface FinishedListener{
        public void onSuccess();
        public void onFaiure();
    }

    public LeaveCancel(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void cancel(String id , final FinishedListener listener){
        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);
        RequestParams params = new RequestParams();
        params.put("id",id);

        client.post(context, context.getString(R.string.tool_api_link)+"cancelLeave", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json",json);
                try {
                    Result result = gson.fromJson(json,Result.class);
                    if (result.errorCode.equals("0")){
                        listener.onSuccess();
                    }else {
                        listener.onFaiure();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onFaiure();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFaiure();
            }
        });
    }


}
