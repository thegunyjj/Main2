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
 * Created by roya on 14/11/21.
 */
public class SoftwaveChanger {

    Context context;
    Gson gson;

    class Result{
        String errorCode;
        String errorMsg;
    }

    public interface OnfinishedListener{
        void onSuccess();
        void onFailure(int errorCode);
    }

    public SoftwaveChanger(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void saveSoftwave(String sid, String classId, String useToolId, final OnfinishedListener listener){

        RequestParams params = new RequestParams();
        params.put("sid",sid);
        params.put("id",classId);
        params.put("use_tool",useToolId);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"saveUseTool",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("saveUseTool",json);
                Result result = gson.fromJson(json,Result.class);
                if (result.errorCode.equals("0")){
                    listener.onSuccess();
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
