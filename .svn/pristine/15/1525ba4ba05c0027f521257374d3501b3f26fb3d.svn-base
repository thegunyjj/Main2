package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.abc360.tool.userdeta.UserIDManager;
import com.abc360.tool.userdeta.UserProfileManger;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by roya on 14/11/13.
 */
public class ClassCanceler {

    Context context;
    Gson gson;

    public class Result{
        public String errorCode;
        public String errorMsg;
    }


    public interface OnCanceledListner{
        void onSuccess();
        void onFailure(int errorCode);
    }

    public ClassCanceler(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void cancerClass(String id, final OnCanceledListner listner){
        RequestParams params = new RequestParams();
        params.put("sid",new UserProfileManger(context).getId());
        params.put("md5",new UserIDManager(context).getPassword());
        params.put("cid",id);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"cancelClass",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("cancelClass",json);
                Result result = gson.fromJson(json, Result.class);
                if (result.errorCode.equals("0")){
                    listner.onSuccess();
                }else{
                    listner.onFailure(1);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listner.onFailure(0);
            }
        });
    }
}
