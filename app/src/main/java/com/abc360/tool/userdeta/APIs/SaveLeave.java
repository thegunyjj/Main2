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
 * Created by roya on 14/12/22.
 */
public class SaveLeave {

    Context context;
    Gson gson;

    class Result{
        String errorCode;
        String errorMsg;
    }

    public SaveLeave(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public interface FinishedListener{
        public void onSuccess();
        public void onFailure(int errorCode, String errorMsg);
    }

    public void save(List<Integer> catalog, String from, int days , final FinishedListener listener){
        RequestParams params = new RequestParams();
        params.put("catalog",catalog);
        params.put("from",from);
        params.put("days",days);
        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);
        client.post(context,context.getString(R.string.tool_api_link)+"saveLeave", params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json",json);
                try {
                    Result result = gson.fromJson(json, Result.class);
                    if (result.errorCode.equals("0")){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(1,result.errorMsg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onFailure(0,"网络连接失败，请稍后再试");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }
}
