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

import java.util.List;

/**
 * Created by roya on 15/1/4.
 */
public class SaveBind {

    Context context;
    Gson gson;

    class Result {
        String errorCode;
        String errorMsg;
    }

    public interface OnSavedListener {
        public void onSuccess();
        public void onFailure(int errorCode, String errorMsg);
    }

    public SaveBind(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void save(String teacherId, List<String> justTimes , final OnSavedListener listener){
        RequestParams params = new RequestParams();
        params.put("tid",teacherId);
        params.put("just_time",justTimes);
        Log.e(getClass().getName(),params.toString());
        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);
        client.post(context, context.getString(R.string.tool_api_link)+"saveBind",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                Log.e(getClass().getName(),json);
                try {
                    Result result = gson.fromJson(json,Result.class);
                    if (result.errorCode.equals("0")){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(1,result.errorMsg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onFailure(0,"网络连接失败");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0,"网络连接失败");
            }
        });
    }
}
